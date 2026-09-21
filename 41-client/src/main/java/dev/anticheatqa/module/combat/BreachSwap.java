package dev.anticheatqa.module.combat;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1799;
import net.minecraft.class_2868;
import net.minecraft.class_310;

/**
 * Glazed breach-swap: on attack key edge, swap to breach-enchanted mace, optional swap-back.
 */
public class BreachSwap extends Module {
   public final Module.BooleanSetting autoFind = new Module.BooleanSetting("Auto Find Breach", true);
   public final Module.NumberSetting targetSlot = new Module.NumberSetting("Target Slot", 1.0, 1.0, 9.0, 1.0);
   public final Module.BooleanSetting swapBack = new Module.BooleanSetting("Swap Back", true);
   public final Module.NumberSetting swapBackDelay = new Module.NumberSetting("Swap Back Delay", 3.0, 0.0, 20.0, 1.0);
   public final Module.BooleanSetting requireWeapon = new Module.BooleanSetting("Require Sword/Axe", false);

   private boolean wasAttack;
   private int prevSlot = -1;
   private int backCd;

   public BreachSwap() {
      super("Breach Swap", "Swaps to breach mace on attack (Glazed).", Category.COMBAT);
      settings.add(autoFind);
      settings.add(targetSlot);
      settings.add(swapBack);
      settings.add(swapBackDelay);
      settings.add(requireWeapon);
   }

   private int findBreach(class_310 c) {
      int best = -1;
      for (int i = 0; i < 9; i++) {
         class_1799 stack = c.field_1724.method_31548().method_5438(i);
         if (stack.method_7960()) continue;
         String s = "";
         try { s = stack.method_58657().toString().toLowerCase(); } catch (Throwable t) {
            s = stack.toString().toLowerCase();
         }
         String id = stack.method_7909().toString().toLowerCase();
         if (s.contains("breach") || (id.contains("mace") && s.contains("breach"))) {
            best = i;
         }
         // also accept any mace if named
         if (best < 0 && id.contains("mace")) best = i;
      }
      return best;
   }

   private void select(class_310 c, int slot) {
      if (slot < 0 || slot > 8) return;
      c.field_1724.method_31548().method_61496(slot);
      try { c.field_1724.field_3944.method_52787(new class_2868(slot)); } catch (Throwable ignored) {}
   }

   @Override
   public void onTick(class_310 c) {
      try {
         if (c.field_1724 == null) return;
         if (backCd > 0) {
            backCd--;
            if (backCd == 0 && prevSlot >= 0 && Boolean.TRUE.equals(swapBack.get())) {
               select(c, prevSlot);
               prevSlot = -1;
            }
         }
         boolean attack = c.field_1690.field_1886.method_1434();
         if (attack && !wasAttack) {
            // edge: attack pressed
            if (Boolean.TRUE.equals(requireWeapon.get())) {
               String id = c.field_1724.method_6047().method_7909().toString().toLowerCase();
               if (!id.contains("sword") && !id.contains("_axe") && !id.contains("mace")) {
                  wasAttack = attack;
                  return;
               }
            }
            prevSlot = c.field_1724.method_31548().method_67532();
            int slot;
            if (Boolean.TRUE.equals(autoFind.get())) {
               slot = findBreach(c);
               if (slot < 0) slot = targetSlot.get().intValue() - 1;
            } else {
               slot = targetSlot.get().intValue() - 1;
            }
            if (slot >= 0) select(c, slot);
            if (Boolean.TRUE.equals(swapBack.get())) {
               backCd = Math.max(1, swapBackDelay.get().intValue());
            }
         }
         wasAttack = attack;
      } catch (Throwable ignored) {}
   }
}
