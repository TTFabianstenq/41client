package dev.anticheatqa.module.combat;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_310;

public class AutoJumpReset extends Module {
   private float lastHp = 20.0F;

   public AutoJumpReset() {
      super("Auto Jump Reset", "Jumps when taking damage to reduce knockback (W-tap style).", Category.COMBAT);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1724 != null) {
         float hp = c.field_1724.method_6032();
         if (hp < this.lastHp - 0.1F && c.field_1724.method_24828()) {
            c.field_1724.method_6043();
         }

         this.lastHp = hp;
      }
   }
}
