package dev.anticheatqa.module.combat;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1268;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_243;
import net.minecraft.class_2868;
import net.minecraft.class_310;
import net.minecraft.class_3965;
import net.minecraft.class_239.class_240;

/** RMB with sword places obsidian then swaps back. */
public class SwordPlaceObsidian extends Module {
   private boolean placing;
   private int previousSlot = -1;

   public SwordPlaceObsidian() {
      super("Sword Place Obi", "Right-click with sword to place obsidian, then swap back.", Category.COMBAT);
   }

   private boolean isSword(class_1799 stack) {
      if (stack.method_7960()) return false;
      return stack.method_7909().toString().toLowerCase().contains("sword");
   }

   private int findObi(class_310 c) {
      for (int i = 0; i < 9; i++) {
         class_1799 s = c.field_1724.method_31548().method_5438(i);
         try {
            if (s.method_31574(class_1802.field_8281)) return i;
         } catch (Throwable ignored) {
         }
         if (s.method_7909().toString().toLowerCase().contains("obsidian")
            && !s.method_7909().toString().toLowerCase().contains("crying")) return i;
      }
      return -1;
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
   public void onTick(class_310 c) {
      try {
         if (c.field_1724 == null || c.field_1687 == null || c.field_1761 == null) return;
         if (c.field_1755 != null) return;

         boolean use = c.field_1690.field_1904.method_1434();
         class_1799 main = c.field_1724.method_6047();

         if (use && !placing && isSword(main)) {
            int obi = findObi(c);
            if (obi < 0) return;
            if (!(c.field_1765 instanceof class_3965 bhr) || c.field_1765.method_17783() != class_240.field_1332) return;

            placing = true;
            previousSlot = c.field_1724.method_31548().method_67532();
            select(c, obi);

            class_2350 face = bhr.method_17780();
            class_2338 against = bhr.method_17777();
            class_243 hit = class_243.method_24953(against);
            class_3965 target = new class_3965(hit, face, against, false);
            c.field_1761.method_2896(c.field_1724, class_1268.field_5808, target);
            try {
               c.field_1724.method_6104(class_1268.field_5808);
            } catch (Throwable ignored) {
            }
         }

         if (placing && !use) {
            if (previousSlot >= 0) select(c, previousSlot);
            placing = false;
            previousSlot = -1;
         }
      } catch (Throwable ignored) {
      }
   }
}
