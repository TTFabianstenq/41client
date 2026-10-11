package dev.anticheatqa.module.misc;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_2561;
import net.minecraft.class_310;

public class WeatherNotifier extends Module {
   private boolean wasRain;

   public WeatherNotifier() {
      super("Weather Notifier", "Chat alert when rain starts or stops.", Category.MISC);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1687 != null && c.field_1724 != null) {
         boolean rain = c.field_1687.method_8419();
         if (rain != this.wasRain) {
            c.field_1724.method_7353(class_2561.method_43470(rain ? "\u00a7b[SC] Rain started" : "\u00a77[SC] Rain stopped"), false);
            this.wasRain = rain;
         }
      }
   }
}
