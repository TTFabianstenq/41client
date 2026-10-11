package dev.anticheatqa.module.donut;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import net.minecraft.class_1923;
import net.minecraft.class_2338;
import net.minecraft.class_2561;
import net.minecraft.class_2586;
import net.minecraft.class_2595;
import net.minecraft.class_2609;
import net.minecraft.class_2611;
import net.minecraft.class_2614;
import net.minecraft.class_2627;
import net.minecraft.class_2636;
import net.minecraft.class_2818;
import net.minecraft.class_310;
import net.minecraft.class_3719;

public class BaseFinder extends Module {
   public final Module.NumberSetting minScore = new Module.NumberSetting("Min Score", 8.0, 1.0, 50.0, 1.0);
   public final Module.NumberSetting interval = new Module.NumberSetting("Scan Interval", 40.0, 10.0, 200.0, 5.0);
   public final Module.NumberSetting radiusChunks = new Module.NumberSetting("Chunk Radius", 6.0, 2.0, 12.0, 1.0);
   public final Module.BooleanSetting chests = new Module.BooleanSetting("Chests", true);
   public final Module.BooleanSetting barrels = new Module.BooleanSetting("Barrels", true);
   public final Module.BooleanSetting shulkers = new Module.BooleanSetting("Shulkers", true);
   public final Module.BooleanSetting spawners = new Module.BooleanSetting("Spawners", true);
   public final Module.BooleanSetting furnaces = new Module.BooleanSetting("Furnaces", true);
   public final Module.BooleanSetting notify = new Module.BooleanSetting("Notify", true);
   private int ticker;
   private final List<String> results = new ArrayList<>();
   private final HashSet<Long> known = new HashSet<>();

   public BaseFinder() {
      super("Base Finder", "Scores loaded chunks by storage/spawner density.", Category.DONUT);
      this.settings.add(this.minScore);
      this.settings.add(this.interval);
      this.settings.add(this.radiusChunks);
      this.settings.add(this.chests);
      this.settings.add(this.barrels);
      this.settings.add(this.shulkers);
      this.settings.add(this.spawners);
      this.settings.add(this.furnaces);
      this.settings.add(this.notify);
   }

   @Override
   public void onTick(class_310 client) {
      if (client.field_1687 != null && client.field_1724 != null) {
         if (!(++this.ticker < this.interval.get())) {
            this.ticker = 0;
            this.results.clear();
            class_1923 origin = client.field_1724.method_31476();
            int r = (int)this.radiusChunks.get().doubleValue();

            for (int dx = -r; dx <= r; dx++) {
               for (int dz = -r; dz <= r; dz++) {
                  class_2818 chunk = client.field_1687.method_2935().method_21730(origin.field_9181 + dx, origin.field_9180 + dz);
                  if (chunk != null) {
                     int score = 0;
                     int c = 0;
                     int b = 0;
                     int s = 0;
                     int sp = 0;
                     int f = 0;

                     for (class_2586 be : chunk.method_12214().values()) {
                        if (this.chests.get() && be instanceof class_2595) {
                           score += 2;
                           c++;
                        } else if (this.barrels.get() && be instanceof class_3719) {
                           score += 2;
                           b++;
                        } else if (this.shulkers.get() && be instanceof class_2627) {
                           score += 4;
                           s++;
                        } else if (this.spawners.get() && be instanceof class_2636) {
                           score += 6;
                           sp++;
                        } else if (this.furnaces.get() && be instanceof class_2609) {
                           score++;
                           f++;
                        } else if (be instanceof class_2614) {
                           score++;
                        } else if (be instanceof class_2611) {
                           score += 3;
                        }
                     }

                     if (score >= this.minScore.get()) {
                        class_2338 center = chunk.method_12004().method_8323().method_10069(8, (int)client.field_1724.method_23318(), 8);
                        long key = class_1923.method_8331(chunk.method_12004().field_9181, chunk.method_12004().field_9180);
                        String line = String.format(
                           "Base ch[%d,%d] score=%d C%d B%d S%d Sp%d", chunk.method_12004().field_9181, chunk.method_12004().field_9180, score, c, b, s, sp
                        );
                        this.results.add(line);
                        if (this.notify.get() && this.known.add(key) && client.field_1724 != null) {
                           client.field_1724.method_7353(class_2561.method_43470("\u00a76[SC] " + line), false);
                        }
                     }
                  }
               }
            }

            this.results.sort(String::compareTo);
            if (this.results.size() > 20) {
               this.results.subList(20, this.results.size()).clear();
            }
         }
      }
   }

   public List<String> getResults() {
      return this.results;
   }

   @Override
   public String getDisplay() {
      return this.isEnabled() ? "Base:" + this.results.size() : "";
   }
}
