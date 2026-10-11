package dev.anticheatqa.module.misc;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_742;

public class AutoLog extends Module {
   public final Module.NumberSetting health = new Module.NumberSetting("Health", 6.0, 1.0, 20.0, 0.5);
   public final Module.BooleanSetting onPlayer = new Module.BooleanSetting("Near Player", false);
   public final Module.NumberSetting playerRange = new Module.NumberSetting("Player Range", 8.0, 2.0, 32.0, 1.0);

   public AutoLog() {
      super("Auto Log", "Disconnects when HP is low or enemy players are close.", Category.MISC);
      this.settings.add(this.health);
      this.settings.add(this.onPlayer);
      this.settings.add(this.playerRange);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1724 != null && c.field_1687 != null && c.method_1562() != null) {
         boolean log = c.field_1724.method_6032() + c.field_1724.method_6067() <= this.health.get();
         if (this.onPlayer.get()) {
            for (class_742 p : c.field_1687.method_18456()) {
               if (p != c.field_1724 && p.method_5805() && c.field_1724.method_5739(p) <= this.playerRange.get()) {
                  log = true;
                  break;
               }
            }
         }

         if (log) {
            c.method_1562().method_48296().method_10747(class_2561.method_43470("41 Client AutoLog"));
            this.setEnabled(false);
         }
      }
   }
}
