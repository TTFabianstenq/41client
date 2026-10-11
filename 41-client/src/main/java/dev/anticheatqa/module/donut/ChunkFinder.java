package dev.anticheatqa.module.donut;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_1923;
import net.minecraft.class_2818;
import net.minecraft.class_310;

public class ChunkFinder extends Module {
   public static final List<String> results = new ArrayList<>();
   public final Module.NumberSetting minBE = new Module.NumberSetting("Min Block Entities", 6.0, 1.0, 30.0, 1.0);
   public final Module.NumberSetting radius = new Module.NumberSetting("Radius", 5.0, 1.0, 10.0, 1.0);
   private int tick;

   public ChunkFinder() {
      super("Chunk Finder", "Flags chunks with high block-entity density (bases).", Category.DONUT);
      this.settings.add(this.minBE);
      this.settings.add(this.radius);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1687 != null && c.field_1724 != null) {
         if (++this.tick % 40 == 0) {
            results.clear();
            int r = this.radius.get().intValue();
            class_1923 o = c.field_1724.method_31476();

            for (int dx = -r; dx <= r; dx++) {
               for (int dz = -r; dz <= r; dz++) {
                  class_2818 ch = c.field_1687.method_2935().method_21730(o.field_9181 + dx, o.field_9180 + dz);
                  if (ch != null) {
                     int n = ch.method_12214().size();
                     if (n >= this.minBE.get().intValue()) {
                        results.add(String.format("[%d,%d] be=%d", o.field_9181 + dx, o.field_9180 + dz, n));
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public String getDisplay() {
      return this.isEnabled() ? "Chunks:" + results.size() : "";
   }
}
