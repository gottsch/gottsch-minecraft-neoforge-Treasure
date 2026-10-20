# Changelog for Treasure2 (Neoforge 1.21.1)

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [5.1.0] - Unreleased

### ➕ Added
- **The Wither Grove has been rebuilt.** The great wither tree now stands at the heart of a whole grove: 10 to 20 withered trees spread over a ragged clearing around it, tallest near the centre and smaller toward the edge. Every grove is different. The trees are grown fresh for each grove rather than copied from a handful of fixed shapes, and they keep the familiar witherwood look: straight trunks, thin branches spiralling up them, twigs, hanging moss and a broken top.
- **Groves have their own biome, the Wither Grove.** It is painted over the grove as it generates, so the surrounding forest's trees, flowers and grass no longer grow up through it. The sky, fog, water and foliage turn grey and sickly, ash drifts in the air, and only the grove's own creatures spawn there: wither skeletons, strays, skeletons, spiders and the occasional Witherwood Golem.
- **The grove floor is blighted.** Each tree stands in a patch of dead ground: Withered Grass, coarse dirt, podzol, mud and mycelium, with Withered Leaf Litter scattered over it. Roots turn the dirt beneath them into rooted dirt. Mossy and deepslate boulders lie half-sunk between the trees, along with fallen hollow logs, some beside the stump they broke from.
- **Strangle vines wrap the trunks.** About half the trees have a strangle vine winding up the trunk, and some are choked by vines at the base. Strangle Vines also creep over the ground round the trees, lie along fallen logs, and cover and climb the boulders.
- **More to find among the trees:** bracket fungus on the trunks, cobwebs in the forks of branches, soul lanterns hanging on chains, Grave Thorns around the clearings (thicker toward the edge), and, at night, faint drifting Wisps between the trees.
- **Suspicious gravel near some trunks.** Brush it for coins, gems, a Wither Key, bones and other oddments.
- **Roots hang from the pit and its rooms** beneath the great wither tree, wherever there is earth, wood or mud brick overhead, and they come out differently in every grove. Other dungeons can use the same `treasure2:hanging_roots_processor` in their processor lists.
- New blocks:
  - **Strangle Vines**: a tangle of vine that clings flat to any face of a block, like glow lichen. It catches at anything pushing through it, slowing and scratching it, but leaves the grove's monsters alone. Only shears take it.
  - **Strangled Witherwood Log**: a witherwood log wrapped in strangle vine. Stack them, each turned a face further round, and the vine spirals up the column. It drops a plain log unless broken with Silk Touch, and an axe strips the vine off with the bark.
  - **Hollow Witherwood Log**: a rotted-out log, lying or standing. Small creatures can pass through it.
  - **Bracket Fungus**: one to three shelves on the side of a log; place more on it to add shelves.
  - **Grave Thorns**: a dead bramble that slows and scratches anything walking through it, except the monsters that live among it. Only shears gather it whole.
  - **Withered Grass Block**: dead grass that never spreads. It drops dirt unless broken with Silk Touch, and can be made into a path or tilled.
  - **Withered Leaf Litter**: fallen dead leaves, one to four to a block, that lie on any solid surface.
  - **Wisp**: an invisible glow that only shows itself at night. It's placed by the grove; there is no item.
- Recipes:
  - **Hollow Witherwood Log** ×8: eight witherwood logs in a ring, with the middle left empty.
  - **Strangled Witherwood Log**: a witherwood log and Strangle Vines.
  - A hollow log makes 2 witherwood planks, and a strangled log makes 4, like any witherwood log.
- Bracket Fungus burns in a furnace, a little longer than a stick. The grove's other new blocks can't be crafted; they're found there.
- Witherwood logs, wood and broken tops can now be mossy, and the grove grows moss where it would grow on a real tree: low on the trunk. The mossy bark texture has been redrawn so the moss can actually be seen.
- Japanese names for the new blocks and the Wither Grove biome.
- **Other mods can now put real Treasure2 chests in their own structures.** Until now the only way was to build a Treasure2 chest block into the structure file itself and run Treasure2's chest processor over it, which makes Treasure2 a hard requirement and leaves that structure broken for anyone playing without it. A mod can now keep a marker of its own and ask Treasure2 for a chest while its structure is being built, so Treasure2 can stay an optional companion instead of a dependency. This is the same as Treasure2 4.1.0 for Forge 1.20.1.
- **A chest placed that way is a real one, not an imitation.** Its rarity, which chest it is, its locks, its loot and any mimic are all chosen by Treasure2 exactly as they are for Treasure2's own structures, and it is recorded the same way, so everything that keeps track of chests knows it is there. A mod can also ask for a particular rarity (a boss room, say) or name the loot table itself, and the rest of the chest is still Treasure2's to decide. Asking for a specific rarity deliberately does not disturb the rarity balance for chests generated normally.
- A mod can also give such a chest a loot table of its own to top it up. It is rolled after Treasure2 has filled the chest, into the slots left empty, so it never pushes out Treasure2's loot; a chest that is already full gets none of it.

