package dev.anticheatqa.module.render;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;

public class HudModule extends Module {
   public final Module.BooleanSetting watermark = new Module.BooleanSetting("Watermark", true);
   public final Module.BooleanSetting arraylist = new Module.BooleanSetting("Arraylist", true);
   public final Module.BooleanSetting coords = new Module.BooleanSetting("Coordinates", true);

   public HudModule() {
      super("HUD", "Master toggle for 41 Client overlay elements.", Category.RENDER);
      this.settings.add(this.watermark);
      this.settings.add(this.arraylist);
      this.settings.add(this.coords);
      this.setEnabled(true);
   }
}
