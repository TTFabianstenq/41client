package dev.anticheatqa.module.qa;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.concurrent.atomic.AtomicInteger;

public class Dashboard extends Module {
   public static final AtomicInteger detections = new AtomicInteger(0);
   public static final AtomicInteger warnings = new AtomicInteger(0);
   public static final AtomicInteger tests = new AtomicInteger(0);

   public Dashboard() {
      super("Dashboard", "Anti-Cheat QA overview panel.", Category.CLIENT);
      this.setEnabled(true);
   }

   @Override
   public String getDisplay() {
      return String.format("QA | Det:%d Warn:%d Tests:%d", detections.get(), warnings.get(), tests.get());
   }
}
