package dev.anticheatqa.module.visuals;

import dev.anticheatqa.AntiCheatQA;
import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_315;
import net.minecraft.class_746;

public class FreeCam extends Module {
   public static boolean active;
   public static double camX;
   public static double camY;
   public static double camZ;
   public static float camYaw;
   public static float camPitch;
   private static double velX;
   private static double velY;
   private static double velZ;
   private static long lastNanos = 0L;
   public final Module.NumberSetting hSpeed = new Module.NumberSetting("H Speed", 1.2, 0.1, 10.0, 0.1);
   public final Module.NumberSetting vSpeed = new Module.NumberSetting("V Speed", 1.0, 0.1, 10.0, 0.1);
   public final Module.NumberSetting accel = new Module.NumberSetting("Accel", 12.0, 1.0, 40.0, 0.5);
   public final Module.NumberSetting decel = new Module.NumberSetting("Decel", 14.0, 1.0, 40.0, 0.5);
   public final Module.NumberSetting boostMul = new Module.NumberSetting("Boost Mult", 3.0, 1.0, 10.0, 0.1);
   public final Module.BooleanSetting freelook = new Module.BooleanSetting("Use Player Look", true);
   public final Module.BooleanSetting cancelInput = new Module.BooleanSetting("Cancel Body Input", true);

   public FreeCam() {
      super("Freecam", "Detached camera only. Body stays real (gravity/AC safe).", Category.RENDER);
      this.settings.add(this.hSpeed);
      this.settings.add(this.vSpeed);
      this.settings.add(this.accel);
      this.settings.add(this.decel);
      this.settings.add(this.boostMul);
      this.settings.add(this.freelook);
      this.settings.add(this.cancelInput);
   }

   @Override
   protected void onEnable() {
      class_310 client = class_310.method_1551();
      if (client.field_1724 != null) {
         class_746 p = client.field_1724;
         class_243 eye = p.method_33571();
         camX = eye.field_1352;
         camY = eye.field_1351;
         camZ = eye.field_1350;
         camYaw = p.method_36454();
         camPitch = p.method_36455();
         velZ = 0.0;
         velY = 0.0;
         velX = 0.0;
         lastNanos = System.nanoTime();
         active = true;
      }
   }

   @Override
   protected void onDisable() {
      active = false;
      velZ = 0.0;
      velY = 0.0;
      velX = 0.0;
      lastNanos = 0L;
   }

   @Override
   public void onTick(class_310 client) {
      if (client.field_1724 != null && active) {
         if (this.freelook.get()) {
            camYaw = client.field_1724.method_36454();
            camPitch = client.field_1724.method_36455();
         }
      }
   }

   public static void onFrame(class_310 client) {
      if (active && client.field_1724 != null) {
         FreeCam mod = instance();
         if (mod != null && mod.isEnabled()) {
            long now = System.nanoTime();
            double dt;
            if (lastNanos == 0L) {
               dt = 0.016666666666666666;
            } else {
               dt = (now - lastNanos) / 1.0E9;
               if (dt > 0.05) {
                  dt = 0.05;
               }

               if (dt < 0.0) {
                  dt = 0.0;
               }
            }

            lastNanos = now;
            double hSp = mod.hSpeed.get();
            double vSp = mod.vSpeed.get();
            double acc = mod.accel.get();
            double dec = mod.decel.get();
            if (client.field_1690.field_1867.method_1434()) {
               hSp *= mod.boostMul.get();
               vSp *= mod.boostMul.get();
            }

            float yaw = camYaw;
            float pitch = camPitch;
            double yawRad = Math.toRadians(yaw);
            double pitchRad = Math.toRadians(pitch);
            double rightX = Math.cos(yawRad);
            double rightZ = Math.sin(yawRad);
            double lookX = -Math.sin(yawRad) * Math.cos(pitchRad);
            double lookY = -Math.sin(pitchRad);
            double lookZ = Math.cos(yawRad) * Math.cos(pitchRad);
            double wishX = 0.0;
            double wishY = 0.0;
            double wishZ = 0.0;
            class_315 opts = client.field_1690;
            if (opts.field_1894.method_1434()) {
               wishX += lookX;
               wishY += lookY;
               wishZ += lookZ;
            }

            if (opts.field_1881.method_1434()) {
               wishX -= lookX;
               wishY -= lookY;
               wishZ -= lookZ;
            }

            if (opts.field_1913.method_1434()) {
               wishX += rightX;
               wishZ += rightZ;
            }

            if (opts.field_1849.method_1434()) {
               wishX -= rightX;
               wishZ -= rightZ;
            }

            if (opts.field_1903.method_1434()) {
               wishY++;
            }

            if (opts.field_1832.method_1434()) {
               wishY--;
            }

            double wishLen = Math.sqrt(wishX * wishX + wishY * wishY + wishZ * wishZ);
            if (wishLen > 1.0E-6) {
               wishX /= wishLen;
               wishY /= wishLen;
               wishZ /= wishLen;
            }

            double targetVX = wishX * hSp * 20.0;
            double targetVY = wishY * vSp * 20.0;
            double targetVZ = wishZ * hSp * 20.0;
            boolean hasInput = wishLen > 1.0E-6;
            double rate = (hasInput ? acc : dec) * dt;
            velX = approach(velX, targetVX, rate * Math.max(hSp, 1.0) * 20.0);
            velY = approach(velY, targetVY, rate * Math.max(vSp, 1.0) * 20.0);
            velZ = approach(velZ, targetVZ, rate * Math.max(hSp, 1.0) * 20.0);
            camX = camX + velX * dt;
            camY = camY + velY * dt;
            camZ = camZ + velZ * dt;
         }
      }
   }

   private static double approach(double current, double target, double maxDelta) {
      double d = target - current;
      return Math.abs(d) <= maxDelta ? target : current + Math.signum(d) * maxDelta;
   }

   private static FreeCam instance() {
      try {
         if (AntiCheatQA.INSTANCE == null) {
            return null;
         } else {
            Module m = AntiCheatQA.INSTANCE.getModuleManager().getModule("Freecam");
            if (m == null) {
               m = AntiCheatQA.INSTANCE.getModuleManager().getModule("FreeCam");
            }

            return m instanceof FreeCam fc ? fc : null;
         }
      } catch (Throwable var2) {
         return null;
      }
   }

   @Override
   public String getDisplay() {
      return active ? "FreeCam" : "";
   }
}
