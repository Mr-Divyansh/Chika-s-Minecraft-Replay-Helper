# D Web Studio — Chika Replay Helper

## Version 1.2.6+mc26.1.2

Minecraft 26.1.2 compatibility extension for **Flashback** (0.43.x).

*Yueyue Flashback Firstperson Extension* — records the real HUD, inventory, containers and cursor while recording a replay, then replays that UI as an overlay in first person.

## Features

- Records the actual client GUI (HUD, inventory, containers) into Flashback replays.
- Replays the recorded UI as a first-person overlay during playback and export.
- Optional follow camera: locks playback camera to the recorded player in first person.
- Configurable include / follow / preview / record toggles in the export window.
- Smooth cursor replay using interpolated mouse samples.
- Safe fallback: segments without UI data use Flashback's original rendering.
- Capture is limited to 125 FPS to keep memory and CPU usage predictable.

## Requirements

- Minecraft Java Edition 26.1.2
- Fabric Loader 0.19.3 or newer
- Java 25 or newer
- Flashback 0.43.3 or newer (suggested)

## Installation

1. Install Fabric Loader and Fabric API for Minecraft 26.1.2.
2. Download Flashback separately from its author and place it in the `mods` folder.
3. Place `D-Web-Studio-Chika-Replay-Helper-1.2.6+mc26.1.2.jar` in the `mods` folder.
4. Launch the game.

## Configuration

Preferences are stored in:

```
config/yueyue-flashback-firstperson.json
```

Available options:

- `record` — record the real UI while capturing a replay.
- `include` — include the recorded UI in exported video.
- `follow` — lock the replay camera to the recorded player.
- `preview` — preview the recorded UI during replay.

Toggles are also available in Flashback's export window.

## Performance notes

- UI capture runs at up to 125 FPS.
- Recorded frames are deduplicated before PNG encoding.
- Texture upload and PNG decoding are cached per frame.
- Mouse movement is interpolated between captured samples for smooth replay.

## Building from source

This repository contains the extension source under `decompiled/yueyue/flashback`. The mod targets Minecraft 26.1.2 and Fabric. Flashback itself is **not** redistributed; obtain it separately.

## License

MIT — see `LICENSE`.

## Credits

- D Web Studio
- Flashback by Moulberry and contributors (compatibility reference only)
- Fabric, Mixin, MixinExtras and their contributors
