package dev.anticheatqa.module.combat;

import dev.anticheatqa.AntiCheatQA;
import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;

public class HitBox extends Module {
   public final Module.NumberSetting expand = new Module.NumberSetting("Expand", 0.3, 0.0, 1.0, 0.05);
   public final Module.BooleanSetting players = new Module.BooleanSetting("Players", true);

   public HitBox() {
      super("HitBox", "Expands player hitboxes client-side for easier hits (visual + interaction expand).", Category.COMBAT);
      this.settings.add(this.expand);
      this.settings.add(this.players);
   }

   public static float expandAmount() {
      try {
         if (AntiCheatQA.INSTANCE == null) {
            return 0.0F;
         }

         if (AntiCheatQA.INSTANCE.getModuleManager().getModule("HitBox") instanceof HitBox hb && hb.isEnabled()) {
            return hb.expand.get().floatValue();
         }
      } catch (Throwable var2) {
      }

      return 0.0F;
   }
}
