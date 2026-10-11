package dev.anticheatqa.module.misc;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_2561;
import net.minecraft.class_310;

public class FakeRoles extends Module {
   public final Module.EnumSetting role = new Module.EnumSetting("Role", "Owner", "Owner", "Admin", "Mod", "Helper", "MVP", "VIP");

   public FakeRoles() {
      super("Fake Roles", "Shows a fake role tag in chat for screenshots.", Category.MISC);
      this.settings.add(this.role);
   }

   @Override
   protected void onEnable() {
      class_310 c = class_310.method_1551();
      if (c.field_1724 != null) {
         c.field_1724
            .method_7353(
               class_2561.method_43470(
                  "\u00a7c[" + this.role.get() + "] \u00a7f" + c.field_1724.method_5477().getString() + " \u00a77role applied (local only)"
               ),
               false
            );
      }

      this.setEnabled(false);
   }
}
