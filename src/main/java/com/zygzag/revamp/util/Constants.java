package com.zygzag.revamp.util;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.OptionalDouble;
import java.util.UUID;
import java.util.function.Predicate;

public class Constants {

    public static final int JOLTED_FRAMETIME = 4;
    public static final int JOLTED_NUM_FRAMES = 4;

    public static int AMETHYST_HOE_COOLDOWN = 400;

    public static int AMETHYST_PICKAXE_COOLDOWN = 1800;

    public static int AMETHYST_SCEPTER_COOLDOWN = 400;
    public static int EMERALD_SCEPTER_COOLDOWN = 1000;
    public static int SKULL_SCEPTER_COOLDOWN = 400;
    public static int WITHER_SKULL_SCEPTER_COOLDOWN = 1000;

    public static int WITHER_SKULL_SWORD_COOLDOWN = 1000;

    public static int EMERALD_SHOVEL_COOLDOWN = 20;
    public static int SKULL_SHOVEL_COOLDOWN = 100;
    public static int WITHER_SKULL_SHOVEL_COOLDOWN = 400;

    public static int SIGHT_EFFECT_COLOR = 0x662382;
    public static int REACH_EFFECT_COLOR = 0xe34b34;

    public static int UNWATERED_SOIL_HIGHLIGHT_COLOR = 0xffffff;
    public static int CROP_COLOR = 0xf5a716;

    public static int COPPER_ORE_COLOR = 0xe77c56;
    public static int COAL_ORE_COLOR = 0x323232;
    public static int IRON_ORE_COLOR = 0xd8d8d8;
    public static int GOLD_ORE_COLOR = 0xfdf55f;
    public static int NETHERITE_ORE_COLOR = 0x654740;
    public static int DIAMOND_ORE_COLOR = 0x2be0b9;
    public static int QUARTZ_ORE_COLOR = 0xddd4c6;
    public static int LAPIS_ORE_COLOR = 0x345ec3;
    public static int REDSTONE_ORE_COLOR = 0xaa0f01;

    public static RenderType.CompositeRenderType TEST = RenderType.create("test", DefaultVertexFormat.POSITION_COLOR_NORMAL, VertexFormat.Mode.LINES, 256, RenderType.CompositeState.builder().setShaderState(RenderType.RENDERTYPE_LINES_SHADER).setLineState(new RenderStateShard.LineStateShard(OptionalDouble.empty())).setLayeringState(RenderType.VIEW_OFFSET_Z_LAYERING).setTransparencyState(RenderType.TRANSLUCENT_TRANSPARENCY).setOutputState(RenderType.OUTLINE_TARGET).setWriteMaskState(RenderType.COLOR_DEPTH_WRITE).setDepthTestState(RenderType.NO_DEPTH_TEST).setCullState(RenderType.NO_CULL).createCompositeState(true));

    public static final boolean RENDER_BEZIER_ARCS = true;
    public static final double ARC_RANDOMNESS = 1.5;
    public static final float CHARGE_DAMAGE_MULTIPLIER = 0.75f;
    public static final float SURGE_PROTECTOR_DAMAGE_MULTIPLIER = 0.25f;
    public static final float EPSILON = 1e-7f;
    public static final float CHARGE_DECAY_RATE = 0.0125f;
    public static final float ARC_RANGE = 6f;
    public static final float CHARGE_SPEED_MULTIPLIER = 0.0025f;
    public static final float CHARGE_ATTACK_SPEED_MULTIPLIER = 0.1f;
    public static final UUID SPEED_MODIFIER_JOLTED_UUID = UUID.fromString("360d6a40-5801-4f65-ba6a-350f6c77f32f");
    public static final UUID ATTACK_SPEED_MODIFIER_JOLTED_UUID = UUID.fromString("06d09ebe-745b-4a8c-af7b-6d720e1ef5f0");

    public static final Predicate<Entity> CHARGEABLE_PREDICATE = (entity) -> !(entity instanceof Player player) || !player.getAbilities().instabuild;
}
