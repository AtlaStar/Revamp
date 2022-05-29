package com.zygzag.revamp.common.charge;

import com.zygzag.revamp.common.Revamp;
import com.zygzag.revamp.common.networking.RevampPacketHandler;
import com.zygzag.revamp.common.networking.packet.ClientboundEntityChargeSyncPacket;
import com.zygzag.revamp.common.registry.Registry;
import com.zygzag.revamp.util.Constants;
import com.zygzag.revamp.util.GeneralUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import org.lwjgl.system.CallbackI;

import java.util.List;
import java.util.Random;

public class EntityChargeHandler {
    private int ticksSinceLastModified = 0;
    public static final DamageSource SHOCKED_DAMAGE_SOURCE = new DamageSource("shocked");
    private final Entity entity;
    private float charge;
    private float maxCharge;
    public boolean dirty = true;

    public EntityChargeHandler(Entity entity, float charge, float maxCharge) {
        this.entity = entity;
        this.charge = charge;
        this.maxCharge = maxCharge;
    }

    public Entity getEntity() {
        return entity;
    }

    public float getCharge() {
        return charge;
    }

    public float getMaxCharge() {
        return maxCharge;
    }

    public void setCharge(float c) {
        this.charge = c;
        ticksSinceLastModified = 0;
        markDirty();
    }

    public void setMaxCharge(float c) {
        this.maxCharge = c;
        markDirty();
    }

    public void markDirty() {
        dirty = true;
    }
    public void markClean() {
        dirty = false;
    }

    public void tick() {
        if (!entity.level.isClientSide) {
            ticksSinceLastModified++;
            if (dirty) {
                ClientboundEntityChargeSyncPacket packet = new ClientboundEntityChargeSyncPacket(entity.getUUID(), charge, maxCharge);
                if (entity instanceof ServerPlayer player)
                    RevampPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
                RevampPacketHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), packet);
                markClean();
            }
            if (Math.abs(charge) > maxCharge) {
                overload();
            }
            if (ticksSinceLastModified > 90) {
                if (Math.abs(charge) < Constants.CHARGE_DECAY_RATE) setCharge(0);
                else {
                    charge -= Math.signum(charge) * Constants.CHARGE_DECAY_RATE;
                    markDirty();
                }
            }
            if (Math.abs(charge) < Constants.EPSILON) setCharge(0);

            if (entity instanceof LivingEntity living) {
                AttributeInstance speedInstance = living.getAttribute(Attributes.MOVEMENT_SPEED);
                if (speedInstance != null) {
                    if (speedInstance.getModifier(Constants.SPEED_MODIFIER_JOLTED_UUID) != null) {
                        speedInstance.removeModifier(Constants.SPEED_MODIFIER_JOLTED_UUID);
                    }
                    speedInstance.addTransientModifier(new AttributeModifier(Constants.SPEED_MODIFIER_JOLTED_UUID, "Jolted speed mod", charge * Constants.CHARGE_SPEED_MULTIPLIER, AttributeModifier.Operation.ADDITION));
                }
                AttributeInstance attackSpeedInstance = living.getAttribute(Attributes.ATTACK_SPEED);
                if (attackSpeedInstance != null) {
                    if (attackSpeedInstance.getModifier(Constants.ATTACK_SPEED_MODIFIER_JOLTED_UUID) != null) {
                        attackSpeedInstance.removeModifier(Constants.ATTACK_SPEED_MODIFIER_JOLTED_UUID);
                    }
                    attackSpeedInstance.addTransientModifier(new AttributeModifier(Constants.ATTACK_SPEED_MODIFIER_JOLTED_UUID, "Jolted attack speed mod", charge * Constants.CHARGE_ATTACK_SPEED_MULTIPLIER, AttributeModifier.Operation.ADDITION));
                }
            }
        }
        if (Math.random() < Math.abs(charge) / 20) {
            Level world = entity.level;
            Random rng = world.getRandom();
            Vec3 pos = GeneralUtil.randomPointOnAABB(entity.getBoundingBox(), rng).add(entity.position());
            world.addParticle(GeneralUtil.particle(charge), pos.x, pos.y, pos.z, 0, 0, 0);
        }
    }

    public void overload() {
        ItemStack helmet = ItemStack.EMPTY;
        Level world = entity.level;
        if (entity instanceof LivingEntity living) {
            helmet = living.getItemBySlot(EquipmentSlot.HEAD);
        }
        int level = EnchantmentHelper.getItemEnchantmentLevel(Registry.EnchantmentRegistry.SURGE_PROTECTOR_ENCHANTMENT.get(), helmet);
        float amountToChange = Math.min(Math.abs(charge) - maxCharge, maxCharge);
        setCharge(charge - Math.signum(charge) * amountToChange);
        if (level > 0) {
            if (!world.isClientSide) {
                List<Entity> entities = world.getEntities(entity, entity.getBoundingBox().inflate(Constants.ARC_RANGE));
                Entity entityNullable = GeneralUtil.minByOrNull(entities, (e) -> e.distanceToSqr(entity));
                GeneralUtil.ifNonNull(entityNullable, (target) -> {
                    new Arc(entity.position().add(0, entity.getBbHeight(), 0), target.getBoundingBox().getCenter(), (int) Math.abs(amountToChange)).sendToClients();
                    GeneralUtil.ifCapability(target, Revamp.ENTITY_CHARGE_CAPABILITY, (handler) -> {
                        handler.setCharge(handler.getCharge() + amountToChange);
                    });
                });
            }
            entity.hurt(SHOCKED_DAMAGE_SOURCE, amountToChange * Constants.CHARGE_DAMAGE_MULTIPLIER * Constants.SURGE_PROTECTOR_DAMAGE_MULTIPLIER);
        } else {
            entity.hurt(SHOCKED_DAMAGE_SOURCE, amountToChange * Constants.CHARGE_DAMAGE_MULTIPLIER);
        }
    }
}
