package dev.anticheatqa.module.qa;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1542;
import net.minecraft.class_1657;
import net.minecraft.class_1923;
import net.minecraft.class_2338;
import net.minecraft.class_2586;
import net.minecraft.class_2595;
import net.minecraft.class_2611;
import net.minecraft.class_2818;
import net.minecraft.class_310;
import net.minecraft.class_3719;

public class YLevelProbe extends Module {
   public final Module.NumberSetting scanRange = new Module.NumberSetting("Scan Range", 24.0, 8.0, 64.0, 4.0);
   public final Module.BooleanSetting countOres = new Module.BooleanSetting("Count Ores/Spawners", true);
   public final Module.BooleanSetting countChests = new Module.BooleanSetting("Count Chests", true);
   public final Module.BooleanSetting countEntities = new Module.BooleanSetting("Count Entities", true);
   public final Module.BooleanSetting countPlayers = new Module.BooleanSetting("Include Players", true);
   public final Module.BooleanSetting countItems = new Module.BooleanSetting("Include Items", false);
   private int blockCount;
   private int chestCount;
   private int entityCount;
   private int playerCount;
   private int scanTicker;
   private String lastDetail = "";

   public YLevelProbe() {
      super(
         "Y-Level Probe",
         "Counts client-visible blocks, chests and entities below Y=0. Use to verify server-side anti-cheat visibility rules.",
         Category.CLIENT
      );
      this.settings.add(this.scanRange);
      this.settings.add(this.countOres);
      this.settings.add(this.countChests);
      this.settings.add(this.countEntities);
      this.settings.add(this.countPlayers);
      this.settings.add(this.countItems);
   }

   @Override
   public void onTick(class_310 client) {
      try {
         this.scanTicker++;
         if (this.scanTicker % 10 != 0) {
            return;
         }

         if (client.field_1687 == null || client.field_1724 == null) {
            this.blockCount = this.chestCount = this.entityCount = this.playerCount = 0;
            this.lastDetail = "";
            return;
         }

         int blocks = 0;
         int chests = 0;
         int entities = 0;
         int players = 0;
         int range = this.scanRange.get().intValue();
         class_2338 playerPos = client.field_1724.method_24515();
         if (this.countChests.get()) {
            int chunkR = range / 16 + 1;
            class_1923 playerChunk = new class_1923(playerPos);

            for (int cx = -chunkR; cx <= chunkR; cx++) {
               for (int cz = -chunkR; cz <= chunkR; cz++) {
                  class_2818 chunk = client.field_1687.method_8497(playerChunk.field_9181 + cx, playerChunk.field_9180 + cz);
                  if (chunk != null) {
                     for (class_2586 be : chunk.method_12214().values()) {
                        if (be instanceof class_2595 || be instanceof class_3719 || be instanceof class_2611) {
                           class_2338 pos = be.method_11016();
                           if (pos.method_10264() < 0 && pos.method_19771(playerPos, range)) {
                              chests++;
                           }
                        }
                     }
                  }
               }
            }
         }

         if (this.countOres.get()) {
            int cx = playerPos.method_10263();
            int cy = Math.min(playerPos.method_10264(), -1);
            int czx = playerPos.method_10260();
            int yMin = Math.max(client.field_1687.method_31607(), cy - range);
            int yMax = Math.min(-1, cy + range);

            for (int x = cx - range; x <= cx + range; x++) {
               for (int z = czx - range; z <= czx + range; z++) {
                  for (int y = yMin; y <= yMax; y++) {
                     class_2338 pos = new class_2338(x, y, z);
                     if (pos.method_10264() < 0) {
                        String id = client.field_1687.method_8320(pos).method_26204().method_63499().toLowerCase();
                        if (id.contains("diamond_ore")
                           || id.contains("emerald_ore")
                           || id.contains("gold_ore")
                           || id.contains("iron_ore")
                           || id.contains("coal_ore")
                           || id.contains("ancient_debris")
                           || id.contains("spawner")
                           || id.contains("deepslate") && (id.contains("ore") || id.contains("debris"))) {
                           blocks++;
                        }
                     }
                  }
               }
            }
         }

         if (this.countEntities.get()) {
            for (class_1297 e : client.field_1687.method_18112()) {
               if (e != client.field_1724 && e.method_5805() && !(e.method_23318() >= 0.0) && !(client.field_1724.method_5858(e) > (double)range * range)) {
                  if (e instanceof class_1657) {
                     if (this.countPlayers.get()) {
                        players++;
                        entities++;
                     }
                  } else if (e instanceof class_1542) {
                     if (this.countItems.get()) {
                        entities++;
                     }
                  } else if (e instanceof class_1309) {
                     entities++;
                  } else {
                     entities++;
                  }
               }
            }
         }

         this.blockCount = blocks;
         this.chestCount = chests;
         this.entityCount = entities;
         this.playerCount = players;
         this.lastDetail = String.format("B:%d C:%d E:%d P:%d", blocks, chests, entities, players);
      } catch (Throwable var18) {
      }
   }

   @Override
   protected void onDisable() {
      this.blockCount = this.chestCount = this.entityCount = this.playerCount = 0;
      this.lastDetail = "";
   }

   @Override
   public String getDisplay() {
      return !this.isEnabled()
         ? ""
         : String.format("Y<0 | Blocks:%d Chests:%d Ents:%d Players:%d", this.blockCount, this.chestCount, this.entityCount, this.playerCount);
   }

   public int getBlockCount() {
      return this.blockCount;
   }

   public int getChestCount() {
      return this.chestCount;
   }

   public int getEntityCount() {
      return this.entityCount;
   }

   public int getPlayerCount() {
      return this.playerCount;
   }
}
