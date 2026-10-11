package dev.anticheatqa.module.misc;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;

public class CustomCrosshair extends Module {
   public final Module.NumberSetting size = new Module.NumberSetting("Size", 6.0, 2.0, 20.0, 1.0);
   public final Module.NumberSetting gap = new Module.NumberSetting("Gap", 2.0, 0.0, 10.0, 1.0);
   public final Module.ColorSetting color = new Module.ColorSetting("Color", -16711800);
   public final Module.BooleanSetting dot = new Module.BooleanSetting("Center Dot", true);

   public CustomCrosshair() {
      super("Custom Crosshair", "Draws a custom crosshair over the vanilla one.", Category.MISC);
      this.settings.add(this.size);
      this.settings.add(this.gap);
      this.settings.add(this.color);
      this.settings.add(this.dot);
   }
}