### ⚙️ Changed
- The tall strangle vine plant is now called **Blightstalk**, so that the name Strangle Vines can go to the new clinging vine. It is the same plant, and nothing changes in existing worlds.
- Wither Groves no longer generate in rivers, lakes or the sea, or with their trees standing in water.
- Wither Groves no longer generate on top of villages.
- Grove trees no longer hang in the air or sink into the ground on slopes.
- In existing worlds, witherwood logs that showed moss at random now show plain bark. Moss is now part of the block rather than chosen by its position, and old logs load as unmossed.
- The chest processor's `loot_table` setting now does something. It could always be written into a processor list, and was quietly ignored: a pack that set it got whatever loot table the chest's rarity would have given anyway. None of Treasure2's own files set it, so no chest generated by Treasure2 changes; a datapack that did set it will now get the loot table it asked for.
- Fixed a crash when saving the world, and the first time a player used a chest, if Treasure2 had recorded the chest without noting which dimension it was in. A mod placing chests with the new API and skipping its last step could cause this.
- New commands for building and testing groves: `/t2 wither_tree <pos> [wither|great] [seed] [scale]` grows a single wither tree, and `/t2 wither_grove <pos>` places a whole grove.
- **Chest loot overhaul**, the same changes as Treasure2 4.2.0 for Forge 1.20.1:
  - **Legendary and Mythical chests have filler of their own.** They used to share Epic's items and food, with Rare as the lesser tier. Now they hold Mending and other high-level enchanted books, nether stars, beacons, elytra, totems, wither skeleton skulls, dragon heads, diamond and emerald blocks, end crystals and shulker boxes, as well as enchanted golden apples and Treasure2's strongest potions. Their lesser tier is now the tier just below: Epic for Legendary, Legendary for Mythical.
  - **Enchanted books at every tier from Uncommon up**, stronger the rarer the chest. Uncommon chests can also hold saddles, name tags and music discs.
  - **Tools are tools.** Swords have left the tools pools (they're still among the weapons) and shovels, shears, fishing rods and flint and steel have joined them. Epic and better chests now find enchanted diamond and netherite tools.
  - Common chests have less junk: fewer bones, rotten flesh, spider eyes and string, less raw food, and some cooked meat and baked potatoes.
  - Golden apples now start at Rare. Enchanted golden apples are found only in Legendary and Mythical chests.
  - **More valuables in every chest.** About two in five item rolls now turn up ores, ingots, gems or their blocks, rising with the chest's rarity: coal, raw ores and lapis in Common chests; diamonds, emeralds, gold blocks and netherite in Epic and better. A Legendary chest now holds about 10 diamonds' worth on average, and a Mythical one about 18.
  - **Fuller chests.** Every chest rolls more items, and Rare chests now roll as many as Epic ones. Chests average about 22 to 24 stacks, up from about 20.

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
- Better gear is now enchanted better. Rare diamond armor came unenchanted while Rare iron armor was heavily enchanted, Epic iron armor was enchanted more than Epic diamond armor, and the Rare and Scarce diamond swords were weaker than the iron ones. Diamond now always comes enchanted at least as well as iron.
- The Epic iron sword no longer stacks three rounds of enchanting.
- The Nether Star no longer turns up in Rare chests, where it was more likely than a diamond block. It now starts at Epic.
- Harmful potions in loot are now splash potions: Weakness, Poison, Harming and Treasure2's Extra Strong Poison. As drinkable potions they only ever hurt whoever drank them.
- Common chests no longer hold a useless Mundane Potion; it's a Healing potion now. The Regeneration potion in Common chests turns up five times as often (it was 1 in 31).
- The chest processor's `alt_loot_table` setting now works as a fallback. If no loot table can be found for a chest's rarity when it's first opened, the chest is filled from that table instead of staying sealed and empty. It was silently ignored before. Treasure2's ruins, wells, dungeons and surface chests set it: vanilla dungeon loot, or a handful of coins.
- A chest with no loot table recorded now draws one for its rarity when opened. Before, it never did, and the chest stayed sealed and empty no matter how often it was opened.
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
