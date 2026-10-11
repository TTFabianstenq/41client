package dev.anticheatqa.module.combat;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1268;
import net.minecraft.class_1802;
import net.minecraft.class_2868;
import net.minecraft.class_310;

/** Toggle once: switch to pearl, throw, restore slot (with slot packet). */
public class KeyPearl extends Module {
   public KeyPearl() {
      super("Key Pearl", "On enable: switch to pearl, throw, restore slot.", Category.COMBAT);
   }

   private void select(class_310 c, int slot) {
      if (slot < 0 || slot > 8) return;
      c.field_1724.method_31548().method_61496(slot);
      try {
         c.field_1724.field_3944.method_52787(new class_2868(slot));
      } catch (Throwable ignored) {
      }
   }

   @Override
   protected void onEnable() {
      try {
         class_310 c = class_310.method_1551();
         if (c.field_1724 == null || c.field_1761 == null) {
            setEnabled(false);
            return;
         }
         int pearl = -1;
         for (int i = 0; i < 9; i++) {
            if (c.field_1724.method_31548().method_5438(i).method_31574(class_1802.field_8634)) {
               pearl = i;
               break;
            }
         }
         if (pearl < 0) {
            // string fallback
            for (int i = 0; i < 9; i++) {
               if (c.field_1724.method_31548().method_5438(i).method_7909().toString().toLowerCase().contains("ender_pearl")) {
                  pearl = i;
                  break;
               }
            }
         }
         if (pearl < 0) {
            setEnabled(false);
            return;
         }
         int prev = c.field_1724.method_31548().method_67532();
         select(c, pearl);
         c.field_1761.method_2919(c.field_1724, class_1268.field_5808);
         c.field_1724.method_6104(class_1268.field_5808);
         select(c, prev);
         setEnabled(false);
      } catch (Throwable t) {
         setEnabled(false);
      }
   }
}
