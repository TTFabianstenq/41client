package dev.anticheatqa.module.combat;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_310;
import net.minecraft.class_3966;
import net.minecraft.class_239.class_240;

public class TargetHud extends Module {
   public final Module.NumberSetting range = new Module.NumberSetting("Range", 8.0, 3.0, 32.0, 0.5);
   public final Module.BooleanSetting playersOnly = new Module.BooleanSetting("Players Only", true);
   private String name = "-";
   private float hp;
   private float maxHp;
   private double dist;

   public TargetHud() {
      super("Target HUD", "Shows info about the entity under crosshair.", Category.COMBAT);
      this.settings.add(this.range);
      this.settings.add(this.playersOnly);
   }

   @Override
   public void onTick(class_310 client) {
      this.name = "-";
      this.hp = this.maxHp = 0.0F;
      this.dist = 0.0;
      if (client.field_1724 != null && client.field_1765 != null) {
         if (client.field_1765.method_17783() == class_240.field_1331) {
            if (client.field_1765 instanceof class_3966 ehr) {
               if (ehr.method_17782() instanceof class_1309 le && le.method_5805()) {
                  if (!this.playersOnly.get() || le instanceof class_1657) {
                     if (!(client.field_1724.method_5739(le) > this.range.get())) {
                        this.name = le.method_5477().getString();
                        this.hp = le.method_6032();
                        this.maxHp = le.method_6063();
                        this.dist = client.field_1724.method_5739(le);
                     }
                  }
               }
            }
         }
      }
   }

   public String getTargetName() {
      return this.name;
   }

   public float getHp() {
      return this.hp;
   }

   public float getMaxHp() {
      return this.maxHp;
   }

   public double getDist() {
      return this.dist;
   }

   @Override
   public String getDisplay() {
      if (this.isEnabled() && !"-".equals(this.name)) {
         return String.format("Target:%s %.0f/%.0f", this.name, this.hp, this.maxHp);
      } else {
         return this.isEnabled() ? "Target:-" : "";
      }
   }
}
