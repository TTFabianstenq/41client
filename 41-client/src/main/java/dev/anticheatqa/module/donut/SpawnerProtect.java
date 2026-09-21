package dev.anticheatqa.module.donut;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.HashSet;
import net.minecraft.class_1657;
import net.minecraft.class_2561;
import net.minecraft.class_310;

public class SpawnerProtect extends Module {
   public final Module.NumberSetting range = new Module.NumberSetting("Player Range", 32.0, 8.0, 128.0, 4.0);
   public final Module.BooleanSetting notify = new Module.BooleanSetting("Notify", true);
   private final HashSet<String> alerted = new HashSet<>();

   public SpawnerProtect() {
      super("Spawner Protect", "Alerts when players enter range near you (spawner camp warning).", Category.DONUT);
      this.settings.add(this.range);
      this.settings.add(this.notify);
   }

   @Override
   public void onTick(class_310 client) {
      if (client.field_1687 != null && client.field_1724 != null) {
         for (class_1657 p : client.field_1687.method_18456()) {
            if (p != client.field_1724 && p.method_5805()) {
               double d = client.field_1724.method_5739(p);
               if (d > this.range.get()) {
                  this.alerted.remove(p.method_5845());
               } else if (this.notify.get() && this.alerted.add(p.method_5845())) {
                  client.field_1724
                     .method_7353(class_2561.method_43470(String.format("\u00a7c[SC] Player nearby: %s (%.0fm)", p.method_5477().getString(), d)), false);
               }
            }
         }
      }
   }
}
