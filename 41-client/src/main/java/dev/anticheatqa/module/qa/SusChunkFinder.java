package dev.anticheatqa.module.qa;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.class_1923;
import net.minecraft.class_2680;
import net.minecraft.class_2818;
import net.minecraft.class_310;
import net.minecraft.class_2338.class_2339;

public class SusChunkFinder extends Module {
   public static final List<SusChunkFinder.SusChunk> flagged = new ArrayList<>();
   public final Module.NumberSetting chunkRadius = new Module.NumberSetting("Chunk Radius", 3.0, 1.0, 6.0, 1.0);
   public final Module.NumberSetting diamondThreshold = new Module.NumberSetting("Diamond Threshold", 8.0, 1.0, 40.0, 1.0);
   public final Module.NumberSetting debrisThreshold = new Module.NumberSetting("Debris Threshold", 4.0, 1.0, 20.0, 1.0);
   public final Module.NumberSetting oreThreshold = new Module.NumberSetting("Any-Ore Threshold", 80.0, 10.0, 300.0, 5.0);
   public final Module.NumberSetting amethystThreshold = new Module.NumberSetting("Amethyst Threshold", 3.0, 1.0, 80.0, 1.0);
   public final Module.BooleanSetting highlightBoxes = new Module.BooleanSetting("Yellow Boxe", true);
   public final Module.BooleanSetting tracers = new Module.BooleanSetting("Tracers", true);
   public final Module.BooleanSetting cornerBeams = new Module.BooleanSetting("Corner Beams", true);
   public final Module.NumberSetting boxHeight = new Module.NumberSetting("Box Height", 0.05, 16.0, 256.0, 8.0);
   public final Module.NumberSetting lineWidth = new Module.NumberSetting("Line Width", 2.5, 1.0, 6.0, 0.5);
   public final Module.BooleanSetting countDeepslate = new Module.BooleanSetting("Count Deepslate Ores", true);
   public final Module.BooleanSetting countAmethyst = new Module.BooleanSetting("Count Amethyst Clusters", true);
   public final Module.ColorSetting colorLow = new Module.ColorSetting("Color Low", -171);
   public final Module.ColorSetting colorHigh = new Module.ColorSetting("Color High", -52429);
   private int ticker;
   private int lastFlagged;

   public SusChunkFinder() {
      super("Sus Chunk Finder", "Meteor-style chunk boxes + tracers for high ore/diamond/debris density.", Category.WORLD);
      this.settings.add(this.chunkRadius);
      this.settings.add(this.diamondThreshold);
      this.settings.add(this.debrisThreshold);
      this.settings.add(this.oreThreshold);
      this.settings.add(this.amethystThreshold);
      this.settings.add(this.highlightBoxes);
      this.settings.add(this.tracers);
      this.settings.add(this.cornerBeams);
      this.settings.add(this.boxHeight);
      this.settings.add(this.lineWidth);
      this.settings.add(this.countDeepslate);
      this.settings.add(this.countAmethyst);
      this.settings.add(this.colorLow);
      this.settings.add(this.colorHigh);
   }

   @Override
   protected void onEnable() {
      flagged.clear();
      this.lastFlagged = 0;
      this.ticker = 0;
   }

   @Override
   protected void onDisable() {
      flagged.clear();
      this.lastFlagged = 0;
   }

   @Override
   public void onTick(class_310 client) {
      if (client.field_1687 != null && client.field_1724 != null) {
         this.ticker++;
         if (this.ticker % 80 == 0) {
            int radius = this.chunkRadius.get().intValue();
            int diamNeed = this.diamondThreshold.get().intValue();
            int debrisNeed = this.debrisThreshold.get().intValue();
            int oreNeed = this.oreThreshold.get().intValue();
            int amethystNeed = this.amethystThreshold.get().intValue();
            boolean deepslate = this.countDeepslate.get();
            boolean doAmethyst = this.countAmethyst.get();
            class_1923 origin = new class_1923(client.field_1724.method_24515());
            List<SusChunkFinder.SusChunk> next = new ArrayList<>();

            for (int dx = -radius; dx <= radius; dx++) {
               for (int dz = -radius; dz <= radius; dz++) {
                  int cx = origin.field_9181 + dx;
                  int cz = origin.field_9180 + dz;
                  class_2818 chunk = client.field_1687.method_8497(cx, cz);
                  if (chunk != null) {
                     int diamonds = 0;
                     int debris = 0;
                     int ores = 0;
                     int spawners = 0;
                     int amethyst = 0;
                     int minY = client.field_1687.method_31607();
                     int yStart = Math.max(minY, -64);
                     int yEnd = 320;
                     class_2339 mut = new class_2339();
                     int baseX = cx << 4;
                     int baseZ = cz << 4;

                     for (int x = 0; x < 16; x += 4) {
                        for (int z = 0; z < 16; z += 4) {
                           for (int y = yStart; y <= yEnd; y += 2) {
                              mut.method_10103(baseX + x, y, baseZ + z);
                              class_2680 state = chunk.method_8320(mut);
                              if (!state.method_26215()) {
                                 String id = state.method_26204().method_63499().toLowerCase();
                                 if (id.contains("diamond_ore")) {
                                    diamonds++;
                                    ores++;
                                 } else if (id.contains("ancient_debris")) {
                                    debris++;
                                    ores++;
                                 } else if (id.contains("spawner")) {
                                    spawners++;
                                 } else if (doAmethyst && id.contains("amethyst_cluster")) {
                                    amethyst++;
                                 } else if (id.contains("_ore") && (deepslate || !id.contains("deepslate"))) {
                                    ores++;
                                 }
                              }
                           }
                        }
                     }

                     diamonds *= 4;
                     debris *= 4;
                     ores *= 4;
                     spawners *= 4;
                     amethyst *= 4;
                     boolean sus = diamonds >= diamNeed || debris >= debrisNeed || ores >= oreNeed || spawners >= 2 || doAmethyst && amethyst >= amethystNeed;
                     if (sus) {
                        int score = diamonds * 10 + debris * 15 + spawners * 20 + amethyst * 8 + ores;
                        next.add(new SusChunkFinder.SusChunk(cx, cz, diamonds, debris, ores, spawners, amethyst, score));
                     }
                  }
               }
            }

            next.sort(Comparator.<SusChunkFinder.SusChunk>comparingInt(s -> s.score).reversed());
            flagged.clear();
            flagged.addAll(next);
            this.lastFlagged = next.size();
         }
      } else {
         flagged.clear();
         this.lastFlagged = 0;
      }
   }

   @Override
   public String getDisplay() {
      return !this.isEnabled() ? "" : "SusChunks:" + this.lastFlagged;
   }

   public static final class SusChunk {
      public final int chunkX;
      public final int chunkZ;
      public final int diamonds;
      public final int debris;
      public final int ores;
      public final int spawners;
      public final int amethyst;
      public final int score;

      public SusChunk(int chunkX, int chunkZ, int diamonds, int debris, int ores, int spawners, int amethyst, int score) {
         this.chunkX = chunkX;
         this.chunkZ = chunkZ;
         this.diamonds = diamonds;
         this.debris = debris;
         this.ores = ores;
         this.spawners = spawners;
         this.amethyst = amethyst;
         this.score = score;
      }
   }
}
