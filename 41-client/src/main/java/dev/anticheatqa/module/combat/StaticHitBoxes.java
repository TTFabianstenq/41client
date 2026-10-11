package dev.anticheatqa.module.combat;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;

public class StaticHitBoxes extends Module {
   public final Module.NumberSetting size = new Module.NumberSetting("Size", 0.6, 0.3, 1.2, 0.05);

   public StaticHitBoxes() {
      super("Static Hitboxes", "Uses fixed expanded hitbox size for players (pairs with HitBox).", Category.COMBAT);
      this.settings.add(this.size);
   }
}
