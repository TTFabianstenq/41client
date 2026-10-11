package dev.anticheatqa.module.client;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_310;

public class ChatMacro extends Module {
   public final Module.TextSetting message = new Module.TextSetting("Message", "gg");

   public ChatMacro() {
      super("Chat Macro", "Sends a configured chat message when toggled.", Category.CLIENT);
      this.settings.add(this.message);
   }

   @Override
   protected void onEnable() {
      class_310 c = class_310.method_1551();
      if (c.field_1724 != null && c.method_1562() != null) {
         String msg = this.message.get();
         if (msg.startsWith("/")) {
            c.method_1562().method_45730(msg.substring(1));
         } else {
            c.method_1562().method_45729(msg);
         }
      }

      this.setEnabled(false);
   }
}
