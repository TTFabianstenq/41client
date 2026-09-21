package dev.anticheatqa.mixin;

import dev.anticheatqa.module.render.SwingSpeed;
import net.minecraft.class_1309;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(class_1309.class)
public class LivingEntityMixin {
   @Inject(method = "method_6028", at = @At("HEAD"), cancellable = true)
   private void fortyone$swingSpeed(CallbackInfoReturnable<Integer> cir) {
      try {
         float mult = SwingSpeed.mult();
         if (mult > 1.01F) {
            cir.setReturnValue(Math.max(1, Math.round(6.0F / mult)));
         } else if (mult < 0.99F) {
            cir.setReturnValue(Math.min(20, Math.round(6.0F / mult)));
         }
      } catch (Throwable ignored) {
      }
   }
}
