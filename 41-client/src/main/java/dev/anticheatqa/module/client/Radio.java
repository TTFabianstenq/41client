package dev.anticheatqa.module.client;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;

public class Radio extends Module {
   public final Module.EnumSetting station = new Module.EnumSetting("Station", "Off", "Off", "LoFi", "Synth", "Classic");

   public Radio() {
      super("Radio", "Cosmetic radio station label in HUD (no audio stream bundled).", Category.CLIENT);
      this.settings.add(this.station);
   }

   @Override
   public String getDisplay() {
      return this.isEnabled() ? "Radio:" + this.station.get() : "";
   }
}
