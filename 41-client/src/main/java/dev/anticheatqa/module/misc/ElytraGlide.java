package dev.anticheatqa.module.misc;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_310;

public class ElytraGlide extends Module {
   public final Module.NumberSetting pitch = new Module.NumberSetting("Pitch", 0.0, -45.0, 45.0, 1.0);

   public ElytraGlide() {
      super("Elytra Glide", "Locks pitch while gliding for stable flight.", Category.MISC);
      this.settings.add(this.pitch);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1724 != null && c.field_1724.method_6128()) {
         c.field_1724.method_36457(this.pitch.get().floatValue());
      }
   }
}
