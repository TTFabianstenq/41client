package dev.anticheatqa.module.render;

import dev.anticheatqa.AntiCheatQA;
import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;

public class SwingSpeed extends Module {
   public final Module.NumberSetting speed = new Module.NumberSetting("Speed", 1.5, 0.5, 5.0, 0.1);

   public SwingSpeed() {
      super("Swing Speed", "Speeds up hand swing animation (client-side).", Category.RENDER);
      this.settings.add(this.speed);
   }

   public static float mult() {
      try {
         if (AntiCheatQA.INSTANCE == null) {
            return 1.0F;
         }

         if (AntiCheatQA.INSTANCE.getModuleManager().getModule("Swing Speed") instanceof SwingSpeed s && s.isEnabled()) {
            return Math.max(0.5F, s.speed.get().floatValue());
         }
      } catch (Throwable var2) {
      }

      return 1.0F;
   }
}
