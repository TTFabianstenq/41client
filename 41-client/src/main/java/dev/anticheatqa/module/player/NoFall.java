package dev.anticheatqa.module.player;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_2828;
import net.minecraft.class_310;

/**
 * Packet NoFall — spoofs on-ground to the server while falling.
 */
public class NoFall extends Module {
   public final Module.NumberSetting minFall = new Module.NumberSetting("Min Fall", 2.5, 0.5, 10.0, 0.5);
   public final Module.BooleanSetting packet = new Module.BooleanSetting("Packet", true);
   public final Module.BooleanSetting resetClient = new Module.BooleanSetting("Reset Client Fall", true);

   public NoFall() {
      super("No Fall", "Packet on-ground spoof while falling.", Category.PLAYER);
      settings.add(minFall);
      settings.add(packet);
      settings.add(resetClient);
   }

   @Override
   public void onTick(class_310 c) {
      try {
         if (c.field_1724 == null || c.field_1724.field_3944 == null) return;
         if (c.field_1724.method_31549().field_7479) return; // already flying

         double fall = c.field_1724.field_6017;
         if (fall < minFall.get()) return;

         if (Boolean.TRUE.equals(packet.get())) {
            // PlayerMoveC2SPacket.OnGroundOnly(onGround, horizontalCollision)
            try {
               c.field_1724.field_3944.method_52787(new class_2828.class_5911(true, true));
            } catch (Throwable t) {
               try {
                  c.field_1724.field_3944.method_52787(new class_2828.class_5911(true, false));
               } catch (Throwable ignored) {
               }
            }
         }

         if (Boolean.TRUE.equals(resetClient.get())) {
            c.field_1724.field_6017 = 0.0F;
            c.field_1724.method_24830(true);
         }
      } catch (Throwable ignored) {
      }
   }

   @Override
   public String getDisplay() {
      return isEnabled() ? "NoFall" : "";
   }
}
