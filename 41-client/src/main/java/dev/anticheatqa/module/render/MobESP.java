package dev.anticheatqa.module.render;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_1297;
import net.minecraft.class_1588;
import net.minecraft.class_310;

public class MobESP extends Module {
   public static final List<class_1588> mobs = new ArrayList<>();
   public final Module.NumberSetting range = new Module.NumberSetting("Range", 64.0, 16.0, 128.0, 4.0);

   public MobESP() {
      super("Mob ESP", "Highlights hostile mobs through walls.", Category.RENDER);
      this.settings.add(this.range);
   }

   @Override
   public void onTick(class_310 c) {
      mobs.clear();
      if (c.field_1687 != null && c.field_1724 != null) {
         for (class_1297 e : c.field_1687.method_18112()) {
            if (e instanceof class_1588 h && h.method_5805() && c.field_1724.method_5739(h) <= this.range.get()) {
               mobs.add(h);
            }
         }
      }
   }
}
