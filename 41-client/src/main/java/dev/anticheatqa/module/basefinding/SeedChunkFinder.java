package dev.anticheatqa.module.basefinding;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_1923;
import net.minecraft.class_310;

public class SeedChunkFinder extends Module {
   public static final List<long[]> hits = new ArrayList<>();
   public final Module.NumberSetting radius = new Module.NumberSetting("Radius", 10.0, 2.0, 20.0, 1.0);
   public final Module.NumberSetting mod = new Module.NumberSetting("Modulo", 16.0, 2.0, 64.0, 1.0);
   private int tick;

   public SeedChunkFinder() {
      super("Seed Chunk Finder", "Flags chunks where X,Z share a modulo pattern (seed structure grids).", Category.WORLD);
      this.settings.add(this.radius);
      this.settings.add(this.mod);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1724 != null) {
         if (++this.tick % 40 == 0) {
            hits.clear();
            class_1923 o = c.field_1724.method_31476();
            int r = this.radius.get().intValue();
            int m = Math.max(2, this.mod.get().intValue());

            for (int dx = -r; dx <= r; dx++) {
               for (int dz = -r; dz <= r; dz++) {
                  int cx = o.field_9181 + dx;
                  int cz = o.field_9180 + dz;
                  if (Math.floorMod(cx, m) == 0 && Math.floorMod(cz, m) == 0) {
                     hits.add(new long[]{cx, cz});
                  }
               }
            }
         }
      }
   }
}
