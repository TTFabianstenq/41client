package dev.anticheatqa.module.visuals;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;

public class CustomBlockOutline extends Module {
   public final Module.ColorSetting color = new Module.ColorSetting("Color", -16711800);
   public final Module.NumberSetting width = new Module.NumberSetting("Width", 2.0, 1.0, 5.0, 0.5);

   public CustomBlockOutline() {
      super("Custom Block Outline", "Custom color/width for the block you look at.", Category.RENDER);
      this.settings.add(this.color);
      this.settings.add(this.width);
   }
}
