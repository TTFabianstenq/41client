package dev.anticheatqa.module.combat;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1268;
import net.minecraft.class_1799;
import net.minecraft.class_2868;
import net.minecraft.class_310;
import net.minecraft.class_3965;
import net.minecraft.class_239.class_240;

/** Glazed double-anchor sequence — hardened (abort if items missing). */
public class DoubleAnchor extends Module {
   public final Module.BooleanSetting holdActivate = new Module.BooleanSetting("Hold Activate", true);
   public final Module.NumberSetting stepDelay = new Module.NumberSetting("Step Delay", 1.0, 0.0, 5.0, 1.0);
   public final Module.NumberSetting totemSlot = new Module.NumberSetting("Totem Slot", 1.0, 1.0, 9.0, 1.0);

   private int step;
   private int wait;
   private boolean running;
   private class_3965 locked;

   public DoubleAnchor() {
      super("Double Anchor", "Glazed-style double anchor place/charge/explode sequence.", Category.COMBAT);
      settings.add(holdActivate);
      settings.add(stepDelay);
      settings.add(totemSlot);
   }

   private int findExact(class_310 c, String path) {
      for (int i = 0; i < 9; i++) {
         class_1799 s = c.field_1724.method_31548().method_5438(i);
         if (s.method_7960()) continue;
         String id = s.method_7909().toString().toLowerCase();
         int colon = id.lastIndexOf(':');
         String p = colon >= 0 ? id.substring(colon + 1) : id;
         // strip trailing junk
         if (p.contains("@")) p = p.substring(0, p.indexOf('@'));
         if (p.equals(path) || p.endsWith("." + path) || id.endsWith("/" + path) || id.endsWith(":" + path)) return i;
         if (p.equals(path)) return i;
      }
      // looser contains but reject dust when looking for glowstone block
      for (int i = 0; i < 9; i++) {
         class_1799 s = c.field_1724.method_31548().method_5438(i);
         if (s.method_7960()) continue;
         String id = s.method_7909().toString().toLowerCase();
         if (path.equals("glowstone") && id.contains("glowstone") && !id.contains("dust")) return i;
         if (path.equals("respawn_anchor") && id.contains("respawn_anchor")) return i;
         if (path.equals("totem") && id.contains("totem_of_undying")) return i;
      }
      return -1;
   }

   private void select(class_310 c, int slot) {
      if (slot < 0 || slot > 8) return;
      c.field_1724.method_31548().method_61496(slot);
      try { c.field_1724.field_3944.method_52787(new class_2868(slot)); } catch (Throwable ignored) {}
   }

   private void interact(class_310 c, class_3965 hit) {
      c.field_1761.method_2896(c.field_1724, class_1268.field_5808, hit);
      c.field_1724.method_6104(class_1268.field_5808);
   }

   private void abort() {
      running = false;
      step = 0;
      locked = null;
      wait = 0;
   }

   @Override
   protected void onEnable() { abort(); }

   @Override
   public void onTick(class_310 c) {
      try {
         if (c.field_1724 == null || c.field_1761 == null) return;
         if (c.field_1755 != null) return;
         if (wait > 0) { wait--; return; }

         boolean held = !Boolean.TRUE.equals(holdActivate.get())
            || c.field_1690.field_1904.method_1434()
            || c.field_1690.field_1886.method_1434();
         if (!held) { abort(); return; }

         if (!running) {
            if (!(c.field_1765 instanceof class_3965 bhr) || c.field_1765.method_17783() != class_240.field_1332) return;
            if (findExact(c, "respawn_anchor") < 0 || findExact(c, "glowstone") < 0) return;
            locked = bhr;
            running = true;
            step = 0;
         }
         if (locked == null) { abort(); return; }

         int d = Math.max(0, stepDelay.get().intValue());
         int slot;
         switch (step) {
            case 0 -> {
               slot = findExact(c, "respawn_anchor");
               if (slot < 0) { abort(); return; }
               select(c, slot);
            }
            case 1 -> interact(c, locked);
            case 2 -> {
               slot = findExact(c, "glowstone");
               if (slot < 0) { abort(); return; }
               select(c, slot);
            }
            case 3 -> interact(c, locked);
            case 4 -> {
               slot = findExact(c, "respawn_anchor");
               if (slot < 0) { abort(); return; }
               select(c, slot);
            }
            case 5 -> { interact(c, locked); interact(c, locked); }
            case 6 -> {
               slot = findExact(c, "glowstone");
               if (slot < 0) { abort(); return; }
               select(c, slot);
            }
            case 7 -> interact(c, locked);
            case 8 -> select(c, totemSlot.get().intValue() - 1);
            case 9 -> interact(c, locked);
            default -> { abort(); return; }
         }
         step++;
         wait = d;
      } catch (Throwable ignored) {
         abort();
      }
   }

   @Override
   public String getDisplay() {
      return isEnabled() ? (running ? "DAnchor:" + step : "DAnchor") : "";
   }
}
