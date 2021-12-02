package com.zygzag.revamp.item.enchant;

import com.zygzag.revamp.item.iridium.IridiumScepterItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class CooldownEnchantment extends Enchantment {
    public CooldownEnchantment(Rarity rarity) {
        super(rarity, EnchantmentCategory.create("scepter", (item) -> item instanceof IridiumScepterItem), new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinCost(int level) {
        return 1 + 15 * level;
    }

    @Override
    public int getMaxCost(int level) {
        return 1 + 20 * level;
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }
}
