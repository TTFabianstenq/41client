package dev.anticheatqa.module.combat;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.lang.reflect.Field;
import net.minecraft.class_310;

/** Clears MinecraftClient.attackCooldown (field_1771) while attacking. */
public class NoHitDelay extends Module {
   private static Field attackCdField;

   public NoHitDelay() {
      super("No Hit Delay", "Removes left-click attack cooldown delay.", Category.COMBAT);
   }

   private static Field getField() {
      if (attackCdField != null) return attackCdField;
      try {
         Field f = class_310.class.getDeclaredField("field_1771");
         f.setAccessible(true);
         attackCdField = f;
         return f;
      } catch (Throwable t) {
         try {
            Field f = class_310.class.getDeclaredField("attackCooldown");
            f.setAccessible(true);
            attackCdField = f;
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
         // always clear so swings aren't delayed
         if (f.getInt(client) > 0) {
            f.setInt(client, 0);
         }
      } catch (Throwable ignored) {
      }
   }

   @Override
   public String getDisplay() {
      return isEnabled() ? "NoHitDelay" : "";
   }
}
