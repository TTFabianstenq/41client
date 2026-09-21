package dev.anticheatqa.module.render;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_310;

public class MusicHud extends Module {
   public MusicHud() {
      super("Music HUD", "Shows currently playing music disc / note info when available.", Category.RENDER);
   }

   @Override
   public String getDisplay() {
      if (!this.isEnabled()) {
         return "";
      } else {
         class_310 c = class_310.method_1551();
         return c.field_1724 == null ? "Music" : "Music";
      }
   }
}
