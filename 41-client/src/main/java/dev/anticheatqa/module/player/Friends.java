package dev.anticheatqa.module.player;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class Friends extends Module {
   public static final Set<String> LIST = new HashSet<>();
   public final Module.TextSetting nameInput = new Module.TextSetting("Name", "");
   public final Module.ColorSetting color = new Module.ColorSetting("Friend Color", -11141121);

   public Friends() {
      super("Friends", "Friend list for ESP/aim filters. Set Name then toggle module to add.", Category.MISC);
      this.settings.add(this.nameInput);
      this.settings.add(this.color);
   }

   @Override
   protected void onEnable() {
      String n = this.nameInput.get().trim();
      if (!n.isEmpty()) {
         LIST.add(n.toLowerCase(Locale.ROOT));
         this.nameInput.set("");
      }

      this.setEnabled(false);
   }

   public static boolean isFriend(String name) {
      return LIST.contains(name.toLowerCase(Locale.ROOT));
   }

   public static Set<String> all() {
      return Collections.unmodifiableSet(LIST);
   }

   @Override
   public String getDisplay() {
      return "Friends:" + LIST.size();
   }
}
