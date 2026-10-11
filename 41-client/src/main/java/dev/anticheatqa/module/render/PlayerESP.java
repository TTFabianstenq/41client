package dev.anticheatqa.module.render;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_1657;
import net.minecraft.class_310;

public class PlayerESP extends Module {
   public static final List<class_1657> players = new ArrayList<>();
   public final Module.NumberSetting range = new Module.NumberSetting("Range", 128.0, 16.0, 256.0, 8.0);
   public final Module.BooleanSetting tracers = new Module.BooleanSetting("Tracers", true);
   public final Module.ColorSetting color = new Module.ColorSetting("Color", -43691);

   public PlayerESP() {
      super("Player ESP", "3D boxes on players through walls.", Category.RENDER);
      this.settings.add(this.range);
      this.settings.add(this.tracers);
      this.settings.add(this.color);
   }

   @Override
   public void onTick(class_310 client) {
      players.clear();
      if (client.field_1687 != null && client.field_1724 != null) {
         for (class_1657 p : client.field_1687.method_18456()) {
            if (p != client.field_1724 && p.method_5805() && !(client.field_1724.method_5739(p) > this.range.get())) {
               players.add(p);
            }
         }
      }
   }

   @Override
   public String getDisplay() {
      return this.isEnabled() ? "PESP:" + players.size() : "";
   }
}
