package dev.anticheatqa.module.visuals;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_310;

public class Nametags extends Module {
   public static boolean active;
   public final Module.BooleanSetting showHealth = new Module.BooleanSetting("Show Health", true);
   public final Module.BooleanSetting showDistance = new Module.BooleanSetting("Show Distance", true);

   public Nametags() {
      super("Name Tags", "Forces entity nameplates + extra info on HUD.", Category.RENDER);
      this.settings.add(this.showHealth);
      this.settings.add(this.showDistance);
   }

   @Override
   protected void onEnable() {
      active = true;
   }

   @Override
   protected void onDisable() {
      active = false;
   }

   @Override
   public void onTick(class_310 client) {
   }

   @Override
   public String getDisplay() {
      return active ? "Nametags" : "";
   }
}
