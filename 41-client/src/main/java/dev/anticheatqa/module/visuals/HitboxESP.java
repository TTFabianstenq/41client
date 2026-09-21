package dev.anticheatqa.module.visuals;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_310;

public class HitboxESP extends Module {
   public final Module.BooleanSetting players = new Module.BooleanSetting("Players", true);
   public final Module.BooleanSetting mobs = new Module.BooleanSetting("Mobs", true);
   public final Module.NumberSetting range = new Module.NumberSetting("Range", 48.0, 8.0, 128.0, 4.0);
   private int count;

   public HitboxESP() {
      super("Hitbox ESP", "Accurate collision hitboxes through walls. Reach / hitbox QA.", Category.RENDER);
      this.settings.add(this.players);
      this.settings.add(this.mobs);
      this.settings.add(this.range);
   }

   @Override
   public void onTick(class_310 client) {
      if (client.field_1687 != null && client.field_1724 != null) {
         int n = 0;
         double max = this.range.get();

         for (class_1297 e : client.field_1687.method_18112()) {
            if (e != client.field_1724 && e.method_5805()) {
               if (this.players.get() && e instanceof class_1657) {
                  if (client.field_1724.method_5739(e) <= max) {
                     n++;
                  }
               } else if (this.mobs.get() && e instanceof class_1309 && !(e instanceof class_1657) && client.field_1724.method_5739(e) <= max) {
                  n++;
               }
            }
         }

         this.count = n;
      } else {
         this.count = 0;
      }
   }

   @Override
   public String getDisplay() {
      return this.isEnabled() ? "Hitboxes:" + this.count : "";
   }
}
