package dev.anticheatqa.module.client;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;

public class SpotifyHud extends Module {
   public final Module.TextSetting song = new Module.TextSetting("Song", "Not Connected");
   public final Module.TextSetting artist = new Module.TextSetting("Artist", "\u2014");
   public final Module.BooleanSetting show = new Module.BooleanSetting("Show On HUD", true);

   public SpotifyHud() {
      super("Spotify HUD", "Displays song/artist text on HUD (set manually or via future API).", Category.CLIENT);
      this.settings.add(this.song);
      this.settings.add(this.artist);
      this.settings.add(this.show);
   }

   @Override
   public String getDisplay() {
      return this.isEnabled() && this.show.get() ? this.song.get() + " - " + this.artist.get() : "";
   }
}
