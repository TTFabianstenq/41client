package dev.anticheatqa.module.combat;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1713;
import net.minecraft.class_1802;
import net.minecraft.class_310;

public class AutoDoubleHand extends Module {
   public final Module.NumberSetting health = new Module.NumberSetting("Health", 6.0, 1.0, 14.0, 0.5);
   private float lastHp = 20.0F;

   public AutoDoubleHand() {
      super("Auto Double Hand", "Swaps totem to offhand after taking damage / low HP.", Category.COMBAT);
      this.settings.add(this.health);
   }

   @Override
   public void onTick(class_310 client) {
      if (client.field_1724 != null && client.field_1761 != null) {
         if (client.field_1755 == null) {
            float hp = client.field_1724.method_6032() + client.field_1724.method_6067();
            boolean popped = hp < this.lastHp - 0.5F;
            this.lastHp = hp;
            if (popped || !(hp > this.health.get())) {
               if (!client.field_1724.method_6079().method_31574(class_1802.field_8288)) {
                  for (int i = 9; i < 45; i++) {
                     if (client.field_1724.field_7498.method_7611(i).method_7677().method_31574(class_1802.field_8288)) {
                        client.field_1761.method_2906(client.field_1724.field_7498.field_7763, i, 40, class_1713.field_7791, client.field_1724);
                        return;
                     }
                  }
               }
            }
         }
      }
   }
}
