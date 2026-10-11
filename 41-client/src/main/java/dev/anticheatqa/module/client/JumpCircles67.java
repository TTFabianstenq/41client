package dev.anticheatqa.module.client;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_310;

public class JumpCircles67 extends Module {
   public static final List<double[]> circles = new ArrayList<>();
   private boolean wasGround = true;

   public JumpCircles67() {
      super("67 Jump Circles", "67-style expanding circles when you jump.", Category.CLIENT);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1724 != null) {
         boolean g = c.field_1724.method_24828();
         if (this.wasGround && !g) {
            circles.add(new double[]{c.field_1724.method_23317(), c.field_1724.method_23318(), c.field_1724.method_23321(), 0.0});
         }

         this.wasGround = g;
         circles.removeIf(a -> {
            a[3] += 0.12;
            return a[3] > 3.0;
         });
      }
   }
}
