package dev.anticheatqa.module.misc;

import dev.anticheatqa.AntiCheatQA;
import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;

public class SkinProtect extends Module {
   public SkinProtect() {
      super("Skin Protect", "Hides custom skins locally (uses default steve rendering flag).", Category.MISC);
   }

   public static boolean active() {
      try {
         if (AntiCheatQA.INSTANCE == null) {
            return false;
         } else {
            Module m = AntiCheatQA.INSTANCE.getModuleManager().getModule("Skin Protect");
            return m != null && m.isEnabled();
         }
      } catch (Throwable var1) {
         return false;
      }
   }
}
