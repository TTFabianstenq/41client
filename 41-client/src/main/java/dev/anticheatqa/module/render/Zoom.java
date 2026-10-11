package dev.anticheatqa.module.render;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_310;

public class Zoom extends Module {
   public final Module.NumberSetting amount = new Module.NumberSetting("FOV", 20.0, 5.0, 50.0, 1.0);
   private double prevFov = -1.0;

   public Zoom() {
      super("Zoom", "Lowers FOV while enabled (hold keybind).", Category.RENDER);
      this.settings.add(this.amount);
   }

   @Override
   protected void onEnable() {
      class_310 c = class_310.method_1551();
      this.prevFov = ((Integer)c.field_1690.method_41808().method_41753()).intValue();
      c.field_1690.method_41808().method_41748((int)this.amount.get().doubleValue());
   }

   @Override
   protected void onDisable() {
      class_310 c = class_310.method_1551();
      if (this.prevFov > 0.0) {
         c.field_1690.method_41808().method_41748((int)this.prevFov);
      }

      this.prevFov = -1.0;
   }
}
