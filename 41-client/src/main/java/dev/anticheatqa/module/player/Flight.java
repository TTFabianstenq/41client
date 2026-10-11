package dev.anticheatqa.module.player;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_243;
import net.minecraft.class_2828;
import net.minecraft.class_310;
import net.minecraft.class_3222;
import net.minecraft.class_746;

/**
 * Flight + no-clip.
 * Singleplayer: also sets noClip on the integrated server player (stops SP snap-back).
 * Multiplayer: packet mode helps a bit on loose hosts; vanilla survival servers
 * still reject inside-block positions and will rubberband.
 */
public class Flight extends Module {
   public static Flight INSTANCE;
   public static volatile boolean noClipFlag;
   public static volatile boolean activeFlag;

   public final Module.NumberSetting speed = new Module.NumberSetting("Speed", 1.5, 0.1, 10.0, 0.1);
   public final Module.NumberSetting vertical = new Module.NumberSetting("Vertical", 1.2, 0.1, 10.0, 0.1);
   public final Module.BooleanSetting noClip = new Module.BooleanSetting("No Clip", true);
   public final Module.BooleanSetting smooth = new Module.BooleanSetting("Smooth", true);
   public final Module.BooleanSetting packet = new Module.BooleanSetting("Packet Position", true);
   public final Module.BooleanSetting resetOnDisable = new Module.BooleanSetting("Reset On Disable", true);

   private boolean prevFlying, prevAllowFlying;
   private float prevFlySpeed;

   public Flight() {
      super("Flight", "Fly + no-clip. SP sets server player noClip too.", Category.MOVEMENT);
      settings.add(speed);
      settings.add(vertical);
      settings.add(noClip);
      settings.add(smooth);
      settings.add(packet);
      settings.add(resetOnDisable);
      INSTANCE = this;
   }

   public static boolean isActive() {
      return activeFlag || (INSTANCE != null && INSTANCE.isEnabled());
   }

   public static boolean isNoClip() {
      return noClipFlag;
   }

   public static class_243 inputVelocity(class_746 player) {
      if (INSTANCE == null) return class_243.field_1353;
      class_310 c = class_310.method_1551();
      if (c == null || c.field_1690 == null) return class_243.field_1353;
      float spd = INSTANCE.speed.get().floatValue();
      float vspd = INSTANCE.vertical.get().floatValue();
      float forward = 0, strafe = 0;
      try {
         if (c.field_1690.field_1894.method_1434()) forward++;
         if (c.field_1690.field_1881.method_1434()) forward--;
         if (c.field_1690.field_1913.method_1434()) strafe++;
         if (c.field_1690.field_1849.method_1434()) strafe--;
      } catch (Throwable ignored) {
      }
      double mx = 0, mz = 0;
      if (forward != 0 || strafe != 0) {
         float len = (float) Math.sqrt(forward * forward + strafe * strafe);
         forward /= len;
         strafe /= len;
         double yaw = Math.toRadians(player.method_36454());
         mx = (-Math.sin(yaw) * forward + Math.cos(yaw) * strafe) * spd;
         mz = (Math.cos(yaw) * forward + Math.sin(yaw) * strafe) * spd;
      }
      double my = 0;
      try {
         if (c.field_1690.field_1903.method_1434()) my += vspd;
         if (c.field_1690.field_1832.method_1434()) my -= vspd;
      } catch (Throwable ignored) {
      }
      return new class_243(mx, my, mz);
   }

   private void syncFlags() {
      activeFlag = isEnabled();
      noClipFlag = activeFlag && Boolean.TRUE.equals(noClip.get());
   }

   /** Integrated server player — set noClip so SP doesn't snap back. */
   private void syncServerPlayerNoClip(class_310 c, boolean value) {
      try {
         if (!c.method_1542()) return; // not singleplayer
         var server = c.method_1576();
         if (server == null || c.field_1724 == null) return;
         var pm = server.method_3760();
         if (pm == null) return;
         class_3222 sp = pm.method_14602(c.field_1724.method_5667());
         if (sp != null) {
            sp.field_5960 = value;
            if (Flight.isActive()) {
               var ab = sp.method_31549();
               ab.field_7478 = true;
               ab.field_7479 = true;
            }
         }
      } catch (Throwable ignored) {
      }
   }

   private void sendPositionPacket(class_310 c) {
      try {
         if (!Boolean.TRUE.equals(packet.get())) return;
         if (c.field_1724 == null || c.field_1724.field_3944 == null) return;
         double x = c.field_1724.method_23317();
         double y = c.field_1724.method_23318();
         double z = c.field_1724.method_23321();
         float yaw = c.field_1724.method_36454();
         float pitch = c.field_1724.method_36455();
         // Full(x,y,z,yaw,pitch,onGround,horizontalCollision)
         c.field_1724.field_3944.method_52787(
            new class_2828.class_2830(x, y, z, yaw, pitch, false, false)
         );
      } catch (Throwable ignored) {
      }
   }

   @Override
   protected void onEnable() {
      INSTANCE = this;
      syncFlags();
      try {
         class_310 c = class_310.method_1551();
         if (c.field_1724 == null) return;
         var ab = c.field_1724.method_31549();
         prevFlying = ab.field_7479;
         prevAllowFlying = ab.field_7478;
         prevFlySpeed = ab.method_7252();
         ab.field_7478 = true;
         ab.field_7479 = true;
         c.field_1724.field_5960 = noClipFlag;
         syncServerPlayerNoClip(c, noClipFlag);
      } catch (Throwable ignored) {
      }
   }

   @Override
   protected void onDisable() {
      activeFlag = false;
      noClipFlag = false;
      try {
         class_310 c = class_310.method_1551();
         if (c.field_1724 == null) return;
         c.field_1724.field_5960 = false;
         syncServerPlayerNoClip(c, false);
         if (Boolean.TRUE.equals(resetOnDisable.get())) {
            var ab = c.field_1724.method_31549();
            ab.field_7479 = prevFlying;
            ab.field_7478 = prevAllowFlying;
            ab.method_7248(prevFlySpeed);
            c.field_1724.method_18800(0, 0, 0);
         }
      } catch (Throwable ignored) {
      }
   }

   @Override
   public void onTick(class_310 c) {
      try {
         if (c.field_1724 == null) return;
         syncFlags();
         var ab = c.field_1724.method_31549();
         ab.field_7478 = true;
         ab.field_7479 = true;
         ab.method_7248(0.05F * Math.max(0.1F, speed.get().floatValue()));
         c.field_1724.field_5960 = noClipFlag;
         syncServerPlayerNoClip(c, noClipFlag);

         if (c.field_1755 != null) {
            c.field_1724.method_18800(0, 0, 0);
            return;
         }

         class_243 vel = inputVelocity(c.field_1724);
         if (Boolean.TRUE.equals(smooth.get())
            && vel.field_1352 == 0 && vel.field_1351 == 0 && vel.field_1350 == 0) {
            c.field_1724.method_18800(0, 0, 0);
         } else {
            c.field_1724.method_18800(vel.field_1352, vel.field_1351, vel.field_1350);
         }
         c.field_1724.field_6017 = 0;
         c.field_1724.method_24830(false);
         sendPositionPacket(c);
      } catch (Throwable ignored) {
      }
   }

   @Override
   public String getDisplay() {
      if (!isEnabled()) return "";
      return noClipFlag ? "Fly:NoClip" : "Fly";
   }
}
