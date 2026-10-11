package dev.anticheatqa.module.basefinding;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_2561;
import net.minecraft.class_310;

public class RtpBaseFinder extends Module {
   public final Module.EnumSetting biome = new Module.EnumSetting("Mode", "rtp", "rtp", "wild");
   public final Module.NumberSetting interval = new Module.NumberSetting("Interval Sec", 8.0, 3.0, 60.0, 1.0);
   private long last;

   public RtpBaseFinder() {
      super("RTP Base Finder", "Periodically runs /rtp or /wild to hop locations while scanning.", Category.WORLD);
      this.settings.add(this.biome);
      this.settings.add(this.interval);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1724 != null && c.method_1562() != null) {
         long now = System.currentTimeMillis();
         if (!(now - this.last < this.interval.get() * 1000.0)) {
            this.last = now;
            c.method_1562().method_45730(this.biome.get());
            c.field_1724.method_7353(class_2561.method_43470("\u00a7d[SC] RTP hop (/ " + this.biome.get() + ")"), true);
         }
      }
   }
}
