package dev.anticheatqa.module.combat;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1713;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_310;

public class ElytraSwap extends Module {
   public ElytraSwap() {
      super("Elytra Swap", "Swaps chestplate/elytra in inventory when toggled.", Category.COMBAT);
   }

   @Override
   protected void onEnable() {
      class_310 c = class_310.method_1551();
      if (c.field_1724 != null && c.field_1761 != null) {
         int elytra = -1;
         int chest = -1;

         for (int i = 9; i < 45; i++) {
            class_1799 s = c.field_1724.field_7498.method_7611(i).method_7677();
            if (s.method_31574(class_1802.field_8833) && elytra < 0) {
               elytra = i;
            }

            if ((s.method_31574(class_1802.field_22028) || s.method_31574(class_1802.field_8058) || s.method_31574(class_1802.field_8523)) && chest < 0) {
               chest = i;
            }
         }

         boolean wearingElytra = c.field_1724.method_31548().method_5438(38).method_31574(class_1802.field_8833);
         int inv = wearingElytra ? chest : elytra;
         if (inv >= 0) {
            try {
               c.field_1761.method_2906(c.field_1724.field_7498.field_7763, inv, 6, class_1713.field_7791, c.field_1724);
            } catch (Throwable var7) {
            }
         }

         this.setEnabled(false);
      } else {
         this.setEnabled(false);
      }
   }
}
