package dev.anticheatqa.module.visuals;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.class_1923;
import net.minecraft.class_2338;
import net.minecraft.class_2586;
import net.minecraft.class_2636;
import net.minecraft.class_2818;
import net.minecraft.class_310;

public class SpawnerESP extends Module {
   public static final List<class_2338> positions = new CopyOnWriteArrayList<>();
   public final Module.NumberSetting range = new Module.NumberSetting("Range", 48.0, 8.0, 96.0, 4.0);
   private int count;
   private int scanTicker;

   public SpawnerESP() {
      super("Spawner ESP", "3D through-wall outlines on mob spawners (Donut farms).", Category.RENDER);
      this.settings.add(this.range);
   }

   @Override
   public void onTick(class_310 client) {
      try {
         this.scanTicker++;
         if (this.scanTicker % 10 != 0) {
            return;
         }

         if (client.field_1687 == null || client.field_1724 == null) {
            return;
         }

         positions.clear();
         int r = this.range.get().intValue();
         class_2338 playerPos = client.field_1724.method_24515();
         int n = 0;
         int chunkR = r / 16 + 1;
         class_1923 pc = new class_1923(playerPos);

         for (int cx = -chunkR; cx <= chunkR; cx++) {
            for (int cz = -chunkR; cz <= chunkR; cz++) {
               class_2818 chunk = client.field_1687.method_8497(pc.field_9181 + cx, pc.field_9180 + cz);
               if (chunk != null) {
                  for (class_2586 be : chunk.method_12214().values()) {
                     if (be instanceof class_2636) {
                        class_2338 pos = be.method_11016();
                        if (pos.method_19771(playerPos, r)) {
                           positions.add(pos.method_10062());
                           n++;
                        }
                     }
                  }
               }
            }
         }

         this.count = n;
      } catch (Throwable var13) {
      }
   }

   @Override
   protected void onDisable() {
      positions.clear();
      this.count = 0;
   }

   @Override
   public String getDisplay() {
      return this.isEnabled() ? "Spawners:" + this.count : "";
   }
}
