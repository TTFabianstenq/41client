package dev.anticheatqa.module.render;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;

public class NoHurtCam extends Module {
   public static boolean active;

   public NoHurtCam() {
      super("No Hurt Cam", "Disables screen shake when taking damage.", Category.RENDER);
   }

   @Override
   protected void onEnable() {
      active = true;
   }

   @Override
   protected void onDisable() {
      active = false;
   }
}
