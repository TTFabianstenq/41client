package dev.anticheatqa.module.client;

import dev.anticheatqa.gui.ClickGuiScreen;
import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_310;

public class ClickGuiModule extends Module {
   public ClickGuiModule() {
      super("Click GUI", "Opens 41 Client ClickGUI (also Right Shift).", Category.CLIENT);
      this.setKeybind(344);
   }

   @Override
   protected void onEnable() {
      class_310 c = class_310.method_1551();
      if (c != null) {
         c.method_1507(new ClickGuiScreen());
      }

      this.setEnabled(false);
   }
}
