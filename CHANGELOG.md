# Changelog for Neoforge Treasure2 1.21.1

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0-alpha.3] - 2026-06-13

### Added
- Spawn eggs for every Treasure2 mob — the bound soul, the witherwood golem, and all of the chest mimics (wood, pirate, viking, cauldron, crate, moldy crate, cardboard box, milk crate, barrel, and vanilla chest).
- A whole armory of weapons to find and fight with: short swords, a rapier, longswords, broadswords, machetes, falchions, katanas, maces, and battle axes — including one-of-a-kind named weapons like Mjolnir, Callandor, Oathbringer, the Sword of Power, the Sword of Omens, Orcus, and the Axe of Durin. The named weapons can land an extra-strong "power attack" for bonus damage.
- The One Key — a rare key that opens any lock and never breaks.
- The Treasure Tool — the special item needed for most Treasure2 crafting recipes.
- Wishing wells now work: toss a coin or gem into the water by a wishing well and it can reward you with treasure.

### Changed
- The floating particle effects are back — the mist and fog around gravestones, black spores off wither twigs and strangle vines, dripping Spanish moss, and the poison and wither mists. (These are the effects promised in the previous update's notes.)
- You can now combine two of the same key in an anvil to merge their leftover uses into a single, longer-lasting key.

---

## [1.0.0-alpha.2] - 2026-06-09

### Added
- Gravestone spawners — special gravestones that summon a bound soul when you wander too close.
- Hidden spawner blocks that let structures surprise you with mobs as you explore.
- Witherwood decorations — branches, roots, broken logs, and twigs for building creepy wither trees.
- A complete witherwood wood set: logs, wood, stripped logs and wood, planks, slabs, stairs, fences, fence gates, buttons, pressure plates, doors, trapdoors, and signs (standing, wall, and hanging). You can strip witherwood logs with an axe to get the stripped version.

### Fixed
- Fixed the Key Ring so its screen opens again, just like the Pouch.

### Notes
- A few of these blocks (wither twigs, strangle vines, wither roots, and others) are meant to give off floating particles. Those special effects aren't back yet and will return in a later update.

---

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