package dev.anticheatqa.module.combat;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1588;
import net.minecraft.class_1657;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_3532;

public class AimAssist extends Module {
   public final Module.NumberSetting range = new Module.NumberSetting("Range", 4.5, 2.0, 8.0, 0.1);
   public final Module.NumberSetting fov = new Module.NumberSetting("FOV", 60.0, 10.0, 180.0, 1.0);
   public final Module.NumberSetting speed = new Module.NumberSetting("Speed", 25.0, 1.0, 100.0, 1.0);
   public final Module.NumberSetting smooth = new Module.NumberSetting("Smoothing", 0.35, 0.05, 1.0, 0.05);
   public final Module.BooleanSetting players = new Module.BooleanSetting("Players", true);
   public final Module.BooleanSetting mobs = new Module.BooleanSetting("Mobs", false);
   public final Module.BooleanSetting visibleOnly = new Module.BooleanSetting("Visible Only", true);
   public final Module.BooleanSetting horizontal = new Module.BooleanSetting("Horizontal", true);
   public final Module.BooleanSetting vertical = new Module.BooleanSetting("Vertical", true);
   public final Module.EnumSetting priority = new Module.EnumSetting("Priority", "Closest", "Closest", "LowestHP", "Crosshair");
   private String lastTarget = "-";

   public AimAssist() {
      super("Aim Assist", "Gently assists aim toward targets in FOV.", Category.COMBAT);
      this.settings.add(this.range);
      this.settings.add(this.fov);
      this.settings.add(this.speed);
      this.settings.add(this.smooth);
      this.settings.add(this.players);
      this.settings.add(this.mobs);
      this.settings.add(this.visibleOnly);
      this.settings.add(this.horizontal);
      this.settings.add(this.vertical);
      this.settings.add(this.priority);
   }

   @Override
   public void onTick(class_310 client) {
      if (client.field_1724 != null && client.field_1687 != null) {
         if (client.field_1755 == null) {
            class_1309 target = this.findTarget(client);
            if (target == null) {
               this.lastTarget = "-";
            } else {
               this.lastTarget = target.method_5477().getString();
               class_243 eye = client.field_1724.method_33571();
               class_243 aim = target.method_5829().method_1005();
               double dx = aim.field_1352 - eye.field_1352;
               double dy = aim.field_1351 - eye.field_1351;
               double dz = aim.field_1350 - eye.field_1350;
               double dist = Math.sqrt(dx * dx + dz * dz);
               float targetYaw = (float)Math.toDegrees(Math.atan2(dz, dx)) - 90.0F;
               float targetPitch = (float)(-Math.toDegrees(Math.atan2(dy, dist)));
               float yaw = client.field_1724.method_36454();
               float pitch = client.field_1724.method_36455();
               float dyaw = class_3532.method_15393(targetYaw - yaw);
               float dpitch = targetPitch - pitch;
               float maxStep = (float)(this.speed.get() * 0.15);
               float sm = this.smooth.get().floatValue();
               dyaw = class_3532.method_15363(dyaw * sm, -maxStep, maxStep);
               dpitch = class_3532.method_15363(dpitch * sm, -maxStep, maxStep);
               if (this.horizontal.get()) {
                  client.field_1724.method_36456(yaw + dyaw);
               }

               if (this.vertical.get()) {
                  client.field_1724.method_36457(class_3532.method_15363(pitch + dpitch, -90.0F, 90.0F));
               }
            }
         }
      }
   }

   private class_1309 findTarget(class_310 client) {
      class_1309 best = null;
      double bestScore = Double.MAX_VALUE;
      float playerYaw = client.field_1724.method_36454();
      float playerPitch = client.field_1724.method_36455();

      for (class_1297 e : client.field_1687.method_18112()) {
         if (e instanceof class_1309 le
            && le.method_5805()
            && le != client.field_1724
            && (e instanceof class_1657 ? this.players.get() : (e instanceof class_1588 ? this.mobs.get() : this.mobs.get()))) {
            double d = client.field_1724.method_5739(e);
            if (!(d > this.range.get()) && (!this.visibleOnly.get() || client.field_1724.method_6057(e))) {
               class_243 eye = client.field_1724.method_33571();
               class_243 center = e.method_5829().method_1005();
               double dx = center.field_1352 - eye.field_1352;
               double dy = center.field_1351 - eye.field_1351;
               double dz = center.field_1350 - eye.field_1350;
               double horiz = Math.sqrt(dx * dx + dz * dz);
               float ty = (float)Math.toDegrees(Math.atan2(dz, dx)) - 90.0F;
               float tp = (float)(-Math.toDegrees(Math.atan2(dy, horiz)));
               float ang = Math.abs(class_3532.method_15393(ty - playerYaw)) + Math.abs(tp - playerPitch) * 0.5F;
               if (!(ang > this.fov.get())) {
                  String var27 = this.priority.get();

                  double score = switch (var27) {
                     case "LowestHP" -> le.method_6032();
                     case "Crosshair" -> ang;
                     default -> d;
                  };
                  if (score < bestScore) {
                     bestScore = score;
                     best = le;
                  }
               }
            }
         }
      }

      return best;
   }

   @Override
   public String getDisplay() {
      return this.isEnabled() ? "Aim:" + this.lastTarget : "";
   }
}
