package dev.anticheatqa.gui;

import dev.anticheatqa.AntiCheatQA;
import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import dev.anticheatqa.module.Module.BooleanSetting;
import dev.anticheatqa.module.Module.EnumSetting;
import dev.anticheatqa.module.Module.NumberSetting;
import dev.anticheatqa.module.Module.Setting;
import dev.anticheatqa.module.Module.TextSetting;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.class_11905;
import net.minecraft.class_11908;
import net.minecraft.class_11909;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_437;
import org.lwjgl.glfw.GLFW;

public class ClickGuiScreen extends class_437 {
   private Module settingsModule = null;
   private boolean waitingForKeybind = false;
   private String search = "";
   private boolean searchFocused = false;
   private int editingTextIndex = -1;
   private final Map<Category, Integer> scroll = new HashMap<>();
   private int searchScroll = 0;
   private static final int LIME = -5701888;
   private static final int LIME_SOFT = 866713344;
   private static final int LIME_ROW = 1437138688;
   private static final int DOT_ON = -8585430;
   private static final int DOT_OFF = -12961214;
   private static final int BG = -1879048192;
   private static final int PANEL = -267251176;
   private static final int HEADER_BG = -266856417;
   private static final int TEXT_DIM = -7697770;
   private static final int TEXT_ON = -1;
   private static final int TEXT_OFF = -5197638;
   private static final int HOVER = 419430399;
   private static final int SEARCH_BG = -267119590;
   private static final Category[] COLUMNS = new Category[]{Category.COMBAT, Category.MOVEMENT, Category.PLAYER, Category.MISC, Category.DONUT, Category.WORLD, Category.RENDER, Category.CLIENT};
   private static final int PANEL_W = 112;
   private static final int ROW_H = 14;
   private static final int HEADER_H = 18;
   private static final int GAP = 6;
   private static final int TOP_H = 36;
   private static final int MAX_BODY = 300;
   private static final int R = 6;

   public ClickGuiScreen() {
      super(class_2561.method_43470("41 Client"));

      for (Category c : COLUMNS) {
         this.scroll.put(c, 0);
      }
   }

   private List<Module> modsFor(Category cat) {
      return AntiCheatQA.INSTANCE == null ? List.of() : AntiCheatQA.INSTANCE.getModuleManager().getModulesByCategory(cat);
   }

   private List<Module> searchResults() {
      return AntiCheatQA.INSTANCE != null && !this.search.isBlank() ? AntiCheatQA.INSTANCE.getModuleManager().search(this.search) : List.of();
   }

   private void persist() {
      try {
         if (AntiCheatQA.INSTANCE != null && AntiCheatQA.INSTANCE.getConfigManager() != null) {
            AntiCheatQA.INSTANCE.getConfigManager().saveSoon();
         }
      } catch (Throwable var2) {
      }
   }

   private int bodyH(int count) {
      return Math.min(Math.max(count, 0) * 14, 300);
   }

   private void roundRect(class_332 ctx, int x, int y, int w, int h, int color) {
      ctx.method_25294(x + 6, y, x + w - 6, y + h, color);
      ctx.method_25294(x, y + 6, x + w, y + h - 6, color);
      ctx.method_25294(x + 3, y + 1, x + 6, y + 2, color);
      ctx.method_25294(x + 2, y + 2, x + 6, y + 3, color);
      ctx.method_25294(x + 1, y + 3, x + 6, y + 6, color);
      ctx.method_25294(x + w - 6, y + 1, x + w - 3, y + 2, color);
      ctx.method_25294(x + w - 6, y + 2, x + w - 2, y + 3, color);
      ctx.method_25294(x + w - 6, y + 3, x + w - 1, y + 6, color);
      ctx.method_25294(x + 1, y + h - 6, x + 6, y + h - 3, color);
      ctx.method_25294(x + 2, y + h - 3, x + 6, y + h - 2, color);
      ctx.method_25294(x + 3, y + h - 2, x + 6, y + h - 1, color);
      ctx.method_25294(x + w - 6, y + h - 6, x + w - 1, y + h - 3, color);
      ctx.method_25294(x + w - 6, y + h - 3, x + w - 2, y + h - 2, color);
      ctx.method_25294(x + w - 6, y + h - 2, x + w - 3, y + h - 1, color);
   }

