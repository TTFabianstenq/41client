package dev.anticheatqa.module.combat;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_310;

public class SpearSwap extends Module {
   public SpearSwap() {
      super("Spear Swap", "Swaps to a spear/trident in hotbar when toggled.", Category.COMBAT);
   }

   @Override
   protected void onEnable() {
      class_310 c = class_310.method_1551();
      if (c.field_1724 == null) {
         this.setEnabled(false);
      } else {
         for (int i = 0; i < 9; i++) {
            String id = c.field_1724.method_31548().method_5438(i).method_7909().toString().toLowerCase();
            if (id.contains("trident") || id.contains("spear")) {
               c.field_1724.method_31548().method_61496(i);
               break;
            }
         }

         this.setEnabled(false);
      }
   }
}
