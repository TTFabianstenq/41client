package dev.anticheatqa.module.render;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1937;
import net.minecraft.class_1959;
import net.minecraft.class_2338;
import net.minecraft.class_310;
import net.minecraft.class_327;
import net.minecraft.class_332;
import net.minecraft.class_5321;
import net.minecraft.class_6880;

public class RegionMap extends Module {
   public final Module.NumberSetting regionSize = new Module.NumberSetting("Region Size", 512.0, 64.0, 2048.0, 64.0);
   public final Module.BooleanSetting showBiome = new Module.BooleanSetting("Show Biome", true);
   public final Module.BooleanSetting showDim = new Module.BooleanSetting("Show Dimension", true);
   public final Module.NumberSetting posX = new Module.NumberSetting("HUD X", 4.0, 0.0, 2000.0, 1.0);
   public final Module.NumberSetting posY = new Module.NumberSetting("HUD Y", 28.0, 0.0, 2000.0, 1.0);

   public RegionMap() {
      super("Region Map", "HUD: region / chunk / coords (Donut-style).", Category.RENDER);
      this.settings.add(this.regionSize);
      this.settings.add(this.showBiome);
      this.settings.add(this.showDim);
      this.settings.add(this.posX);
      this.settings.add(this.posY);
   }

   @Override
   public String getDisplay() {
      if (!this.isEnabled()) {
         return "";
      } else {
         class_310 c = class_310.method_1551();
         if (c.field_1724 == null) {
            return "";
         } else {
            int x = c.field_1724.method_31477();
            int z = c.field_1724.method_31479();
            int rs = Math.max(64, this.regionSize.get().intValue());
            int rx = Math.floorDiv(x, rs);
            int rz = Math.floorDiv(z, rs);
            return String.format("R[%d,%d]", rx, rz);
         }
      }
   }

   public void renderHud(class_332 context) {
      class_310 c = class_310.method_1551();
      if (c.field_1724 != null && c.field_1687 != null) {
         class_327 tr = c.field_1772;
         int x = c.field_1724.method_31477();
         int y = c.field_1724.method_31478();
         int z = c.field_1724.method_31479();
         int rs = Math.max(64, this.regionSize.get().intValue());
         int rx = Math.floorDiv(x, rs);
         int rz = Math.floorDiv(z, rs);
         int cx = x >> 4;
         int cz = z >> 4;
         int hx = this.posX.get().intValue();
         int hy = this.posY.get().intValue();
         String line1 = String.format("Region  %d, %d", rx, rz);
         String line2 = String.format("Chunk   %d, %d", cx, cz);
         String line3 = String.format("XYZ     %d %d %d", x, y, z);
         int maxW = Math.max(tr.method_1727(line1), Math.max(tr.method_1727(line2), tr.method_1727(line3))) + 10;
         int lines = 3;
         String dimLine = null;
         String biomeLine = null;
         if (this.showDim.get()) {
            class_5321<class_1937> key = c.field_1687.method_27983();
            String path = key.method_29177().method_12832();
            dimLine = "Dim     " + path;
            maxW = Math.max(maxW, tr.method_1727(dimLine) + 10);
            lines++;
         }

         if (this.showBiome.get()) {
            try {
               class_2338 pos = c.field_1724.method_24515();
               class_6880<class_1959> biome = c.field_1687.method_23753(pos);
               String bname = biome.method_40230().map(k -> k.method_29177().method_12832()).orElse("unknown");
               biomeLine = "Biome   " + bname;
               maxW = Math.max(maxW, tr.method_1727(biomeLine) + 10);
               lines++;
            } catch (Throwable var24) {
            }
         }

         int h = 4 + lines * 11 + 4;
         context.method_25294(hx - 2, hy - 2, hx + maxW, hy + h, -1442840576);
         context.method_25294(hx - 2, hy - 2, hx - 1, hy + h, -16718337);
         int ty = hy + 2;
         context.method_51433(tr, line1, hx + 4, ty, -16718337, false);
         ty += 11;
         context.method_51433(tr, line2, hx + 4, ty, -2039576, false);
         ty += 11;
         context.method_51433(tr, line3, hx + 4, ty, -2039576, false);
         ty += 11;
         if (dimLine != null) {
            context.method_51433(tr, dimLine, hx + 4, ty, -5592406, false);
            ty += 11;
         }

         if (biomeLine != null) {
            context.method_51433(tr, biomeLine, hx + 4, ty, -7798904, false);
         }
      }
   }
}
