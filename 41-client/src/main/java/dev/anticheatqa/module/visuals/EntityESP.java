package dev.anticheatqa.module.visuals;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1297;
import net.minecraft.class_1308;
import net.minecraft.class_1429;
import net.minecraft.class_1542;
import net.minecraft.class_1657;
import net.minecraft.class_1676;
import net.minecraft.class_310;

public class EntityESP extends Module {
   public static boolean boxesEnabled;
   private int count;
   public final Module.BooleanSetting players = new Module.BooleanSetting("Players", true);
   public final Module.BooleanSetting mobs = new Module.BooleanSetting("Mobs", true);
   public final Module.BooleanSetting animals = new Module.BooleanSetting("Animals", false);
   public final Module.BooleanSetting items = new Module.BooleanSetting("Items", false);
   public final Module.BooleanSetting projectiles = new Module.BooleanSetting("Projectiles", false);
   public final Module.BooleanSetting boxes = new Module.BooleanSetting("3D Boxes", true);

   public EntityESP() {
      super("Entity ESP", "Glow + optional 3D box outlines on entities.", Category.RENDER);
      this.settings.add(this.players);
      this.settings.add(this.mobs);
      this.settings.add(this.animals);
      this.settings.add(this.items);
      this.settings.add(this.projectiles);
      this.settings.add(this.boxes);
   }

   @Override
   protected void onEnable() {
      boxesEnabled = this.boxes.get();
   }

   @Override
   protected void onDisable() {
      boxesEnabled = false;

      try {
         class_310 client = class_310.method_1551();
         if (client.field_1687 != null) {
            for (class_1297 e : client.field_1687.method_18112()) {
               e.method_5834(false);
            }
         }
      } catch (Throwable var4) {
      }

      this.count = 0;
   }

   @Override
   public void onTick(class_310 client) {
      try {
         if (client.field_1687 == null || client.field_1724 == null) {
            return;
         }

         boxesEnabled = this.isEnabled() && this.boxes.get();
         int n = 0;

         for (class_1297 e : client.field_1687.method_18112()) {
            if (e != client.field_1724) {
               boolean shouldGlow = false;
               if (this.players.get() && e instanceof class_1657) {
                  shouldGlow = true;
               } else if (this.mobs.get() && e instanceof class_1308 && !(e instanceof class_1429)) {
                  shouldGlow = true;
               } else if (this.animals.get() && e instanceof class_1429) {
                  shouldGlow = true;
               } else if (this.items.get() && e instanceof class_1542) {
                  shouldGlow = true;
               } else if (this.projectiles.get() && e instanceof class_1676) {
                  shouldGlow = true;
               }

               e.method_5834(shouldGlow);
               if (shouldGlow) {
                  n++;
               }
            }
         }

         this.count = n;
      } catch (Throwable var6) {
      }
   }

   @Override
   public String getDisplay() {
      return "ESP Entities: " + this.count;
   }
}
