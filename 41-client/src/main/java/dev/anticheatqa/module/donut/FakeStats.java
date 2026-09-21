package dev.anticheatqa.module.donut;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_2561;
import net.minecraft.class_310;

public class FakeStats extends Module {
   public final Module.NumberSetting bal = new Module.NumberSetting("Balance", 9999999.0, 0.0, 1.0E12, 1.0);
   public final Module.NumberSetting kills = new Module.NumberSetting("Kills", 0.0, 0.0, 100000.0, 1.0);

   public FakeStats() {
      super("Fake Stats", "Prints fake balance/kills to chat for screenshots.", Category.DONUT);
      this.settings.add(this.bal);
      this.settings.add(this.kills);
   }

   @Override
   protected void onEnable() {
      class_310 c = class_310.method_1551();
      if (c.field_1724 != null) {
         c.field_1724
            .method_7353(
               class_2561.method_43470(
                  "\u00a76[Stats] \u00a7fBal: \u00a7a$" + this.bal.get().longValue() + " \u00a7fKills: \u00a7c" + this.kills.get().intValue()
               ),
               false
            );
      }

      this.setEnabled(false);
   }
}
