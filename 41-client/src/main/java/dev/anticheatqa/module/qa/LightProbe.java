package dev.anticheatqa.module.qa;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1944;
import net.minecraft.class_2338;
import net.minecraft.class_310;

public class LightProbe extends Module {
   public final Module.NumberSetting sampleRadius = new Module.NumberSetting("Sample Radius", 8.0, 2.0, 24.0, 1.0);
   private int minBlock = 15;
   private int maxBlock = 0;
   private int minSky = 15;
   private int maxSky = 0;
   private int avgBlock;
   private int darkCount;
   private int ticker;

   public LightProbe() {
      super("Light Probe", "Samples block/sky light nearby. Flags dark pockets for visibility QA.", Category.CLIENT);
      this.settings.add(this.sampleRadius);
   }

   @Override
   public void onTick(class_310 client) {
      try {
         this.ticker++;
         if (this.ticker % 15 != 0) {
            return;
         }

         if (client.field_1687 == null || client.field_1724 == null) {
            return;
         }

         int r = this.sampleRadius.get().intValue();
         class_2338 origin = client.field_1724.method_24515();
         int minB = 15;
         int maxB = 0;
         int minS = 15;
         int maxS = 0;
         long sumB = 0L;
         int samples = 0;
         int dark = 0;

         for (int x = -r; x <= r; x += 2) {
            for (int y = -r; y <= r; y += 2) {
               for (int z = -r; z <= r; z += 2) {
                  class_2338 p = origin.method_10069(x, y, z);
                  int bl = client.field_1687.method_8314(class_1944.field_9282, p);
                  int sk = client.field_1687.method_8314(class_1944.field_9284, p);
                  if (bl < minB) {
                     minB = bl;
                  }

                  if (bl > maxB) {
                     maxB = bl;
                  }

                  if (sk < minS) {
                     minS = sk;
                  }

                  if (sk > maxS) {
                     maxS = sk;
                  }

                  sumB += bl;
                  samples++;
                  if (bl == 0 && sk == 0) {
                     dark++;
                  }
               }
            }
         }

         this.minBlock = minB;
         this.maxBlock = maxB;
         this.minSky = minS;
         this.maxSky = maxS;
         this.avgBlock = samples > 0 ? (int)(sumB / samples) : 0;
         this.darkCount = dark;
      } catch (Throwable var18) {
      }
   }

   @Override
   public String getDisplay() {
      return !this.isEnabled()
         ? ""
         : String.format("Light B:%d-%d avg%d  Sky:%d-%d  Dark:%d", this.minBlock, this.maxBlock, this.avgBlock, this.minSky, this.maxSky, this.darkCount);
   }
}
