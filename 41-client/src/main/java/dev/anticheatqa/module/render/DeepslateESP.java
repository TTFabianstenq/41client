package dev.anticheatqa.module.render;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_2338;
import net.minecraft.class_310;
import net.minecraft.class_2338.class_2339;

public class DeepslateESP extends Module {
   public static final List<class_2338> positions = new ArrayList<>();
   public final Module.NumberSetting range = new Module.NumberSetting("Range", 32.0, 8.0, 64.0, 1.0);
   public final Module.NumberSetting maxBlocks = new Module.NumberSetting("Max Blocks", 200.0, 20.0, 500.0, 10.0);
   private int tick;

   public DeepslateESP() {
      super("Deepslate ESP", "Highlights deepslate diamond / ancient debris-related ores.", Category.RENDER);
      this.settings.add(this.range);
      this.settings.add(this.maxBlocks);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1687 != null && c.field_1724 != null) {
         if (++this.tick % 25 == 0) {
            positions.clear();
            class_2338 o = c.field_1724.method_24515();
            int r = this.range.get().intValue();
            int max = this.maxBlocks.get().intValue();
            class_2339 m = new class_2339();

            for (int x = -r; x <= r; x += 2) {
               for (int y = -r; y <= r; y += 2) {
                  for (int z = -r; z <= r; z += 2) {
                     if (positions.size() >= max) {
                        return;
                     }

                     m.method_10103(o.method_10263() + x, o.method_10264() + y, o.method_10260() + z);
                     String id = c.field_1687.method_8320(m).method_26204().method_63499().toLowerCase();
                     if (id.contains("deepslate_diamond")
                        || id.contains("deepslate_gold")
                        || id.contains("deepslate_iron")
                        || id.contains("deepslate_coal")
                        || id.contains("deepslate_lapis")
                        || id.contains("deepslate_redstone")
                        || id.contains("deepslate_emerald")
                        || id.contains("deepslate_copper")
                        || id.contains("ancient_debris")) {
                        positions.add(m.method_10062());
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public String getDisplay() {
      return this.isEnabled() ? "Deep:" + positions.size() : "";
   }
}
