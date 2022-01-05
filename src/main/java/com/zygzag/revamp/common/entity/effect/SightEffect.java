package com.zygzag.revamp.common.entity.effect;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Function;
import java.util.function.Predicate;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SightEffect extends MobEffect {
    public Predicate<BlockState> predicate;
    public Function<BlockState, Integer> selector;
    public SightEffect(MobEffectCategory category, int color, Predicate<BlockState> blockPredicate, Function<BlockState, Integer> colorSelector) {
        super(category, color);
        this.predicate = blockPredicate;
        this.selector = colorSelector;
    }

    public boolean test(BlockState state) {
        return predicate.test(state);
    }

    public int color(BlockState state) {
        return selector.apply(state);
    }
}
