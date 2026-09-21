package dev.anticheatqa.module.misc;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.lang.reflect.Field;
import net.minecraft.class_310;

public class FastUse extends Module {
   public final Module.NumberSetting delay = new Module.NumberSetting("Delay", 0.0, 0.0, 4.0, 1.0);
   private static Field cooldownField;

   public FastUse() {
      super("Fast Use", "Reduces item use cooldown.", Category.MISC);
      settings.add(delay);
   }

   private static Field getField() {
      if (cooldownField != null) return cooldownField;
      try {
         Field f = class_310.class.getDeclaredField("field_1752");
         f.setAccessible(true);
         cooldownField = f;
         return f;
      } catch (Throwable t) {
         try {
            Field f = class_310.class.getDeclaredField("itemUseCooldown");
            f.setAccessible(true);
            cooldownField = f;
            return f;
         } catch (Throwable t2) {
            return null;
         }
      }
   }

   @Override
   public void onTick(class_310 client) {
      try {
         if (client.field_1724 == null) return;
         Field f = getField();
         if (f == null) return;
         int d = Math.max(0, delay.get().intValue());
         int cur = f.getInt(client);
         if (cur > d) f.setInt(client, d);
      } catch (Throwable ignored) {
      }
   }
}
