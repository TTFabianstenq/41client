package dev.anticheatqa.module.visuals;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;

public class MotionBlur extends Module {
   public final Module.NumberSetting amount = new Module.NumberSetting("Amount", 0.4, 0.05, 0.9, 0.05);

   public MotionBlur() {
      super("Motion Blur", "Soft motion blur intensity setting (shader-style placeholder intensity).", Category.VISUALS);
      this.settings.add(this.amount);
   }
}
