package dev.anticheatqa.module.combat;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1713;
import net.minecraft.class_1802;
import net.minecraft.class_310;
import net.minecraft.class_490;

public class HoverTotem extends Module {
   public final Module.NumberSetting delay = new Module.NumberSetting("Delay Ticks", 2.0, 0.0, 10.0, 1.0);
   private int cd;

   public HoverTotem() {
      super("Hover Totem", "When inventory is open, moves totem to offhand if missing.", Category.COMBAT);
      this.settings.add(this.delay);
   }

   @Override
   public void onTick(class_310 client) {
      if (client.field_1724 != null && client.field_1761 != null) {
         if (client.field_1755 instanceof class_490) {
            if (this.cd > 0) {
               this.cd--;
            } else if (!client.field_1724.method_6079().method_31574(class_1802.field_8288)) {
               for (int i = 9; i < 45; i++) {
                  if (client.field_1724.field_7498.method_7611(i).method_7677().method_31574(class_1802.field_8288)) {
                     client.field_1761.method_2906(client.field_1724.field_7498.field_7763, i, 40, class_1713.field_7791, client.field_1724);
                     this.cd = this.delay.get().intValue();
                     return;
                  }
               }
            }
         }
      }
   }
}
