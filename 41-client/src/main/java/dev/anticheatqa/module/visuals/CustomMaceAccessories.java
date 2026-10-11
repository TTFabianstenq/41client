package dev.anticheatqa.module.visuals;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;

public class CustomMaceAccessories extends Module {
   public final Module.BooleanSetting trail = new Module.BooleanSetting("Trail", true);
   public final Module.ColorSetting color = new Module.ColorSetting("Color", -22016);

   public CustomMaceAccessories() {
      super("Custom Mace Accessories", "Visual flair when holding a mace.", Category.RENDER);
      this.settings.add(this.trail);
      this.settings.add(this.color);
   }
}
