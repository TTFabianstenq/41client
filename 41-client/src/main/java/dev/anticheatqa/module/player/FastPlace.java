package dev.anticheatqa.module.player;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.lang.reflect.Field;
import net.minecraft.class_310;

public class FastPlace extends Module {
   public final Module.NumberSetting delay = new Module.NumberSetting("Delay", 0.0, 0.0, 4.0, 1.0);

   public FastPlace() {
      super("Fast Place", "Reduces right-click / place delay.", Category.MISC);
      this.settings.add(this.delay);
   }

   @Override
   public void onTick(class_310 client) {
      if (client.field_1724 != null) {
         try {
            Field f = class_310.class.getDeclaredField("itemUseCooldown");
            f.setAccessible(true);
            int d = this.delay.get().intValue();
            if ((Integer)f.get(client) > d) {
               f.set(client, d);
            }
         } catch (Throwable var5) {
            try {
               Field fx = class_310.class.getDeclaredField("field_1752");
               fx.setAccessible(true);
               fx.set(client, this.delay.get().intValue());
            } catch (Throwable var4) {
            }
         }
      }
   }
}
