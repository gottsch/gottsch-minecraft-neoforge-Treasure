# Changelog for Treasure2 (Neoforge 1.21.1)

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [5.0.4] - 2026-10-16

### 🛠️ Fixed
- On a server, a modified client could make poison and wither mist hit any player it liked, wherever they were. Mist now only ever affects the player actually standing in it.
- A chest could open completely empty and stay that way if its loot couldn't be generated the first time it was opened. It now stays sealed and tries again the next time it's opened, instead of being emptied for good.
- Some chests failed to open at all on the first try because they had no record of how they were generated. They now open and fill with common loot.
- Mimic chests no longer give themselves away. The game used to tell every player which chests were mimics, so a client-side mod or NBT viewer could pick them out.
- Mining or blowing up a mimic chest no longer defuses it. The mimic springs out instead of the chest dropping as an item.
- A mimic chest standing in water no longer vanishes, taking its loot with it, when it is opened or broken. The mimic now springs out as it should.
- Lock picks now use the chances set in the config. The config was never applied: both picks opened common locks 90% of the time and could never open uncommon or scarce locks. With the default config, the Pilferer's Lock Pick now opens common locks 48% of the time and uncommon 32%, and the Thief's Lock Pick opens common 60%, uncommon 45% and scarce 30%.
- Lock pick tooltips now show those same chances.
- The max uses set in the config for each key and lock pick now take effect. They were ignored, so every key kept its built-in number of uses whatever the config said. The defaults are unchanged.
- Callandor can now be found. Its place in the mythical loot pool handed out Oathbringer instead, so Callandor never dropped.
- A mimic no longer makes its ambient sound while it is still dormant. It goes quiet until it is sprung from a chest or finds a target.
- The big teeth of crate and moldy crate mimics no longer poke up through the lid while the mimic opens.
- The Viking chest mimic's teeth no longer poke through its lid.
- Treasure2 now loads after GottschCore, the library it needs, instead of before it.

---

## [5.0.3] - 2026-10-08

### 🛠️ Fixed
- Epic potion loot never gave its extra-strong potions. The six potions it asked for (Healing, Strength, Leaping, Swiftness, Regeneration and Poison) didn't exist, so the whole epic potion pool failed to load. They're now real level III potions, found only as loot.
- The uncommon item pool failed to load because it still used the old Scute item name. It now uses Turtle Scute, so that pool drops items again.

---

## [5.0.2] - 2026-08-13

### 🛠️ Fixed
- Structures and chests were noticeably rarer when playing with Biomes O' Plenty. Several BOP biomes were never registered as valid generation biomes, so any terrain they covered was quietly skipped. Bayou, Bog, Marsh, Fungal Jungle and Overgrown Greens now generate treasure like their vanilla counterparts.
- Forest and jungle wishing wells never appeared in Biomes O' Plenty forests or jungles. They now do.
- Biomes We've Gone jungles were being offered forest wishing wells instead of jungle wishing wells. They now get jungle wells.

---

## [5.0.1] - 2026-07-19

### 🛠️ Fixed
- Locked chests could sometimes open up completely empty, even though they clearly had a lock on them. Loot generation is now much more reliable, so a locked chest should always have something inside.
- Chests found underwater (shipwrecks, sunken ruins) were quietly using the same rarity and loot rules as chests found on land. They now use their own underwater rules, so what you find down there should feel more distinct from land loot.

---

## [5.0.0] - 2026-06-14

Treasure2 has moved to **Minecraft 1.21.1 (NeoForge)**. It brings along everything from the 1.20.1 version, so the notes below cover only what is *different* from 4.0.3 — not the features that simply came along for the ride.

### ⚙️ Changed
- Treasure2 now runs on Minecraft 1.21.1 with the NeoForge mod loader. Earlier versions were for Minecraft 1.20.1 on Forge.

### ➕ Added

#### Chest Loot Tables
- Mace to Legendary
- Heavy Core to Epic
- Breeze Rod to Epic
- Trial Key, Ominous Trial Key to Epic, Legendary, Mythical
- Ominous Bottle to Rare, Epic, Legendary, Mythical
- Potion of Infestation, Oozing, Weaving, and Wind Charged to Uncommon

### 🛠️ Fixed
- Weapons now land critical hits at the chance shown on the item. Previously, any weapon that listed a critical-hit chance would critically hit on *every* swing.

### Important
- Worlds created with the old Minecraft 1.20.1 version are **not** carried over automatically. Start a fresh world on 1.21.1, or keep playing your existing world on the 1.20.1 version.

---
