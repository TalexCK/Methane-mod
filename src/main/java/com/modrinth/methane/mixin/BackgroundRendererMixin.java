package com.modrinth.methane.mixin;

import com.modrinth.methane.Methane;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.enums.CameraSubmersionType;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BackgroundRenderer.class)
public class BackgroundRendererMixin {
    /**
     * @author AnOpenSauceDev
     * @reason what fog??!?!? never heard of it.
     */
    @Inject(method = "applyFog", at = @At("HEAD"), cancellable = true)
    private static void applyFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
        if (!Methane.ModActive && !Methane.settings.fogSettings.persistFogSettings) return;

        if (hasBlindOrDark(camera.getFocusedEntity())) return;

        if (shouldDisableFog(camera.getSubmersionType(), thickFog, fogType)) {
            ci.cancel();
        }
    }

    @Unique
    private static boolean shouldDisableFog(CameraSubmersionType submersionType, boolean thickFog, BackgroundRenderer.FogType fogType) {
        if (submersionType == CameraSubmersionType.NONE && Methane.settings.fogSettings.disableAirFog) return true;
        if (submersionType == CameraSubmersionType.WATER && Methane.settings.fogSettings.disableWaterFog) return true;
        if (submersionType == CameraSubmersionType.LAVA && Methane.settings.fogSettings.disableLavaFog) return true;
        if (submersionType == CameraSubmersionType.POWDER_SNOW && Methane.settings.fogSettings.disablePowderedSnowFog) return true;
        if (thickFog && Methane.settings.fogSettings.disableThickFog) return true;
        if (fogType == BackgroundRenderer.FogType.FOG_SKY && Methane.settings.fogSettings.disableSkyFog) return true;
        return false;
    }

    @Unique
    private static boolean hasBlindOrDark(Entity entity) {
        if (!(entity instanceof ClientPlayerEntity)) return false;
        return ((ClientPlayerEntity) entity).hasStatusEffect(StatusEffects.BLINDNESS)
                || ((ClientPlayerEntity) entity).hasStatusEffect(StatusEffects.DARKNESS);
    }
}
