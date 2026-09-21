package dev.anticheatqa.module.player;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1799;
import net.minecraft.class_2680;
import net.minecraft.class_310;
import net.minecraft.class_3965;
import net.minecraft.class_239.class_240;

public class AutoTool extends Module {
   public final Module.BooleanSetting switchBack = new Module.BooleanSetting("Switch Back", false);
   private int prevSlot = -1;

   public AutoTool() {
      super("Auto Tool", "Switches to the best hotbar tool for the targeted block.", Category.MISC);
      this.settings.add(this.switchBack);
   }

   @Override
   public void onTick(class_310 client) {
      if (client.field_1724 != null && client.field_1687 != null) {
         if (!client.field_1690.field_1886.method_1434()) {
            if (this.switchBack.get() && this.prevSlot >= 0) {
               client.field_1724.method_31548().method_61496(this.prevSlot);
               this.prevSlot = -1;
            }
         } else if (client.field_1765 instanceof class_3965 bhr && client.field_1765.method_17783() == class_240.field_1332) {
            class_2680 state = client.field_1687.method_8320(bhr.method_17777());
            int best = -1;
            float bestSpeed = 1.0F;

            for (int i = 0; i < 9; i++) {
               class_1799 stack = client.field_1724.method_31548().method_5438(i);
               float sp = stack.method_7924(state);
               if (sp > bestSpeed) {
                  bestSpeed = sp;
                  best = i;
               }
            }

            int current = client.field_1724.method_31548().method_67532();
            if (best >= 0 && best != current) {
               if (this.prevSlot < 0) {
                  this.prevSlot = current;
               }

               client.field_1724.method_31548().method_61496(best);
            }
         }
      }
   }
}
