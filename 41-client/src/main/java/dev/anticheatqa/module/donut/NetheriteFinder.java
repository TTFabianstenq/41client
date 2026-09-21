package dev.anticheatqa.module.donut;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import net.minecraft.class_1923;
import net.minecraft.class_2338;
import net.minecraft.class_2561;
import net.minecraft.class_2680;
import net.minecraft.class_2818;
import net.minecraft.class_310;

/**
 * Lag-safe ancient debris scanner for DonutSMP.
 * Spreads work: few chunks per tick, step sampling, caches already-scanned chunks.
 */
public class NetheriteFinder extends Module {
   public static final List<class_2338> hits = new ArrayList<>();

   public final Module.NumberSetting radius = new Module.NumberSetting("Chunk Radius", 3.0, 1.0, 6.0, 1.0);
   public final Module.NumberSetting minY = new Module.NumberSetting("Min Y", 8.0, 0.0, 64.0, 1.0);
   public final Module.NumberSetting maxY = new Module.NumberSetting("Max Y", 48.0, 16.0, 128.0, 1.0);
   public final Module.NumberSetting chunksPerTick = new Module.NumberSetting("Chunks/Tick", 2.0, 1.0, 6.0, 1.0);
   public final Module.NumberSetting blockStep = new Module.NumberSetting("Block Step", 2.0, 1.0, 4.0, 1.0);
   public final Module.BooleanSetting notify = new Module.BooleanSetting("Notify", true);

   private final HashSet<Long> knownDebris = new HashSet<>();
   private final HashSet<Long> scannedChunks = new HashSet<>();
   private final LinkedHashSet<Long> pendingChunks = new LinkedHashSet<>();
   private int pruneTicker;

   public NetheriteFinder() {
      super(
         "Netherite Finder",
         "Lag-safe debris ESP for DonutSMP (incremental scan, no spikes).",
         Category.DONUT
      );
      settings.add(radius);
      settings.add(minY);
      settings.add(maxY);
      settings.add(chunksPerTick);
      settings.add(blockStep);
      settings.add(notify);
   }

   @Override
   protected void onEnable() {
      hits.clear();
      knownDebris.clear();
      scannedChunks.clear();
      pendingChunks.clear();
      pruneTicker = 0;
   }

   @Override
   protected void onDisable() {
      hits.clear();
      knownDebris.clear();
      scannedChunks.clear();
      pendingChunks.clear();
   }

   private static long chunkKey(int cx, int cz) {
      return ((long) cx << 32) ^ (cz & 0xffffffffL);
   }

   @Override
   public void onTick(class_310 client) {
      try {
         if (client.field_1687 == null || client.field_1724 == null) {
            hits.clear();
            pendingChunks.clear();
            return;
         }

         int r = Math.max(1, Math.min(6, radius.get().intValue()));
         int y0 = minY.get().intValue();
         int y1 = maxY.get().intValue();
         if (y1 < y0) {
            int t = y0; y0 = y1; y1 = t;
         }
         int step = Math.max(1, Math.min(4, blockStep.get().intValue()));
         int budget = Math.max(1, Math.min(6, chunksPerTick.get().intValue()));

         class_1923 o = client.field_1724.method_31476();
         var cm = client.field_1687.method_2935();
         int ox = o.field_9181;
         int oz = o.field_9180;

         // Queue nearby loaded chunks that we have not scanned yet
         for (int dx = -r; dx <= r; dx++) {
            for (int dz = -r; dz <= r; dz++) {
               int cx = ox + dx;
               int cz = oz + dz;
               long ck = chunkKey(cx, cz);
               if (scannedChunks.contains(ck)) continue;
               if (cm.method_21730(cx, cz) == null) continue;
               pendingChunks.add(ck);
            }
         }

         // Process a few pending chunks this tick only
         class_2338.class_2339 m = new class_2338.class_2339();
         int done = 0;
         var it = pendingChunks.iterator();
         while (it.hasNext() && done < budget) {
            long ck = it.next().longValue();
            it.remove();
            int cx = (int) (ck >> 32);
            int cz = (int) ck;
            class_2818 ch = cm.method_21730(cx, cz);
            scannedChunks.add(ck);
            done++;
            if (ch == null) continue;

            int bx = cx << 4;
            int bz = cz << 4;
            for (int x = 0; x < 16; x += step) {
               for (int z = 0; z < 16; z += step) {
                  for (int y = y0; y <= y1; y += step) {
                     m.method_10103(bx + x, y, bz + z);
                     class_2680 st = ch.method_8320(m);
                     if (st.method_26215()) continue;
                     String id = st.method_26204().method_63499().toLowerCase();
                     if (!id.contains("ancient_debris")) continue;

                     class_2338 p = m.method_10062();
                     long key = p.method_10063();
                     if (!knownDebris.add(key)) continue;
                     hits.add(p);
                     if (Boolean.TRUE.equals(notify.get())) {
                        try {
                           client.field_1724.method_7353(
                              class_2561.method_43470(
                                 "\u00a7c[Debris] " + p.method_10263() + " " + p.method_10264() + " " + p.method_10260()
                              ),
                              false
                           );
                        } catch (Throwable ignored) {}
                     }
                  }
               }
            }
         }

         // Occasional prune of far hits / far scanned markers so memory stays fine
         if (++pruneTicker % 100 == 0) {
            int keep = (r + 4) * 16;
            class_2338 pp = client.field_1724.method_24515();
            hits.removeIf(p -> {
               boolean far = Math.abs(p.method_10263() - pp.method_10263()) > keep
                  || Math.abs(p.method_10260() - pp.method_10260()) > keep;
               if (far) knownDebris.remove(p.method_10063());
               return far;
            });
            // Allow rescanning chunks that are far away (player left)
            scannedChunks.removeIf((Long ck0) -> { long ck = ck0;
               int cx = (int) (ck >> 32);
               int cz = (int) ck;
               return Math.abs(cx - ox) > r + 2 || Math.abs(cz - oz) > r + 2;
            });
         }
      } catch (Throwable ignored) {
      }
   }

   @Override
   public String getDisplay() {
      return isEnabled() ? "Debris:" + hits.size() : "";
   }
}
