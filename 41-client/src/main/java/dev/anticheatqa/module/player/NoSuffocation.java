package dev.anticheatqa.module.player;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_310;

/** Blocks in-wall / suffocation for local player. */
public class NoSuffocation extends Module {
   public static NoSuffocation INSTANCE;
   public static volatile boolean activeFlag;

   public NoSuffocation() {
      super("No Suffocation", "No in-wall suffocation damage.", Category.PLAYER);
      INSTANCE = this;
   }

   public static boolean isActive() {
      return activeFlag || (INSTANCE != null && INSTANCE.isEnabled());
   }

   @Override
   protected void onEnable() {
      INSTANCE = this;
      activeFlag = true;
   }

   @Override
   protected void onDisable() {
      activeFlag = false;
   }

   @Override
   public void onTick(class_310 c) {
      activeFlag = isEnabled();
      try {
         // Also keep noClip briefly while inside blocks if module on
         if (activeFlag && c.field_1724 != null && Boolean.TRUE.equals(isEnabled())) {
            // isInsideWall check is handled by mixin; clear fall
            c.field_1724.field_6017 = 0;
         }
      } catch (Throwable ignored) {
      }
   }

   @Override
   public String getDisplay() {
      return isEnabled() ? "NoSuff" : "";
   }
}
