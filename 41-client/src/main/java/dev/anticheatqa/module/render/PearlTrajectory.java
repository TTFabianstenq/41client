package dev.anticheatqa.module.render;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_1802;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_310;

public class PearlTrajectory extends Module {
   public static final List<class_243> path = new ArrayList<>();

   public PearlTrajectory() {
      super("Pearl Trajectory", "Simulates ender pearl landing path when holding a pearl.", Category.RENDER);
   }

   @Override
   public void onTick(class_310 c) {
      path.clear();
      if (c.field_1724 != null && c.field_1687 != null) {
         if (c.field_1724.method_6047().method_31574(class_1802.field_8634) || c.field_1724.method_6079().method_31574(class_1802.field_8634)) {
            class_243 pos = c.field_1724.method_33571();
            float yaw = c.field_1724.method_36454();
            float pitch = c.field_1724.method_36455();
            double mx = -Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)) * 1.5;
            double my = -Math.sin(Math.toRadians(pitch)) * 1.5;
            double mz = Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)) * 1.5;
            class_243 vel = new class_243(mx, my, mz);

            for (int i = 0; i < 60; i++) {
               path.add(pos);
               pos = pos.method_1019(vel);
               vel = vel.method_1021(0.99).method_1031(0.0, -0.03, 0.0);
               if (c.field_1687.method_8320(class_2338.method_49638(pos)).method_26212(c.field_1687, class_2338.method_49638(pos))) {
                  break;
               }
            }
         }
      }
   }
}
