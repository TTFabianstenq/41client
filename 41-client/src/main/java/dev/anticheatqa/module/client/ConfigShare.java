package dev.anticheatqa.module.client;

import dev.anticheatqa.AntiCheatQA;
import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_2561;
import net.minecraft.class_310;

public class ConfigShare extends Module {
   public ConfigShare() {
      super("Config Share", "Copies a simple enabled-module list to clipboard.", Category.CLIENT);
   }

   @Override
   protected void onEnable() {
      class_310 c = class_310.method_1551();
      if (c.field_1724 != null && AntiCheatQA.INSTANCE != null) {
         StringBuilder sb = new StringBuilder("GrokClient:");

         for (Module m : AntiCheatQA.INSTANCE.getModuleManager().getModules()) {
            if (m.isEnabled()) {
               sb.append(m.getName()).append(',');
            }
         }

         c.field_1774.method_1455(sb.toString());
         c.field_1724.method_7353(class_2561.method_43470("\u00a7a[SC] Config list copied to clipboard"), false);
         this.setEnabled(false);
      } else {
         this.setEnabled(false);
      }
   }
}
