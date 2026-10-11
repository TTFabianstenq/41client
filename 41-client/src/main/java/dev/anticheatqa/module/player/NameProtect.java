package dev.anticheatqa.module.player;

import dev.anticheatqa.AntiCheatQA;
import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_310;

public class NameProtect extends Module {
   public final Module.TextSetting replacement = new Module.TextSetting("Name", "You");
   public final Module.BooleanSetting hideSelf = new Module.BooleanSetting("Hide Self", true);
   public final Module.BooleanSetting hideOthers = new Module.BooleanSetting("Hide Others Friend", false);

   public NameProtect() {
      super("Name Protect", "Replaces your name in HUD/nametags with a custom string.", Category.MISC);
      this.settings.add(this.replacement);
      this.settings.add(this.hideSelf);
      this.settings.add(this.hideOthers);
   }

   public static String protect(String original) {
      class_310 c = class_310.method_1551();
      if (c.field_1724 != null && AntiCheatQA.INSTANCE != null) {
         if (AntiCheatQA.INSTANCE.getModuleManager().getModule("Name Protect") instanceof NameProtect np && np.isEnabled()) {
            String self = c.field_1724.method_5477().getString();
            return np.hideSelf.get() && original.contains(self) ? original.replace(self, np.replacement.get()) : original;
         } else {
            return original;
         }
      } else {
         return original;
      }
   }
}
