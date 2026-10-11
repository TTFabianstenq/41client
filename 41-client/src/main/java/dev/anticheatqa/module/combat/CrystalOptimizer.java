package dev.anticheatqa.module.combat;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_310;

public class CrystalOptimizer extends Module {
   public final Module.BooleanSetting reduceParticles = new Module.BooleanSetting("Reduce Particles", true);

   public CrystalOptimizer() {
      super("Crystal Optimizer", "Client-side crystal combat assist flag (safe, no entity discard).", Category.COMBAT);
      this.settings.add(this.reduceParticles);
   }

   @Override
   public void onTick(class_310 c) {
   }
}
