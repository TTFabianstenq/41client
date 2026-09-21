package dev.anticheatqa.module.combat;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_310;
import net.minecraft.class_3966;
import net.minecraft.class_239.class_240;

/** RAGE: minimal delay, hit as soon as cooldown allows. */
public class TriggerBot extends Module {
   public final Module.NumberSetting delay = new Module.NumberSetting("Delay Ms", 0.0, 0.0, 300.0, 5.0);
   public final Module.BooleanSetting players = new Module.BooleanSetting("Players", true);
   public final Module.BooleanSetting mobs = new Module.BooleanSetting("Mobs", true);
   public final Module.NumberSetting range = new Module.NumberSetting("Range", 4.5, 2.0, 6.0, 0.1);
   public final Module.NumberSetting cooldownMin = new Module.NumberSetting("Cooldown Min", 0.0, 0.0, 1.0, 0.05);

   private long lastHit;

   public TriggerBot() {
      super("Trigger Bot", "RAGE instant crosshair attacks.", Category.COMBAT);
      settings.add(delay);
      settings.add(players);
      settings.add(mobs);
      settings.add(range);
      settings.add(cooldownMin);
   }

   @Override
   public void onTick(class_310 c) {
      try {
         if (c.field_1724 == null || c.field_1761 == null || c.field_1755 != null) return;
         if (!(c.field_1765 instanceof class_3966 ehr) || c.field_1765.method_17783() != class_240.field_1331) return;
         class_1297 e = ehr.method_17782();
         if (!(e instanceof class_1309 le) || !le.method_5805() || e == c.field_1724) return;
         if (e instanceof class_1657) {
            if (!Boolean.TRUE.equals(players.get())) return;
         } else if (!Boolean.TRUE.equals(mobs.get())) return;
         if (c.field_1724.method_5739(e) > range.get()) return;
         long now = System.currentTimeMillis();
         if (now - lastHit < delay.get()) return;
         if (c.field_1724.method_7261(0.5F) < cooldownMin.get().floatValue()) return;
         c.field_1761.method_2918(c.field_1724, e);
         c.field_1724.method_6104(class_1268.field_5808);
         lastHit = now;
      } catch (Throwable ignored) {}
   }

   @Override
   public String getDisplay() {
      return isEnabled() ? "TB:RAGE" : "";
   }
}
