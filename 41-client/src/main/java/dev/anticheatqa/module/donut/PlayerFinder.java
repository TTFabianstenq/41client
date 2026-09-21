package dev.anticheatqa.module.donut;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import net.minecraft.class_1657;
import net.minecraft.class_2561;
import net.minecraft.class_310;

public class PlayerFinder extends Module {
   public final Module.TextSetting targets = new Module.TextSetting("Targets", "");
   public final Module.NumberSetting range = new Module.NumberSetting("Range", 128.0, 16.0, 512.0, 8.0);
   public final Module.BooleanSetting notify = new Module.BooleanSetting("Notify", true);
   public final Module.BooleanSetting espHint = new Module.BooleanSetting("HUD Highlight", true);
   private final List<String> found = new ArrayList<>();
   private final HashSet<String> alerted = new HashSet<>();

   public PlayerFinder() {
      super("Player Finder", "Alerts when configured player names are nearby.", Category.DONUT);
      this.settings.add(this.targets);
      this.settings.add(this.range);
      this.settings.add(this.notify);
      this.settings.add(this.espHint);
   }

   @Override
   public void onTick(class_310 client) {
      this.found.clear();
      if (client.field_1687 != null && client.field_1724 != null) {
         String raw = this.targets.get().trim();
         if (!raw.isEmpty()) {
            String[] names = raw.split(",");

            for (class_1657 p : client.field_1687.method_18456()) {
               if (p != client.field_1724 && p.method_5805()) {
                  double d = client.field_1724.method_5739(p);
                  if (!(d > this.range.get())) {
                     String pn = p.method_5477().getString();

                     for (String t : names) {
                        if (!t.trim().isEmpty() && pn.equalsIgnoreCase(t.trim())) {
                           this.found.add(String.format("%s %.0fm", pn, d));
                           String key = pn.toLowerCase(Locale.ROOT);
                           if (this.notify.get() && !this.alerted.contains(key)) {
                              client.field_1724.method_7353(class_2561.method_43470(String.format("\u00a7e[SC] Player found: %s at %.0fm", pn, d)), false);
                              this.alerted.add(key);
                           }
                        }
                     }
                  }
               }
            }

            this.alerted.removeIf(a -> this.found.stream().noneMatch(f -> f.toLowerCase(Locale.ROOT).startsWith(a)));
         }
      }
   }

   public List<String> getFound() {
      return this.found;
   }

   @Override
   public String getDisplay() {
      return this.isEnabled() ? "PFind:" + this.found.size() : "";
   }
}
