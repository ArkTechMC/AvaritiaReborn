package com.iafenvoy.avaritia.mixin;

import com.google.gson.JsonElement;
import com.iafenvoy.avaritia.AvaritiaReborn;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.CustomValue;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {
    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V", at = @At("HEAD"))
    private void loadAndRemoveRecipe(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo ci) {
        for (ModContainer container : FabricLoader.getInstance().getAllMods()) {
            CustomValue cv = container.getMetadata().getCustomValue("avaritia-disable-recipe");
            if (cv == null) continue;
            if (cv.getType() != CustomValue.CvType.ARRAY)
                AvaritiaReborn.LOGGER.warn("Cannot load avaritia-disable-recipe in mod {}", container.getMetadata().getId());
            else
                for (CustomValue cv2 : cv.getAsArray())
                    if (cv2.getType() == CustomValue.CvType.STRING)
                        map.remove(new Identifier(cv2.getAsString()));
        }
    }
}
