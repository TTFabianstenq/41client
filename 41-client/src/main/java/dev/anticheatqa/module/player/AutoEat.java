package dev.anticheatqa.module.player;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1268;
import net.minecraft.class_1799;
import net.minecraft.class_310;
import net.minecraft.class_9334;

public class AutoEat extends Module {
   public final Module.NumberSetting hunger = new Module.NumberSetting("Hunger Below", 14.0, 1.0, 20.0, 1.0);
   public final Module.BooleanSetting allowGap = new Module.BooleanSetting("Allow Golden Apple", false);
   private boolean eating;

   public AutoEat() {
      super("Auto Eat", "Eats food from hotbar when hunger is low.", Category.MISC);
      this.settings.add(this.hunger);
      this.settings.add(this.allowGap);
   }

   @Override
   public void onTick(class_310 client) {
      if (client.field_1724 != null && client.field_1761 != null) {
         if (client.field_1755 == null) {
            if (client.field_1724.method_7344().method_7586() > this.hunger.get()) {
               if (this.eating) {
                  client.field_1690.field_1904.method_23481(false);
                  this.eating = false;
               }
            } else {
               int food = this.findFood(client);
               if (food >= 0) {
                  client.field_1724.method_31548().method_61496(food);
                  client.field_1690.field_1904.method_23481(true);
                  client.field_1761.method_2919(client.field_1724, class_1268.field_5808);
                  this.eating = true;
               }
            }
         }
      }
   }

   private int findFood(class_310 client) {
      for (int i = 0; i < 9; i++) {
         class_1799 s = client.field_1724.method_31548().method_5438(i);
         if (!s.method_7960() && s.method_58694(class_9334.field_50075) != null) {
            String id = s.method_7909().toString().toLowerCase();
            if (this.allowGap.get() || !id.contains("golden_apple") && !id.contains("enchanted_golden")) {
               return i;
            }
         }
      }

      return -1;
   }

   @Override
   protected void onDisable() {
      class_310 c = class_310.method_1551();
      if (c.field_1690 != null) {
         c.field_1690.field_1904.method_23481(false);
      }

      this.eating = false;
   }
}
