package dev.anticheatqa.module.misc;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_310;
import net.minecraft.class_3965;
import net.minecraft.class_239.class_240;

public class AutoMine extends Module {
   public AutoMine() {
      super("Auto Mine", "Holds left-click to mine the targeted block continuously.", Category.MISC);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1724 != null && c.field_1761 != null) {
         if (c.field_1765 instanceof class_3965 bhr && c.field_1765.method_17783() == class_240.field_1332) {
            c.field_1690.field_1886.method_23481(true);
            c.field_1761.method_2902(bhr.method_17777(), bhr.method_17780());
         }
      }
   }

   @Override
   protected void onDisable() {
      class_310 c = class_310.method_1551();
      if (c.field_1690 != null) {
         c.field_1690.field_1886.method_23481(false);
      }
   }
}
