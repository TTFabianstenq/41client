package dev.anticheatqa.module.client;

import dev.anticheatqa.discord.DiscordIPC;
import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_310;

public class DiscordPresence extends Module {
   public static final String DEFAULT_APP_ID = "1542915602648145950";
   public final Module.TextSetting appId = new Module.TextSetting("App ID", "1542915602648145950");
   public final Module.TextSetting details = new Module.TextSetting("Details", "Playing Minecraft");
   public final Module.TextSetting state = new Module.TextSetting("State", "41 Client");
   public final Module.BooleanSetting showTime = new Module.BooleanSetting("Show Time", true);
   private DiscordIPC ipc;
   private int tickCounter;
   private volatile boolean failed;

   public DiscordPresence() {
      super("Discord Presence", "Shows 41 Client on Discord (async).", Category.CLIENT);
      this.settings.add(this.appId);
      this.settings.add(this.details);
      this.settings.add(this.state);
      this.settings.add(this.showTime);
   }

   @Override
   public void onEnable() {
      this.failed = false;
      this.tickCounter = 0;

      try {
         String id = this.appId.get();
         if (id == null || id.isBlank()) {
            id = "1542915602648145950";
         }

         this.ipc = new DiscordIPC(id.trim());
         this.ipc.start();
      } catch (Throwable var2) {
         this.failed = true;
         this.ipc = null;
      }
   }

   @Override
   public void onDisable() {
      try {
         if (this.ipc != null) {
            this.ipc.close();
         }
      } catch (Throwable var2) {
      }

      this.ipc = null;
   }

   @Override
   public void onTick(class_310 client) {
      if (!this.failed && this.ipc != null) {
         if (++this.tickCounter % 100 == 0) {
            try {
               String det = sanitize(this.details.get() != null ? this.details.get() : "Playing Minecraft");
               String st = sanitize(this.state.get() != null ? this.state.get() : "41 Client");
               StringBuilder act = new StringBuilder("{\"details\":\"").append(det).append("\",\"state\":\"").append(st).append("\"");
               if (this.showTime.get()) {
                  act.append(",\"timestamps\":{\"start\":").append(System.currentTimeMillis() / 1000L).append("}");
               }

               act.append("}");
               this.ipc.setPresence(act.toString());
            } catch (Throwable var6) {
               this.failed = true;

               try {
                  if (this.ipc != null) {
                     this.ipc.close();
                  }
               } catch (Throwable var5) {
               }

               this.ipc = null;
            }
         }
      }
   }

   private static String sanitize(String s) {
      if (s == null) {
         return "";
      } else {
         if (s.length() > 120) {
            s = s.substring(0, 120);
         }

         return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", " ").replace("\r", "");
      }
   }
}
