package dev.anticheatqa.module.qa;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import dev.anticheatqa.util.LogManager;
import java.util.Random;
import net.minecraft.class_310;

public class TestSimulator extends Module {
   public final Module.BooleanSetting auto = new Module.BooleanSetting("Auto Simulate", false);
   public final Module.NumberSetting interval = new Module.NumberSetting("Interval (s)", 5.0, 1.0, 30.0, 1.0);
   private int tickCounter = 0;
   private final Random random = new Random();
   private final String[] checks = new String[]{"Speed", "Flight", "Reach", "NoFall", "Scaffold", "CPS", "Rotation", "Velocity"};
   private final String[] severities = new String[]{"LOW", "MEDIUM", "HIGH"};

   public TestSimulator() {
      super("Test Simulator", "Safe local simulation of detection events for GUI/logging tests.", Category.CLIENT);
      this.settings.add(this.auto);
      this.settings.add(this.interval);
   }

   @Override
   public void onTick(class_310 client) {
      if (this.auto.get()) {
         this.tickCounter++;
         if (this.tickCounter >= this.interval.get() * 20.0) {
            this.tickCounter = 0;
            this.simulateRandom();
         }
      }
   }

   public void simulateRandom() {
      String check = this.checks[this.random.nextInt(this.checks.length)];
      String severity = this.severities[this.random.nextInt(this.severities.length)];
      this.simulate(check, severity);
   }

   public void simulate(String check, String severity) {
      Dashboard.tests.incrementAndGet();
      if ("HIGH".equals(severity)) {
         Dashboard.detections.incrementAndGet();
      } else {
         Dashboard.warnings.incrementAndGet();
      }

      String msg = String.format("[SIMULATION] Check: %s | Severity: %s | Result: Detection Triggered", check, severity);
      LogManager.log(msg);
   }

   @Override
   public String getDisplay() {
      return this.auto.get() ? "Simulating..." : "";
   }
}
