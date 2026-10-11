package dev.anticheatqa.module.misc;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_310;

public class CustomFOV extends Module {
   public final Module.NumberSetting fov = new Module.NumberSetting("FOV", 110.0, 30.0, 150.0, 1.0);
   private int prev = -1;

   public CustomFOV() {
      super("Custom FOV", "Forces a custom field of view while enabled.", Category.MISC);
      this.settings.add(this.fov);
   }

   @Override
   protected void onEnable() {
      class_310 c = class_310.method_1551();
      this.prev = (Integer)c.field_1690.method_41808().method_41753();
      c.field_1690.method_41808().method_41748(this.fov.get().intValue());
   }

   @Override
   public void onTick(class_310 c) {
      if ((Integer)c.field_1690.method_41808().method_41753() != this.fov.get().intValue()) {
         c.field_1690.method_41808().method_41748(this.fov.get().intValue());
      }
   }

   @Override
   protected void onDisable() {
      class_310 c = class_310.method_1551();
      if (this.prev > 0) {
         c.field_1690.method_41808().method_41748(this.prev);
      }
   }
}
