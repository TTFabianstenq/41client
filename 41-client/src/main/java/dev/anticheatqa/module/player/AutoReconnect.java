package dev.anticheatqa.module.player;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_310;
import net.minecraft.class_412;
import net.minecraft.class_419;
import net.minecraft.class_442;
import net.minecraft.class_500;
import net.minecraft.class_639;
import net.minecraft.class_642;

public class AutoReconnect extends Module {
   public final Module.NumberSetting delay = new Module.NumberSetting("Delay Sec", 3.0, 1.0, 15.0, 0.5);
   private long disconnectAt;
   private class_642 last;

   public AutoReconnect() {
      super("Auto Reconnect", "Rejoins last server after disconnect.", Category.MISC);
      this.settings.add(this.delay);
   }

   @Override
   public void onTick(class_310 client) {
      if (client.method_1558() != null) {
         this.last = client.method_1558();
         this.disconnectAt = 0L;
      } else if (client.field_1755 instanceof class_419) {
         if (this.last != null) {
            if (this.disconnectAt == 0L) {
               this.disconnectAt = System.currentTimeMillis();
            }

            if (!(System.currentTimeMillis() - this.disconnectAt < this.delay.get() * 1000.0)) {
               this.disconnectAt = System.currentTimeMillis() + 999999L;

               try {
                  class_412.method_36877(new class_500(new class_442()), client, class_639.method_2950(this.last.field_3761), this.last, false, null);
               } catch (Throwable var3) {
                  System.err.println("[SC] AutoReconnect: " + var3.getMessage());
               }
            }
         }
      }
   }
}
