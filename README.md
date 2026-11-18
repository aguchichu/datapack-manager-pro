# Datapack Manager Pro 1.0.0

Minecraft Java 1.21.1 · NeoForge 21.1.256 · Java 21.

## Build
GitHub Actions builds the mod automatically on every push. The JAR is published as a workflow artifact named `DatapackManagerPro-1.0.0`.

## Current features
- Creates `.minecraft/global_datapacks` on startup.
- Scans datapacks in folder and ZIP format.
- Reads `pack.mcmeta` and displays the datapack name and description.
- Opens the global folder and refreshes the list from the in-game screen.

## Current scope
This initial source release is a global datapack library/browser. It does not yet copy datapacks into worlds or manage per-world activation, and it does not display `pack.png` icons. The in-game button currently appears on client screens generally; this will be narrowed to supported world/menu screens in a later update.