package dev.anticheatqa.render;

import dev.anticheatqa.AntiCheatQA;
import dev.anticheatqa.module.Module;
import dev.anticheatqa.module.render.HudModule;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.class_310;
import net.minecraft.class_327;
import net.minecraft.class_332;
import org.lwjgl.glfw.GLFW;

public class HudRenderer {
   private static final int LIME = -5701888;
   private static final int DIM = -8750470;
   private static final int ON = -5701888;
   private static final int OFF = -11184811;

   public void render(class_332 context, float tickDelta) {
      class_310 client = class_310.method_1551();
      if (client.field_1724 != null && client.field_1687 != null) {
         if (!client.field_1690.field_1842) {
            if (client.field_1755 == null) {
               if (AntiCheatQA.INSTANCE != null) {
                  try {
                     class_327 tr = client.field_1772;
                     int sw = client.method_22683().method_4486();
                     boolean showWatermark = true;
                     boolean showArraylist = true;
                     boolean showCoords = true;
                     if (AntiCheatQA.INSTANCE.getModuleManager().getModule("HUD") instanceof HudModule hm) {
                        if (!hm.isEnabled()) {
                           showWatermark = false;
                           showArraylist = false;
                           showCoords = false;
                        } else {
                           showWatermark = hm.watermark.get();
                           showArraylist = hm.arraylist.get();
                           showCoords = hm.coords.get();
                        }
                     }

                     int topY = 4;
                     if (showWatermark) {
                        context.method_51433(tr, "41 CLIENT", 4, topY, -5701888, true);
                        context.method_51433(tr, "2.9.26", 4 + tr.method_1727("41 CLIENT") + 4, topY, -8750470, true);
                        topY += 11;
                     }

                     Module fpsMod = AntiCheatQA.INSTANCE.getModuleManager().getModule("FPS Monitor");
                     if (fpsMod != null && fpsMod.isEnabled()) {
                        context.method_51433(tr, client.method_47599() + " FPS", 4, topY, -5701888, true);
                        topY += 11;
                     }

                     if (showCoords) {
                        context.method_51433(
                           tr,
                           String.format(
                              "XYZ %.1f %.1f %.1f", client.field_1724.method_23317(), client.field_1724.method_23318(), client.field_1724.method_23321()
                           ),
                           4,
                           topY,
                           -5197648,
                           false
                        );
                        topY += 11;
                     }

                     long win = client.method_22683().method_4490();
                     int keyY = topY + 2;
                     this.drawKey(context, client, "W", GLFW.glfwGetKey(win, 87) == 1, 16, keyY);
                     this.drawKey(context, client, "A", GLFW.glfwGetKey(win, 65) == 1, 4, keyY + 12);
                     this.drawKey(context, client, "S", GLFW.glfwGetKey(win, 83) == 1, 16, keyY + 12);
                     this.drawKey(context, client, "D", GLFW.glfwGetKey(win, 68) == 1, 28, keyY + 12);
                     this.drawKey(context, client, "SPC", GLFW.glfwGetKey(win, 32) == 1, 44, keyY + 12);
                     this.drawKey(context, client, "SH", GLFW.glfwGetKey(win, 340) == 1, 68, keyY + 12);
                     if (showArraylist) {
                        List<Module> enabled = new ArrayList<>();

                        for (Module m : AntiCheatQA.INSTANCE.getModuleManager().getModules()) {
                           if (m.isEnabled()) {
                              String n = m.getName();
                              if (!n.equalsIgnoreCase("HUD") && !n.equalsIgnoreCase("Click GUI")) {
                                 enabled.add(m);
                              }
                           }
                        }

                        enabled.sort(Comparator.comparingInt(mx -> -tr.method_1727(mx.getName())));
                        int y = 4;

                        for (Module mx : enabled) {
                           String line = mx.getName();

                           try {
                              String extra = mx.getDisplay();
                              if (extra != null && !extra.isEmpty() && !extra.equals(line)) {
                                 line = line + " " + extra;
                              }
                           } catch (Throwable var23) {
                           }

                           int plainW = tr.method_1727(line) + 8;
                           int x = sw - plainW - 4;
                           context.method_25294(x - 2, y - 1, sw - 2, y + 10, -1728053248);
                           context.method_25294(sw - 2, y - 1, sw, y + 10, -5701888);
                           context.method_51433(tr, line, x, y, -5701888, false);
                           y += 11;
                        }
                     }
                  } catch (Throwable var24) {
                     Throwable t = var24;

                     try {
                        AntiCheatQA.LOGGER.error("HUD render error", t);
                     } catch (Throwable var22) {
                     }
                  }
               }
            }
         }
      }
   }

   private void drawKey(class_332 ctx, class_310 mc, String label, boolean down, int x, int y) {
      int w = mc.field_1772.method_1727(label) + 4;
      ctx.method_25294(x, y, x + w, y + 11, down ? -1440071680 : -2013265920);
      ctx.method_51433(mc.field_1772, label, x + 2, y + 2, down ? -5701888 : -11184811, false);
   }
}
