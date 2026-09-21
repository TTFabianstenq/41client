package dev.anticheatqa.module.visuals;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1923;
import net.minecraft.class_310;

public class ChunkBorders extends Module {
   public final Module.NumberSetting radius = new Module.NumberSetting("Chunk Radius", 1.0, 0.0, 3.0, 1.0);

   public ChunkBorders() {
      super("Chunk Borders", "3D chunk border lines. Current chunk white, others cyan.", Category.RENDER);
      this.settings.add(this.radius);
   }

   @Override
   public String getDisplay() {
      if (!this.isEnabled()) {
         return "";
      } else {
         class_310 client = class_310.method_1551();
         if (client.field_1724 == null) {
            return "ChunkBorders";
         } else {
            class_1923 cp = new class_1923(client.field_1724.method_24515());
            return String.format("Chunk [%d,%d]", cp.field_9181, cp.field_9180);
         }
      }
   }
}
