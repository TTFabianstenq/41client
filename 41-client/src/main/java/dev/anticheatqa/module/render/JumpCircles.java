package dev.anticheatqa.module.render;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_310;

public class JumpCircles extends Module {
   public static final List<double[]> circles = new ArrayList<>();
   private boolean wasOnGround = true;

   public JumpCircles() {
      super("Jump Circles", "Spawns expanding circles when you jump.", Category.RENDER);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1724 != null) {
         boolean g = c.field_1724.method_24828();
         if (this.wasOnGround && !g) {
            circles.add(new double[]{c.field_1724.method_23317(), c.field_1724.method_23318(), c.field_1724.method_23321(), 0.0});
         }

         this.wasOnGround = g;
         circles.removeIf(a -> {
            a[3] += 0.15;
            return a[3] > 2.5;
         });
      }
   }
}
