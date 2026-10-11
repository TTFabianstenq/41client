package dev.anticheatqa.module;

import dev.anticheatqa.AntiCheatQA;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.class_310;

public abstract class Module {
   private final String name;
   private final String description;
   private final Category category;
   private boolean enabled;
   private int keybind = -1;
   protected final List<Module.Setting<?>> settings = new ArrayList<>();

   public Module(String name, String description, Category category) {
      this.name = name;
      this.description = description;
      this.category = category;
   }

   public String getName() {
      return this.name;
   }

   public String getDescription() {
      return this.description;
   }

   public Category getCategory() {
      return this.category;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public int getKeybind() {
      return this.keybind;
   }

   public void setKeybind(int key) {
      this.keybind = key;

      try {
         if (AntiCheatQA.INSTANCE != null && AntiCheatQA.INSTANCE.getConfigManager() != null) {
            AntiCheatQA.INSTANCE.getConfigManager().saveSoon();
         }
      } catch (Throwable var3) {
      }
   }

   public List<Module.Setting<?>> getSettings() {
      return this.settings;
   }

   public void setEnabled(boolean enabled) {
      if (this.enabled != enabled) {
         this.enabled = enabled;
         if (enabled) {
            this.onEnable();
         } else {
            this.onDisable();
         }

         try {
            if (AntiCheatQA.INSTANCE != null && AntiCheatQA.INSTANCE.getConfigManager() != null) {
               AntiCheatQA.INSTANCE.getConfigManager().saveSoon();
            }
         } catch (Throwable var3) {
         }
      }
   }

   public void toggle() {
      this.setEnabled(!this.enabled);
   }

   protected void onEnable() {
   }

   protected void onDisable() {
   }

   public void onTick(class_310 client) {
   }

   public void onRender(class_310 client, float tickDelta) {
   }

   public String getDisplay() {
      return "";
   }

   public static class BooleanSetting extends Module.Setting<Boolean> {
      public BooleanSetting(String name, boolean defaultValue) {
         super(name, defaultValue);
      }
   }

   public static class ColorSetting extends Module.Setting<Integer> {
      public ColorSetting(String name, int defaultArgb) {
         super(name, defaultArgb);
      }
   }

   public static class EnumSetting extends Module.Setting<String> {
      private final List<String> options;

      public EnumSetting(String name, String defaultValue, String... options) {
         super(name, defaultValue);
         this.options = Arrays.asList(options);
      }

      public List<String> getOptions() {
         return this.options;
      }

      public void cycle() {
         int i = this.options.indexOf(this.get());
         if (i < 0) {
            i = 0;
         }

         this.set(this.options.get((i + 1) % this.options.size()));
      }
   }

   public static class NumberSetting extends Module.Setting<Double> {
      private final double min;
      private final double max;
      private final double step;

      public NumberSetting(String name, double defaultValue, double min, double max, double step) {
         super(name, defaultValue);
         this.min = min;
         this.max = max;
         this.step = step;
      }

      public double getMin() {
         return this.min;
      }

      public double getMax() {
         return this.max;
      }

      public double getStep() {
         return this.step;
      }

      public void setClamped(double v) {
         this.set(Math.max(this.min, Math.min(this.max, v)));
      }
   }

   public abstract static class Setting<T> {
      private final String name;
      private T value;
      private final T defaultValue;

      public Setting(String name, T defaultValue) {
         this.name = name;
         this.defaultValue = defaultValue;
         this.value = defaultValue;
      }

      public String getName() {
         return this.name;
      }

      public T get() {
         return this.value;
      }

      public void set(T value) {
         this.value = value;
      }

      public T getDefault() {
         return this.defaultValue;
      }

      public void reset() {
         this.value = this.defaultValue;
      }
   }

   public static class TextSetting extends Module.Setting<String> {
      public TextSetting(String name, String defaultValue) {
         super(name, defaultValue);
      }
   }
}
