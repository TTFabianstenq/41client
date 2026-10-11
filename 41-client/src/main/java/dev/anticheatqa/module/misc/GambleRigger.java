package dev.anticheatqa.module.misc;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_2561;
import net.minecraft.class_310;

public class GambleRigger extends Module {
   public final Module.EnumSetting mode = new Module.EnumSetting("Mode", "coinflip", "coinflip", "dice", "slots");

   public GambleRigger() {
      super("Gamble Rigger", "Local fake gamble result messages (does not affect real server gambles).", Category.MISC);
      this.settings.add(this.mode);
   }

   @Override
   protected void onEnable() {
      class_310 c = class_310.method_1551();
      if (c.field_1724 != null) {
         String var3 = this.mode.get();

         String r = switch (var3) {
            case "dice" -> "\u00a7aYou rolled a \u00a7f6\u00a7a!";
            case "slots" -> "\u00a76JACKPOT \u00a7e\u00a7l777";
            default -> "\u00a7aCoin flip: \u00a7fHEADS \u00a7a\u2014 you win!";
         };
         c.field_1724.method_7353(class_2561.method_43470("\u00a77[SC Gamble] " + r + " \u00a78(local only)"), false);
      }

      this.setEnabled(false);
   }
}
