package dev.anticheatqa.module.basefinding;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_2338;
import net.minecraft.class_310;
import net.minecraft.class_2338.class_2339;

public class LightFinder extends Module {
   public static final List<class_2338> dark = new ArrayList<>();
   public final Module.NumberSetting maxLight = new Module.NumberSetting("Max Light", 0.0, 0.0, 7.0, 1.0);
   public final Module.NumberSetting range = new Module.NumberSetting("Range", 12.0, 4.0, 32.0, 1.0);
   private int tick;

   public LightFinder() {
      super("Light Finder", "Marks dark blocks that can spawn mobs.", Category.WORLD);
      this.settings.add(this.maxLight);
      this.settings.add(this.range);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1687 != null && c.field_1724 != null) {
         if (++this.tick % 25 == 0) {
            dark.clear();
            class_2338 o = c.field_1724.method_24515();
            int r = this.range.get().intValue();
            class_2339 m = new class_2339();

            for (int x = -r; x <= r; x += 2) {
               for (int z = -r; z <= r; z += 2) {
                  for (int y = -4; y <= 8; y++) {
                     m.method_10103(o.method_10263() + x, o.method_10264() + y, o.method_10260() + z);
                     if (c.field_1687.method_8320(m).method_26215()) {
                        if (c.field_1687.method_22339(m) <= this.maxLight.get().intValue()) {
                           dark.add(m.method_10062());
                        }

                        if (dark.size() > 200) {
                           return;
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
