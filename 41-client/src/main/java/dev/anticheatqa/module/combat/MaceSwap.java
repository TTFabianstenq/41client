package dev.anticheatqa.module.combat;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2868;
import net.minecraft.class_310;

/** Swap to mace while falling, restore on land. */
public class MaceSwap extends Module {
   private int prev = -1;

   public MaceSwap() {
      super("Mace Swap", "Swaps to mace while falling, restores slot on land.", Category.COMBAT);
   }

   private void select(class_310 c, int slot) {
      if (slot < 0 || slot > 8) return;
      c.field_1724.method_31548().method_61496(slot);
      try {
         c.field_1724.field_3944.method_52787(new class_2868(slot));
      } catch (Throwable ignored) {
      }
   }

   private int findMace(class_310 c) {
      for (int i = 0; i < 9; i++) {
         class_1799 s = c.field_1724.method_31548().method_5438(i);
         if (s.method_7960()) continue;
         try {
            if (s.method_31574(class_1802.field_49814)) return i;
         } catch (Throwable ignored) {
         }
         if (s.method_7909().toString().toLowerCase().contains("mace")) return i;
      }
      return -1;
   }

   @Override
   public void onTick(class_310 c) {
      try {
         if (c.field_1724 == null) return;
         if (c.field_1724.field_6017 > 2.0F) {
            if (prev < 0) prev = c.field_1724.method_31548().method_67532();
            int mace = findMace(c);
            if (mace >= 0 && c.field_1724.method_31548().method_67532() != mace) {
               select(c, mace);
            }
         } else if (prev >= 0) {
            select(c, prev);
            prev = -1;
         }
      } catch (Throwable ignored) {
      }
   }
}
