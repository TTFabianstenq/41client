package dev.anticheatqa.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dev.anticheatqa.AntiCheatQA;
import dev.anticheatqa.module.Module;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConfigManager {
   private static final Path CONFIG_PATH = Paths.get("config", "fortyone-client.json");
   private static final Path LEGACY_PATH = Paths.get("config", "grok-client.json");
   private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
   private final AtomicBoolean loading = new AtomicBoolean(false);
   private volatile long lastSaveMs = 0L;

   public void save() {
      if (!this.loading.get() && AntiCheatQA.INSTANCE != null && AntiCheatQA.INSTANCE.getModuleManager() != null) {
         try {
            Files.createDirectories(CONFIG_PATH.getParent());
            JsonObject root = new JsonObject();
            root.addProperty("version", "2.9.26");
            JsonObject modules = new JsonObject();

            for (Module m : AntiCheatQA.INSTANCE.getModuleManager().getModules()) {
               JsonObject mod = new JsonObject();
               mod.addProperty("enabled", m.isEnabled());
               mod.addProperty("keybind", m.getKeybind());
               JsonObject settings = new JsonObject();

               for (Module.Setting<?> s : m.getSettings()) {
                  try {
                     if (s instanceof Module.BooleanSetting bs) {
                        settings.addProperty(s.getName(), bs.get());
                     } else if (s instanceof Module.NumberSetting ns) {
                        settings.addProperty(s.getName(), ns.get());
                     } else if (s instanceof Module.ColorSetting cs) {
                        settings.addProperty(s.getName(), cs.get());
                     } else if (s instanceof Module.TextSetting ts) {
                        settings.addProperty(s.getName(), ts.get() != null ? ts.get() : "");
                     } else if (s instanceof Module.EnumSetting es) {
                        settings.addProperty(s.getName(), es.get());
                     }
                  } catch (Throwable var15) {
                  }
               }

               mod.add("settings", settings);
               modules.add(m.getName(), mod);
            }

            root.add("modules", modules);
            Path tmp = CONFIG_PATH.resolveSibling("fortyone-client.json.tmp");
            Files.writeString(tmp, this.gson.toJson(root));
            Files.move(tmp, CONFIG_PATH, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            this.lastSaveMs = System.currentTimeMillis();
         } catch (Exception var16) {
            Exception e = var16;

            try {
               AntiCheatQA.LOGGER.error("save failed", e);
            } catch (Throwable var14) {
            }
         }
      }
   }

   public void saveSoon() {
      if (System.currentTimeMillis() - this.lastSaveMs >= 150L) {
         this.save();
      }
   }

   public void load() {
      Path path = Files.exists(CONFIG_PATH) ? CONFIG_PATH : LEGACY_PATH;
      if (Files.exists(path) && AntiCheatQA.INSTANCE != null && AntiCheatQA.INSTANCE.getModuleManager() != null) {
         this.loading.set(true);

         try {
            JsonObject root = (JsonObject)this.gson.fromJson(Files.readString(path), JsonObject.class);
            if (root != null && root.has("modules")) {
               JsonObject modules = root.getAsJsonObject("modules");

               for (Module m : AntiCheatQA.INSTANCE.getModuleManager().getModules()) {
                  if (modules.has(m.getName())) {
                     JsonObject mod = modules.getAsJsonObject(m.getName());
                     if (mod.has("keybind")) {
                        try {
                           m.setKeybind(mod.get("keybind").getAsInt());
                        } catch (Throwable var25) {
                        }
                     }

                     if (mod.has("settings") && mod.get("settings").isJsonObject()) {
                        JsonObject settings = mod.getAsJsonObject("settings");

                        for (Module.Setting<?> s : m.getSettings()) {
                           if (settings.has(s.getName())) {
                              try {
                                 if (s instanceof Module.BooleanSetting bs) {
                                    bs.set(settings.get(s.getName()).getAsBoolean());
                                 } else if (s instanceof Module.NumberSetting ns) {
                                    ns.set(settings.get(s.getName()).getAsDouble());
                                 } else if (s instanceof Module.ColorSetting cs) {
                                    cs.set(settings.get(s.getName()).getAsInt());
                                 } else if (s instanceof Module.TextSetting ts) {
                                    ts.set(settings.get(s.getName()).getAsString());
                                 } else if (s instanceof Module.EnumSetting es) {
                                    es.set(settings.get(s.getName()).getAsString());
                                 }
                              } catch (Throwable var24) {
                              }
                           }
                        }
                     }

                     if (mod.has("enabled")) {
                        try {
                           boolean want = mod.get("enabled").getAsBoolean();
                           if (want != m.isEnabled()) {
                              m.setEnabled(want);
                           }
                        } catch (Throwable var23) {
                        }
                     }
                  }
               }

               if (path.equals(LEGACY_PATH)) {
                  this.loading.set(false);
                  this.save();
               }

               return;
            }
         } catch (Exception var26) {
            Exception e = var26;

            try {
               AntiCheatQA.LOGGER.error("load failed", e);
            } catch (Throwable var22) {
            }

            return;
         } finally {
            this.loading.set(false);
         }
      }
   }
}
