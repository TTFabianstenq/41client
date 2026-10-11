package dev.anticheatqa.module.monitor;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_310;

public class FPSMonitor extends Module {
   public FPSMonitor() {
      super("FPS Monitor", "Displays current FPS on screen.", Category.RENDER);
   }

   @Override
   public String getDisplay() {
      try {
         return class_310.method_1551().method_47599() + " fps";
      } catch (Throwable var2) {
         return "";
      }
   }
}
