package dev.anticheatqa.module;

import dev.anticheatqa.gui.ClickGuiScreen;
import dev.anticheatqa.module.basefinding.BlockEntityDebug;
import dev.anticheatqa.module.basefinding.HoleESP;
import dev.anticheatqa.module.basefinding.LightFinder;
import dev.anticheatqa.module.basefinding.PrimeChunkFinder;
import dev.anticheatqa.module.basefinding.RtpBaseFinder;
import dev.anticheatqa.module.basefinding.SeedChunkFinder;
import dev.anticheatqa.module.basefinding.SuspiciousESP;
import dev.anticheatqa.module.basefinding.TunnelBaseFinder;
import dev.anticheatqa.module.client.ChatMacro;
import dev.anticheatqa.module.client.ClickGuiModule;
import dev.anticheatqa.module.client.ConfigShare;
import dev.anticheatqa.module.client.DiscordPresence;
import dev.anticheatqa.module.client.ItemSimulator;
import dev.anticheatqa.module.client.ProxyModule;
import dev.anticheatqa.module.client.Radio;
import dev.anticheatqa.module.client.SpotifyHud;
import dev.anticheatqa.module.combat.AimAssist;
import dev.anticheatqa.module.combat.AnchorMacro;
import dev.anticheatqa.module.combat.AutoClicker;
import dev.anticheatqa.module.combat.AutoCrystal;
import dev.anticheatqa.module.combat.AutoDoubleHand;
import dev.anticheatqa.module.combat.AutoHitCrystal;
import dev.anticheatqa.module.combat.AutoInvTotem;
import dev.anticheatqa.module.combat.AutoJumpReset;
import dev.anticheatqa.module.combat.AutoTotem;
import dev.anticheatqa.module.combat.BreachSwap;
import dev.anticheatqa.module.combat.CombatAnchor;
import dev.anticheatqa.module.combat.CrystalOptimizer;
import dev.anticheatqa.module.combat.DoubleAnchor;
import dev.anticheatqa.module.combat.ElytraSwap;
import dev.anticheatqa.module.combat.HitBox;
import dev.anticheatqa.module.combat.HoverTotem;
import dev.anticheatqa.module.combat.InvTotem;
import dev.anticheatqa.module.combat.KeyPearl;
import dev.anticheatqa.module.combat.MaceBomber;
import dev.anticheatqa.module.combat.MaceSwap;
import dev.anticheatqa.module.combat.NoHitDelay;
import dev.anticheatqa.module.combat.ShieldBreaker;
import dev.anticheatqa.module.combat.SpearSwap;
import dev.anticheatqa.module.combat.StaticHitBoxes;
import dev.anticheatqa.module.combat.SwordPlaceObsidian;
import dev.anticheatqa.module.combat.TargetHud;
import dev.anticheatqa.module.combat.TotemOffhand;
import dev.anticheatqa.module.combat.TriggerBot;
import dev.anticheatqa.module.combat.WindPearlMacro;
import dev.anticheatqa.module.donut.AntiTrap;
import dev.anticheatqa.module.donut.AuctionSniper;
import dev.anticheatqa.module.donut.AutoSell;
import dev.anticheatqa.module.donut.AutoShulkerBuy;
import dev.anticheatqa.module.donut.AutoSpawnerSell;
import dev.anticheatqa.module.donut.BaseFinder;
import dev.anticheatqa.module.donut.ChunkFinder;
import dev.anticheatqa.module.donut.CoordSnapper;
import dev.anticheatqa.module.donut.FakePay;
import dev.anticheatqa.module.donut.FakeStats;
import dev.anticheatqa.module.donut.ItemDropper;
import dev.anticheatqa.module.donut.NetheriteFinder;
import dev.anticheatqa.module.donut.PlayerChunks;
import dev.anticheatqa.module.donut.PlayerDetection;
import dev.anticheatqa.module.donut.PlayerFinder;
import dev.anticheatqa.module.donut.SpawnerProtect;
import dev.anticheatqa.module.donut.StaffList;
import dev.anticheatqa.module.donut.Waypoints;
import dev.anticheatqa.module.misc.ArmorTrimHider;
import dev.anticheatqa.module.misc.AutoFirework;
import dev.anticheatqa.module.misc.AutoLog;
import dev.anticheatqa.module.misc.AutoLoot;
import dev.anticheatqa.module.misc.AutoMine;
import dev.anticheatqa.module.misc.AutoTpa;
import dev.anticheatqa.module.misc.AutoWalk;
import dev.anticheatqa.module.misc.CustomCrosshair;
import dev.anticheatqa.module.misc.CustomFOV;
import dev.anticheatqa.module.misc.CustomGlint;
import dev.anticheatqa.module.misc.ElytraGlide;
import dev.anticheatqa.module.misc.FakePlayer;
import dev.anticheatqa.module.misc.FakePoles;
import dev.anticheatqa.module.misc.FastUse;
import dev.anticheatqa.module.misc.GambleRigger;
import dev.anticheatqa.module.misc.KeyWindCharge;
import dev.anticheatqa.module.misc.MediaStaffIcons;
import dev.anticheatqa.module.misc.SkinProtect;
import dev.anticheatqa.module.misc.WeatherNotifier;
import dev.anticheatqa.module.monitor.FPSMonitor;
import dev.anticheatqa.module.monitor.ReachMonitor;
import dev.anticheatqa.module.player.AntiAfk;
import dev.anticheatqa.module.player.AutoEat;
import dev.anticheatqa.module.player.AutoReconnect;
import dev.anticheatqa.module.player.AutoTool;
import dev.anticheatqa.module.player.FastPlace;
import dev.anticheatqa.module.player.Flight;
import dev.anticheatqa.module.player.NoFall;
import dev.anticheatqa.module.player.AntiHunger;
import dev.anticheatqa.module.player.NoSuffocation;
import dev.anticheatqa.module.player.Friends;
import dev.anticheatqa.module.player.NameProtect;
import dev.anticheatqa.module.player.ToggleSprint;
import dev.anticheatqa.module.qa.Dashboard;
import dev.anticheatqa.module.qa.LightProbe;
import dev.anticheatqa.module.qa.StashScanner;
import dev.anticheatqa.module.qa.SusChunkFinder;
import dev.anticheatqa.module.qa.TestSimulator;
import dev.anticheatqa.module.qa.YLevelProbe;
import dev.anticheatqa.module.render.BlockNotifier;
import dev.anticheatqa.module.render.DebugHoleESP;
import dev.anticheatqa.module.render.DeepslateESP;
import dev.anticheatqa.module.render.FreeLook;
import dev.anticheatqa.module.render.HudModule;
import dev.anticheatqa.module.render.ItemESP;
import dev.anticheatqa.module.render.MobESP;
import dev.anticheatqa.module.render.NoHurtCam;
import dev.anticheatqa.module.render.OreSim;
import dev.anticheatqa.module.render.PearlTrajectory;
import dev.anticheatqa.module.render.PlayerESP;
import dev.anticheatqa.module.render.SwingSpeed;
import dev.anticheatqa.module.render.Zoom;
import dev.anticheatqa.module.visuals.BlockESP;
import dev.anticheatqa.module.visuals.ChestESP;
import dev.anticheatqa.module.visuals.ChunkBorders;
import dev.anticheatqa.module.visuals.CrystalESP;
import dev.anticheatqa.module.visuals.CustomBlockOutline;
import dev.anticheatqa.module.visuals.CustomMaceAccessories;
import dev.anticheatqa.module.visuals.EntityESP;
import dev.anticheatqa.module.visuals.FreeCam;
import dev.anticheatqa.module.visuals.Fullbright;
import dev.anticheatqa.module.visuals.HitParticles;
import dev.anticheatqa.module.visuals.HitboxESP;
import dev.anticheatqa.module.visuals.Nametags;
import dev.anticheatqa.module.visuals.SpawnerESP;
import dev.anticheatqa.module.visuals.StorageESP;
import dev.anticheatqa.module.visuals.Tracers;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import net.minecraft.class_310;
import org.lwjgl.glfw.GLFW;

