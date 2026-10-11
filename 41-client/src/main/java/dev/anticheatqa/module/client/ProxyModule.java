package dev.anticheatqa.module.client;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_2561;
import net.minecraft.class_310;

public class ProxyModule extends Module {
   public final Module.TextSetting host = new Module.TextSetting("Host", "127.0.0.1");
   public final Module.NumberSetting port = new Module.NumberSetting("Port", 8080.0, 1.0, 65535.0, 1.0);

   public ProxyModule() {
      super("Proxy", "Stores proxy host/port for manual launcher use (does not inject SOCKS).", Category.CLIENT);
      this.settings.add(this.host);
      this.settings.add(this.port);
   }

   @Override
   protected void onEnable() {
      class_310 c = class_310.method_1551();
      if (c.field_1724 != null) {
         c.field_1724
            .method_7353(
               class_2561.method_43470("\u00a77[SC] Proxy set: " + this.host.get() + ":" + this.port.get().intValue() + " (configure in launcher)"), false
            );
      }

      this.setEnabled(false);
   }
}
