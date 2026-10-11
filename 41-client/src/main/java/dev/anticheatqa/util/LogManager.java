package dev.anticheatqa.util;

import dev.anticheatqa.AntiCheatQA;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogManager {
   private static final List<String> entries = Collections.synchronizedList(new ArrayList<>());
   private static final Path LOG_PATH = Paths.get("config", "anticheat-qa-log.txt");
   private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("HH:mm:ss");

   public static void log(String message) {
      String line = "[" + LocalTime.now().format(FMT) + "] " + message;
      entries.add(line);
      if (entries.size() > 500) {
         entries.remove(0);
      }

      AntiCheatQA.LOGGER.info(line);

      try {
         Files.createDirectories(LOG_PATH.getParent());
         Files.writeString(LOG_PATH, line + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
      } catch (IOException var3) {
      }
   }

   public static List<String> getEntries() {
      return new ArrayList<>(entries);
   }

   public static void clear() {
      entries.clear();
   }
}