public class ModuleManager {
   private final List<Module> modules = new ArrayList<>();
   private final boolean[] keyWasDown = new boolean[512];

   public ModuleManager() {
      this.register(new BlockEntityDebug());
      this.register(new HoleESP());
      this.register(new LightFinder());
      this.register(new PrimeChunkFinder());
      this.register(new RtpBaseFinder());
      this.register(new SeedChunkFinder());
      this.register(new SuspiciousESP());
      this.register(new TunnelBaseFinder());
      this.register(new ChatMacro());
      this.register(new ClickGuiModule());
      this.register(new ConfigShare());
      this.register(new DiscordPresence());
      this.register(new ItemSimulator());
      this.register(new ProxyModule());
      this.register(new Radio());
      this.register(new SpotifyHud());
      this.register(new AimAssist());
      this.register(new AnchorMacro());
      this.register(new AutoClicker());
      this.register(new AutoCrystal());
      this.register(new AutoDoubleHand());
      this.register(new AutoHitCrystal());
      this.register(new AutoInvTotem());
      this.register(new AutoJumpReset());
      this.register(new AutoTotem());
      this.register(new CombatAnchor());
      this.register(new CrystalOptimizer());
      this.register(new DoubleAnchor());
      this.register(new ElytraSwap());
      this.register(new HitBox());
      this.register(new HoverTotem());
      this.register(new InvTotem());
      this.register(new KeyPearl());
      this.register(new MaceBomber());
      this.register(new MaceSwap());
      this.register(new NoHitDelay());
      this.register(new ShieldBreaker());
      this.register(new SpearSwap());
      this.register(new StaticHitBoxes());
      this.register(new TargetHud());
      this.register(new TotemOffhand());
      this.register(new TriggerBot());
      this.register(new SwordPlaceObsidian());
      this.register(new WindPearlMacro());
      this.register(new BreachSwap());
      this.register(new AntiTrap());
      this.register(new AuctionSniper());
      this.register(new AutoSell());
      this.register(new AutoShulkerBuy());
      this.register(new AutoSpawnerSell());
      this.register(new BaseFinder());
      this.register(new ChunkFinder());
      this.register(new CoordSnapper());
      this.register(new FakePay());
      this.register(new FakeStats());
      this.register(new ItemDropper());
      this.register(new NetheriteFinder());
      this.register(new PlayerChunks());
      this.register(new PlayerDetection());
      this.register(new PlayerFinder());
      this.register(new SpawnerProtect());
      this.register(new StaffList());
      this.register(new Waypoints());
      this.register(new ArmorTrimHider());
      this.register(new AutoFirework());
      this.register(new AutoLog());
      this.register(new AutoLoot());
      this.register(new AutoMine());
      this.register(new AutoTpa());
      this.register(new AutoWalk());
      this.register(new CustomCrosshair());
      this.register(new CustomFOV());
      this.register(new CustomGlint());
      this.register(new ElytraGlide());
      this.register(new FakePlayer());
      this.register(new FakePoles());
      this.register(new FastUse());
      this.register(new GambleRigger());
      this.register(new KeyWindCharge());
      this.register(new MediaStaffIcons());
      this.register(new SkinProtect());
      this.register(new WeatherNotifier());
      this.register(new FPSMonitor());
      this.register(new ReachMonitor());
      this.register(new AntiAfk());
      this.register(new AutoEat());
      this.register(new AutoReconnect());
      this.register(new AutoTool());
      this.register(new FastPlace());
      this.register(new Flight());
      this.register(new NoFall());
      this.register(new AntiHunger());
      this.register(new NoSuffocation());
      this.register(new Friends());
      this.register(new NameProtect());
      this.register(new ToggleSprint());
      this.register(new Dashboard());
      this.register(new LightProbe());
      this.register(new StashScanner());
      this.register(new SusChunkFinder());
      this.register(new TestSimulator());
      this.register(new YLevelProbe());
      this.register(new BlockNotifier());
      this.register(new DebugHoleESP());
      this.register(new DeepslateESP());
      this.register(new FreeLook());
      this.register(new HudModule());
      this.register(new ItemESP());
      this.register(new MobESP());
      this.register(new NoHurtCam());
      this.register(new OreSim());
      this.register(new PearlTrajectory());
      this.register(new PlayerESP());
      this.register(new SwingSpeed());
      this.register(new Zoom());
      this.register(new BlockESP());
      this.register(new ChestESP());
      this.register(new ChunkBorders());
      this.register(new CrystalESP());
      this.register(new CustomBlockOutline());
      this.register(new CustomMaceAccessories());
      this.register(new EntityESP());
      this.register(new FreeCam());
      this.register(new Fullbright());
      this.register(new HitParticles());
      this.register(new HitboxESP());
      this.register(new Nametags());
      this.register(new SpawnerESP());
      this.register(new StorageESP());
      this.register(new Tracers());
   }

