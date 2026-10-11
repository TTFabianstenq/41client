package dev.anticheatqa.module.monitor;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_310;

public class RotationMonitor extends Module {
   private float yaw;
   private float pitch;
   private float deltaYaw;
   private float deltaPitch;
   private float rotationSpeed;
   private float lastYaw;
   private float lastPitch;

   public RotationMonitor() {
      super("Rotation Monitor", "Displays yaw, pitch and rotation change.", Category.MONITOR);
   }

   @Override
   public void onTick(class_310 client) {
      if (client.field_1724 != null) {
         this.lastYaw = this.yaw;
         this.lastPitch = this.pitch;
         this.yaw = client.field_1724.method_36454();
         this.pitch = client.field_1724.method_36455();
         this.deltaYaw = this.yaw - this.lastYaw;
         this.deltaPitch = this.pitch - this.lastPitch;

         while (this.deltaYaw > 180.0F) {
            this.deltaYaw -= 360.0F;
         }

         while (this.deltaYaw < -180.0F) {
            this.deltaYaw += 360.0F;
         }

         this.rotationSpeed = (float)Math.sqrt(this.deltaYaw * this.deltaYaw + this.deltaPitch * this.deltaPitch);
      }
   }

   @Override
   public String getDisplay() {
      return String.format("Yaw:%.1f Pitch:%.1f  dY:%.2f dP:%.2f  Speed:%.2f", this.yaw, this.pitch, this.deltaYaw, this.deltaPitch, this.rotationSpeed);
   }
}
