package dev.anticheatqa.module.combat;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1657;
import net.minecraft.class_1743;
import net.minecraft.class_1799;
import net.minecraft.class_310;
import net.minecraft.class_3966;

public class ShieldBreaker extends Module {
   public final Module.NumberSetting range = new Module.NumberSetting("Range", 4.5, 2.0, 6.0, 0.1);
   public final Module.BooleanSetting requireAxe = new Module.BooleanSetting("Require Axe", true);
   public final Module.NumberSetting cooldown = new Module.NumberSetting("Cooldown Ticks", 5.0, 0.0, 20.0, 1.0);
   private int cd;
   private int breaks;

   public ShieldBreaker() {
      super("Shield Breaker", "Prioritizes attacking shield-blocking players with an axe.", Category.COMBAT);
      this.settings.add(this.range);
      this.settings.add(this.requireAxe);
      this.settings.add(this.cooldown);
   }

   @Override
   public void onTick(class_310 client) {
      if (client.field_1724 != null && client.field_1761 != null) {
         if (client.field_1755 == null) {
            if (this.cd > 0) {
               this.cd--;
            } else {
               class_1799 main = client.field_1724.method_6047();
               if (!this.requireAxe.get() || main.method_7909() instanceof class_1743) {
                  if (client.field_1765 instanceof class_3966 ehr
                     && ehr.method_17782() instanceof class_1657 target
                     && target.method_5805()
                     && target.method_6039()
                     && client.field_1724.method_5739(target) <= this.range.get()) {
                     client.field_1761.method_2918(client.field_1724, target);
                     client.field_1724.method_6104(client.field_1724.method_6058());
                     this.breaks++;
                     this.cd = (int)this.cooldown.get().doubleValue();
                  }
               }
            }
         }
      }
   }

   @Override
   public String getDisplay() {
      return this.isEnabled() ? "ShieldBrk:" + this.breaks : "";
   }
}
