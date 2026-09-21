package dev.anticheatqa.module.combat;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1268;
import net.minecraft.class_1802;
import net.minecraft.class_2246;
import net.minecraft.class_2680;
import net.minecraft.class_310;
import net.minecraft.class_3965;
import net.minecraft.class_4969;
import net.minecraft.class_239.class_240;

public class CombatAnchor extends Module {
   public final Module.NumberSetting charges = new Module.NumberSetting("Charges", 4.0, 1.0, 4.0, 1.0);
   public final Module.BooleanSetting explode = new Module.BooleanSetting("Explode", true);

   public CombatAnchor() {
      super("Combat Anchor", "Charges and detonates respawn anchors under crosshair.", Category.COMBAT);
      this.settings.add(this.charges);
      this.settings.add(this.explode);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1724 != null && c.field_1687 != null && c.field_1761 != null && c.field_1755 == null) {
         if (c.field_1765 instanceof class_3965 bhr && c.field_1765.method_17783() == class_240.field_1332) {
            class_2680 st = c.field_1687.method_8320(bhr.method_17777());
            if (st.method_27852(class_2246.field_23152)) {
               int ch = (Integer)st.method_11654(class_4969.field_23153);
               boolean glow = c.field_1724.method_6047().method_31574(class_1802.field_8801) || c.field_1724.method_6079().method_31574(class_1802.field_8801);
               if (ch < this.charges.get().intValue() && glow) {
                  class_1268 h = c.field_1724.method_6047().method_31574(class_1802.field_8801) ? class_1268.field_5808 : class_1268.field_5810;
                  c.field_1761.method_2896(c.field_1724, h, bhr);
                  c.field_1724.method_6104(h);
               } else if (this.explode.get() && ch >= this.charges.get().intValue() && !c.field_1724.method_6047().method_31574(class_1802.field_8801)) {
                  c.field_1761.method_2896(c.field_1724, class_1268.field_5808, bhr);
                  c.field_1724.method_6104(class_1268.field_5808);
               }
            }
         }
      }
   }
}
