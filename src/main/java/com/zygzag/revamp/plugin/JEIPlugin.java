package com.zygzag.revamp.plugin;
/*
import com.zygzag.revamp.Revamp;
import com.zygzag.revamp.item.recipe.ModRecipeType;
import com.zygzag.revamp.item.recipe.TransmutationRecipe;
import com.zygzag.revamp.registry.Registry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("unused")
@JeiPlugin
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault*/
public class JEIPlugin /*implements IModPlugin*/ { /*
    @Nullable
    public IRecipeCategory<TransmutationRecipe> transmutationCategory;

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Revamp.MODID + ".plugin.jei");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(transmutationCategory = new TransmutationCategory(registration.getJeiHelpers()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        if (Minecraft.getInstance().level != null) registration.addRecipes(Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(ModRecipeType.TRANSMUTATION), Revamp.loc("transmutation"));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(Registry.TRANSMUTATION_BOTTLE.get().getDefaultInstance(), Revamp.loc("transmutation"));
        registration.addRecipeCatalyst(Registry.SKULL_SOCKETED_IRIDIUM_PICKAXE.get().getDefaultInstance(), Revamp.loc("transmutation"));
    }*/
}
