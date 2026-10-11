package dev.anticheatqa.discord;

import java.io.RandomAccessFile;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public final class DiscordIPC implements AutoCloseable {
   private static final int OP_HANDSHAKE = 0;
   private static final int OP_FRAME = 1;
   private final String clientId;
   private final AtomicBoolean connected = new AtomicBoolean(false);
   private final AtomicBoolean running = new AtomicBoolean(false);
   private final ArrayBlockingQueue<String> presenceQueue = new ArrayBlockingQueue<>(4);
   private Thread worker;
   private volatile SocketChannel unixCh;
   private volatile RandomAccessFile winPipe;

   public DiscordIPC(String clientId) {
      this.clientId = clientId == null ? "" : clientId.trim();
   }

   public void start() {
      try {
         if (this.clientId.isEmpty() || this.running.getAndSet(true)) {
            return;
         }

         this.worker = new Thread(this::workerLoop, "41-DiscordIPC");
         this.worker.setDaemon(true);
         this.worker.setPriority(1);
         this.worker.start();
      } catch (Throwable var2) {
         this.running.set(false);
      }
   }

   public void setPresence(String j) {
      try {
         if (this.running.get()) {
            this.presenceQueue.offer(j);
         }
      } catch (Throwable var3) {
      }
   }

   @Override
   public void close() {
      try {
         this.running.set(false);
         this.presenceQueue.offer("__CLOSE__");
         if (this.worker != null) {
            this.worker.interrupt();
         }
      } catch (Throwable var2) {
      }

      this.connected.set(false);
   }

   private void workerLoop() {
      try {
         if (this.tryConnect()) {
            while (this.running.get()) {
               try {
                  String cmd = this.presenceQueue.poll(2L, TimeUnit.SECONDS);
                  if (cmd != null) {
                     if ("__CLOSE__".equals(cmd)) {
                        return;
                     }

                     this.sendPresence(cmd);
                  }
               } catch (InterruptedException var10) {
                  return;
               } catch (Throwable var11) {
                  this.connected.set(false);

                  try {
                     Thread.sleep(1500L);
                  } catch (InterruptedException var9) {
                     return;
                  }

                  if (!this.running.get()) {
                     return;
                  }

                  this.closeChannels();
                  if (!this.tryConnect()) {
                     return;
                  }
               }
            }

            return;
         }

         this.connected.set(false);
      } catch (Throwable var12) {
         return;
      } finally {
         this.closeChannels();
         this.connected.set(false);
         this.running.set(false);
      }
   }

   private boolean tryConnect() {
      try {
         String os = System.getProperty("os.name", "").toLowerCase();
         if (os.contains("win") ? this.connectWindows() : this.connectUnix()) {
            this.connected.set(true);
            return true;
         }
      } catch (Throwable var2) {
      }

      this.connected.set(false);
      return false;
   }

   private boolean connectUnix() {
      try {
         String runtime = System.getenv("XDG_RUNTIME_DIR");
         if (runtime == null || runtime.isBlank()) {
            runtime = "/tmp";
         }

         for (String base : new String[]{runtime, runtime + "/app/com.discordapp.Discord", runtime + "/snap.discord"}) {
            for (int i = 0; i < 10; i++) {
               Path path = Path.of(base, "discord-ipc-" + i);
               if (Files.exists(path)) {
                  try {
                     SocketChannel ch = SocketChannel.open(StandardProtocolFamily.UNIX);
                     ch.configureBlocking(true);
                     ch.connect(UnixDomainSocketAddress.of(path));
                     this.unixCh = ch;
                     if (this.handshake()) {
                        return true;
                     }

                     this.closeChannels();
                  } catch (Throwable var9) {
                     this.closeChannels();
                  }
               }
            }
         }
      } catch (Throwable var10) {
      }

      return false;
   }

   private boolean connectWindows() {
      for (int i = 0; i < 10; i++) {
         try {
            this.winPipe = new RandomAccessFile("\\\\.\\pipe\\discord-ipc-" + i, "rw");
            if (this.handshake()) {
               return true;
            }

            this.closeChannels();
         } catch (Throwable var3) {
            this.closeChannels();
         }
      }

      return false;
   }

   private boolean handshake() {
      try {
         this.writeFrame(0, "{\"v\":1,\"client_id\":\"" + this.clientId.replace("\\", "\\\\").replace("\"", "\\\"") + "\"}");
         return true;
      } catch (Throwable var2) {
         return false;
      }
   }

   private void sendPresence(String a) throws Exception {
      this.writeFrame(
         1,
         "{\"cmd\":\"SET_ACTIVITY\",\"args\":{\"pid\":" + ProcessHandle.current().pid() + ",\"activity\":" + a + "},\"nonce\":\"" + UUID.randomUUID() + "\"}"
      );
   }

   private void writeFrame(int op, String json) throws Exception {
      byte[] data = json.getBytes(StandardCharsets.UTF_8);
      ByteBuffer buf = ByteBuffer.allocate(8 + data.length).order(ByteOrder.LITTLE_ENDIAN);
      buf.putInt(op);
      buf.putInt(data.length);
      buf.put(data);
      buf.flip();
      if (this.unixCh != null && this.unixCh.isOpen()) {
         while (buf.hasRemaining()) {
            this.unixCh.write(buf);
         }
      } else {
         if (this.winPipe == null) {
            throw new IllegalStateException("no channel");
         }

         this.winPipe.write(buf.array(), 0, buf.limit());
      }
   }

   private void closeChannels() {
      try {
         if (this.unixCh != null) {
            this.unixCh.close();
         }
      } catch (Throwable var3) {
      }

      try {
         if (this.winPipe != null) {
            this.winPipe.close();
         }
      } catch (Throwable var2) {
      }

      this.unixCh = null;
      this.winPipe = null;
   }
}
