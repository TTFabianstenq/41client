package dev.anticheatqa.module.basefinding;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_1923;
import net.minecraft.class_310;

public class PrimeChunkFinder extends Module {
   public static final List<long[]> primes = new ArrayList<>();
   public final Module.NumberSetting radius = new Module.NumberSetting("Radius", 8.0, 2.0, 16.0, 1.0);
   private int tick;

   public PrimeChunkFinder() {
      super("Prime Chunk Finder", "Highlights prime-number chunk coordinates (seed-pattern hunting).", Category.WORLD);
      this.settings.add(this.radius);
   }

   private static boolean isPrime(int n) {
      if (n < 2) {
         return false;
      } else if (n % 2 == 0) {
         return n == 2;
      } else {
         for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
               return false;
            }
         }

         return true;
      }
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1724 != null) {
         if (++this.tick % 30 == 0) {
            primes.clear();
            class_1923 o = c.field_1724.method_31476();
            int r = this.radius.get().intValue();

            for (int dx = -r; dx <= r; dx++) {
               for (int dz = -r; dz <= r; dz++) {
                  int cx = o.field_9181 + dx;
                  int cz = o.field_9180 + dz;
                  if (isPrime(Math.abs(cx)) && isPrime(Math.abs(cz))) {
                     primes.add(new long[]{cx, cz});
                  }
               }
            }
         }
      }
   }

   @Override
   public String getDisplay() {
      return this.isEnabled() ? "Prime:" + primes.size() : "";
   }
}