   private void drawDot(class_332 ctx, int cx, int cy, boolean on) {
      int c = on ? -8585430 : -12961214;
      ctx.method_25294(cx - 1, cy - 2, cx + 2, cy + 3, c);
      ctx.method_25294(cx - 2, cy - 1, cx + 3, cy + 2, c);
      if (on) {
         ctx.method_25294(cx, cy, cx + 1, cy + 1, -1507408);
      }
   }

   private void frame(class_332 ctx, int x, int y, int w, int h) {
      this.roundRect(ctx, x + 2, y + 3, w, h, 1073741824);
      this.roundRect(ctx, x, y, w, h, -267251176);
   }

   public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
      context.method_25294(0, 0, this.field_22789, this.field_22790, -1879048192);
      context.method_51433(this.field_22793, "41 CLIENT", 8, 8, -5701888, false);
      context.method_51433(this.field_22793, "2.9.26", 8 + this.field_22793.method_1727("41 CLIENT") + 5, 8, -7697770, false);
      int searchW = 180;
      int searchH = 16;
      int sx = (this.field_22789 - searchW) / 2;
      int sy = 6;
      this.roundRect(context, sx, sy, searchW, searchH, -267119590);
      if (this.searchFocused) {
         context.method_25294(sx + 2, sy + 3, sx + 4, sy + searchH - 3, -5701888);
      }

      String sShown = this.search.isEmpty() && !this.searchFocused ? "SEARCH MODULES..." : this.search + (this.searchFocused ? "_" : "");
      context.method_51433(this.field_22793, sShown, sx + 10, sy + 4, this.searchFocused ? -5701888 : -7697770, false);
      String hint = "L toggle · R settings · ESC";
      context.method_51433(this.field_22793, hint, this.field_22789 - this.field_22793.method_1727(hint) - 8, 8, -7697770, false);
      int cols = COLUMNS.length;
      int pw = Math.max(110, Math.min(130, (this.field_22789 - 20 - (cols - 1) * 6) / cols));
      int total = cols * pw + (cols - 1) * 6;
      int startX = Math.max(8, (this.field_22789 - total) / 2);
      int startY = 36;
      if (!this.search.isBlank()) {
         this.renderSearchPanel(context, mouseX, mouseY, startX, startY, total);
      } else {
         for (int i = 0; i < COLUMNS.length; i++) {
            this.renderCategory(context, startX + i * (pw + 6), startY, COLUMNS[i], this.modsFor(COLUMNS[i]), mouseX, mouseY, pw);
         }
      }

