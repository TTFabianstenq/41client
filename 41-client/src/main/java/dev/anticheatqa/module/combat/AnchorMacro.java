package dev.anticheatqa.module.combat;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1268;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2680;
import net.minecraft.class_2868;
import net.minecraft.class_310;
import net.minecraft.class_3965;
import net.minecraft.class_4969;
import net.minecraft.class_239.class_240;

public class AnchorMacro extends Module {
   public final Module.BooleanSetting holdActivate = new Module.BooleanSetting("Hold Activate", true);
   public final Module.NumberSetting switchDelay = new Module.NumberSetting("Switch Delay", 0.0, 0.0, 10.0, 1.0);
   public final Module.NumberSetting glowstoneDelay = new Module.NumberSetting("Glowstone Delay", 0.0, 0.0, 10.0, 1.0);
   public final Module.NumberSetting explodeDelay = new Module.NumberSetting("Explode Delay", 0.0, 0.0, 10.0, 1.0);
   public final Module.NumberSetting totemSlot = new Module.NumberSetting("Totem Slot", 1.0, 1.0, 9.0, 1.0);
   public final Module.BooleanSetting preferTotem = new Module.BooleanSetting("Prefer Totem In Hotbar", true);

   private int switchCd, glowCd, explodeCd;
   private boolean hasCharged, hasExploded;

   public AnchorMacro() {
      super("Anchor Macro", "Charge+explode anchors. Hold click. Never swaps to crystal.", Category.COMBAT);
      settings.add(holdActivate);
      settings.add(switchDelay);
      settings.add(glowstoneDelay);
      settings.add(explodeDelay);
      settings.add(totemSlot);
      settings.add(preferTotem);
   }

   @Override
   protected void onEnable() {
      switchCd = glowCd = explodeCd = 0;
      hasCharged = hasExploded = false;
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

   private boolean isCrystalStack(class_1799 s) {
      if (s.method_7960()) return false;
      try {
         if (s.method_31574(class_1802.field_8301)) return true;
      } catch (Throwable ignored) {
      }
      return s.method_7909().toString().toLowerCase().contains("end_crystal");
   }

   private boolean isGlowStack(class_1799 s) {
      if (s.method_7960()) return false;
      try {
         if (s.method_31574(class_1802.field_8801)) return true;
      } catch (Throwable ignored) {
      }
      String id = s.method_7909().toString().toLowerCase();
      return id.contains("glowstone") && !id.contains("dust");
   }

   private int findGlow(class_310 c) {
      for (int i = 0; i < 9; i++) {
         if (isGlowStack(c.field_1724.method_31548().method_5438(i))) return i;
      }
      return -1;
   }

   private boolean holdingGlow(class_310 c) {
      return isGlowStack(c.field_1724.method_6047());
   }

   /** Never return a crystal slot. Prefer totem, else configured slot, else empty/sword. */
   private int explodeSlot(class_310 c) {
      if (Boolean.TRUE.equals(preferTotem.get())) {
         for (int i = 0; i < 9; i++) {
            class_1799 s = c.field_1724.method_31548().method_5438(i);
            if (s.method_7960()) continue;
            if (isCrystalStack(s) || isGlowStack(s)) continue;
            try {
               if (s.method_31574(class_1802.field_8288)) return i;
            } catch (Throwable ignored) {
            }
            if (s.method_7909().toString().toLowerCase().contains("totem")) return i;
         }
      }
      int cfg = Math.max(0, Math.min(8, totemSlot.get().intValue() - 1));
      class_1799 at = c.field_1724.method_31548().method_5438(cfg);
      if (!isGlowStack(at) && !isCrystalStack(at)) return cfg;
      for (int i = 0; i < 9; i++) {
         class_1799 s = c.field_1724.method_31548().method_5438(i);
         if (!isGlowStack(s) && !isCrystalStack(s)) return i;
      }
      return cfg;
   }

   private int charges(class_2680 state) {
      try {
         return (Integer) state.method_11654(class_4969.field_23153);
      } catch (Throwable t) {
         String s = state.toString().toLowerCase();
         for (int i = 4; i >= 1; i--) {
            if (s.contains("charges=" + i)) return i;
         }
         return 0;
      }
   }

   private boolean isAnchorBlock(class_310 c, class_2338 pos) {
      class_2680 st = c.field_1687.method_8320(pos);
      try {
         if (st.method_27852(class_2246.field_23152)) return true;
      } catch (Throwable ignored) {
      }
      return st.method_26204().method_63499().toLowerCase().contains("respawn_anchor");
   }

   private void interact(class_310 c, class_3965 hit) {
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
         if (!shouldRun(c)) {
            hasCharged = hasExploded = false;
            return;
         }
         // Holding anchor item to PLACE it — don't run charge logic on the ground
         class_1799 main = c.field_1724.method_6047();
         String mid = main.method_7909().toString().toLowerCase();
         if (mid.contains("respawn_anchor")) {
            // only intervene if crosshair is already on a placed anchor block
            if (!(c.field_1765 instanceof class_3965 bhr0)
               || c.field_1765.method_17783() != class_240.field_1332
               || !isAnchorBlock(c, bhr0.method_17777())) {
               return;
            }
         }

         if (!(c.field_1765 instanceof class_3965 bhr) || c.field_1765.method_17783() != class_240.field_1332) {
            hasCharged = hasExploded = false;
            return;
         }
         class_2338 pos = bhr.method_17777();
         if (!isAnchorBlock(c, pos)) {
            hasCharged = hasExploded = false;
            return;
         }

         if (switchCd > 0) { switchCd--; return; }
         if (glowCd > 0) { glowCd--; return; }
         if (explodeCd > 0) { explodeCd--; return; }

         int ch = charges(c.field_1687.method_8320(pos));

         if (ch <= 0 && !hasCharged) {
            if (!holdingGlow(c)) {
               int g = findGlow(c);
               if (g < 0) return;
               select(c, g);
               switchCd = Math.max(0, switchDelay.get().intValue());
               return;
            }
            interact(c, bhr);
            hasCharged = true;
            hasExploded = false;
            glowCd = Math.max(0, glowstoneDelay.get().intValue());
            return;
         }

         if (ch > 0 && !hasExploded) {
            int want = explodeSlot(c);
            if (c.field_1724.method_31548().method_67532() != want || holdingGlow(c)) {
               select(c, want);
               switchCd = Math.max(0, switchDelay.get().intValue());
               return;
            }
            interact(c, bhr);
            hasExploded = true;
            hasCharged = false;
            explodeCd = Math.max(0, explodeDelay.get().intValue());
            return;
         }

         if (hasCharged && hasExploded) {
            hasCharged = false;
            hasExploded = false;
         }
      } catch (Throwable ignored) {
      }
   }

   @Override
   public String getDisplay() {
      return isEnabled() ? "Anchor" : "";
   }
}
