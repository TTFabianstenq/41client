package dev.anticheatqa.module.visuals;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_310;
import net.minecraft.class_3966;

public class HitParticles extends Module {
   public final Module.EnumSetting type = new Module.EnumSetting("Particle", "CRIT", "CRIT", "HEART", "FLAME", "ENCHANT");
   private boolean was;
   public static int lastHitTick;

   public HitParticles() {
      super("Hit Particles", "Hit feedback (safe on 1.21.11).", Category.RENDER);
      this.settings.add(this.type);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1724 != null && c.field_1687 != null) {
         boolean press = c.field_1690.field_1886.method_1434();
         if (press && !this.was && c.field_1765 instanceof class_3966) {
            lastHitTick = c.field_1724.field_6012;
         }

         this.was = press;
      }
   }
}
