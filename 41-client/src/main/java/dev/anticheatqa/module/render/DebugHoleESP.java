package dev.anticheatqa.module.render;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_2338;
import net.minecraft.class_310;
import net.minecraft.class_2338.class_2339;

public class DebugHoleESP extends Module {
   public static final List<class_2338> holes = new ArrayList<>();
   public final Module.NumberSetting range = new Module.NumberSetting("Range", 20.0, 4.0, 48.0, 1.0);
   private int tick;

   public DebugHoleESP() {
      super("Debug Hole ESP", "Finds 1x1 safe holes (obsidian/bedrock) for PvP.", Category.RENDER);
      this.settings.add(this.range);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1687 != null && c.field_1724 != null) {
         if (++this.tick % 15 == 0) {
            holes.clear();
            class_2338 o = c.field_1724.method_24515();
            int r = this.range.get().intValue();
            class_2339 m = new class_2339();

            for (int x = -r; x <= r; x++) {
               for (int z = -r; z <= r; z++) {
                  for (int y = -3; y <= 3; y++) {
                     m.method_10103(o.method_10263() + x, o.method_10264() + y, o.method_10260() + z);
                     if (c.field_1687.method_8320(m).method_26215() && c.field_1687.method_8320(m.method_10084()).method_26215()) {
                        String down = c.field_1687.method_8320(m.method_10074()).method_26204().method_63499();
                        if (down.contains("obsidian") || down.contains("bedrock")) {
                           boolean ok = true;

                           for (class_2338 s : new class_2338[]{m.method_10095(), m.method_10072(), m.method_10078(), m.method_10067()}) {
                              String id = c.field_1687.method_8320(s).method_26204().method_63499();
                              if (!id.contains("obsidian") && !id.contains("bedrock") && !id.contains("crying")) {
                                 ok = false;
                                 break;
                              }
                           }

                           if (ok) {
                              holes.add(m.method_10062());
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
