# Changelog for Neoforge Treasure2 1.21.1

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0-alpha.1] - 2026-05-22

### Added
- Bone Key item (placeholder texture — final art coming in a later release).

### Fixed
- Fixed a crash on startup where blocks couldn't be loaded properly due to a circular setup problem.
- Fixed a startup error caused by datagen providers conflicting with each other.
- Fixed missing item tag references that would cause errors when loading the mod.
- Fixed datagen so it runs cleanly and generates all item models, loot tables, tags, recipes, and language files.

### Changed
- Cleaned up several unfinished placeholders left over from the port that weren't needed for this release.

---

## [4.0.1] - 2025-10-05

### Changed
- Fixed server crash caused by non-existent library.

## [4.0.0] - 2025-10-03

## This version is a breaking change! Backup your world first.

### Changed

** **Ported from Forge 1.20.1 to Neoforge 1.21.1** **

- **All structure, pit, marker, well, witherwood grove, etc. generation uses _Jigsaw Structures_.**
- PreGen compatibility. 
- No cascading nor outside bounds errors. 
- No additional generation lag.
- Able to locate using vanilla command /locate structure
- Changed License to OSL-3.0.
- Expanded Mob Sets and Mob Set based Blocks.
- Removed Deferred generation system.
- Removed all non-standard config toml files. Uses /data json files instead.
- Removed most Config options. Uses /data json files instead.
- Made the One Key more rare.
- Fixed reflection called in Constructor error on startup.
- Fixed all asset errors on startup.
- Requires GottschCore 2.6+

Neoforge specific changes
- removed ILockSlot

### Added
- **Bone, Celestial, and Infernal Chests**
- Bone Key, Bone Lock (not accessible to players ie part of Bone Chest).
- Multiple new surface structures (mausoleum, ponds, statues, natural area, etc)
- Reduced and cleanup up codebase.