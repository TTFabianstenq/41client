package dev.anticheatqa.module.misc;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1268;
import net.minecraft.class_310;

public class KeyWindCharge extends Module {
   public KeyWindCharge() {
      super("Key Wind Charge", "Switches to wind charge and uses it, then restores slot.", Category.MISC);
   }

   @Override
   protected void onEnable() {
      class_310 c = class_310.method_1551();
      if (c.field_1724 != null && c.field_1761 != null) {
         int slot = -1;

         for (int i = 0; i < 9; i++) {
            if (c.field_1724.method_31548().method_5438(i).method_7909().toString().toLowerCase().contains("wind_charge")) {
               slot = i;
               break;
            }
         }

         if (slot < 0) {
            this.setEnabled(false);
         } else {
            int prev = c.field_1724.method_31548().method_67532();
            c.field_1724.method_31548().method_61496(slot);
            c.field_1761.method_2919(c.field_1724, class_1268.field_5808);
            c.field_1724.method_31548().method_61496(prev);
            this.setEnabled(false);
         }
      } else {
         this.setEnabled(false);
      }
   }
}
