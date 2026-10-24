#!/usr/bin/env python
import zipfile
import json
import shutil
import os

# Source and target JAR files
source_jar = "yueyue-flashback-firstperson-1.0.0+mc26.3.jar"
target_jar = "yueyue-flashback-chika-mc26.1.2.jar"

# Backup the original
backup_jar = "yueyue-flashback-firstperson-1.0.0+mc26.3.jar.backup"
shutil.copy2(source_jar, backup_jar)
print(f"Backed up {source_jar} to {backup_jar}")

# Read the JAR contents
with zipfile.ZipFile(source_jar, 'r') as zf:
    entries = zf.namelist()
    print(f"JAR entries ({len(entries)}):")
    for entry in sorted(entries):
        print(f"  {entry}")
    
    # Check for the files we need to replace
    mod_json_entries = [e for e in entries if 'fabric.mod.json' in e]
    mixin_entries = [e for e in entries if 'yueyue-flashback.mixins.json' in e]
    icon_entries = [e for e in entries if 'icon.png' in e or 'Chika' in e]
    
    print(f"\nfabric.mod.json entries: {mod_json_entries}")
    print(f"yueyue-flashback.mixins.json entries: {mixin_entries}")
    print(f"icon entries: {icon_entries}")
    
    # Read the current fabric.mod.json from the JAR
    mod_json_content = None
    if mod_json_entries:
        mod_json_content = zf.read(mod_json_entries[0]).decode('utf-8')
        print(f"\nCurrent fabric.mod.json content:\n{mod_json_content}")
    
    # Read the current yueyue-flashback.mixins.json from the JAR
    mixin_content = None
    if mixin_entries:
        mixin_content = zf.read(mixin_entries[0]).decode('utf-8')
        print(f"\nCurrent yueyue-flashback.mixins.json content:\n{mixin_content}")
    
    # The new fabric.mod.json content (updated for Minecraft 26.1.2 + Fabric 0.19.3)
    new_mod_json = """{
  "schemaVersion": 1,
  "id": "yueyue_flashback_firstperson",
  "version": "1.0.0+mc26.1.2",
  "name": "Yueyue Flashback Firstperson Extension Mod",
  "authors": ["Yisibite"],
  "license": "MIT",
  "environment": "client",
  "description": "For Flashback, add real HUD, inventory and container interface recording, can choose whether to export with video.",
  "icon": "assets/yueyue_flashback/Chika's Minecraft Replay Helper.png",
  "entrypoints": {
    "client": ["yueyue.flashback.UiBootstrap"]
  },
  "mixins": ["yueyue-flashback.mixins.json"],
  "depends": {
    "minecraft": "26.1.2",
    "fabricloader": ">=0.19.3",
    "java": ">=26"
  },
  "suggests": {
    "flashback": "0.43.6"
  }
}"""
    
    # The new yueyue-flashback.mixins.json content (updated compatibilityLevel to JAVA_26)
    new_mixin_json = """{
  "required": true,
  "package": "yueyue.flashback.mixin",
  "compatibilityLevel": "JAVA_26",
  "plugin": "yueyue.flashback.UiMixinPlugin",
  "client": ["GuiCaptureMixin", "RecorderMixin", "SaverQueueMixin", "ReplayGuiMixin", "ExportWindowMixin", "ExportSettingsMixin", "CameraMixin", "HudInvoker"],
  "injectors": {"defaultRequire": 1}
}"""
    
    # Replace fabric.mod.json in the entries
    new_entries = []
    modified_entries = set()
    
    for entry in entries:
        if any(e in entry for e in mod_json_entries):
            # Replace fabric.mod.json
            new_entries.append(zipfile.ZipEntry('fabric.mod.json'))
            modified_entries.add('fabric.mod.json')
            print(f"Will replace: {entry} -> fabric.mod.json")
        elif any(e in entry for e in mixin_entries):
            # Replace yueyue-flashback.mixins.json
            new_entries.append(zipfile.ZipEntry('yueyue-flashback.mixins.json'))
            modified_entries.add('yueyue-flashback.mixins.json')
            print(f"Will replace: {entry} -> yueyue-flashback.mixins.json")
        elif any(e in entry for e in icon_entries):
            # Keep icon entries as-is
            new_entries.append(zipfile.ZipEntry(entry))
        else:
            # Keep other entries as-is
            new_entries.append(zipfile.ZipEntry(entry))
    
    # Create the new JAR
    with zipfile.ZipFile(target_jar, 'w', zipfile.ZIP_DEFLATED) as new_zf:
        for i, entry in enumerate(entries):
            if entry in modified_entries:
                # Read content from original and write with new name
                content = zf.read(entry)
                new_zf.writestr(new_entries[i], content)
            else:
                # Copy other entries
                new_zf.write(zf.read(entry), new_entries[i])
    
    print(f"\nCreated {target_jar}")

    # Verify the new JAR
    print("\n--- Verifying new JAR ---")
    with zipfile.ZipFile(target_jar, 'r') as new_zf:
        new_entries = new_zf.namelist()
        print(f"New JAR entries ({len(new_entries)}):")
        for entry in sorted(new_entries):
            print(f"  {entry}")
        
        # Check for duplicates
        from collections import Counter
        counts = Counter(new_entries)
        duplicates = [item for item, count in counts.items() if count > 1]
        if duplicates:
            print(f"WARNING: Duplicate entries found: {duplicates}")
        else:
            print("No duplicate entries found.")
        
        # Parse fabric.mod.json
        for entry in new_entries:
            if 'fabric.mod.json' in entry:
                content = new_zf.read(entry).decode('utf-8')
                try:
                    data = json.loads(content)
                    print(f"\nfabric.mod.json parsed successfully:")
                    print(f"  version: {data.get('version')}")
                    print(f"  minecraft: {data.get('minecraft')}")
                    print(f"  fabricloader: {data.get('fabricloader')}")
                    print(f"  java: {data.get('java')}")
                    print(f"  icon: {data.get('icon')}")
                except json.JSONDecodeError as e:
                    print(f"\nERROR: Failed to parse fabric.mod.json: {e}")

    # Check icon path reference
    print("\n--- Checking icon path ---")
    for entry in new_entries:
        if 'Chika' in entry or 'icon.png' in entry:
            print(f"Icon entry: {entry}")

print("\nDone!")