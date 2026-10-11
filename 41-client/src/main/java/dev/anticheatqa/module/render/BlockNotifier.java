package dev.anticheatqa.module.render;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.HashSet;
import net.minecraft.class_2338;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_2338.class_2339;

public class BlockNotifier extends Module {
   public final Module.TextSetting block = new Module.TextSetting("Block Contains", "diamond_ore");
   public final Module.NumberSetting range = new Module.NumberSetting("Range", 32.0, 8.0, 64.0, 1.0);
   private final HashSet<Long> seen = new HashSet<>();
   private int tick;

   public BlockNotifier() {
      super("Block Notifier", "Chat when a matching block appears in range.", Category.RENDER);
      this.settings.add(this.block);
      this.settings.add(this.range);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1687 != null && c.field_1724 != null) {
         if (++this.tick % 30 == 0) {
            String want = this.block.get().toLowerCase();
            class_2338 o = c.field_1724.method_24515();
            int r = this.range.get().intValue();
            class_2339 m = new class_2339();

            for (int x = -r; x <= r; x += 2) {
               for (int y = -16; y <= 16; y += 2) {
                  for (int z = -r; z <= r; z += 2) {
                     m.method_10103(o.method_10263() + x, o.method_10264() + y, o.method_10260() + z);
                     String id = c.field_1687.method_8320(m).method_26204().method_63499().toLowerCase();
                     if (id.contains(want)) {
                        long k = m.method_10063();
                        if (this.seen.add(k)) {
                           c.field_1724
                              .method_7353(
                                 class_2561.method_43470(
                                    "\u00a7a[SC] Block \u00a7f" + want + " \u00a7aat " + m.method_10263() + " " + m.method_10264() + " " + m.method_10260()
                                 ),
                                 false
                              );
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
