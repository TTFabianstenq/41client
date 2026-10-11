package dev.anticheatqa.module;

public enum Category {
   COMBAT("COMBAT"),
   MISC("MISC"),
   DONUT("DONUT"),
   WORLD("WORLD"),
   RENDER("RENDER"),
   CLIENT("CLIENT"),
   VISUALS("VISUALS"),
   MOVEMENT("MOVEMENT"),
   PLAYER("PLAYER"),
   MONITOR("MONITOR"),
   HUD("HUD"),
   SETTINGS("SETTINGS");

   public final String displayName;

   private Category(String displayName) {
      this.displayName = displayName;
   }
}
