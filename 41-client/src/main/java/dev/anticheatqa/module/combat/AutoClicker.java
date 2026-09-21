package dev.anticheatqa.module.combat;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.class_310;
import net.minecraft.class_3966;
import net.minecraft.class_239.class_240;

public class AutoClicker extends Module {
   public final Module.NumberSetting minCps = new Module.NumberSetting("Min CPS", 8.0, 1.0, 20.0, 0.5);
   public final Module.NumberSetting maxCps = new Module.NumberSetting("Max CPS", 12.0, 1.0, 20.0, 0.5);
   public final Module.BooleanSetting left = new Module.BooleanSetting("Left Click", true);
   public final Module.BooleanSetting right = new Module.BooleanSetting("Right Click", false);
   public final Module.BooleanSetting onlyWeapon = new Module.BooleanSetting("Only On Target", false);
   public final Module.BooleanSetting randomize = new Module.BooleanSetting("Randomize", true);
   private long nextLeft;
   private long nextRight;

   public AutoClicker() {
      super("Auto Clicker", "Clicks at randomized CPS while held.", Category.COMBAT);
      this.settings.add(this.minCps);
      this.settings.add(this.maxCps);
      this.settings.add(this.left);
      this.settings.add(this.right);
      this.settings.add(this.onlyWeapon);
      this.settings.add(this.randomize);
   }

   @Override
   public void onTick(class_310 client) {
      if (client.field_1724 != null && client.field_1761 != null) {
         if (client.field_1755 == null) {
            long now = System.currentTimeMillis();
            if (this.left.get() && client.field_1690.field_1886.method_1434()) {
               if (this.onlyWeapon.get() && (client.field_1765 == null || client.field_1765.method_17783() != class_240.field_1331)) {
                  return;
               }

               if (now >= this.nextLeft) {
                  if (client.field_1765 instanceof class_3966 ehr) {
                     client.field_1761.method_2918(client.field_1724, ehr.method_17782());
                     client.field_1724.method_6104(client.field_1724.method_6058());
                  } else {
                     client.field_1724.method_6104(client.field_1724.method_6058());
                  }

                  this.nextLeft = now + this.delayMs(this.minCps.get(), this.maxCps.get());
               }
            }

            if (this.right.get() && client.field_1690.field_1904.method_1434() && now >= this.nextRight) {
               client.field_1761.method_2919(client.field_1724, client.field_1724.method_6058());
               this.nextRight = now + this.delayMs(this.minCps.get(), this.maxCps.get());
            }
         }
      }
   }

   private long delayMs(double min, double max) {
      if (max < min) {
         max = min;
      }

      double cps = this.randomize.get() ? ThreadLocalRandom.current().nextDouble(min, max + 0.01) : (min + max) * 0.5;
      if (cps < 0.5) {
         cps = 0.5;
      }

      return (long)(1000.0 / cps);
   }
}
