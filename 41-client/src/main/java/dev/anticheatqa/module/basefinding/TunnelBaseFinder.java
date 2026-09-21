package dev.anticheatqa.module.basefinding;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_2338;
import net.minecraft.class_310;

public class TunnelBaseFinder extends Module {
   public static final List<class_2338> tunnels = new ArrayList<>();
   public final Module.NumberSetting range = new Module.NumberSetting("Range", 24.0, 8.0, 48.0, 1.0);
   private int tick;

   public TunnelBaseFinder() {
      super("Tunnel Base Finder", "Detects long straight air tunnels (dug bases).", Category.WORLD);
      this.settings.add(this.range);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1687 != null && c.field_1724 != null) {
         if (++this.tick % 35 == 0) {
            tunnels.clear();
            class_2338 o = c.field_1724.method_24515();
            int r = this.range.get().intValue();

            for (int z = -r; z <= r; z += 4) {
               int run = 0;
               class_2338 start = null;

               for (int x = -r; x <= r; x++) {
                  class_2338 p = o.method_10069(x, 0, z);
                  if (c.field_1687.method_8320(p).method_26215() && c.field_1687.method_8320(p.method_10084()).method_26215()) {
                     if (run == 0) {
                        start = p;
                     }

                     run++;
                  } else {
                     if (run >= 12 && start != null) {
                        tunnels.add(start);
                     }

                     run = 0;
                     start = null;
                  }
               }
            }
         }
      }
   }

   @Override
   public String getDisplay() {
      return this.isEnabled() ? "Tunnels:" + tunnels.size() : "";
   }
}
