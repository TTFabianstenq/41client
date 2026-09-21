# 41 Client

Fabric client mod for **Minecraft 1.21.11** (Yarn mappings).

Version **2.9.70** — sources decompiled from the shipped jar plus hand-fixed modules (Flight, Sus Chunk, combat macros, mixins, Click GUI).

## Requirements

- JDK **21**
- Internet (to resolve Fabric Loom / Yarn / game jars)

## Build

```bash
./gradlew build
```

Output: `build/libs/41-client-2.9.70.jar`

## Run (dev)

```bash
./gradlew runClient
```

## Structure

```
src/main/java/dev/anticheatqa/
  AntiCheatQA.java          # client entrypoint
  module/                   # modules (combat, player, qa, donut, ...)
  mixin/                    # Fabric mixins
  gui/                      # Click GUI
  render/                   # HUD / world ESP
  config/                   # config save/load
src/main/resources/
  fabric.mod.json
  anticheat-qa.mixins.json
  assets/fortyone-client/
```

## Notes

- Intermediary names (`class_310`, `method_*`) appear in decompiled code where Yarn names were not recovered.
- Some modules are intermediary-based client patches; map to Yarn when editing heavily.
- Flight no-clip is client-side; multiplayer survival servers can still rubberband.

## License

MIT (as declared in `fabric.mod.json`).
