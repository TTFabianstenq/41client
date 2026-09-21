package dev.anticheatqa.module.combat;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1713;
import net.minecraft.class_1723;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_310;
import net.minecraft.class_490;

/**
 * PASSIVE ONLY — never opens or closes inventory.
 * Only does a single offhand swap while YOU already have inv open.
 */
public class AutoInvTotem extends Module {
   public final Module.NumberSetting delay = new Module.NumberSetting("Delay Ticks", 5.0, 1.0, 20.0, 1.0);
   public final Module.BooleanSetting oncePerOpen = new Module.BooleanSetting("Once Per Open", true);

   private int cd;
   private boolean didThisOpen;

   public AutoInvTotem() {
      super(
         "Auto Inv Totem",
         "ONLY while your inventory is already open — never opens/closes it for you.",
         Category.COMBAT
      );
      settings.add(delay);
      settings.add(oncePerOpen);
   }

   @Override
   protected void onEnable() {
      cd = 0;
      didThisOpen = false;
   }

   private boolean hasTotemOffhand(class_310 c) {
      return c.field_1724.method_6079().method_31574(class_1802.field_8288);
   }

   private int findTotem(class_1723 h) {
      for (int i = 9; i < 45; i++) {
         try {
            class_1799 s = h.method_7611(i).method_7677();
            if (!s.method_7960() && s.method_31574(class_1802.field_8288)) return i;
         } catch (Throwable ignored) {
         }
      }
      return -1;
   }

   @Override
   public void onTick(class_310 c) {
      try {
         if (c.field_1724 == null || c.field_1761 == null) return;

         // Inventory not open → do nothing, reset flag
         if (!(c.field_1755 instanceof class_490)) {
            didThisOpen = false;
            cd = 0;
            return;
         }

         // NEVER open, NEVER close, NEVER setScreen
         if (cd > 0) {
            cd--;
            return;
         }

         if (hasTotemOffhand(c)) return;
         if (Boolean.TRUE.equals(oncePerOpen.get()) && didThisOpen) return;

         class_1723 h = c.field_1724.field_7498;
         int slot = findTotem(h);
         if (slot < 0) return;

         // Single SWAP to offhand — one click, then wait
         c.field_1761.method_2906(h.field_7763, slot, 40, class_1713.field_7791, c.field_1724);
         didThisOpen = true;
         cd = Math.max(1, delay.get().intValue());
      } catch (Throwable ignored) {
      }
   }

   @Override
   public String getDisplay() {
      return isEnabled() ? "InvTotem" : "";
   }
}
