package dev.anticheatqa.module.combat;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1511;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_239.class_240;

/**
 * Break-only crystal assist. Crosshair preferred, angle-limited, jittered delay.
 */
public class AutoHitCrystal extends Module {
   public final Module.NumberSetting range = new Module.NumberSetting("Range", 4.2, 2.5, 5.5, 0.1);
   public final Module.NumberSetting delay = new Module.NumberSetting("Delay", 2.0, 1.0, 8.0, 1.0);
   public final Module.NumberSetting jitter = new Module.NumberSetting("Jitter", 1.0, 0.0, 3.0, 1.0);
   public final Module.NumberSetting maxAngle = new Module.NumberSetting("Max Angle", 30.0, 10.0, 90.0, 1.0);
   public final Module.BooleanSetting requireLook = new Module.BooleanSetting("Require Look", true);

   private int cd;

   public AutoHitCrystal() {
      super("Auto Hit Crystal", "Breaks crystals you look at with humanized delay.", Category.COMBAT);
      settings.add(range);
      settings.add(delay);
      settings.add(jitter);
      settings.add(maxAngle);
      settings.add(requireLook);
   }

   @Override
   protected void onEnable() {
      cd = 0;
   }

   private boolean lookingAt(class_310 c, class_1297 e, double maxDeg) {
      class_243 eye = c.field_1724.method_33571();
      class_243 look = c.field_1724.method_5828(1.0F);
      class_243 to = new class_243(e.method_23317(), e.method_23318(), e.method_23321()).method_1031(0.0, e.method_17682() * 0.5, 0.0).method_1020(eye).method_1029();
      double dot = look.method_1026(to);
      if (dot > 1.0) dot = 1.0;
      if (dot < -1.0) dot = -1.0;
      return Math.toDegrees(Math.acos(dot)) <= maxDeg;
   }

   @Override
   public void onTick(class_310 c) {
      try {
         if (c.field_1724 == null || c.field_1687 == null || c.field_1761 == null) return;
         if (c.field_1755 != null) return;
         if (cd > 0) { cd--; return; }

         double rng = range.get();
         class_1511 best = null;
         double bestDist = rng + 1.0;

         if (c.field_1765 != null && c.field_1765.method_17783() == class_240.field_1331) {
            class_1297 te = ((net.minecraft.class_3966) c.field_1765).method_17782();
            if (te instanceof class_1511 ec && ec.method_5805() && c.field_1724.method_5739(ec) <= rng) {
               best = ec;
            }
         }

         if (best == null) {
            boolean needLook = Boolean.TRUE.equals(requireLook.get());
            double ang = maxAngle.get();
            for (class_1297 e : c.field_1687.method_18112()) {
               if (!(e instanceof class_1511) || !e.method_5805()) continue;
               double d = c.field_1724.method_5739(e);
               if (d > rng || d >= bestDist) continue;
               if (needLook && !lookingAt(c, e, ang)) continue;
               best = (class_1511) e;
               bestDist = d;
            }
         }

         if (best == null) return;
         if (c.field_1724.method_7261(0.5F) < 0.8F) return;

         c.field_1761.method_2918(c.field_1724, best);
         c.field_1724.method_6104(class_1268.field_5808);
         int base = Math.max(1, delay.get().intValue());
         int j = Math.max(0, jitter.get().intValue());
         cd = base + (j > 0 ? ThreadLocalRandom.current().nextInt(j + 1) : 0);
      } catch (Throwable ignored) {
      }
   }

   @Override
   public String getDisplay() {
      return isEnabled() ? "HitCry" : "";
   }
}
