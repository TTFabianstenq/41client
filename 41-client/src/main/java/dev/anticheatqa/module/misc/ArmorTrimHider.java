package dev.anticheatqa.module.misc;

import dev.anticheatqa.AntiCheatQA;
import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;

public class ArmorTrimHider extends Module {
   public ArmorTrimHider() {
      super("Armor Trim Hider", "Hides armor trims on other players client-side when possible.", Category.MISC);
   }

   public static boolean active() {
      try {
         Module m = AntiCheatQA.INSTANCE.getModuleManager().getModule("Armor Trim Hider");
         return m != null && m.isEnabled();
      } catch (Throwable var1) {
         return false;
      }
   }
}
