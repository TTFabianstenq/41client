package dev.anticheatqa.module.basefinding;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_2818;
import net.minecraft.class_310;

public class BlockEntityDebug extends Module {
   private int count;

   public BlockEntityDebug() {
      super("Block Entity Debug", "Shows count of block entities in your current chunk.", Category.WORLD);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1687 != null && c.field_1724 != null) {
         try {
            class_2818 ch = c.field_1687.method_8500(c.field_1724.method_24515());
            this.count = ch != null ? ch.method_12214().size() : 0;
         } catch (Throwable var5) {
            try {
               class_2818 chx = c.field_1687.method_8497(c.field_1724.method_31476().field_9181, c.field_1724.method_31476().field_9180);
               this.count = chx.method_12214().size();
            } catch (Throwable var4) {
               this.count = 0;
            }
         }
      }
   }

   @Override
   public String getDisplay() {
      return this.isEnabled() ? "BE:" + this.count : "";
   }
}
