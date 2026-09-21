package dev.anticheatqa.module.player;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_310;

/**
 * Cancels exhaustion (via mixin) and keeps client hunger full.
 * Works fully on singleplayer / integrated; multiplayer depends on server.
 */
public class AntiHunger extends Module {
   public static AntiHunger INSTANCE;

   public final Module.BooleanSetting cancelExhaustion = new Module.BooleanSetting("Cancel Exhaustion", true);
   public final Module.BooleanSetting fillHunger = new Module.BooleanSetting("Fill Hunger Bar", true);

   public AntiHunger() {
      super("Anti Hunger", "Stops hunger drain (exhaustion cancel).", Category.PLAYER);
      settings.add(cancelExhaustion);
      settings.add(fillHunger);
      INSTANCE = this;
   }

   public static boolean shouldCancelExhaustion() {
      return INSTANCE != null && INSTANCE.isEnabled() && Boolean.TRUE.equals(INSTANCE.cancelExhaustion.get());
   }

   @Override
   protected void onEnable() {
      INSTANCE = this;
   }

   @Override
   public void onTick(class_310 c) {
      try {
         if (c.field_1724 == null) return;
         if (!Boolean.TRUE.equals(fillHunger.get())) return;
         // Client hunger manager - field_7493 is hungerManager on player
         var hm = c.field_1724.method_7344();
         if (hm != null) {
            // set food level 20, saturation high
            try {
               hm.method_7580(20);
            } catch (Throwable ignored) {
            }
         }
      } catch (Throwable ignored) {
      }
   }

   @Override
   public String getDisplay() {
      return isEnabled() ? "AntiHunger" : "";
   }
}