   private void register(Module var1) {
      for (Module var3 : this.modules) {
         if (var3.getName().equalsIgnoreCase(var1.getName())) {
            return;
         }
      }

      this.modules.add(var1);
   }

   public List<Module> getModules() {
      return Collections.unmodifiableList(this.modules);
   }

   public List<Module> getModulesByCategory(Category var1) {
      ArrayList<Module> var2 = new ArrayList<>();
      LinkedHashSet var3 = new LinkedHashSet();

      for (Module var5 : this.modules) {
         if (var5.getCategory() == var1 && var3.add(var5.getName().toLowerCase(Locale.ROOT).trim())) {
            var2.add(var5);
         }
      }

      var2.sort((Module a, Module b) -> a.getName().compareToIgnoreCase(b.getName()));
      return var2;
   }

   public List<Module> search(String var1) {
      if (var1 != null && !var1.isBlank()) {
         String var2 = var1.toLowerCase(Locale.ROOT).trim();
         return this.modules
            .stream()
            .filter(
               var1x -> var1x.getName().toLowerCase(Locale.ROOT).contains(var2)
                  || var1x.getDescription() != null && var1x.getDescription().toLowerCase(Locale.ROOT).contains(var2)
            )
            .sorted((var0, var1x) -> var0.getName().compareToIgnoreCase(var1x.getName()))
            .collect(Collectors.toList());
      } else {
         return List.of();
      }
   }

   public Module getModule(String var1) {
      for (Module var3 : this.modules) {
         if (var3.getName().equalsIgnoreCase(var1)) {
            return var3;
         }
      }

      return null;
   }

   public void onTick(class_310 var1) {
      long var2 = var1.method_22683().method_4490();

      for (Module var5 : this.modules) {
         int var6 = var5.getKeybind();
         if (var6 != -1 && var6 >= 0 && var6 < this.keyWasDown.length) {
            boolean var7 = GLFW.glfwGetKey(var2, var6) == 1;
            if (var7 && !this.keyWasDown[var6] && (var1.field_1755 == null || var1.field_1755 instanceof ClickGuiScreen)) {
               var5.toggle();
            }

            this.keyWasDown[var6] = var7;
         }
      }

      for (Module var10 : this.modules) {
         if (var10.isEnabled()) {
            try {
               var10.onTick(var1);
            } catch (Throwable var8) {
               System.err.println("[41Client] " + var10.getName() + ": " + var8.getMessage());
            }
         }
      }
   }
}
