package dev.anticheatqa.module.donut;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_1657;
import net.minecraft.class_1923;
import net.minecraft.class_310;

public class PlayerChunks extends Module {
   public static final List<String> lines = new ArrayList<>();

   public PlayerChunks() {
      super("Player Chunks", "Lists which chunks nearby players are in.", Category.DONUT);
   }

   @Override
   public void onTick(class_310 c) {
      lines.clear();
      if (c.field_1687 != null && c.field_1724 != null) {
         for (class_1657 p : c.field_1687.method_18456()) {
            if (p != c.field_1724) {
               class_1923 cp = p.method_31476();
               lines.add(String.format("%s \u2192 [%d,%d]", p.method_5477().getString(), cp.field_9181, cp.field_9180));
            }
         }
      }
   }

   @Override
   public String getDisplay() {
      return this.isEnabled() ? "PChunks:" + lines.size() : "";
   }
}
