package dev.anticheatqa.module.basefinding;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_2338;
import net.minecraft.class_310;
import net.minecraft.class_2338.class_2339;

public class HoleESP extends Module {
   public static final List<class_2338> holes = new ArrayList<>();
   public final Module.NumberSetting range = new Module.NumberSetting("Range", 16.0, 4.0, 48.0, 1.0);
   private int tick;

   public HoleESP() {
      super("Hole ESP", "Highlights 1x1 bedrock/obsidian holes nearby.", Category.WORLD);
      this.settings.add(this.range);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1687 != null && c.field_1724 != null) {
         if (++this.tick % 20 == 0) {
            holes.clear();
            class_2338 origin = c.field_1724.method_24515();
            int r = this.range.get().intValue();
            class_2339 m = new class_2339();

            for (int x = -r; x <= r; x++) {
               for (int z = -r; z <= r; z++) {
                  for (int y = -4; y <= 4; y++) {
                     m.method_10103(origin.method_10263() + x, origin.method_10264() + y, origin.method_10260() + z);
                     if (c.field_1687.method_8320(m).method_26215() && c.field_1687.method_8320(m.method_10074()).method_26212(c.field_1687, m.method_10074())) {
                        boolean walls = true;

                        for (class_2338 d : new class_2338[]{m.method_10095(), m.method_10072(), m.method_10078(), m.method_10067()}) {
                           String id = c.field_1687.method_8320(d).method_26204().method_63499();
                           if (!id.contains("obsidian") && !id.contains("bedrock") && !id.contains("crying")) {
                              walls = false;
                              break;
                           }
                        }

                        if (walls) {
                           holes.add(m.method_10062());
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
      return this.isEnabled() ? "Holes:" + holes.size() : "";
   }
}
