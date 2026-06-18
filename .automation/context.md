# TinkerSurvival -- Project Context

## What This Is
The original early-game survival progression mod, purpose-built for Tinkers' Construct. Enforces correct tool use, requires TC tools for progression, adds crude early tools, saw mechanic, and special drops (sticks from leaves, rocks, plant fiber, flint shards).

Dates from 1.12.2. SurvivalistEssentials was created by extracting the non-TC mechanics from this mod (correct tool enforcement, saw, crude tools, vanilla-friendly variants) into a standalone dependency. TinkerSurvival now requires SurvivalistEssentials and layers TC-specific progression and integration on top.

## Project Structure
Forge-only. No multi-loader structure -- update pace is gated by Tinkers' Construct.
Single `src/` at root.

## Branch Convention
| Branch | Modloader | Status  |
|--------|-----------|---------|
| 1.12.2 | Forge     | Legacy  |
| 1.18.2 | Forge     | Legacy  |
| 1.19.2 | Forge     | Legacy  |
| 1.20.1 | Forge     | Active  |

## Dependencies
- SurvivalistEssentials (required -- the extracted standalone base)
- Mantle (required)
- Tinkers' Construct (required)
- WhiteNoise (runtimeOnly -- not jarJar/include)

### Optional Integration Targets
Wendall911 mods: ActuallyHarvest, BetterDays, ChargedCharms, CreeperFireworks, Homeostatic, MagicalPsiRevival, SimpleTextOverlay, TCIntegrations, ReadyPlayerFun

Third-party: Botania, Ars Nouveau, Quark, Biomes O' Plenty, Fruit Trees, SushiGoCrafting, ExNihiloSequentia, and others

## Distribution
Side: both (clientRequired = true, serverRequired = true) CurseForge + Modrinth

## Version Lock Note
Update pace is gated by Tinkers' Construct. TC is currently on 1.20.1 and has been for an extended period. Do not plan NeoForge or 1.21+ work until TC updates.

## Relationship to SurvivalistEssentials
SurvivalistEssentials contains the core mechanics (tool enforcement, saw, early tools, special drops). TinkerSurvival depends on it and adds the Tinkers' Construct progression layer. Users who don't use TC can use SurvivalistEssentials standalone -- that separation was the point of the extraction.

## Release Process
Follow the standard wendall911 release process in `../docs/minecraft/MINECRAFT_DEVELOPMENT_NOTES.md`.
