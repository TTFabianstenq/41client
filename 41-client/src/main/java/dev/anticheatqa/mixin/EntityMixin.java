package dev.anticheatqa.mixin;

import dev.anticheatqa.module.combat.HitBox;
import dev.anticheatqa.module.player.Flight;
import dev.anticheatqa.module.player.NoSuffocation;
import dev.anticheatqa.module.render.FreeLook;
import dev.anticheatqa.module.visuals.FreeCam;
import net.minecraft.class_1297;
import net.minecraft.class_1313;
import net.minecraft.class_1657;
import net.minecraft.class_243;
import net.minecraft.class_310;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(class_1297.class)
public class EntityMixin {
   @Inject(method = "method_5872", at = @At("HEAD"), cancellable = true)
   private void fortyone$freeLook(double cursorDeltaX, double cursorDeltaY, CallbackInfo ci) {
      try {
         if (!FreeLook.active || FreeCam.active) return;
         class_1297 self = (class_1297) (Object) this;
         class_310 mc = class_310.method_1551();
         if (mc.field_1724 == null || self != mc.field_1724) return;
         FreeLook.applyDelta(cursorDeltaX, cursorDeltaY);
         ci.cancel();
      } catch (Throwable ignored) {
      }
   }

   @Inject(method = "method_5871", at = @At("RETURN"), cancellable = true)
   private void fortyone$hitbox(CallbackInfoReturnable<Float> cir) {
      try {
         class_1297 self = (class_1297) (Object) this;
         class_310 mc = class_310.method_1551();
         if (mc.field_1724 == null || self == mc.field_1724) return;
         if (!(self instanceof class_1657)) return;
         float expand = HitBox.expandAmount();
         if (expand <= 0.0F) return;
         float base = cir.getReturnValue() != null ? cir.getReturnValue() : 0.0F;
         cir.setReturnValue(base + expand);
      } catch (Throwable ignored) {
      }
   }

   @Inject(method = "method_5784", at = @At("HEAD"))
   private void fortyone$entityMoveNoClip(class_1313 type, class_243 movement, CallbackInfo ci) {
      try {
         if (!Flight.isNoClip() && !NoSuffocation.isActive()) return;
         class_1297 self = (class_1297) (Object) this;
         class_310 mc = class_310.method_1551();
         if (mc.field_1724 != null && self == mc.field_1724) {
            self.field_5960 = true;
         }
      } catch (Throwable ignored) {
      }
   }

   @Inject(method = "method_5757", at = @At("HEAD"), cancellable = true)
   private void fortyone$noSuffocation(CallbackInfoReturnable<Boolean> cir) {
      try {
         if (!NoSuffocation.isActive() && !Flight.isNoClip()) return;
         class_1297 self = (class_1297) (Object) this;
         class_310 mc = class_310.method_1551();
         if (mc.field_1724 != null && self == mc.field_1724) {
            cir.setReturnValue(false);
         }
      } catch (Throwable ignored) {
      }
   }
}
