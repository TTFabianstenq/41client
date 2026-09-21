package dev.anticheatqa.module.misc;

import dev.anticheatqa.AntiCheatQA;
import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;

public class CustomGlint extends Module {
   public final Module.ColorSetting color = new Module.ColorSetting("Glint Color", -5635841);
   public final Module.NumberSetting speed = new Module.NumberSetting("Speed", 1.0, 0.1, 3.0, 0.1);

   public CustomGlint() {
      super("Custom Glint", "Overrides enchantment glint color client-side (visual).", Category.MISC);
      this.settings.add(this.color);
      this.settings.add(this.speed);
   }

   public static int glintColor() {
      try {
         if (AntiCheatQA.INSTANCE.getModuleManager().getModule("Custom Glint") instanceof CustomGlint g && g.isEnabled()) {
            return g.color.get();
         }
      } catch (Throwable var2) {
      }

      return -1;
   }
}
