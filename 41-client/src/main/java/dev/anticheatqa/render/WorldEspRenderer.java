package dev.anticheatqa.render;

import dev.anticheatqa.AntiCheatQA;
import dev.anticheatqa.module.Module;
import dev.anticheatqa.module.basefinding.HoleESP;
import dev.anticheatqa.module.basefinding.SuspiciousESP;
import dev.anticheatqa.module.donut.NetheriteFinder;
import dev.anticheatqa.module.qa.StashScanner;
import dev.anticheatqa.module.qa.SusChunkFinder;
import dev.anticheatqa.module.render.BlockEntityESP;
import dev.anticheatqa.module.render.DebugHoleESP;
import dev.anticheatqa.module.render.DeepslateESP;
import dev.anticheatqa.module.render.ItemESP;
import dev.anticheatqa.module.render.KelpESP;
import dev.anticheatqa.module.render.MobESP;
import dev.anticheatqa.module.render.PearlTrajectory;
import dev.anticheatqa.module.render.PlayerESP;
import dev.anticheatqa.module.render.VillagerESP;
import dev.anticheatqa.module.visuals.BlockESP;
import dev.anticheatqa.module.visuals.ChestESP;
import dev.anticheatqa.module.visuals.ChunkBorders;
import dev.anticheatqa.module.visuals.CrystalESP;
import dev.anticheatqa.module.visuals.EntityESP;
import dev.anticheatqa.module.visuals.HitboxESP;
import dev.anticheatqa.module.visuals.SpawnerESP;
import dev.anticheatqa.module.visuals.StorageESP;
import dev.anticheatqa.module.visuals.Tracers;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.minecraft.class_12249;
import net.minecraft.class_1297;
import net.minecraft.class_1308;
import net.minecraft.class_1309;
import net.minecraft.class_1429;
import net.minecraft.class_1542;
import net.minecraft.class_1588;
import net.minecraft.class_1646;
import net.minecraft.class_1657;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_259;
import net.minecraft.class_265;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_9974;
import net.minecraft.class_4587.class_4665;
import net.minecraft.class_4597.class_4598;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public final class WorldEspRenderer {
   private static final float LINE_W = 3.0F;
   private static final class_265 BEAD = class_259.method_1081(0.0, 0.0, 0.0, 0.1, 0.1, 0.1);
   private static final class_265 END_BOX = class_259.method_1081(0.0, 0.0, 0.0, 0.4, 0.4, 0.4);
   private static final class_265 CHUNK_SLAB = class_259.method_1081(0.0, 0.0, 0.0, 16.0, 0.08, 16.0);
   private static final class_265 PILLAR = class_259.method_1081(0.0, 0.0, 0.0, 0.12, 1.0, 0.12);
   private static final class_265 EDGE_X = class_259.method_1081(0.0, 0.0, 0.0, 16.0, 0.08, 0.08);
   private static final class_265 EDGE_Z = class_259.method_1081(0.0, 0.0, 0.0, 0.08, 0.08, 16.0);
   private static final class_265 CHUNK_BOX = class_259.method_1081(0.0, 0.0, 0.0, 16.0, 64.0, 16.0);

   private WorldEspRenderer() {
   }

   public static void render(WorldRenderContext context) {
      try {
         class_310 client = class_310.method_1551();
         if (client.field_1724 == null || client.field_1687 == null) {
            return;
         }

         class_4587 matrices = context.matrices();
         if (matrices == null) {
            return;
         }

         class_243 cam = context.worldState().field_63082.field_63078;
         class_4598 immediate = client.method_22940().method_23000();
         class_4588 lines = immediate.method_73477(class_12249.method_76015());
         matrices.method_22903();
         disableDepth();

         try {
            GL11.glLineWidth(2.0F);
         } catch (Throwable var24) {
         }

         if (ChestESP.positions != null && !ChestESP.positions.isEmpty()) {
            int orange = -29696;

            for (class_2338 pos : ChestESP.positions) {
               double x = pos.method_10263() - cam.field_1352;
               double y = pos.method_10264() - cam.field_1351;
               double z = pos.method_10260() - cam.field_1350;
               class_9974.method_62296(matrices, lines, class_259.method_1077(), x, y, z, orange, 3.0F);
            }
         }

         if (BlockESP.positions != null && !BlockESP.positions.isEmpty()) {
            for (class_2338 pos : BlockESP.positions) {
               double x = pos.method_10263() - cam.field_1352;
               double y = pos.method_10264() - cam.field_1351;
               double z = pos.method_10260() - cam.field_1350;
               String id = client.field_1687.method_8320(pos).method_26204().method_63499().toLowerCase();
               int color = id.contains("spawner") ? -52225 : -13369345;
               class_9974.method_62296(matrices, lines, class_259.method_1077(), x, y, z, color, 3.0F);
            }
         }

         if (EntityESP.boxesEnabled) {
            for (class_1297 e : client.field_1687.method_18112()) {
               if (e != client.field_1724 && e.method_5805() && e.method_5851()) {
                  class_238 box = e.method_5829().method_1014(0.05);
                  int color = e instanceof class_1657 ? -49088 : -12517568;
                  double x = box.field_1323 - cam.field_1352;
                  double y = box.field_1322 - cam.field_1351;
                  double z = box.field_1321 - cam.field_1350;
                  double sx = box.field_1320 - box.field_1323;
                  double sy = box.field_1325 - box.field_1322;
                  double sz = box.field_1324 - box.field_1321;
                  class_265 shape = class_259.method_1081(0.0, 0.0, 0.0, sx, sy, sz);
                  class_9974.method_62296(matrices, lines, shape, x, y, z, color, 3.0F);
               }
            }
         }

         renderHitboxes(client, matrices, lines, cam);
         renderChunkBorders(client, matrices, lines, cam);
         renderStorageEsp(client, matrices, lines, cam);
         renderSpawnerEsp(client, matrices, lines, cam);
         renderCrystalEsp(client, matrices, lines, cam);
         renderStashChunks(client, matrices, lines, cam);
         renderSusChunks(client, matrices, lines, cam);
         renderPlayerEsp(client, matrices, lines, cam);
         renderItemEsp(client, matrices, lines, cam);
         renderNetherite(client, matrices, lines, cam);
         renderHoleEsp(client, matrices, lines, cam);
         renderSuspiciousEsp(client, matrices, lines, cam);
         renderMobEsp(client, matrices, lines, cam);
         renderPearlPath(client, matrices, lines, cam);
         renderBlockEntityEsp(client, matrices, lines, cam);
         renderDebugHoles(client, matrices, lines, cam);
         renderDeepslateEsp(client, matrices, lines, cam);
         renderKelpEsp(client, matrices, lines, cam);
         renderVillagerEsp(client, matrices, lines, cam);
         renderTracers3D(client, matrices, lines, cam);
         immediate.method_22994(class_12249.method_76015());
         enableDepth();
         matrices.method_22909();
      } catch (Throwable var25) {
         try {
            enableDepth();
         } catch (Throwable var23) {
         }
      }
   }

   private static void disableDepth() {
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
   }

   private static void enableDepth() {
      GL11.glDepthMask(true);
      GL11.glEnable(2929);
   }

   private static void renderSusChunks(class_310 client, class_4587 matrices, class_4588 lines, class_243 cam) {
      Module mod = AntiCheatQA.INSTANCE != null ? AntiCheatQA.INSTANCE.getModuleManager().getModule("Sus Chunk Finder") : null;
      if (mod == null || !mod.isEnabled()) return;
      if (!(mod instanceof SusChunkFinder scf)) return;
      if (SusChunkFinder.flagged.isEmpty()) return;

      boolean boxes = scf.highlightBoxes.get();
      boolean tracers = scf.tracers.get();
      float lw = Math.max(2.0F, scf.lineWidth.get().floatValue());
      int py = client.field_1724.method_31478();

      // Forced bright yellow - solid chunk square look
      final int YELLOW = 0xFFFFFF00;
      final int YELLOW_HOT = 0xFFFFCC00;

      java.util.List<SusChunkFinder.SusChunk> susList = new java.util.ArrayList<>(SusChunkFinder.flagged);
      int maxScore = 1;
      for (SusChunkFinder.SusChunk sc : susList) {
         if (sc.score > maxScore) maxScore = sc.score;
      }

      class_243 eye = client.field_1724.method_33571();
      double sx = eye.field_1352 - cam.field_1352;
      double sy = eye.field_1351 - cam.field_1351;
      double sz = eye.field_1350 - cam.field_1350;

      // Flat slab covering full 16x16 chunk + dense strips = reads as solid yellow square
      class_265 square = class_259.method_1081(0.0, 0.0, 0.0, 16.0, 0.12, 16.0);
      class_265 stripX = class_259.method_1081(0.0, 0.0, 0.0, 16.0, 0.12, 0.4);
      class_265 stripZ = class_259.method_1081(0.0, 0.0, 0.0, 0.4, 0.12, 16.0);
      class_265 border = class_259.method_1081(0.0, 0.0, 0.0, 16.0, 0.2, 16.0);

      for (SusChunkFinder.SusChunk sc : susList) {
         int bx = sc.chunkX << 4;
         int bz = sc.chunkZ << 4;
         float tScore = Math.min(1.0F, (float) sc.score / (float) maxScore);
         int color = tScore > 0.55F ? YELLOW_HOT : YELLOW;

         double x = bx - cam.field_1352;
         double y = (py + 0.05) - cam.field_1351;
         double z = bz - cam.field_1350;

         if (boxes) {
            // Outer border thicker
            class_9974.method_62296(matrices, lines, border, x, y, z, color, lw + 2.0F);
            // Full square fill
            class_9974.method_62296(matrices, lines, square, x, y, z, color, lw + 1.0F);
            // Dense X strips
            for (int i = 0; i < 16; i++) {
               class_9974.method_62296(matrices, lines, stripX, x, y, z + i, color, lw);
            }
            // Dense Z strips
            for (int i = 0; i < 16; i++) {
               class_9974.method_62296(matrices, lines, stripZ, x + i, y, z, color, lw);
            }
         }

         if (tracers) {
            double cx = bx + 8.0 - cam.field_1352;
            double cy = py + 2.0 - cam.field_1351;
            double cz = bz + 8.0 - cam.field_1350;
            drawTracer(matrices, lines, sx, sy, sz, cx, cy, cz, color, lw);
            class_9974.method_62296(matrices, lines, END_BOX, cx - 0.2, cy - 0.2, cz - 0.2, color, lw);
         }
      }
   }

   private static void drawTracer(
      class_4587 matrices, class_4588 lines, double x0, double y0, double z0, double x1, double y1, double z1, int color, float width
   ) {
      double dx = x1 - x0;
      double dy = y1 - y0;
      double dz = z1 - z0;
      double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
      int steps = Math.max(12, Math.min(80, (int)(dist * 2.5)));

      for (int i = 0; i <= steps; i++) {
         double t = (double)i / steps;
         double x = x0 + dx * t;
         double y = y0 + dy * t;
         double z = z0 + dz * t;
         class_9974.method_62296(matrices, lines, BEAD, x - 0.04, y - 0.04, z - 0.04, color, width);
      }
   }

   private static int lerpColor(int a, int b, float t) {
      int aa = a >> 24 & 0xFF;
      int ar = a >> 16 & 0xFF;
      int ag = a >> 8 & 0xFF;
      int ab = a & 0xFF;
      int ba = b >> 24 & 0xFF;
      int br = b >> 16 & 0xFF;
      int bg = b >> 8 & 0xFF;
      int bb = b & 0xFF;
      int ra = (int)(aa + (ba - aa) * t);
      int rr = (int)(ar + (br - ar) * t);
      int rg = (int)(ag + (bg - ag) * t);
      int rb = (int)(ab + (bb - ab) * t);
      return ra << 24 | rr << 16 | rg << 8 | rb;
   }

   private static void renderTracers3D(class_310 client, class_4587 matrices, class_4588 lines, class_243 cam) {
      Module mod = AntiCheatQA.INSTANCE != null ? AntiCheatQA.INSTANCE.getModuleManager().getModule("Tracers") : null;
      if (mod != null && mod.isEnabled()) {
         if (mod instanceof Tracers tracers) {
            if (tracers.draw.get()) {
               boolean wantPlayers = tracers.players.get();
               boolean wantMobs = tracers.mobs.get();
               boolean wantAnimals = tracers.animals.get();
               double maxRange = tracers.range.get();
               class_243 eye = client.field_1724.method_33571();
               float yaw = client.field_1724.method_36454();
               float pitch = client.field_1724.method_36455();
               double lookX = -Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));
               double lookY = -Math.sin(Math.toRadians(pitch));
               double lookZ = Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));
               double sx = eye.field_1352 + lookX * 0.2 - cam.field_1352;
               double sy = eye.field_1351 + lookY * 0.2 - cam.field_1351;
               double sz = eye.field_1350 + lookZ * 0.2 - cam.field_1350;
               class_4665 entry = matrices.method_23760();
               Matrix4f mat = entry.method_23761();

               for (class_1297 e : client.field_1687.method_18112()) {
                  if (e != client.field_1724 && e.method_5805()) {
                     boolean match = false;
                     if (wantPlayers && e instanceof class_1657) {
                        match = true;
                     } else if (wantAnimals && e instanceof class_1429) {
                        match = true;
                     } else if (wantMobs && e instanceof class_1308 && !(e instanceof class_1429)) {
                        match = true;
                     } else if (wantMobs && e instanceof class_1309 && !(e instanceof class_1657) && !(e instanceof class_1429)) {
                        match = true;
                     }

                     if (match) {
                        double dist = client.field_1724.method_5739(e);
                        if (!(dist > maxRange) && !(dist < 0.35)) {
                           double ex = e.method_23317() - cam.field_1352;
                           double ey = e.method_23318() + e.method_17682() * 0.5 - cam.field_1351;
                           double ez = e.method_23321() - cam.field_1350;
                           int color = e instanceof class_1657 ? -43691 : -11141291;
                           int r = color >> 16 & 0xFF;
                           int g = color >> 8 & 0xFF;
                           int b = color & 0xFF;
                           int a = color >> 24 & 0xFF;
                           float dx = (float)(ex - sx);
                           float dy = (float)(ey - sy);
                           float dz = (float)(ez - sz);
                           float len = (float)Math.sqrt(dx * dx + dy * dy + dz * dz);
                           if (!(len < 1.0E-4F)) {
                              float nx = dx / len;
                              float ny = dy / len;
                              float nz = dz / len;
                              lines.method_22918(mat, (float)sx, (float)sy, (float)sz).method_1336(r, g, b, a).method_60831(entry, nx, ny, nz);
                              lines.method_22918(mat, (float)ex, (float)ey, (float)ez).method_1336(r, g, b, a).method_60831(entry, nx, ny, nz);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static void renderHitboxes(class_310 client, class_4587 matrices, class_4588 lines, class_243 cam) {
      Module mod = AntiCheatQA.INSTANCE != null ? AntiCheatQA.INSTANCE.getModuleManager().getModule("Hitbox ESP") : null;
      if (mod != null && mod.isEnabled()) {
         if (mod instanceof HitboxESP hb) {
            boolean wantPlayers = hb.players.get();
            boolean wantMobs = hb.mobs.get();
            double maxRange = hb.range.get();
            float width = 2.5F;

            for (class_1297 e : client.field_1687.method_18112()) {
               if (e != client.field_1724 && e.method_5805()) {
                  boolean match = false;
                  if (wantPlayers && e instanceof class_1657) {
                     match = true;
                  } else if (wantMobs && e instanceof class_1309 && !(e instanceof class_1657)) {
                     match = true;
                  }

                  if (match && !(client.field_1724.method_5739(e) > maxRange)) {
                     class_238 box = e.method_5829();
                     int color = e instanceof class_1657 ? -22016 : -16711766;
                     double x = box.field_1323 - cam.field_1352;
                     double y = box.field_1322 - cam.field_1351;
                     double z = box.field_1321 - cam.field_1350;
                     class_265 shape = class_259.method_1081(
                        0.0, 0.0, 0.0, box.field_1320 - box.field_1323, box.field_1325 - box.field_1322, box.field_1324 - box.field_1321
                     );
                     class_9974.method_62296(matrices, lines, shape, x, y, z, color, width);
                  }
               }
            }
         }
      }
   }

   private static void renderChunkBorders(class_310 client, class_4587 matrices, class_4588 lines, class_243 cam) {
      Module mod = AntiCheatQA.INSTANCE != null ? AntiCheatQA.INSTANCE.getModuleManager().getModule("Chunk Borders") : null;
      if (mod != null && mod.isEnabled()) {
         if (mod instanceof ChunkBorders cb) {
            int radius = cb.radius.get().intValue();
            int py = client.field_1724.method_31478();
            int cx = client.field_1724.method_31477() >> 4;
            int cz = client.field_1724.method_31479() >> 4;
            int cyan = -16711681;
            byte white = -1;

            for (int dx = -radius; dx <= radius; dx++) {
               for (int dz = -radius; dz <= radius; dz++) {
                  int bx = cx + dx << 4;
                  int bz = cz + dz << 4;
                  boolean current = dx == 0 && dz == 0;
                  int color = current ? white : cyan;
                  float w = current ? 4.0F : 2.5F;

                  for (int yOff : new int[]{py - 8, py, py + 8}) {
                     double x = bx - cam.field_1352;
                     double y = yOff - cam.field_1351;
                     double z = bz - cam.field_1350;
                     class_9974.method_62296(matrices, lines, CHUNK_SLAB, x, y, z, color, w);
                     class_9974.method_62296(matrices, lines, EDGE_X, x, y, z, color, w);
                     class_9974.method_62296(matrices, lines, EDGE_X, x, y, z + 15.65, color, w);
                     class_9974.method_62296(matrices, lines, EDGE_Z, x, y, z, color, w);
                     class_9974.method_62296(matrices, lines, EDGE_Z, x + 15.65, y, z, color, w);
                  }
               }
            }
         }
      }
   }

   private static void renderStorageEsp(class_310 client, class_4587 matrices, class_4588 lines, class_243 cam) {
      if (StorageESP.positions != null && !StorageESP.positions.isEmpty()) {
         Module mod = AntiCheatQA.INSTANCE != null ? AntiCheatQA.INSTANCE.getModuleManager().getModule("Storage ESP") : null;
         if (mod != null && mod.isEnabled()) {
            for (class_2338 pos : StorageESP.positions) {
               double x = pos.method_10263() - cam.field_1352;
               double y = pos.method_10264() - cam.field_1351;
               double z = pos.method_10260() - cam.field_1350;
               String id = client.field_1687.method_8320(pos).method_26204().method_63499().toLowerCase();
               int color = -5614081;
               if (id.contains("shulker")) {
                  color = -43521;
               } else if (id.contains("hopper")) {
                  color = -7829368;
               } else if (id.contains("dispenser") || id.contains("dropper")) {
                  color = -11162881;
               } else if (id.contains("furnace") || id.contains("smoker") || id.contains("blast")) {
                  color = -30652;
               }

               class_9974.method_62296(matrices, lines, class_259.method_1077(), x, y, z, color, 3.0F);
            }
         }
      }
   }

   private static void renderSpawnerEsp(class_310 client, class_4587 matrices, class_4588 lines, class_243 cam) {
      if (SpawnerESP.positions != null && !SpawnerESP.positions.isEmpty()) {
         Module mod = AntiCheatQA.INSTANCE != null ? AntiCheatQA.INSTANCE.getModuleManager().getModule("Spawner ESP") : null;
         if (mod != null && mod.isEnabled()) {
            int magenta = -65281;

            for (class_2338 pos : SpawnerESP.positions) {
               double x = pos.method_10263() - cam.field_1352;
               double y = pos.method_10264() - cam.field_1351;
               double z = pos.method_10260() - cam.field_1350;
               class_9974.method_62296(matrices, lines, class_259.method_1077(), x, y, z, magenta, 4.0F);
            }
         }
      }
   }

   private static void renderCrystalEsp(class_310 client, class_4587 matrices, class_4588 lines, class_243 cam) {
      Module mod = AntiCheatQA.INSTANCE != null ? AntiCheatQA.INSTANCE.getModuleManager().getModule("Crystal ESP") : null;
      if (mod != null && mod.isEnabled()) {
         if (CrystalESP.crystals != null) {
            int cyan = -16711681;

            for (class_1297 e : CrystalESP.crystals) {
               if (e != null && e.method_5805()) {
                  class_238 box = e.method_5829().method_1014(0.1);
                  double x = box.field_1323 - cam.field_1352;
                  double y = box.field_1322 - cam.field_1351;
                  double z = box.field_1321 - cam.field_1350;
                  class_265 shape = class_259.method_1081(
                     0.0, 0.0, 0.0, box.field_1320 - box.field_1323, box.field_1325 - box.field_1322, box.field_1324 - box.field_1321
                  );
                  class_9974.method_62296(matrices, lines, shape, x, y, z, cyan, 3.5F);
               }
            }
         }

         if (CrystalESP.anchors != null) {
            int red = -52429;

            for (class_2338 pos : CrystalESP.anchors) {
               double x = pos.method_10263() - cam.field_1352;
               double y = pos.method_10264() - cam.field_1351;
               double z = pos.method_10260() - cam.field_1350;
               class_9974.method_62296(matrices, lines, class_259.method_1077(), x, y, z, red, 3.5F);
            }
         }
      }
   }

   private static void renderStashChunks(class_310 client, class_4587 matrices, class_4588 lines, class_243 cam) {
      Module mod = AntiCheatQA.INSTANCE != null ? AntiCheatQA.INSTANCE.getModuleManager().getModule("Stash Scanner") : null;
      if (mod != null && mod.isEnabled()) {
         if (mod instanceof StashScanner ss) {
            if (!StashScanner.hits.isEmpty() || !StashScanner.containerPositions.isEmpty()) {
               float lw = ss.lineWidth.get().floatValue();
               int color = ss.color.get() | 0xFF000000;
               boolean doChunk = ss.chunkBox.get();
               boolean doBlocks = ss.blockBoxes.get();
               boolean tracers = ss.tracers.get();
               class_243 eye = client.field_1724.method_33571();
               double sx = eye.field_1352 - cam.field_1352;
               double sy = eye.field_1351 - cam.field_1351;
               double sz = eye.field_1350 - cam.field_1350;
               if (doBlocks && !StashScanner.containerPositions.isEmpty()) {
                  for (class_2338 pos : new ArrayList<>(StashScanner.containerPositions)) {
                     double x = pos.method_10263() - cam.field_1352;
                     double y = pos.method_10264() - cam.field_1351;
                     double z = pos.method_10260() - cam.field_1350;
                     class_9974.method_62296(matrices, lines, class_259.method_1077(), x, y, z, color, lw);
                  }
               }

               for (StashScanner.StashHit hit : new ArrayList<>(StashScanner.hits)) {
                  int bx = hit.chunkX << 4;
                  int bz = hit.chunkZ << 4;
                  int h = Math.max(2, hit.highestY - hit.lowestY + 2);
                  class_265 cluster = class_259.method_1081(0.0, 0.0, 0.0, 16.0, h, 16.0);
                  if (doChunk) {
                     double x = bx - cam.field_1352;
                     double y = hit.lowestY - 1 - cam.field_1351;
                     double z = bz - cam.field_1350;
                     class_9974.method_62296(matrices, lines, cluster, x, y, z, color, lw + 0.5F);
                     class_9974.method_62296(matrices, lines, CHUNK_SLAB, x, hit.highestY + 1 - cam.field_1351, z, color, lw);
                  }

                  if (tracers) {
                     double cx = hit.centerX - cam.field_1352;
                     double cy = hit.centerY - cam.field_1351;
                     double cz = hit.centerZ - cam.field_1350;
                     drawTracer(matrices, lines, sx, sy, sz, cx, cy, cz, color, lw);
                     class_9974.method_62296(matrices, lines, END_BOX, cx - 0.2, cy - 0.2, cz - 0.2, color, lw);
                  }
               }
            }
         }
      }
   }

   private static void renderPlayerEsp(class_310 client, class_4587 matrices, class_4588 lines, class_243 cam) {
      Module mod = AntiCheatQA.INSTANCE != null ? AntiCheatQA.INSTANCE.getModuleManager().getModule("Player ESP") : null;
      if (mod != null && mod.isEnabled() && mod instanceof PlayerESP pe) {
         int color = pe.color.get() | 0xFF000000;
         class_243 eye = client.field_1724.method_33571();
         double sx = eye.field_1352 - cam.field_1352;
         double sy = eye.field_1351 - cam.field_1351;
         double sz = eye.field_1350 - cam.field_1350;

         for (class_1657 p : PlayerESP.players) {
            class_238 box = p.method_5829().method_1014(0.05);
            double x = box.field_1323 - cam.field_1352;
            double y = box.field_1322 - cam.field_1351;
            double z = box.field_1321 - cam.field_1350;
            class_265 shape = class_259.method_1081(
               0.0, 0.0, 0.0, box.field_1320 - box.field_1323, box.field_1325 - box.field_1322, box.field_1324 - box.field_1321
            );
            class_9974.method_62296(matrices, lines, shape, x, y, z, color, 2.5F);
            if (pe.tracers.get()) {
               double cx = p.method_23317() - cam.field_1352;
               double cy = p.method_23318() + p.method_17682() * 0.5 - cam.field_1351;
               double cz = p.method_23321() - cam.field_1350;
               drawTracer(matrices, lines, sx, sy, sz, cx, cy, cz, color, 2.0F);
            }
         }
      }
   }

   private static void renderItemEsp(class_310 client, class_4587 matrices, class_4588 lines, class_243 cam) {
      Module mod = AntiCheatQA.INSTANCE != null ? AntiCheatQA.INSTANCE.getModuleManager().getModule("Item ESP") : null;
      if (mod != null && mod.isEnabled()) {
         int color = -8875;

         for (class_1542 ie : ItemESP.items) {
            class_238 box = ie.method_5829().method_1014(0.1);
            double x = box.field_1323 - cam.field_1352;
            double y = box.field_1322 - cam.field_1351;
            double z = box.field_1321 - cam.field_1350;
            class_265 shape = class_259.method_1081(
               0.0, 0.0, 0.0, box.field_1320 - box.field_1323, box.field_1325 - box.field_1322, box.field_1324 - box.field_1321
            );
            class_9974.method_62296(matrices, lines, shape, x, y, z, color, 2.0F);
         }
      }
   }

   private static void renderNetherite(class_310 client, class_4587 matrices, class_4588 lines, class_243 cam) {
      Module mod = AntiCheatQA.INSTANCE != null ? AntiCheatQA.INSTANCE.getModuleManager().getModule("Netherite Finder") : null;
      if (mod != null && mod.isEnabled()) {
         int color = -5627392;

         for (class_2338 pos : NetheriteFinder.hits) {
            double x = pos.method_10263() - cam.field_1352;
            double y = pos.method_10264() - cam.field_1351;
            double z = pos.method_10260() - cam.field_1350;
            class_9974.method_62296(matrices, lines, class_259.method_1077(), x, y, z, color, 3.0F);
         }
      }
   }

   private static void renderHoleEsp(class_310 client, class_4587 matrices, class_4588 lines, class_243 cam) {
      Module mod = AntiCheatQA.INSTANCE != null ? AntiCheatQA.INSTANCE.getModuleManager().getModule("Hole ESP") : null;
      if (mod != null && mod.isEnabled()) {
         int col = -11141206;

         for (class_2338 pos : HoleESP.holes) {
            double x = pos.method_10263() - cam.field_1352;
            double y = pos.method_10264() - cam.field_1351;
            double z = pos.method_10260() - cam.field_1350;
            class_9974.method_62296(matrices, lines, class_259.method_1077(), x, y, z, col, 2.5F);
         }
      }
   }

   private static void renderSuspiciousEsp(class_310 client, class_4587 matrices, class_4588 lines, class_243 cam) {
      Module mod = AntiCheatQA.INSTANCE != null ? AntiCheatQA.INSTANCE.getModuleManager().getModule("Suspicious ESP") : null;
      if (mod != null && mod.isEnabled()) {
         int col = -43521;

         for (class_2338 pos : SuspiciousESP.positions) {
            double x = pos.method_10263() - cam.field_1352;
            double y = pos.method_10264() - cam.field_1351;
            double z = pos.method_10260() - cam.field_1350;
            class_9974.method_62296(matrices, lines, class_259.method_1077(), x, y, z, col, 3.0F);
         }
      }
   }

   private static void renderMobEsp(class_310 client, class_4587 matrices, class_4588 lines, class_243 cam) {
      Module mod = AntiCheatQA.INSTANCE != null ? AntiCheatQA.INSTANCE.getModuleManager().getModule("Mob ESP") : null;
      if (mod != null && mod.isEnabled()) {
         int col = -48060;

         for (class_1588 e : MobESP.mobs) {
            class_238 box = e.method_5829().method_1014(0.05);
            double x = box.field_1323 - cam.field_1352;
            double y = box.field_1322 - cam.field_1351;
            double z = box.field_1321 - cam.field_1350;
            class_265 shape = class_259.method_1081(
               0.0, 0.0, 0.0, box.field_1320 - box.field_1323, box.field_1325 - box.field_1322, box.field_1324 - box.field_1321
            );
            class_9974.method_62296(matrices, lines, shape, x, y, z, col, 2.0F);
         }
      }
   }

   private static void renderPearlPath(class_310 client, class_4587 matrices, class_4588 lines, class_243 cam) {
      Module mod = AntiCheatQA.INSTANCE != null ? AntiCheatQA.INSTANCE.getModuleManager().getModule("Pearl Trajectory") : null;
      if (mod != null && mod.isEnabled()) {
         int col = -11141121;

         for (class_243 p : PearlTrajectory.path) {
            class_9974.method_62296(
               matrices,
               lines,
               BEAD,
               p.field_1352 - cam.field_1352 - 0.05,
               p.field_1351 - cam.field_1351 - 0.05,
               p.field_1350 - cam.field_1350 - 0.05,
               col,
               2.0F
            );
         }
      }
   }

   private static void renderBlockEntityEsp(class_310 client, class_4587 matrices, class_4588 lines, class_243 cam) {
      Module mod = AntiCheatQA.INSTANCE != null ? AntiCheatQA.INSTANCE.getModuleManager().getModule("Block Entity ESP") : null;
      if (mod != null && mod.isEnabled()) {
         int col = -7798870;

         for (class_2338 pos : BlockEntityESP.positions) {
            double x = pos.method_10263() - cam.field_1352;
            double y = pos.method_10264() - cam.field_1351;
            double z = pos.method_10260() - cam.field_1350;
            class_9974.method_62296(matrices, lines, class_259.method_1077(), x, y, z, col, 1.8F);
         }
      }
   }

   private static void renderDebugHoles(class_310 client, class_4587 matrices, class_4588 lines, class_243 cam) {
      Module mod = AntiCheatQA.INSTANCE != null ? AntiCheatQA.INSTANCE.getModuleManager().getModule("Debug Hole ESP") : null;
      if (mod != null && mod.isEnabled()) {
         int col = -16711732;

         for (class_2338 pos : DebugHoleESP.holes) {
            double x = pos.method_10263() - cam.field_1352;
            double y = pos.method_10264() - cam.field_1351;
            double z = pos.method_10260() - cam.field_1350;
            class_9974.method_62296(matrices, lines, class_259.method_1077(), x, y, z, col, 2.5F);
         }
      }
   }

   private static void renderDeepslateEsp(class_310 client, class_4587 matrices, class_4588 lines, class_243 cam) {
      Module mod = AntiCheatQA.INSTANCE != null ? AntiCheatQA.INSTANCE.getModuleManager().getModule("Deepslate ESP") : null;
      if (mod != null && mod.isEnabled()) {
         int col = -12285697;

         for (class_2338 pos : new ArrayList<>(DeepslateESP.positions)) {
            double x = pos.method_10263() - cam.field_1352;
            double y = pos.method_10264() - cam.field_1351;
            double z = pos.method_10260() - cam.field_1350;
            class_9974.method_62296(matrices, lines, class_259.method_1077(), x, y, z, col, 2.0F);
         }
      }
   }

   private static void renderKelpEsp(class_310 client, class_4587 matrices, class_4588 lines, class_243 cam) {
      Module mod = AntiCheatQA.INSTANCE != null ? AntiCheatQA.INSTANCE.getModuleManager().getModule("Kelp ESP") : null;
      if (mod != null && mod.isEnabled()) {
         int col = -14483610;

         for (class_2338 pos : new ArrayList<>(KelpESP.positions)) {
            double x = pos.method_10263() - cam.field_1352;
            double y = pos.method_10264() - cam.field_1351;
            double z = pos.method_10260() - cam.field_1350;
            class_9974.method_62296(matrices, lines, class_259.method_1077(), x, y, z, col, 1.5F);
         }
      }
   }

   private static void renderVillagerEsp(class_310 client, class_4587 matrices, class_4588 lines, class_243 cam) {
      Module mod = AntiCheatQA.INSTANCE != null ? AntiCheatQA.INSTANCE.getModuleManager().getModule("Villager ESP") : null;
      if (mod != null && mod.isEnabled()) {
         int col = -21948;

         for (class_1646 e : new ArrayList<>(VillagerESP.villagers)) {
            try {
               class_238 box = e.method_5829().method_1014(0.05);
               double x = box.field_1323 - cam.field_1352;
               double y = box.field_1322 - cam.field_1351;
               double z = box.field_1321 - cam.field_1350;
               class_265 shape = class_259.method_1081(
                  0.0, 0.0, 0.0, box.field_1320 - box.field_1323, box.field_1325 - box.field_1322, box.field_1324 - box.field_1321
               );
               class_9974.method_62296(matrices, lines, shape, x, y, z, col, 2.0F);
            } catch (Throwable var16) {
            }
         }
      }
   }
}
