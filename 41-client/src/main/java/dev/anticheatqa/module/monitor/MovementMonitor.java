package dev.anticheatqa.module.monitor;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_746;

public class MovementMonitor extends Module {
   private double x;
   private double y;
   private double z;
   private double hSpeed;
   private double vSpeed;
   private boolean onGround;
   private boolean sprinting;
   private class_243 velocity = class_243.field_1353;

   public MovementMonitor() {
      super("Movement Monitor", "Displays position, speed and movement state.", Category.MONITOR);
   }

   @Override
   public void onTick(class_310 client) {
      if (client.field_1724 != null) {
         class_746 p = client.field_1724;
         this.x = p.method_23317();
         this.y = p.method_23318();
         this.z = p.method_23321();
         this.velocity = p.method_18798();
         this.hSpeed = Math.sqrt(this.velocity.field_1352 * this.velocity.field_1352 + this.velocity.field_1350 * this.velocity.field_1350);
         this.vSpeed = this.velocity.field_1351;
         this.onGround = p.method_24828();
         this.sprinting = p.method_5624();
      }
   }

   @Override
   public String getDisplay() {
      return String.format(
         "XYZ: %.1f %.1f %.1f  H:%.3f V:%.3f  Ground:%s Sprint:%s", this.x, this.y, this.z, this.hSpeed, this.vSpeed, this.onGround, this.sprinting
      );
   }
}
