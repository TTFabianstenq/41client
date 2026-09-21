package dev.anticheatqa.module.visuals;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.class_1923;
import net.minecraft.class_2338;
import net.minecraft.class_2586;
import net.minecraft.class_2601;
import net.minecraft.class_2608;
import net.minecraft.class_2614;
import net.minecraft.class_2627;
import net.minecraft.class_2818;
import net.minecraft.class_310;
import net.minecraft.class_3720;
import net.minecraft.class_3723;
import net.minecraft.class_3866;

public class StorageESP extends Module {
   public static List<class_2338> positions = new CopyOnWriteArrayList<>();
   public final Module.NumberSetting range = new Module.NumberSetting("Range", 32.0, 8.0, 64.0, 4.0);
   public final Module.BooleanSetting shulkers = new Module.BooleanSetting("Shulkers", true);
   public final Module.BooleanSetting hoppers = new Module.BooleanSetting("Hoppers", true);
   public final Module.BooleanSetting dispensers = new Module.BooleanSetting("Dispensers/Droppers", true);
   public final Module.BooleanSetting furnaces = new Module.BooleanSetting("Furnaces", true);
   private int count;
   private int scanTicker;

   public StorageESP() {
      super("Storage ESP", "Shulkers, hoppers, dispensers, furnaces — 3D through-wall outlines.", Category.RENDER);
      this.settings.add(this.range);
      this.settings.add(this.shulkers);
      this.settings.add(this.hoppers);
      this.settings.add(this.dispensers);
      this.settings.add(this.furnaces);
   }

   @Override
   public void onTick(class_310 client) {
      try {
         this.scanTicker++;
         if (this.scanTicker % 12 != 0) {
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
         class_1923 playerChunk = new class_1923(playerPos);

         for (int cx = -chunkR; cx <= chunkR; cx++) {
            for (int cz = -chunkR; cz <= chunkR; cz++) {
               class_2818 chunk = client.field_1687.method_8497(playerChunk.field_9181 + cx, playerChunk.field_9180 + cz);
               if (chunk != null) {
                  for (class_2586 be : chunk.method_12214().values()) {
                     boolean match = false;
                     if (this.shulkers.get() && be instanceof class_2627) {
                        match = true;
                     } else if (this.hoppers.get() && be instanceof class_2614) {
                        match = true;
                     } else if (!this.dispensers.get() || !(be instanceof class_2601) && !(be instanceof class_2608)) {
                        if (this.furnaces.get() && (be instanceof class_3866 || be instanceof class_3723 || be instanceof class_3720)) {
                           match = true;
                        }
                     } else {
                        match = true;
                     }

                     if (match) {
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
      } catch (Throwable var14) {
      }
   }

   @Override
   protected void onDisable() {
      positions.clear();
      this.count = 0;
   }

   @Override
   public String getDisplay() {
      return this.isEnabled() ? "Storage:" + this.count : "";
   }
}
