package dev.anticheatqa.module.donut;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_2561;
import net.minecraft.class_310;

public class FakePay extends Module {
   public final Module.TextSetting target = new Module.TextSetting("Player", "Steve");
   public final Module.NumberSetting amount = new Module.NumberSetting("Amount", 1000000.0, 1.0, 1.0E9, 1.0);

   public FakePay() {
      super("Fake Pay", "Shows a fake payment message in chat (does not send /pay).", Category.DONUT);
      this.settings.add(this.target);
      this.settings.add(this.amount);
   }

   @Override
   protected void onEnable() {
      class_310 c = class_310.method_1551();
      if (c.field_1724 != null) {
         c.field_1724
            .method_7353(
               class_2561.method_43470(
                  "\u00a7a$\u00a7f You have been paid \u00a7a$" + this.amount.get().longValue() + "\u00a7f from \u00a7e" + this.target.get()
               ),
               false
            );
      }

      this.setEnabled(false);
   }
}
