package dev.anticheatqa.module.qa;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.class_1923;
import net.minecraft.class_2338;
import net.minecraft.class_2561;
import net.minecraft.class_2586;
import net.minecraft.class_2595;
import net.minecraft.class_2611;
import net.minecraft.class_2614;
import net.minecraft.class_2627;
import net.minecraft.class_2818;
import net.minecraft.class_310;
import net.minecraft.class_3719;

public class StashScanner extends Module {
   public static final List<StashScanner.StashHit> hits = new ArrayList<>();
   public static final List<class_2338> containerPositions = new ArrayList<>();
   public final Module.NumberSetting chunkRadius = new Module.NumberSetting("Chunk Radius", 4.0, 1.0, 8.0, 1.0);
   public final Module.NumberSetting minContainers = new Module.NumberSetting("Min Containers", 4.0, 2.0, 20.0, 1.0);
   public final Module.NumberSetting maxY = new Module.NumberSetting("Max Y", 64.0, -64.0, 256.0, 8.0);
   public final Module.BooleanSetting includeShulkers = new Module.BooleanSetting("Shulkers", true);
   public final Module.BooleanSetting includeHoppers = new Module.BooleanSetting("Hoppers", true);
   public final Module.BooleanSetting chatAlert = new Module.BooleanSetting("Chat On New", true);
   public final Module.BooleanSetting chunkBox = new Module.BooleanSetting("Chunk Box", true);
   public final Module.BooleanSetting blockBoxes = new Module.BooleanSetting("Block Boxes", true);
   public final Module.BooleanSetting tracers = new Module.BooleanSetting("Tracers", true);
   public final Module.NumberSetting lineWidth = new Module.NumberSetting("Line Width", 2.5, 1.0, 6.0, 0.5);
   public final Module.ColorSetting color = new Module.ColorSetting("Color", -22016);
   private final Set<Long> announced = new HashSet<>();
   private int ticker;
   private int lastHits;

   public StashScanner() {
      super("Stash Scanner", "Meteor-style stash ESP: chunk boxes, container outlines, tracers.", Category.DONUT);
      this.settings.add(this.chunkRadius);
      this.settings.add(this.minContainers);
      this.settings.add(this.maxY);
      this.settings.add(this.includeShulkers);
      this.settings.add(this.includeHoppers);
      this.settings.add(this.chatAlert);
      this.settings.add(this.chunkBox);
      this.settings.add(this.blockBoxes);
      this.settings.add(this.tracers);
      this.settings.add(this.lineWidth);
      this.settings.add(this.color);
   }

   @Override
   protected void onEnable() {
      hits.clear();
      containerPositions.clear();
      this.announced.clear();
      this.lastHits = 0;
      this.ticker = 0;
   }

   @Override
   protected void onDisable() {
      hits.clear();
      containerPositions.clear();
      this.announced.clear();
      this.lastHits = 0;
   }

   @Override
   public void onTick(class_310 client) {
      try {
         this.ticker++;
         if (this.ticker % 25 != 0) {
            return;
         }

         if (client.field_1687 == null || client.field_1724 == null) {
            hits.clear();
            containerPositions.clear();
            this.lastHits = 0;
            return;
         }

         int radius = this.chunkRadius.get().intValue();
         int need = this.minContainers.get().intValue();
         int yCap = this.maxY.get().intValue();
         boolean shulkers = this.includeShulkers.get();
         boolean hoppers = this.includeHoppers.get();
         class_1923 origin = new class_1923(client.field_1724.method_24515());
         Map<Long, List<class_2338>> byChunk = new HashMap<>();

         for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
               int cx = origin.field_9181 + dx;
               int cz = origin.field_9180 + dz;
               class_2818 chunk = client.field_1687.method_8497(cx, cz);
               if (chunk != null) {
                  long key = (long)cx << 32 ^ cz & 4294967295L;
                  List<class_2338> list = byChunk.computeIfAbsent(key, k -> new ArrayList<>());

                  for (class_2586 be : chunk.method_12214().values()) {
                     boolean match = be instanceof class_2595
                        || be instanceof class_3719
                        || be instanceof class_2611
                        || shulkers && be instanceof class_2627
                        || hoppers && be instanceof class_2614;
                     if (match) {
                        class_2338 pos = be.method_11016();
                        if (pos.method_10264() <= yCap) {
                           list.add(pos.method_10062());
                        }
                     }
                  }
               }
            }
         }

         List<StashScanner.StashHit> next = new ArrayList<>();
         List<class_2338> allBoxes = new ArrayList<>();

         for (Entry<Long, List<class_2338>> e : byChunk.entrySet()) {
            List<class_2338> list = e.getValue();
            if (list.size() >= need) {
               long key = e.getKey();
               int cx = (int)(key >> 32);
               int cz = (int)key;
               int minY = Integer.MAX_VALUE;
               int maxYY = Integer.MIN_VALUE;
               double sx = 0.0;
               double sy = 0.0;
               double sz = 0.0;

               for (class_2338 p : list) {
                  if (p.method_10264() < minY) {
                     minY = p.method_10264();
                  }

                  if (p.method_10264() > maxYY) {
                     maxYY = p.method_10264();
                  }

                  sx += p.method_10263() + 0.5;
                  sy += p.method_10264() + 0.5;
                  sz += p.method_10260() + 0.5;
                  allBoxes.add(p);
               }

               int n = list.size();
               next.add(new StashScanner.StashHit(cx, cz, n, minY, maxYY, sx / n, sy / n, sz / n));
            }
         }

         next.sort(Comparator.<StashScanner.StashHit>comparingInt(hx -> hx.count).reversed());
         if (this.chatAlert.get() && client.field_1724 != null) {
            for (StashScanner.StashHit h : next) {
               long key = (long)h.chunkX << 32 ^ h.chunkZ & 4294967295L;
               if (!this.announced.contains(key)) {
                  this.announced.add(key);
                  int bx = h.chunkX << 4;
                  int bz = h.chunkZ << 4;
                  String msg = String.format(
                     "\u00a76[Stash] \u00a7eNEW \u00a7f%d \u00a77containers @ \u00a7f%d %d %d \u00a77(chunk \u00a7f%d,%d\u00a77)",
                     h.count,
                     (int)h.centerX,
                     (int)h.centerY,
                     (int)h.centerZ,
                     h.chunkX,
                     h.chunkZ
                  );
                  client.field_1724.method_7353(class_2561.method_43470(msg), false);
               }
            }
         }

         hits.clear();
         hits.addAll(next);
         containerPositions.clear();
         containerPositions.addAll(allBoxes);
         this.lastHits = next.size();
      } catch (Throwable var28) {
      }
   }

   @Override
   public String getDisplay() {
      return !this.isEnabled() ? "" : "Stashes:" + this.lastHits;
   }

   public static final class StashHit {
      public final int chunkX;
      public final int chunkZ;
      public final int count;
      public final int lowestY;
      public final int highestY;
      public final double centerX;
      public final double centerY;
      public final double centerZ;

      public StashHit(int chunkX, int chunkZ, int count, int lowestY, int highestY, double centerX, double centerY, double centerZ) {
         this.chunkX = chunkX;
         this.chunkZ = chunkZ;
         this.count = count;
         this.lowestY = lowestY;
         this.highestY = highestY;
         this.centerX = centerX;
         this.centerY = centerY;
         this.centerZ = centerZ;
      }
   }
}
