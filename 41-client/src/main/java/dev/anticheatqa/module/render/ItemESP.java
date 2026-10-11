package dev.anticheatqa.module.render;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_1297;
import net.minecraft.class_1542;
import net.minecraft.class_310;

public class ItemESP extends Module {
   public static final List<class_1542> items = new ArrayList<>();
   public final Module.NumberSetting range = new Module.NumberSetting("Range", 48.0, 8.0, 128.0, 4.0);
   public final Module.BooleanSetting valuablesOnly = new Module.BooleanSetting("Valuables Only", false);

   public ItemESP() {
      super("Item ESP", "Highlights dropped items in the world.", Category.RENDER);
      this.settings.add(this.range);
      this.settings.add(this.valuablesOnly);
   }

   @Override
   public void onTick(class_310 client) {
      items.clear();
      if (client.field_1687 != null && client.field_1724 != null) {
         for (class_1297 e : client.field_1687.method_18112()) {
            if (e instanceof class_1542 ie && !(client.field_1724.method_5739(e) > this.range.get())) {
               if (this.valuablesOnly.get()) {
                  String id = ie.method_6983().method_7909().toString().toLowerCase();
                  if (!id.contains("diamond")
                     && !id.contains("netherite")
                     && !id.contains("shulker")
                     && !id.contains("totem")
                     && !id.contains("elytra")
                     && !id.contains("enchanted")) {
                     continue;
                  }
               }

               items.add(ie);
            }
         }
      }
   }

   @Override
   public String getDisplay() {
      return this.isEnabled() ? "Items:" + items.size() : "";
   }
}
