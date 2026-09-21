package dev.anticheatqa.module.donut;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.HashSet;
import net.minecraft.class_1657;
import net.minecraft.class_2561;
import net.minecraft.class_310;

public class PlayerDetection extends Module {
   public final Module.NumberSetting range = new Module.NumberSetting("Range", 64.0, 16.0, 256.0, 8.0);
   public final Module.BooleanSetting notify = new Module.BooleanSetting("Chat Notify", true);
   private final HashSet<String> seen = new HashSet<>();
   private int count;

   public PlayerDetection() {
      super("Player Detection", "Notifies when new players enter render distance.", Category.DONUT);
      this.settings.add(this.range);
      this.settings.add(this.notify);
   }

   @Override
   public void onTick(class_310 client) {
      if (client.field_1687 != null && client.field_1724 != null) {
         this.count = 0;
         HashSet<String> now = new HashSet<>();

         for (class_1657 p : client.field_1687.method_18456()) {
            if (p != client.field_1724 && p.method_5805() && !(client.field_1724.method_5739(p) > this.range.get())) {
               this.count++;
               String id = p.method_5845();
               now.add(id);
               if (this.notify.get() && this.seen.add(id)) {
                  client.field_1724
                     .method_7353(
                        class_2561.method_43470(
                           "\u00a7e[SC] Player detected: \u00a7f"
                              + p.method_5477().getString()
                              + String.format(" \u00a77(%.0fm)", client.field_1724.method_5739(p))
                        ),
                        false
                     );
               }
            }
         }

         this.seen.retainAll(now);
      }
   }

   @Override
   public String getDisplay() {
      return this.isEnabled() ? "Players:" + this.count : "";
   }
}
