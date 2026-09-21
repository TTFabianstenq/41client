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
import net.minecraft.class_640;

public class StaffList extends Module {
   public final Module.TextSetting names = new Module.TextSetting("Staff Names", "Admin,Owner,Mod");
   public final Module.BooleanSetting notify = new Module.BooleanSetting("Notify On Detect", true);
   public final Module.NumberSetting range = new Module.NumberSetting("Alert Range", 128.0, 16.0, 512.0, 8.0);
   private final List<String> online = new ArrayList<>();
   private final HashSet<String> alerted = new HashSet<>();

   public StaffList() {
      super("Staff List", "Tracks configured staff usernames online/nearby.", Category.DONUT);
      this.settings.add(this.names);
      this.settings.add(this.notify);
      this.settings.add(this.range);
   }

   @Override
   public void onTick(class_310 client) {
      this.online.clear();
      if (client.method_1562() != null && client.field_1724 != null) {
         String[] configured = this.names.get().split(",");

         for (class_640 e : client.method_1562().method_2880()) {
            String n = e.method_2966().name();

            for (String c : configured) {
               if (!c.trim().isEmpty() && n.equalsIgnoreCase(c.trim())) {
                  this.online.add(n);
                  if (this.notify.get() && !this.alerted.contains(n.toLowerCase(Locale.ROOT))) {
                     double dist = -1.0;

                     for (class_1657 p : client.field_1687.method_18456()) {
                        if (p.method_5477().getString().equalsIgnoreCase(n)) {
                           dist = client.field_1724.method_5739(p);
                           break;
                        }
                     }

                     if (dist < 0.0 || dist <= this.range.get()) {
                        client.field_1724
                           .method_7353(class_2561.method_43470("\u00a7c[SC] Staff online: " + n + (dist >= 0.0 ? String.format(" (%.0fm)", dist) : "")), false);
                        this.alerted.add(n.toLowerCase(Locale.ROOT));
                     }
                  }
               }
            }
         }

         this.alerted.removeIf(a -> this.online.stream().noneMatch(o -> o.equalsIgnoreCase(a)));
      }
   }

   public List<String> getOnlineStaff() {
      return this.online;
   }

   @Override
   public String getDisplay() {
      return this.isEnabled() ? "Staff:" + this.online.size() : "";
   }
}
