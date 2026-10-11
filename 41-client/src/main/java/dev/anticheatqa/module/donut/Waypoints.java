package dev.anticheatqa.module.donut;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_2338;
import net.minecraft.class_2561;
import net.minecraft.class_310;

public class Waypoints extends Module {
   public static final List<Waypoints.Waypoint> LIST = new ArrayList<>();
   public final Module.BooleanSetting showDistance = new Module.BooleanSetting("Show Distance", true);
   public final Module.BooleanSetting showInWorld = new Module.BooleanSetting("World Render", true);
   public final Module.TextSetting addName = new Module.TextSetting("New Name", "Waypoint");

   public Waypoints() {
      super(
         "Waypoints",
         "Save and display world waypoints. Right-click settings \u2192 use Add At Pos action via name field + enable toggle trick, or chat / not needed \u2014 use keybind Add.",
         Category.DONUT
      );
      this.settings.add(this.showDistance);
      this.settings.add(this.showInWorld);
      this.settings.add(this.addName);
   }

   public static void addHere(String name) {
      class_310 c = class_310.method_1551();
      if (c.field_1724 != null) {
         class_2338 p = c.field_1724.method_24515();
         String dim = c.field_1687 != null ? c.field_1687.method_27983().method_29177().method_12832() : "overworld";
         LIST.add(new Waypoints.Waypoint(name, p.method_10263(), p.method_10264(), p.method_10260(), dim, -11141121));
         c.field_1724
            .method_7353(
               class_2561.method_43470(String.format("\u00a7b[SC] Waypoint '%s' @ %d %d %d", name, p.method_10263(), p.method_10264(), p.method_10260())),
               false
            );
      }
   }

   @Override
   public void onTick(class_310 client) {
   }

   @Override
   public String getDisplay() {
      return this.isEnabled() ? "WP:" + LIST.size() : "";
   }

   public static class Waypoint {
      public String name;
      public int x;
      public int y;
      public int z;
      public String dimension;
      public int color;

      public Waypoint(String name, int x, int y, int z, String dimension, int color) {
         this.name = name;
         this.x = x;
         this.y = y;
         this.z = z;
         this.dimension = dimension;
         this.color = color;
      }
   }
}
