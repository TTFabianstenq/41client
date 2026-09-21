package dev.anticheatqa.mixin;

import dev.anticheatqa.module.render.FreeLook;
import dev.anticheatqa.module.visuals.FreeCam;
import net.minecraft.class_1297;
import net.minecraft.class_1937;
import net.minecraft.class_310;
import net.minecraft.class_4184;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(class_4184.class)
public abstract class CameraMixin {
   @Shadow
   protected abstract void method_19327(double x, double y, double z);

   @Shadow
   protected abstract void method_19325(float yaw, float pitch);

   @Inject(method = "method_19321", at = @At("RETURN"))
   private void fortyone$camera(class_1937 area, class_1297 focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
      try {
         if (FreeCam.active) {
            FreeCam.onFrame(class_310.method_1551());
            this.method_19327(FreeCam.camX, FreeCam.camY, FreeCam.camZ);
            this.method_19325(FreeCam.camYaw, FreeCam.camPitch);
            return;
         }
         if (FreeLook.active) {
            FreeLook.tickRender();
            this.method_19325(FreeLook.renderYaw, FreeLook.renderPitch);
         }
      } catch (Throwable ignored) {
      }
   }
}
