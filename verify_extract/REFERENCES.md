# References

- **Flashback**, Moulberry and contributors: https://github.com/Moulberry/Flashback . Integration inspected against version 0.43.6, Minecraft 26.3, source commit `bf046b763ddf466bdc1b6d9c6cc7ba06e527f3e2` (2026-10-19 local inspection). Its ActionRegistry, optional-action reader behavior, ordered recorder/snapshot lifecycle, export-settings and GUI integration are the compatibility reference. Original extension code calls these interfaces or wraps integration points; no Flashback source or binaries are redistributed in the extension.
- **Minecraft Java Edition**, Mojang Studios / Microsoft: client GUI extraction, prepared draw reuse, render target and PNG texture interfaces inspected for compatibility with 26.3. Minecraft assets/classes and inspected decompilation are not included in the source release.
- **Fabric API / Fabric Loader**, FabricMC and contributors: https://github.com/FabricMC/fabric and https://github.com/FabricMC/fabric-loader . Used for client entrypoints, optional dependency detection and isolated verification.
- **Mixin / MixinExtras**, SpongePowered and LlamaLad7 and contributors: https://github.com/SpongePowered/Mixin and https://github.com/LlamaLad7/MixinExtras . Injection and wrapping interfaces are supplied by the runtime, not copied into this mod.
- **Gradle Wrapper**, Gradle contributors: https://github.com/gradle/gradle . The standard wrapper bootstrap is supplied under Apache-2.0; its embedded license is retained.

The independent implementation was developed with AI assistance. References identify actual integration/design sources rather than attributing copied implementation that is not present.
