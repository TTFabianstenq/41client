package dev.anticheatqa.module.render;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_1923;
import net.minecraft.class_2338;
import net.minecraft.class_2586;
import net.minecraft.class_2636;
import net.minecraft.class_2818;
import net.minecraft.class_310;

public class SpawnerNameTags extends Module {
   public static final List<class_2338> spawners = new ArrayList<>();
   public final Module.NumberSetting radius = new Module.NumberSetting("Radius", 4.0, 1.0, 8.0, 1.0);
   private int tick;

   public SpawnerNameTags() {
      super("Spawner Name Tags", "Tracks mob spawners with distance labels on HUD.", Category.RENDER);
      this.settings.add(this.radius);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1687 != null && c.field_1724 != null) {
         if (++this.tick % 25 == 0) {
            spawners.clear();
            int r = this.radius.get().intValue();
            class_1923 o = c.field_1724.method_31476();

            for (int dx = -r; dx <= r; dx++) {
               for (int dz = -r; dz <= r; dz++) {
                  class_2818 ch = c.field_1687.method_2935().method_21730(o.field_9181 + dx, o.field_9180 + dz);
                  if (ch != null) {
                     for (class_2586 be : ch.method_12214().values()) {
                        if (be instanceof class_2636) {
                           spawners.add(be.method_11016().method_10062());
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public String getDisplay() {
      return this.isEnabled() ? "Spawners:" + spawners.size() : "";
   }
}
