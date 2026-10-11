package dev.anticheatqa.mixin;

import dev.anticheatqa.module.player.AntiHunger;
import net.minecraft.class_1657;
import net.minecraft.class_310;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(class_1657.class)
public class PlayerEntityMixin {
   @Inject(method = "method_7322", at = @At("HEAD"), cancellable = true)
   private void fortyone$antiHunger(float exhaustion, CallbackInfo ci) {
      try {
         if (!AntiHunger.shouldCancelExhaustion()) return;
         class_1657 self = (class_1657) (Object) this;
         class_310 mc = class_310.method_1551();
         if (mc.field_1724 != null && self == mc.field_1724) {
            ci.cancel();
         }
      } catch (Throwable ignored) {
      }
   }
}
