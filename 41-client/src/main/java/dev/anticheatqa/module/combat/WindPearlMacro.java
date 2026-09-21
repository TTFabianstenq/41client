package dev.anticheatqa.module.combat;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1268;
import net.minecraft.class_1799;
import net.minecraft.class_2868;
import net.minecraft.class_310;

/** Glazed pearl → wind charge. Toggle once to fire. */
public class WindPearlMacro extends Module {
   public final Module.NumberSetting delay = new Module.NumberSetting("Delay", 8.0, 0.0, 40.0, 1.0);

   private int step = -1;
   private int wait;
   private int prevSlot = -1;

   public WindPearlMacro() {
      super("Wind Pearl Macro", "Glazed: throw pearl, then wind charge after delay.", Category.COMBAT);
      settings.add(delay);
   }

   private int find(class_310 c, String needle) {
      for (int i = 0; i < 9; i++) {
         class_1799 s = c.field_1724.method_31548().method_5438(i);
         if (s.method_7960()) continue;
         String id = s.method_7909().toString().toLowerCase();
         if (id.contains(needle)) return i;
      }
      return -1;
   }

   private void select(class_310 c, int slot) {
      if (slot < 0 || slot > 8) return;
      c.field_1724.method_31548().method_61496(slot);
      try { c.field_1724.field_3944.method_52787(new class_2868(slot)); } catch (Throwable ignored) {}
   }

   @Override
   protected void onEnable() {
      step = 0;
      wait = 0;
      prevSlot = -1;
   }

   @Override
   protected void onDisable() {
      step = -1;
   }

   @Override
   public void onTick(class_310 c) {
      try {
         if (c.field_1724 == null || c.field_1761 == null) { setEnabled(false); return; }
         if (c.field_1755 != null) return;
         if (step < 0) return;
         if (wait > 0) { wait--; return; }

         if (step == 0) {
            int pearl = find(c, "ender_pearl");
            int wind = find(c, "wind_charge");
            if (pearl < 0) { setEnabled(false); return; }
            prevSlot = c.field_1724.method_31548().method_67532();
            select(c, pearl);
            c.field_1761.method_2919(c.field_1724, class_1268.field_5808);
            c.field_1724.method_6104(class_1268.field_5808);
            wait = Math.max(0, delay.get().intValue());
            step = wind >= 0 ? 1 : 2;
            return;
         }
         if (step == 1) {
            int wind = find(c, "wind_charge");
            if (wind >= 0) {
               select(c, wind);
               c.field_1761.method_2919(c.field_1724, class_1268.field_5808);
               c.field_1724.method_6104(class_1268.field_5808);
            }
            step = 2;
            return;
         }
         if (step == 2) {
            if (prevSlot >= 0) select(c, prevSlot);
            step = -1;
            setEnabled(false);
         }
      } catch (Throwable t) {
         setEnabled(false);
      }
   }
}
