package dev.anticheatqa;

import dev.anticheatqa.config.ConfigManager;
import dev.anticheatqa.gui.ClickGuiScreen;
import dev.anticheatqa.module.ModuleManager;
import dev.anticheatqa.render.HudRenderer;
import dev.anticheatqa.render.WorldEspRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.EndTick;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents.AfterEntities;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AntiCheatQA implements ClientModInitializer {
   public static final String MOD_ID = "fortyone-client";
   public static final Logger LOGGER = LoggerFactory.getLogger("fortyone-client");
   public static AntiCheatQA INSTANCE;
   private ModuleManager moduleManager;
   private ConfigManager configManager;
   private HudRenderer hudRenderer;
   private boolean lastRightShift = false;
   private int saveTick = 0;

   public void onInitializeClient() {
      INSTANCE = this;
      this.moduleManager = new ModuleManager();
      this.configManager = new ConfigManager();
      this.hudRenderer = new HudRenderer();
      this.configManager.load();
      ClientTickEvents.END_CLIENT_TICK.register((EndTick)client -> {
         if (++this.saveTick % 200 == 0) {
            try {
               this.configManager.saveSoon();
            } catch (Throwable var8) {
            }
         }

         if (client.field_1724 != null) {
            long window = client.method_22683().method_4490();
            boolean pressed = GLFW.glfwGetKey(window, 344) == 1;
            if (pressed && !this.lastRightShift) {
               if (client.field_1755 instanceof ClickGuiScreen) {
                  try {
                     this.configManager.save();
                  } catch (Throwable var7) {
                  }

                  client.method_1507(null);
               } else if (client.field_1755 == null) {
                  client.method_1507(new ClickGuiScreen());
               }
            }

            this.lastRightShift = pressed;

            try {
               this.moduleManager.onTick(client);
            } catch (Throwable var6) {
               LOGGER.error("tick", var6);
            }
         }
      });
      HudElementRegistry.attachElementAfter(
         VanillaHudElements.MISC_OVERLAYS, class_2960.method_60655("fortyone-client", "overlay"), (context, tickCounter) -> {
            try {
               if (INSTANCE != null && class_310.method_1551().field_1724 != null) {
                  this.hudRenderer.render(context, 0.0F);
               }
            } catch (Throwable var4) {
            }
         }
      );
      WorldRenderEvents.AFTER_ENTITIES.register((AfterEntities)ctx -> {
         try {
            WorldEspRenderer.render(ctx);
         } catch (Throwable var2) {
         }
      });
      Runtime.getRuntime().addShutdownHook(new Thread(() -> {
         try {
            this.configManager.save();
         } catch (Throwable var2) {
         }
      }, "41-ConfigSave"));
      LOGGER.info("41 Client 2.9.26 ready. Modules: " + this.moduleManager.getModules().size());
   }

   public ModuleManager getModuleManager() {
      return this.moduleManager;
   }

   public ConfigManager getConfigManager() {
      return this.configManager;
   }

   public HudRenderer getHudRenderer() {
      return this.hudRenderer;
   }
}
