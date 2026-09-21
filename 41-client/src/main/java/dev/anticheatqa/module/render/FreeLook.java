package dev.anticheatqa.module.render;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_746;

public class FreeLook extends Module {
   public static boolean active;
   public static float yaw;
   public static float pitch;

   public FreeLook() {
      super("Free Look", "Look around without turning your body. Client-side only.", Category.RENDER);
   }

   @Override
   protected void onEnable() {
      class_746 p = class_310.method_1551().field_1724;
      if (p != null) {
         yaw = p.method_36454();
         pitch = p.method_36455();
      }

      active = true;
   }

   @Override
   protected void onDisable() {
      active = false;
   }

   public static void applyDelta(double dx, double dy) {
      yaw += (float)dx * 0.15F;
      pitch = class_3532.method_15363(pitch + (float)dy * 0.15F, -90.0F, 90.0F);
   }

   @Override
   public String getDisplay() {
      return active ? "FreeLook" : "";
   }
}
