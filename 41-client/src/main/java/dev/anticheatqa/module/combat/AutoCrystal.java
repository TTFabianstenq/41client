package dev.anticheatqa.module.combat;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1511;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_239;
import net.minecraft.class_2868;
import net.minecraft.class_310;
import net.minecraft.class_3965;
import net.minecraft.class_3966;
import net.minecraft.class_239.class_240;

/**
 * STRICT Glazed-style: does NOTHING unless main hand is already an end crystal.
 * Never auto-picks crystal from hotbar. Will not steal your anchor/sword/etc.
 */
public class AutoCrystal extends Module {
   public final Module.BooleanSetting holdActivate = new Module.BooleanSetting("Hold Activate", true);
   public final Module.NumberSetting placeDelay = new Module.NumberSetting("Place Delay", 0.0, 0.0, 10.0, 1.0);
   public final Module.NumberSetting breakDelay = new Module.NumberSetting("Break Delay", 0.0, 0.0, 10.0, 1.0);
   public final Module.BooleanSetting placeObi = new Module.BooleanSetting("Place Obi If Missing", true);

   private int placeCd;
   private int breakCd;
   private int crystalSlot = -1; // slot we were on when sequence started

   public AutoCrystal() {
      super(
         "Auto Crystal",
         "ONLY works with crystal already in main hand. Never steals other slots.",
         Category.COMBAT
      );
      settings.add(holdActivate);
      settings.add(placeDelay);
      settings.add(breakDelay);
      settings.add(placeObi);
   }

   @Override
   protected void onEnable() {
      placeCd = breakCd = 0;
      crystalSlot = -1;
   }

   private boolean isCrystal(class_1799 s) {
      if (s == null || s.method_7960()) return false;
      try {
         if (s.method_31574(class_1802.field_8301)) return true;
      } catch (Throwable ignored) {
      }
      return s.method_7909().toString().toLowerCase().contains("end_crystal");
   }

   private boolean isObi(class_1799 s) {
      if (s == null || s.method_7960()) return false;
      try {
         if (s.method_31574(class_1802.field_8281)) return true;
      } catch (Throwable ignored) {
      }
      String id = s.method_7909().toString().toLowerCase();
      return id.contains("obsidian") && !id.contains("crying");
   }

   /** Hard gate: main hand MUST be crystal or we do nothing. */
   private boolean holdingCrystal(class_310 c) {
      return isCrystal(c.field_1724.method_6047());
   }

   private boolean shouldRun(class_310 c) {
      if (!Boolean.TRUE.equals(holdActivate.get())) return true;
      return c.field_1690.field_1886.method_1434() || c.field_1690.field_1904.method_1434();
   }

   private void select(class_310 c, int slot) {
      if (slot < 0 || slot > 8) return;
      if (c.field_1724.method_31548().method_67532() == slot) return;
      c.field_1724.method_31548().method_61496(slot);
      try {
         c.field_1724.field_3944.method_52787(new class_2868(slot));
      } catch (Throwable ignored) {
      }
   }

   private int findObiSlot(class_310 c) {
      for (int i = 0; i < 9; i++) {
         if (isObi(c.field_1724.method_31548().method_5438(i))) return i;
      }
      return -1;
   }

   private boolean isObiOrBedrock(class_310 c, class_2338 pos) {
      var st = c.field_1687.method_8320(pos);
      try {
         if (st.method_27852(class_2246.field_10540) || st.method_27852(class_2246.field_9987)) return true;
      } catch (Throwable ignored) {
      }
      String id = st.method_26204().method_63499().toLowerCase();
      return id.contains("obsidian") || id.contains("bedrock");
   }

   private void interactBlock(class_310 c, class_3965 hit) {
      c.field_1761.method_2896(c.field_1724, class_1268.field_5808, hit);
      try {
         c.field_1724.method_6104(class_1268.field_5808);
      } catch (Throwable ignored) {
      }
   }

   @Override
   public void onTick(class_310 c) {
      try {
         if (c.field_1724 == null || c.field_1687 == null || c.field_1761 == null) return;
         if (c.field_1755 != null) return;
         if (placeCd > 0) placeCd--;
         if (breakCd > 0) breakCd--;

         // ===== NEVER touch inventory unless crystal is already in hand =====
         if (!holdingCrystal(c)) {
            crystalSlot = -1;
            return;
         }

         // remember which slot the crystal is on
         crystalSlot = c.field_1724.method_31548().method_67532();

         if (!shouldRun(c)) return;

         class_239 hit = c.field_1765;
         if (hit == null) return;

         // BREAK
         if (hit instanceof class_3966 ehr && hit.method_17783() == class_240.field_1331) {
            if (breakCd > 0) return;
            class_1297 ent = ehr.method_17782();
            if (ent instanceof class_1511 && ent.method_5805()) {
               c.field_1761.method_2918(c.field_1724, ent);
               try {
                  c.field_1724.method_6104(class_1268.field_5808);
               } catch (Throwable ignored) {
               }
               breakCd = Math.max(0, breakDelay.get().intValue());
            }
            return;
         }

         // PLACE
         if (hit instanceof class_3965 bhr && hit.method_17783() == class_240.field_1332) {
            if (placeCd > 0) return;
            class_2338 pos = bhr.method_17777();
            boolean obi = isObiOrBedrock(c, pos);

            // Place obi only if already on crystal — brief swap, always restore crystal slot
            if (!obi && Boolean.TRUE.equals(placeObi.get())) {
               int o = findObiSlot(c);
               if (o < 0) return;
               int back = crystalSlot >= 0 ? crystalSlot : c.field_1724.method_31548().method_67532();
               select(c, o);
               interactBlock(c, bhr);
               select(c, back); // ALWAYS back to crystal — never leave on obi/random
               placeCd = Math.max(0, placeDelay.get().intValue());
               return;
            }

            // Place crystal on obi — already holding crystal, just click
            if (obi && c.field_1687.method_8320(pos.method_10084()).method_26215()) {
               interactBlock(c, bhr);
               placeCd = Math.max(0, placeDelay.get().intValue());
            }
         }
      } catch (Throwable ignored) {
      }
   }

   @Override
   public String getDisplay() {
      return isEnabled() ? "Crystal" : "";
   }
}
