# D Web Studio - Chika Replay Helper

## Version 5.2.1+mc26.1.2

Minecraft 26.1.2 compatibility extension for Flashback (0.43.x).

Yueyue Flashback Firstperson Extension: records the real HUD, inventory, containers and mouse cursor while recording a replay, then replays that UI as a first-person overlay.

## What it does

- Captures the actual client GUI during recording, including HUD, inventory, containers and cursor.
- Stores UI frames inside the Flashback replay.
- Replays the recorded UI as a first-person overlay during playback and export.
- Can lock the replay camera to the recorded player (first person).
- Lets you choose include/follow/preview/record in Flashback export window.
- Adds an in-game keybind (default U) to toggle GUI recording.

## Requirements

- Minecraft Java Edition 26.1.2
- Fabric Loader 0.19.3+
- Java 25+
- Flashback 0.43.3+ (suggested)

## Installation

1. Install Fabric Loader and Fabric API.
2. Put Flashback jar in mods.
3. Put D-Web-Studio-Chika-Replay-Helper-5.2.1+mc26.1.2.jar in mods.
4. Launch game.

## In-game control

- Default key U toggles GUI recording on/off.
- Change it in Controls under Chika Replay Helper.
- Chat message confirms state.

## Configuration

File: config/yueyue-flashback-firstperson.json (legacy name) / d_web_studio... depending build.
Options: record, include, follow, preview.

## Performance

- Capture up to 125 FPS.
- Frames deduplicated before PNG encoding.
- Texture upload and PNG decode cached per frame.
- Mouse movement interpolated between samples for smooth replay.

## Links

- GitHub: https://github.com/Mr-Divyansh/Chika-s-Minecraft-Replay-Helper
- Issues: https://github.com/Mr-Divyansh/Chika-s-Minecraft-Replay-Helper/issues

## License

MIT. See LICENSE.
