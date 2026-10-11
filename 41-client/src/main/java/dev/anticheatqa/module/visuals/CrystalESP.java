package dev.anticheatqa.module.visuals;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.class_1297;
import net.minecraft.class_1511;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_310;

public class CrystalESP extends Module {
   public static final List<class_1297> crystals = new CopyOnWriteArrayList<>();
   public static final List<class_2338> anchors = new CopyOnWriteArrayList<>();
   public final Module.NumberSetting range = new Module.NumberSetting("Range", 48.0, 8.0, 96.0, 4.0);
   public final Module.BooleanSetting showCrystals = new Module.BooleanSetting("End Crystals", true);
   public final Module.BooleanSetting showAnchors = new Module.BooleanSetting("Respawn Anchors", true);
   private int crystalCount;
   private int anchorCount;
   private int ticker;

   public CrystalESP() {
      super("Crystal ESP", "End crystals + respawn anchors through walls. Crystal PvP QA.", Category.RENDER);
      this.settings.add(this.range);
      this.settings.add(this.showCrystals);
      this.settings.add(this.showAnchors);
   }

   @Override
   public void onTick(class_310 client) {
      try {
         if (client.field_1687 == null || client.field_1724 == null) {
            crystals.clear();
            anchors.clear();
            this.crystalCount = this.anchorCount = 0;
            return;
         }

         double max = this.range.get();
         List<class_1297> nextCrystals = new ArrayList<>();
         if (this.showCrystals.get()) {
            for (class_1297 e : client.field_1687.method_18112()) {
               if (e instanceof class_1511 && !(client.field_1724.method_5739(e) > max)) {
                  nextCrystals.add(e);
               }
            }
         }

         crystals.clear();
         crystals.addAll(nextCrystals);
         this.crystalCount = nextCrystals.size();
         this.ticker++;
         if (this.ticker % 8 == 0 && this.showAnchors.get()) {
            anchors.clear();
            int r = (int)max;
            class_2338 origin = client.field_1724.method_24515();
            int n = 0;

            for (int x = -r; x <= r; x++) {
               for (int y = -8; y <= 8; y++) {
                  for (int z = -r; z <= r; z++) {
                     class_2338 p = origin.method_10069(x, y, z);
                     if (client.field_1687.method_8320(p).method_27852(class_2246.field_23152)) {
                        anchors.add(p.method_10062());
                        n++;
                     }
                  }
               }
            }

            this.anchorCount = n;
         } else if (!this.showAnchors.get()) {
            anchors.clear();
            this.anchorCount = 0;
         }
      } catch (Throwable var12) {
      }
   }

   @Override
   protected void onDisable() {
      crystals.clear();
      anchors.clear();
      this.crystalCount = this.anchorCount = 0;
   }

   @Override
   public String getDisplay() {
      return !this.isEnabled() ? "" : String.format("Crystals:%d Anchors:%d", this.crystalCount, this.anchorCount);
   }
}
