package dev.anticheatqa.module.monitor;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1268;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_310;

public class TotemCounter extends Module {
   private int total;
   private boolean offhand;

   public TotemCounter() {
      super("Totem Counter", "Totems in inv + offhand. Crystal PvP readiness.", Category.MONITOR);
   }

   @Override
   public void onTick(class_310 client) {
      if (client.field_1724 == null) {
         this.total = 0;
         this.offhand = false;
      } else {
         int n = 0;

         for (int i = 0; i < client.field_1724.method_31548().method_5439(); i++) {
            class_1799 stack = client.field_1724.method_31548().method_5438(i);
            if (stack.method_31574(class_1802.field_8288)) {
               n += stack.method_7947();
            }
         }

         class_1799 off = client.field_1724.method_5998(class_1268.field_5810);
         this.offhand = off.method_31574(class_1802.field_8288);
         if (this.offhand) {
         }

         this.total = n;
      }
   }

   @Override
   public String getDisplay() {
      return !this.isEnabled() ? "" : String.format("Totems:%d%s", this.total, this.offhand ? " [OFFHAND]" : "");
   }
}
