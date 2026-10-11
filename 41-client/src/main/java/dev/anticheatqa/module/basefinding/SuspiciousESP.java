package dev.anticheatqa.module.basefinding;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_1923;
import net.minecraft.class_2338;
import net.minecraft.class_2586;
import net.minecraft.class_2611;
import net.minecraft.class_2614;
import net.minecraft.class_2636;
import net.minecraft.class_2818;
import net.minecraft.class_310;

public class SuspiciousESP extends Module {
   public static final List<class_2338> positions = new ArrayList<>();
   public final Module.NumberSetting radius = new Module.NumberSetting("Radius", 4.0, 1.0, 8.0, 1.0);
   private int tick;

   public SuspiciousESP() {
      super("Suspicious ESP", "ESP for spawners, hoppers, and ender chests in loaded chunks.", Category.WORLD);
      this.settings.add(this.radius);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1687 != null && c.field_1724 != null) {
         if (++this.tick % 20 == 0) {
            positions.clear();
            int r = this.radius.get().intValue();
            class_1923 o = c.field_1724.method_31476();

            for (int dx = -r; dx <= r; dx++) {
               for (int dz = -r; dz <= r; dz++) {
                  class_2818 ch = c.field_1687.method_2935().method_21730(o.field_9181 + dx, o.field_9180 + dz);
                  if (ch != null) {
                     for (class_2586 be : ch.method_12214().values()) {
                        if (be instanceof class_2636 || be instanceof class_2614 || be instanceof class_2611) {
                           positions.add(be.method_11016().method_10062());
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
