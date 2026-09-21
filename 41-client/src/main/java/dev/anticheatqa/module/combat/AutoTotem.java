
package dev.anticheatqa.module.combat;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1713;
import net.minecraft.class_1802;
import net.minecraft.class_310;
import net.minecraft.class_490;

/** RAGE: swap totem to offhand as fast as possible (inv open or via screen handler). */
public class AutoTotem extends Module {
   public final Module.NumberSetting delay = new Module.NumberSetting("Delay Ticks", 0.0, 0.0, 20.0, 1.0);
   public final Module.NumberSetting health = new Module.NumberSetting("Health Threshold", 20.0, 0.0, 20.0, 0.5);
   private int cd;

   public AutoTotem() {
      super("Auto Totem", "RAGE 0-delay offhand totem refill.", Category.COMBAT);
      settings.add(delay);
      settings.add(health);
   }

   @Override
   public void onTick(class_310 c) {
      try {
         if (c.field_1724 == null || c.field_1761 == null) return;
         if (cd > 0) { cd--; return; }
         if (c.field_1724.method_6079().method_31574(class_1802.field_8288)) return;
         if (c.field_1724.method_6032() > health.get().floatValue() && health.get() < 20.0) {
            // still refill always at 20 threshold = always
         }
         // Prefer when inv open for reliable click
         if (!(c.field_1755 instanceof class_490) && c.field_1755 != null) return;
         var h = c.field_1724.field_7498;
         for (int i = 9; i < 45; i++) {
            try {
               if (h.method_7611(i).method_7677().method_31574(class_1802.field_8288)) {
                  c.field_1761.method_2906(h.field_7763, i, 40, class_1713.field_7791, c.field_1724);
                  cd = delay.get().intValue();
                  return;
               }
            } catch (Throwable ignored) {}
         }
      } catch (Throwable ignored) {}
   }

   @Override
   public String getDisplay() {
      return isEnabled() ? "Totem:RAGE" : "";
   }
}
