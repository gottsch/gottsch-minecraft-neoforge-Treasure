# Changelog for Treasure2 (Neoforge 1.21.1)

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

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
