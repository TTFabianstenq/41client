package dev.anticheatqa.module.render;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_1297;
import net.minecraft.class_1646;
import net.minecraft.class_310;

public class VillagerESP extends Module {
   public static final List<class_1646> villagers = new ArrayList<>();
   public final Module.NumberSetting range = new Module.NumberSetting("Range", 64.0, 16.0, 128.0, 4.0);

   public VillagerESP() {
      super("Villager ESP", "Highlights villagers through walls.", Category.RENDER);
      this.settings.add(this.range);
   }

   @Override
   public void onTick(class_310 c) {
      villagers.clear();
      if (c.field_1687 != null && c.field_1724 != null) {
         for (class_1297 e : c.field_1687.method_18112()) {
            if (e instanceof class_1646 v && v.method_5805() && c.field_1724.method_5739(v) <= this.range.get()) {
               villagers.add(v);
            }
         }
      }
   }

   @Override
   public String getDisplay() {
      return this.isEnabled() ? "Vill:" + villagers.size() : "";
   }
}
