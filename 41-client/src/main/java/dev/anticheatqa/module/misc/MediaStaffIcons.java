package dev.anticheatqa.module.misc;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;

public class MediaStaffIcons extends Module {
   public final Module.BooleanSetting staffIcon = new Module.BooleanSetting("Staff Icon", true);
   public final Module.BooleanSetting mediaIcon = new Module.BooleanSetting("Media Icon", true);
   public final Module.TextSetting mediaNames = new Module.TextSetting("Media Names", "");

   public MediaStaffIcons() {
      super("Media Staff Icons", "Marks staff/media names in tab-style HUD lists.", Category.MISC);
      this.settings.add(this.staffIcon);
      this.settings.add(this.mediaIcon);
      this.settings.add(this.mediaNames);
   }
}
