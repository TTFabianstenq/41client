package dev.anticheatqa.module.visuals;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_310;

public class Tracers extends Module {
   public static boolean active;
   public final Module.BooleanSetting draw = new Module.BooleanSetting("Draw Tracers", true);
   public final Module.BooleanSetting players = new Module.BooleanSetting("Players", true);
   public final Module.BooleanSetting mobs = new Module.BooleanSetting("Mobs", true);
   public final Module.BooleanSetting animals = new Module.BooleanSetting("Animals", false);
   public final Module.NumberSetting range = new Module.NumberSetting("Range", 64.0, 8.0, 128.0, 4.0);
   public final Module.NumberSetting lineWidth = new Module.NumberSetting("Line Width", 2.0, 1.0, 5.0, 0.5);

   public Tracers() {
      super("Tracers", "3D world-space lines from your eye to entities. Right-click for Draw on/off.", Category.RENDER);
      this.settings.add(this.draw);
      this.settings.add(this.players);
      this.settings.add(this.mobs);
      this.settings.add(this.animals);
      this.settings.add(this.range);
      this.settings.add(this.lineWidth);
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
      active = this.isEnabled() && this.draw.get();
   }

   @Override
   public String getDisplay() {
      if (!this.isEnabled()) {
         return "";
      } else {
         return !this.draw.get() ? "Tracers 3D [OFF]" : "Tracers 3D";
      }
   }
}
