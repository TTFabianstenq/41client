package dev.anticheatqa.module.visuals;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_310;
import net.minecraft.class_7172;

public class Fullbright extends Module {
   public final Module.NumberSetting gamma = new Module.NumberSetting("Gamma", 16.0, 1.0, 16.0, 0.5);
   private double previousGamma = 1.0;

   public Fullbright() {
      super("Fullbright", "Client-side brightness (gamma).", Category.RENDER);
      this.settings.add(this.gamma);
   }

   @Override
   protected void onEnable() {
      try {
         class_310 client = class_310.method_1551();
         if (client.field_1690 == null) {
            return;
         }

         class_7172<Double> opt = client.field_1690.method_42473();
         this.previousGamma = (Double)opt.method_41753();
         opt.method_41748(this.gamma.get());
      } catch (Throwable var3) {
      }
   }

   @Override
   protected void onDisable() {
      try {
         class_310 client = class_310.method_1551();
         if (client.field_1690 == null) {
            return;
         }

         client.field_1690.method_42473().method_41748(this.previousGamma);
      } catch (Throwable var2) {
      }
   }

   @Override
   public void onTick(class_310 client) {
      try {
         if (client.field_1690 == null) {
            return;
         }

         client.field_1690.method_42473().method_41748(this.gamma.get());
      } catch (Throwable var3) {
      }
   }

   @Override
   public String getDisplay() {
      return "Fullbright";
   }
}
