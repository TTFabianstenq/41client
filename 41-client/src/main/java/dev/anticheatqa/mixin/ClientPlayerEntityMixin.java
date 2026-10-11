package dev.anticheatqa.mixin;

import dev.anticheatqa.AntiCheatQA;
import dev.anticheatqa.module.player.Flight;
import dev.anticheatqa.module.player.NoSuffocation;
import dev.anticheatqa.module.visuals.FreeCam;
import net.minecraft.class_10185;
import net.minecraft.class_1313;
import net.minecraft.class_243;
import net.minecraft.class_744;
import net.minecraft.class_746;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(class_746.class)
public class ClientPlayerEntityMixin {
   @Shadow
   public class_744 field_3913;

   private void forceFlags(class_746 self) {
      try {
         if (Flight.isNoClip() || NoSuffocation.isActive()) {
            self.field_5960 = true;
         }
         if (Flight.isActive()) {
            var ab = self.method_31549();
            ab.field_7478 = true;
            ab.field_7479 = true;
         }
      } catch (Throwable ignored) {
      }
   }

   @Inject(method = "method_5773", at = @At("HEAD"))
   private void fortyone$tickHead(CallbackInfo ci) {
      class_746 self = (class_746) (Object) this;
      forceFlags(self);
      // Apply flight velocity at START of tick so travel/move sees it
      try {
         if (Flight.isActive()) {
            class_243 vel = Flight.inputVelocity(self);
            self.method_18800(vel.field_1352, vel.field_1351, vel.field_1350);
            self.field_6017 = 0;
            self.method_24830(false);
         }
      } catch (Throwable ignored) {
      }
   }

   @Inject(method = "method_5773", at = @At("RETURN"))
   private void fortyone$tickReturn(CallbackInfo ci) {
      forceFlags((class_746) (Object) this);
   }

   @Inject(method = "method_6007", at = @At("HEAD"))
   private void fortyone$tickMovementHead(CallbackInfo ci) {
      forceFlags((class_746) (Object) this);
   }

   /**
    * HARD no-clip: skip ClientPlayer.move entirely and apply movement with no collision.
    * Vanilla super.move still collides if noClip flag races; cancelling is reliable.
    */
   @Inject(method = "method_5784", at = @At("HEAD"), cancellable = true)
   private void fortyone$hardNoClipMove(class_1313 type, class_243 movement, CallbackInfo ci) {
      try {
         if (!Flight.isNoClip() && !NoSuffocation.isActive()) return;
         class_746 self = (class_746) (Object) this;
         self.field_5960 = true;

         if (movement == null) {
            ci.cancel();
            return;
         }

         double nx = self.method_23317() + movement.field_1352;
         double ny = self.method_23318() + movement.field_1351;
         double nz = self.method_23321() + movement.field_1350;
         self.method_5814(nx, ny, nz);
         try {
            self.method_23327(nx, ny, nz);
         } catch (Throwable ignored) {
         }
         self.field_5976 = false;
         self.field_6037 = false;
         self.field_6017 = 0;
         ci.cancel();
      } catch (Throwable ignored) {
      }
   }

   @Inject(method = "method_30673", at = @At("HEAD"), cancellable = true)
   private void fortyone$noPush(double x, double z, CallbackInfo ci) {
      try {
         if (Flight.isNoClip() || NoSuffocation.isActive()) ci.cancel();
      } catch (Throwable ignored) {
      }
   }

   @Inject(method = "method_6007", at = @At("HEAD"))
   private void a(CallbackInfo ci) {
      this.zero();
   }

   @Inject(method = "method_6007", at = @At("RETURN"))
   private void b(CallbackInfo ci) {
      this.zero();
      try {
         if (FreeCam.active) {
            class_746 self = (class_746) (Object) this;
            self.method_18800(0.0, self.method_18798().field_1351, 0.0);
         }
      } catch (Throwable ignored) {
      }
   }

   private void zero() {
      try {
         if (!FreeCam.active || this.field_3913 == null) return;
         FreeCam mod = null;
         try {
            if (AntiCheatQA.INSTANCE != null
               && AntiCheatQA.INSTANCE.getModuleManager().getModule("Freecam") instanceof FreeCam fc) {
               mod = fc;
            }
         } catch (Throwable ignored) {
         }
         if (mod != null && !Boolean.TRUE.equals(mod.cancelInput.get())) return;
         this.field_3913.field_54155 = new class_10185(false, false, false, false, false, false, false);
      } catch (Throwable ignored) {
      }
   }
}