      if (this.settingsModule != null) {
         this.renderSettings(context, mouseX, mouseY);
      }
   }

   private void renderCategory(class_332 context, int x, int y, Category cat, List<Module> mods, int mouseX, int mouseY, int pw) {
      int body = this.bodyH(mods.size());
      int h = 18 + body + 4;
      int sc = this.scroll.getOrDefault(cat, 0);
      int maxScroll = Math.max(0, mods.size() * 14 - body);
      if (sc > maxScroll) {
         sc = maxScroll;
         this.scroll.put(cat, maxScroll);
      }

      this.frame(context, x, y, pw, h);
      context.method_25294(x + 6, y + 1, x + pw - 6, y + 18, -266856417);
      context.method_25294(x + 1, y + 6, x + pw - 1, y + 18, -266856417);
      context.method_25294(x + 3, y + 4, x + 5, y + 18 - 3, -5701888);
      context.method_51433(this.field_22793, cat.displayName, x + 10, y + 5, -1, false);
      int bodyTop = y + 18;
      int bodyBot = bodyTop + body;
      int first = body > 0 ? sc / 14 : 0;
      int yOff = body > 0 ? -(sc % 14) : 0;

      for (int i = first; i < mods.size(); i++) {
         int my = bodyTop + yOff + (i - first) * 14;
         if (my > bodyBot) {
            break;
         }

         if (my + 14 >= bodyTop) {
            Module m = mods.get(i);
            boolean on = m.isEnabled();
            boolean hover = mouseX >= x && mouseX < x + pw && mouseY >= Math.max(my, bodyTop) && mouseY < Math.min(my + 14, bodyBot);
            int rowL = x + 2;
            int rowR = x + pw - 2;
            int rowT = Math.max(my, bodyTop);
            int rowB = Math.min(my + 14, bodyBot);
            if (on) {
               context.method_25294(rowL, rowT, rowR, rowB, 1437138688);
            } else if (hover) {
               context.method_25294(rowL, rowT, rowR, rowB, 419430399);
            }

            if (my + 3 >= bodyTop && my + 14 - 2 <= bodyBot + 1) {
               context.method_51433(this.field_22793, m.getName().toUpperCase(Locale.ROOT), x + 8, my + 3, on ? -1 : -5197638, false);
               this.drawDot(context, x + pw - 12, my + 7, on);
            }
         }
      }

      if (maxScroll > 0) {
         int barH = Math.max(10, body * body / Math.max(1, mods.size() * 14));
         int barY = bodyTop + (int)((body - barH) * ((double)sc / maxScroll));
         context.method_25294(x + pw - 3, barY, x + pw - 2, barY + barH, -12961214);
      }
   }

   private void renderSearchPanel(class_332 context, int mouseX, int mouseY, int startX, int startY, int totalW) {
      List<Module> results = this.searchResults();
      int pw = Math.min(280, totalW);
      int x = startX + (totalW - pw) / 2;
      int body = this.bodyH(Math.min(results.size(), 20));
      int h = 18 + body + 4;
      this.frame(context, x, startY, pw, h);
      context.method_25294(x + 3, startY + 4, x + 5, startY + 18 - 3, -5701888);
      context.method_51433(this.field_22793, "RESULTS (" + results.size() + ")", x + 10, startY + 5, -1, false);
      int bodyTop = startY + 18;
      int maxScroll = Math.max(0, results.size() * 14 - body);
      if (this.searchScroll > maxScroll) {
         this.searchScroll = maxScroll;
      }

      int first = body > 0 ? this.searchScroll / 14 : 0;
      int yOff = body > 0 ? -(this.searchScroll % 14) : 0;

      for (int i = first; i < results.size() && i < first + 25; i++) {
         int my = bodyTop + yOff + (i - first) * 14;
         if (my > bodyTop + body) {
            break;
         }

         Module m = results.get(i);
         boolean on = m.isEnabled();
         boolean hover = mouseX >= x && mouseX < x + pw && mouseY >= my && mouseY < my + 14;
         if (on) {
            context.method_25294(x + 2, my, x + pw - 2, my + 14, 1437138688);
         } else if (hover) {
            context.method_25294(x + 2, my, x + pw - 2, my + 14, 419430399);
         }

         context.method_51433(this.field_22793, m.getName().toUpperCase(Locale.ROOT), x + 8, my + 3, on ? -1 : -5197638, false);
         this.drawDot(context, x + pw - 12, my + 7, on);
      }
   }

   private void renderSettings(class_332 context, int mouseX, int mouseY) {
      int w = 220;
      int rows = 1 + this.settingsModule.getSettings().size();
      int h = 32 + rows * 18 + 10;
      int x = (this.field_22789 - w) / 2;
      int y = Math.max(40, (this.field_22790 - h) / 2);
      context.method_25294(0, 0, this.field_22789, this.field_22790, -1610612736);
      this.frame(context, x, y, w, h);
      context.method_25294(x + 3, y + 5, x + 5, y + 16, -5701888);
      context.method_51433(this.field_22793, this.settingsModule.getName().toUpperCase(Locale.ROOT), x + 10, y + 6, -5701888, false);
      context.method_51433(this.field_22793, "ESC", x + w - 24, y + 6, -7697770, false);
      int sy = y + 24;
      String kb = this.waitingForKeybind ? "PRESS KEY..." : "BIND: " + this.keyName(this.settingsModule.getKeybind());
      if (mouseX >= x + 6 && mouseX < x + w - 6 && mouseY >= sy && mouseY < sy + 12) {
         context.method_25294(x + 6, sy, x + w - 6, sy + 12, 866713344);
      }

      context.method_51433(this.field_22793, kb, x + 10, sy + 2, this.waitingForKeybind ? -5701888 : -5197638, false);
      sy += 16;
      int idx = 0;

      for (Setting<?> s : this.settingsModule.getSettings()) {
         context.method_51433(this.field_22793, s.getName(), x + 10, sy + 1, -7697770, false);
         if (s instanceof BooleanSetting b) {
            this.drawDot(context, x + w - 14, sy + 6, (Boolean)b.get());
         } else if (s instanceof NumberSetting n) {
            double min = n.getMin();
            double max = n.getMax();
            double val = (Double)n.get();
            double pct = max == min ? 0.0 : (val - min) / (max - min);
            pct = Math.max(0.0, Math.min(1.0, pct));
            int barX = x + 10;
            int barW = w - 20;
            context.method_25294(barX, sy + 11, barX + barW, sy + 14, -15066590);
            context.method_25294(barX, sy + 11, barX + (int)(barW * pct), sy + 14, -5701888);
            String vs = String.format(Locale.ROOT, "%.2f", val);
            context.method_51433(this.field_22793, vs, x + w - 10 - this.field_22793.method_1727(vs), sy + 1, -5701888, false);
         } else if (s instanceof EnumSetting e) {
            String v = String.valueOf(e.get());
            context.method_51433(this.field_22793, v, x + w - 10 - this.field_22793.method_1727(v), sy + 1, -5701888, false);
         } else if (s instanceof TextSetting t) {
            String v = t.get() != null ? (String)t.get() : "";
            if (v.length() > 16) {
               v = v.substring(0, 14) + "..";
            }

            boolean edit = this.editingTextIndex == idx;
            context.method_25294(x + 10, sy + 10, x + w - 10, sy + 16, -16119280);
            if (edit) {
               context.method_25294(x + 10, sy + 15, x + w - 10, sy + 16, -5701888);
            }

            context.method_51433(this.field_22793, edit ? v + "_" : (v.isEmpty() ? "..." : v), x + 12, sy + 10, edit ? -5701888 : -5197638, false);
         }

         sy += 18;
         idx++;
      }
   }

   private String keyName(int key) {
      if (key != -1 && key != 0) {
         String n = GLFW.glfwGetKeyName(key, 0);
         return n != null ? n.toUpperCase(Locale.ROOT) : "K" + key;
      } else {
         return "NONE";
      }
   }

   public boolean method_25402(class_11909 click, boolean doubled) {
      double mx = click.comp_4798();
      double my = click.comp_4799();
      int button = click.method_74245();
      int searchW = 180;
      int searchH = 16;
      int ssx = (this.field_22789 - searchW) / 2;
      int ssy = 6;
      if (mx >= ssx && mx < ssx + searchW && my >= ssy && my < ssy + searchH && button == 0) {
         this.searchFocused = true;
         return true;
      } else if (this.settingsModule == null) {
         this.searchFocused = false;
         if (!this.search.isBlank()) {
            List<Module> results = this.searchResults();
            int cols = COLUMNS.length;
            int pwCol = Math.max(110, Math.min(130, (this.field_22789 - 20 - (cols - 1) * 6) / cols));
            int total = cols * pwCol + (cols - 1) * 6;
            int startX = Math.max(8, (this.field_22789 - total) / 2);
            int pw = Math.min(280, total);
            int x = startX + (total - pw) / 2;
            int bodyTop = 54;
            int body = this.bodyH(Math.min(results.size(), 20));
            if (mx >= x && mx < x + pw && my >= bodyTop && my < bodyTop + body) {
               int idx = ((int)my - bodyTop + this.searchScroll) / 14;
               if (idx >= 0 && idx < results.size()) {
                  Module m = results.get(idx);
                  if (button == 0) {
                     m.toggle();
                  } else if (button == 1) {
                     this.settingsModule = m;
                  }

                  return true;
               }
            }

            return true;
         } else {
            int cols = COLUMNS.length;
            int pw = Math.max(110, Math.min(130, (this.field_22789 - 20 - (cols - 1) * 6) / cols));
            int total = cols * pw + (cols - 1) * 6;
            int startX = Math.max(8, (this.field_22789 - total) / 2);
            int startY = 36;

            for (int i = 0; i < COLUMNS.length; i++) {
               Category cat = COLUMNS[i];
               int px = startX + i * (pw + 6);
               List<Module> mods = this.modsFor(cat);
               int body = this.bodyH(mods.size());
               int sc = this.scroll.getOrDefault(cat, 0);
               int bodyTop = startY + 18;
               if (mx >= px && mx < px + pw && my >= bodyTop && my < bodyTop + body) {
                  int idx = ((int)my - bodyTop + sc) / 14;
                  if (idx >= 0 && idx < mods.size()) {
                     Module m = mods.get(idx);
                     if (button == 0) {
                        m.toggle();
                     } else if (button == 1) {
                        this.settingsModule = m;
                        this.waitingForKeybind = false;
                        this.editingTextIndex = -1;
                     }

                     return true;
                  }
               }
            }

            return super.method_25402(click, doubled);
         }
      } else {
         int w = 220;
         int rows = 1 + this.settingsModule.getSettings().size();
         int h = 32 + rows * 18 + 10;
         int x = (this.field_22789 - w) / 2;
         int y = Math.max(40, (this.field_22790 - h) / 2);
         if ((mx < x || mx > x + w || my < y || my > y + h) && !this.waitingForKeybind) {
            this.settingsModule = null;
            this.editingTextIndex = -1;
            this.persist();
            return true;
         } else {
            int sy = y + 24;
            if (mx >= x + 6 && mx < x + w - 6 && my >= sy && my < sy + 12 && button == 0) {
               this.waitingForKeybind = true;
               return true;
            } else {
               sy += 16;
               int idx = 0;

               for (Setting<?> s : this.settingsModule.getSettings()) {
                  if (s instanceof BooleanSetting b) {
                     if (mx >= x + w - 20 && mx < x + w - 6 && my >= sy && my < sy + 12 && button == 0) {
                        b.set(!(Boolean)b.get());
                        this.persist();
                        return true;
                     }
                  } else if (s instanceof NumberSetting n) {
                     int barX = x + 10;
                     int barW = w - 20;
                     if (mx >= barX && mx < barX + barW && my >= sy + 10 && my < sy + 15 && button == 0) {
                        this.setSlider(n, mx, barX, barW);
                        return true;
                     }
                  } else if (s instanceof EnumSetting e) {
                     if (mx >= x + 6 && mx < x + w - 6 && my >= sy && my < sy + 12 && button == 0) {
                        e.cycle();
                        this.persist();
                        return true;
                     }
                  } else if (s instanceof TextSetting && mx >= x + 10 && mx < x + w - 10 && my >= sy + 10 && my < sy + 16 && button == 0) {
                     this.editingTextIndex = idx;
                     return true;
                  }

                  sy += 18;
                  idx++;
               }

               return true;
            }
         }
      }
   }

   private void setSlider(NumberSetting n, double mx, int barX, int barW) {
      double pct = Math.max(0.0, Math.min(1.0, (mx - barX) / barW));
      double val = n.getMin() + pct * (n.getMax() - n.getMin());
      double step = n.getStep() > 0.0 ? n.getStep() : 0.1;
      val = Math.round(val / step) * step;
      n.set(Math.max(n.getMin(), Math.min(n.getMax(), val)));
      this.persist();
   }

   public boolean method_25403(class_11909 click, double dx, double dy) {
      if (this.settingsModule != null) {
         double mx = click.comp_4798();
         double my = click.comp_4799();
         int w = 220;
         int x = (this.field_22789 - w) / 2;
         int h = 32 + (1 + this.settingsModule.getSettings().size()) * 18 + 10;
         int y = Math.max(40, (this.field_22790 - h) / 2);
         int sy = y + 40;

         for (Setting<?> s : this.settingsModule.getSettings()) {
            if (s instanceof NumberSetting n) {
               int barX = x + 10;
               int barW = w - 20;
               if (mx >= barX - 4 && mx <= barX + barW + 4 && my >= sy + 8 && my <= sy + 16) {
                  this.setSlider(n, mx, barX, barW);
                  return true;
               }
            }

            sy += 18;
         }
      }

      return super.method_25403(click, dx, dy);
   }

   public boolean method_25401(double mouseX, double mouseY, double ha, double va) {
      int delta = (int)(-va * 14.0 * 3.0);
      if (!this.search.isBlank()) {
         List<Module> results = this.searchResults();
         int body = this.bodyH(Math.min(results.size(), 20));
         int maxScroll = Math.max(0, results.size() * 14 - body);
         this.searchScroll = Math.max(0, Math.min(maxScroll, this.searchScroll + delta));
         return true;
      } else {
         int cols = COLUMNS.length;
         int pw = Math.max(110, Math.min(130, (this.field_22789 - 20 - (cols - 1) * 6) / cols));
         int total = cols * pw + (cols - 1) * 6;
         int startX = Math.max(8, (this.field_22789 - total) / 2);
         int startY = 36;

         for (int i = 0; i < COLUMNS.length; i++) {
            Category cat = COLUMNS[i];
            int px = startX + i * (pw + 6);
            List<Module> mods = this.modsFor(cat);
            int body = this.bodyH(mods.size());
            int h = 18 + body + 4;
            if (mouseX >= px && mouseX < px + pw && mouseY >= startY && mouseY < startY + h) {
               int maxScroll = Math.max(0, mods.size() * 14 - body);
               this.scroll.put(cat, Math.max(0, Math.min(maxScroll, this.scroll.getOrDefault(cat, 0) + delta)));
               return true;
            }
         }

         return super.method_25401(mouseX, mouseY, ha, va);
      }
   }

   public boolean method_25404(class_11908 input) {
      int key = input.comp_4795();
      if (this.waitingForKeybind && this.settingsModule != null) {
         if (key != 256 && key != 261 && key != 259) {
            this.settingsModule.setKeybind(key);
         } else {
            this.settingsModule.setKeybind(-1);
         }

         this.waitingForKeybind = false;
         this.persist();
         return true;
      } else if (key == 256) {
         if (this.settingsModule != null) {
            this.settingsModule = null;
            this.editingTextIndex = -1;
            this.persist();
            return true;
         } else if (!this.searchFocused && this.search.isEmpty()) {
            this.persist();
            this.method_25419();
            return true;
         } else {
            this.searchFocused = false;
            this.search = "";
            this.searchScroll = 0;
            return true;
         }
      } else {
         if (this.searchFocused) {
            if (key == 259 && !this.search.isEmpty()) {
               this.search = this.search.substring(0, this.search.length() - 1);
               this.searchScroll = 0;
               return true;
            }

            if (key == 257) {
               this.searchFocused = false;
               return true;
            }
         }

         if (this.editingTextIndex >= 0 && this.settingsModule != null) {
            List<Setting<?>> settings = this.settingsModule.getSettings();
            if (this.editingTextIndex < settings.size() && settings.get(this.editingTextIndex) instanceof TextSetting t) {
               if (key == 259) {
                  String v = t.get() != null ? (String)t.get() : "";
                  if (!v.isEmpty()) {
                     t.set(v.substring(0, v.length() - 1));
                  }

                  this.persist();
                  return true;
               }

               if (key == 257 || key == 256) {
                  this.editingTextIndex = -1;
                  this.persist();
                  return true;
               }
            }
         }

         return super.method_25404(input);
      }
   }

   public boolean method_25400(class_11905 input) {
      char c = (char)input.comp_4793();
      if (this.searchFocused && c >= ' ' && c != 127) {
         this.search = this.search + c;
         this.searchScroll = 0;
         return true;
      } else {
         if (this.editingTextIndex >= 0 && this.settingsModule != null) {
            List<Setting<?>> settings = this.settingsModule.getSettings();
            if (this.editingTextIndex < settings.size() && settings.get(this.editingTextIndex) instanceof TextSetting t && c >= ' ' && c != 127) {
               t.set((t.get() != null ? (String)t.get() : "") + c);
               this.persist();
               return true;
            }
         }

         return super.method_25400(input);
      }
   }

   public void method_25432() {
      this.persist();
      super.method_25432();
   }

   public boolean method_25421() {
      return false;
   }
}
