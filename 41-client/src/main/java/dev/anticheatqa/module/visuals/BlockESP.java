package dev.anticheatqa.module.visuals;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.class_2338;
import net.minecraft.class_2680;
import net.minecraft.class_310;

public class BlockESP extends Module {
   public static List<class_2338> positions = new CopyOnWriteArrayList<>();
   private int count;
   private int scanTicker;
   public final Module.NumberSetting range = new Module.NumberSetting("Range", 12.0, 4.0, 32.0, 1.0);
   public final Module.BooleanSetting diamond = new Module.BooleanSetting("Diamond Ore", true);
   public final Module.BooleanSetting emerald = new Module.BooleanSetting("Emerald Ore", true);
   public final Module.BooleanSetting gold = new Module.BooleanSetting("Gold Ore", true);
   public final Module.BooleanSetting iron = new Module.BooleanSetting("Iron Ore", true);
   public final Module.BooleanSetting coal = new Module.BooleanSetting("Coal Ore", true);
   public final Module.BooleanSetting ancient = new Module.BooleanSetting("Ancient Debris", true);
   public final Module.BooleanSetting spawner = new Module.BooleanSetting("Monster Spawner", true);

   public BlockESP() {
      super("Block ESP", "3D outlines for ores and monster spawners.", Category.RENDER);
      this.settings.add(this.range);
      this.settings.add(this.diamond);
      this.settings.add(this.emerald);
      this.settings.add(this.gold);
      this.settings.add(this.iron);
      this.settings.add(this.coal);
      this.settings.add(this.ancient);
      this.settings.add(this.spawner);
   }

   @Override
   public void onTick(class_310 client) {
      try {
         this.scanTicker++;
         if (this.scanTicker % 20 != 0) {
            return;
         }

         if (client.field_1687 == null || client.field_1724 == null) {
            return;
         }

         positions.clear();
         int r = this.range.get().intValue();
         int cx = (int)client.field_1724.method_23317();
         int cy = (int)client.field_1724.method_23318();
         int cz = (int)client.field_1724.method_23321();
         int n = 0;

         for (int x = -r; x <= r; x++) {
            for (int y = -r; y <= r; y++) {
               for (int z = -r; z <= r; z++) {
                  class_2338 pos = new class_2338(cx + x, cy + y, cz + z);
                  class_2680 state = client.field_1687.method_8320(pos);
                  if (!state.method_26215()) {
                     String id = state.method_26204().method_63499().toLowerCase();
                     boolean match = false;
                     if (this.diamond.get() && id.contains("diamond_ore")) {
                        match = true;
                     } else if (this.emerald.get() && id.contains("emerald_ore")) {
                        match = true;
                     } else if (this.gold.get() && id.contains("gold_ore")) {
                        match = true;
                     } else if (this.iron.get() && id.contains("iron_ore")) {
                        match = true;
                     } else if (this.coal.get() && id.contains("coal_ore")) {
                        match = true;
                     } else if (this.ancient.get() && id.contains("ancient_debris")) {
                        match = true;
                     } else if (this.spawner.get() && id.contains("spawner")) {
                        match = true;
                     }

                     if (match) {
                        positions.add(pos.method_10062());
                        n++;
                     }
                  }
               }
            }
         }

         this.count = n;
      } catch (Throwable var14) {
      }
   }

   @Override
   protected void onDisable() {
      positions.clear();
      this.count = 0;
   }

   @Override
   public String getDisplay() {
      return "Blocks: " + this.count;
   }
}
