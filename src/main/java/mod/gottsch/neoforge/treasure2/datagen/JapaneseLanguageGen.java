/*
 * This file is part of Treasure2.
 * Copyright (c) 2025 Mark Gottschling (gottsch)
 *
 * Treasure2 is free software: you can redistribute it and/or modify
 * it under the terms of the Open Software Licence 3.0.
 *
 * Treasure2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * Open Software Licence 3.0 for more details.
 *
 * You should have received a copy of the Open Software Licence
 * along with Treasure2. If not, see <https://www.tldrlegal.com/license/open-software-licence-3-0>.
 */
package mod.gottsch.neoforge.treasure2.datagen;

import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.block.TreasureBlocks;
import mod.gottsch.neoforge.treasure2.core.entity.TreasureEntities;
import mod.gottsch.neoforge.treasure2.core.item.TreasureItems;
import mod.gottsch.neoforge.treasure2.core.util.LangUtil;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

/**
 * @author Mark Gottschling on Apr 6, 2022
 */
public class JapaneseLanguageGen extends LanguageProvider {

    public JapaneseLanguageGen(PackOutput output, String locale) {
        super(output, Treasure.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        // tabs
        add("itemGroup." + Treasure.MODID, "Treasure2");

        // keys
        add(TreasureItems.WOOD_KEY.get(), "木のカギ");
        add(TreasureItems.STONE_KEY.get(), "石のカギ");
        add(TreasureItems.LEAF_KEY.get(), "樹葉のカギ");
        add(TreasureItems.EMBER_KEY.get(), "残り火のカギ");
        add(TreasureItems.LIGHTNING_KEY.get(), "雷光のカギ");

        add(TreasureItems.IRON_KEY.get(), "鉄のカギ");
        add(TreasureItems.GOLD_KEY.get(), "金のカギ");
        add(TreasureItems.METALLURGISTS_KEY.get(), "金属博士のカギ");

        add(TreasureItems.DIAMOND_KEY.get(), "ダイヤモンドのカギ");
        add(TreasureItems.EMERALD_KEY.get(), "エメラルドのカギ");
        add(TreasureItems.TOPAZ_KEY.get(), "トパーズのカギ");
        add(TreasureItems.ONYX_KEY.get(), "オニキスのカギ");
        add(TreasureItems.RUBY_KEY.get(), "ルビーのカギ");
        add(TreasureItems.SAPPHIRE_KEY.get(), "サファイヤのカギ");
        add(TreasureItems.JEWELLED_KEY.get(), "宝石飾りのカギ");

        add(TreasureItems.SPIDER_KEY.get(), "クモのカギ");
        add(TreasureItems.WITHER_KEY.get(), "ウィザーのカギ");

        add(TreasureItems.SKELETON_KEY.get(), "骨のカギ");
        add(TreasureItems.PILFERERS_LOCK_PICK.get(), "コソ泥のピッキング");
        add(TreasureItems.THIEFS_LOCK_PICK.get(), "盗賊のピッキング");
        add(TreasureItems.BONE_KEY.get(), "骨のカギ");
        // TODO: add(TreasureItems.ONE_KEY.get(), "マスターキー") when ONE_KEY is ported

        // locks
        add(TreasureItems.WOOD_LOCK.get(), "木の錠前");
        add(TreasureItems.STONE_LOCK.get(), "石の錠前");
        add(TreasureItems.LEAF_LOCK.get(), "樹葉の錠前");
        add(TreasureItems.EMBER_LOCK.get(), "残り火の錠前");
        add(TreasureItems.IRON_LOCK.get(), "鉄の錠前");
        add(TreasureItems.GOLD_LOCK.get(), "金の錠前");

        add(TreasureItems.DIAMOND_LOCK.get(), "ダイヤモンドの錠前");
        add(TreasureItems.EMERALD_LOCK.get(), "エメラルドの錠前");
        add(TreasureItems.TOPAZ_LOCK.get(), "トパーズの錠前");
        add(TreasureItems.ONYX_LOCK.get(), "オニキスの上飴");
        add(TreasureItems.RUBY_LOCK.get(), "ルビーの錠前");
        add(TreasureItems.SAPPHIRE_LOCK.get(), "サファイヤの錠前");

        add(TreasureItems.SPIDER_LOCK.get(), "クモの錠前");
        add(TreasureItems.WITHER_LOCK.get(), "ウィザーの錠前");
        // TODO: add(TreasureItems.BONE_LOCK.get(), ...) when BONE_LOCK is ported

        // key ring
        add(TreasureItems.KEY_RING.get(), "キーホルダー");

        // pouch
        add(TreasureItems.POUCH.get(), "小銭入れ");

        // spawn eggs
        add(TreasureItems.BOUND_SOUL_EGG.get(), "地縛霊のスポーンエッグ");
        add(TreasureItems.WITHERWOOD_GOLEM_EGG.get(), "ウィザーウッドゴーレムのスポーンエッグ");
        add(TreasureItems.WOOD_CHEST_MIMIC_EGG.get(), "チェストミミックのスポーンエッグ");
        add(TreasureItems.PIRATE_CHEST_MIMIC_EGG.get(), "海賊チェストミミックのスポーンエッグ");
        add(TreasureItems.VIKING_CHEST_MIMIC_EGG.get(), "バイキングミミックのスポーンエッグ");
        add(TreasureItems.CAULDRON_CHEST_MIMIC_EGG.get(), "大釜ミミックのスポーンエッグ");
        add(TreasureItems.CRATE_CHEST_MIMIC_EGG.get(), "木箱ミミックのスポーンエッグ");
        add(TreasureItems.MOLDY_CRATE_CHEST_MIMIC_EGG.get(), "苔むした木箱ミミックのスポーンエッグ");
        add(TreasureItems.CARDBOARD_BOX_MIMIC_EGG.get(), "段ボールミミックのスポーンエッグ");
        add(TreasureItems.MILK_CRATE_MIMIC_EGG.get(), "ミルク入れミミックのスポーンエッグ");
        add(TreasureItems.BARREL_MIMIC_EGG.get(), "樽ミミックのスポーンエッグ");
        add(TreasureItems.VANILLA_CHEST_MIMIC_EGG.get(), "宝箱ミミックのスポーンエッグ");
        // TODO: restore TREASURE_TOOL, coins, gems, EYE_PATCH, CLOVER when ported

        // chests
        add(TreasureBlocks.WOOD_CHEST.get(), "木のチェスト");
        add(TreasureBlocks.CRATE_CHEST.get(), "木箱");
        add(TreasureBlocks.MOLDY_CRATE_CHEST.get(), "カビた木箱");
        add(TreasureBlocks.IRONBOUND_CHEST.get(), "鉄張りのチェスト");
        add(TreasureBlocks.SAFE.get(), "金庫");
        add(TreasureBlocks.PIRATE_CHEST.get(), "海賊のチェスト");
        add(TreasureBlocks.IRON_STRONGBOX.get(), "鉄の保管庫");
        add(TreasureBlocks.GOLD_STRONGBOX.get(), "金の保管庫");
        add(TreasureBlocks.DREAD_PIRATE_CHEST.get(), "大海賊のチェスト");
        add(TreasureBlocks.COMPRESSOR_CHEST.get(), "圧縮チェスト");
        add(TreasureBlocks.SKULL_CHEST.get(), "ドクロのチェスト");
        add(TreasureBlocks.GOLD_SKULL_CHEST.get(), "金ドクロのチェスト");
        add(TreasureBlocks.CRYSTAL_SKULL_CHEST.get(), "水晶ドクロのチェスト");
        add(TreasureBlocks.CAULDRON_CHEST.get(), "大釜チェスト");
        add(TreasureBlocks.SPIDER_CHEST.get(), "クモのチェスト");
        add(TreasureBlocks.VIKING_CHEST.get(), "バイキングのチェスト");
        add(TreasureBlocks.CARDBOARD_BOX.get(), "段ボール箱");
        add(TreasureBlocks.MILK_CRATE.get(), "ミルク木箱");
        add(TreasureBlocks.BARREL_CHEST.get(), "宝入りの樽");
        add(TreasureBlocks.VANILLA_CHEST.get(), "宝のチェスト");
        add(TreasureBlocks.WITHER_CHEST.get(), "ウィザーのチェスト");
        add(TreasureBlocks.WITHER_CHEST_TOP.get(), "ウィザーのチェスト");
        add(TreasureBlocks.BONE_CHEST.get(), "骨のチェスト");
        add(TreasureBlocks.CELESTIAL_CHEST.get(), "天界のチェスト");
        add(TreasureBlocks.INFERNAL_CHEST.get(), "魔界のチェスト");
        // TODO: restore witherwood, gravestone, wishing well, clover, spanish moss block translations when ported

        // ore
        add(TreasureBlocks.TOPAZ_ORE.get(), "トパーズ鉱石");
        add(TreasureBlocks.ONYX_ORE.get(), "オニキス鉱石");
        add(TreasureBlocks.RUBY_ORE.get(), "ルビー鉱石");
        add(TreasureBlocks.SAPPHIRE_ORE.get(), "サファイヤ鉱石");

        add(TreasureBlocks.DEEPSLATE_TOPAZ_ORE.get(), "深層トパーズ鉱石");
        add(TreasureBlocks.DEEPSLATE_ONYX_ORE.get(), "深層オニキス鉱石");
        add(TreasureBlocks.DEEPSLATE_RUBY_ORE.get(), "深層ルビー鉱石");
        add(TreasureBlocks.DEEPSLATE_SAPPHIRE_ORE.get(), "深層サファイヤ鉱石");

        // mobs
        add(TreasureEntities.BOUND_SOUL_ENTITY_TYPE.get(), "地縛霊");
        add(TreasureEntities.WITHERWOOD_GOLEM_ENTITY_TYPE.get(), "ウィザーウッドゴーレム");
        add(TreasureEntities.WOOD_CHEST_MIMIC_ENTITY_TYPE.get(), "チェストミミック");
        add(TreasureEntities.PIRATE_CHEST_MIMIC_ENTITY_TYPE.get(), "海賊チェストミミック");
        add(TreasureEntities.VIKING_CHEST_MIMIC_ENTITY_TYPE.get(), "イバキングミミック");
        add(TreasureEntities.CAULDRON_CHEST_MIMIC_ENTITY_TYPE.get(), "大釜ミミック");
        add(TreasureEntities.CRATE_CHEST_MIMIC_ENTITY_TYPE.get(), "木箱ミミック");
        add(TreasureEntities.MOLDY_CRATE_CHEST_MIMIC_ENTITY_TYPE.get(), "苔むした木箱ミミック");
        add(TreasureEntities.CARDBOARD_BOX_MIMIC_ENTITY_TYPE.get(), "段ボールミミック");
        add(TreasureEntities.MILK_CRATE_MIMIC_ENTITY_TYPE.get(), "ミルク入れミミック");
        add(TreasureEntities.BARREL_MIMIC_ENTITY_TYPE.get(), "樽ミミック");
        add(TreasureEntities.VANILLA_CHEST_MIMIC_ENTITY_TYPE.get(), "宝箱ミミック");
        // TODO: restore spawn eggs when ported

        /*
         * tooltips
         */
        // general
        add(LangUtil.tooltip("boolean.yes"), "Yes");
        add(LangUtil.tooltip("boolean.no"), "No");
        add(LangUtil.tooltip("hold_shift"), "[SHIFT]長押しで展開");
        add(LangUtil.tooltip("treasure_tool"), "Treasure2のレシピでよく必要になる");
        add(LangUtil.tooltip("pouch"), "コインや宝石、お守り等の小さい貴重品を入れておける");
        add(LangUtil.tooltip("wishable"), "願い井戸に投げ込むと何かが見つかるかもしれない");
        add(LangUtil.tooltip("clover"), "特定のブロックに対して使用できる。例えば、苔むした丸石に使うと願い井戸ブロックに変換できる。~このとき、半径４ブロックの隣接する範囲が全て変換される。");

        // keys and locks
        add(LangUtil.tooltip("key_lock.rarity"), "レア度: %s");
        add(LangUtil.tooltip("key_lock.category"), "タイプ: %s");
        add(LangUtil.tooltip("key_lock.craftable"), "作成可能: %s");
        add(LangUtil.tooltip("key_lock.breakable"), "破壊可能: %s");
        add(LangUtil.tooltip("key_lock.damageable"), "損傷可能: %s");
        add(LangUtil.tooltip("key_lock.accepts_keys"), "有効なカギ:");
        add(LangUtil.tooltip("key_lock.specials"), "特殊: %s");
        add(LangUtil.tooltip("key_lock.skeleton_key.specials"), "通常、上級、逸品およびレアの錠前を~開けることができる(ただしウィザーを除く)");
        add(LangUtil.tooltip("key_lock.ember_key.specials"), "木の錠前と樹葉の錠前を壊す");
        add(LangUtil.tooltip("key_lock.ember_lock.specials"), "破壊可否に関わらずすべての錠前を壊す(ただし残り火と雷光の錠前を除く)");
        add(LangUtil.tooltip("key_lock.lightning_key.specials"), "精霊タイプの全ての錠前を開けることができる");
        add(LangUtil.tooltip("key_lock.metallurgists_key.specials"), "金属タイプの全ての錠前を開けることができる");
        add(LangUtil.tooltip("key_lock.jewelled_key.specials"), "宝石タイプの全ての錠前を開けることができる");
        add(LangUtil.tooltip("key_lock.pilferers_lock_pick.specials"), "通常(%s%%)と上級(%s%%)の錠前を開けることができる");
        add(LangUtil.tooltip("key_lock.thiefs_lock_pick.specials"), "通常(%s%%)、上級(%s%%)および逸品(%s%%)の錠前を開けることができる");
        add(LangUtil.tooltip("key_lock.one_key.specials"), "あらゆる錠前を開けることができる");
        add(LangUtil.tooltip("key_lock.one_key.lore"), "マスターキーは全てを支配する");
        add(LangUtil.tooltip("key_lock.key_ring"), "複数のカギを付けておける");

        // chests
        add(LangUtil.tooltip("chest.locked"), "施錠されている！");
        add(LangUtil.tooltip("chest.usage"), "願い井戸に投げ込むと全ての錠前を外すことができる・・代償はあるが");
        add(LangUtil.tooltip("chest.rarity"), "レア度: %s");
        add(LangUtil.tooltip("chest.max_locks"), "施錠上限: %s");
        add(LangUtil.tooltip("chest.container_size"), "収納数: %s");

        // capabilities
        add(LangUtil.tooltip("cap.durability.amount"), "耐久度: [%s/%s]");
        add(LangUtil.tooltip("cap.durability.amount.infinite"), "耐久度: 無限");
        add(LangUtil.tooltip("cap.durability.repairs"), "R[%s/%s]");
        add(LangUtil.tooltip("cap.spell.recharges"), "R[%s/%s]");

        // weapons (lang keys only; items not yet ported)
        add(LangUtil.tooltip("weapons.black_sword.lore"), "アバターの剣。~悪魔アルカディオンを招き入れし者。");
        add(LangUtil.tooltip("weapons.sword_of_omens.lore"), "'時を超え 今すぐに 旅立て'");
        add(LangUtil.tooltip("weapons.sword_of_power.lore"), "'人よ 生命よ 力を見る'");
        add(LangUtil.tooltip("weapons.orcus.lore"), "'美しき人よ、もはや豪奢な衣は無用。~誇りも喜びも捨て去るが一興。~今宵我と共に彼岸へ逝こう。~    - 死神の名の下に");
        add(LangUtil.tooltip("weapons.snake_eyes_katana.lore"), "風と共に出で来、また風と共に去りぬ~    - 蛇ノ目");
        add(LangUtil.tooltip("weapons.storm_shadows_katana.lore"), "ニンジャは実在しない。いいね？~    - 黒風");
        add(LangUtil.tooltip("weapons.oathbringer.lore"), "死の前に生が~弱さの前に強さが~目的地の前に旅がある");
        add(LangUtil.tooltip("weapons.callandor.lore"), "我を意のままに操る者こそが運命の支配者。~我を手に取り、最後の旅路に足を踏み出すのだ。~    -「竜王の再来」より");
        add(LangUtil.tooltip("weapons.mjolnir.lore"), "'汝にその資格があるならばこのハンマーを手に取れ。~雷神トールの力をその手にできるだろう'");
        add(LangUtil.tooltip("weapons.headsmans_axe.lore"), "良き時代を築こうじゃないか");
        add(LangUtil.tooltip("weapons.axe_of_durin.lore"), "'・・俺は斧を捧げよう'");
        add(LangUtil.tooltip("weapons.power_attack_chance"), "渾身の一撃発生確率: %s%%");
        add(LangUtil.tooltip("weapons.power_attack_damage"), "渾身の一撃ダメージ: +%s");

        /*
         * screens
         */
        // chests
        add(LangUtil.screen("wood_chest.name"), "木のチェスト");
        add(LangUtil.screen("crate_chest.name"), "木箱");
        add(LangUtil.screen("moldy_crate_chest.name"), "カビた木箱");
        add(LangUtil.screen("ironbound_chest.name"), "鉄張りのチェスト");
        add(LangUtil.screen("pirate_chest.name"), "海賊のチェスト");
        add(LangUtil.screen("safe.name"), "金庫");
        add(LangUtil.screen("iron_strongbox.name"), "鉄の保管庫");
        add(LangUtil.screen("gold_strongbox.name"), "金の保管庫");
        add(LangUtil.screen("dread_pirate_chest.name"), "大海賊のチェスト");
        add(LangUtil.screen("compressor_chest.name"), "圧縮チェスト");
        add(LangUtil.screen("skull_chest.name"), "ドクロのチェスト");
        add(LangUtil.screen("gold_skull_chest.name"), "金ドクロのチェスト");
        add(LangUtil.screen("crystal_skull_chest.name"), "水晶ドクロのチェスト");
        add(LangUtil.screen("cauldron_chest.name"), "大釜チェスト");
        add(LangUtil.screen("viking_chest.name"), "バイキングチェスト");
        add(LangUtil.screen("spider_chest.name"), "クモのチェスト");
        add(LangUtil.screen("cardboard_box.name"), "段ボール箱");
        add(LangUtil.screen("milk_crate.name"), "ミルク木箱");
        add(LangUtil.screen("barrel_chest.name"), "宝入りの樽");
        add(LangUtil.screen("vanilla_chest.name"), "宝箱");
        add(LangUtil.screen("wither_chest.name"), "ウィザーのチェスト");
        add(LangUtil.screen("bone_chest.name"), "骨のチェスト");
        add(LangUtil.screen("celestial_chest.name"), "天界のチェスト");
        add(LangUtil.screen("infernal_chest.name"), "魔界のチェスト");

        add(LangUtil.screen("treasure_map.common"), "通常の宝の地図");
        add(LangUtil.screen("treasure_map.uncommon"), "上級の宝の地図");
        add(LangUtil.screen("treasure_map.scarce"), "逸品の宝の地図");
        add(LangUtil.screen("treasure_map.rare"), "レアな宝の地図");
        add(LangUtil.screen("treasure_map.epic"), "至高の宝の地図");
        add(LangUtil.screen("treasure_map.legendary"), "伝説の宝の地図");
        add(LangUtil.screen("treasure_map.mythical"), "神々の宝の地図");
        add(LangUtil.screen("treasure_map.skull"), "逸品の宝の地図");
        add(LangUtil.screen("treasure_map.gold_skull"), "レアな宝の地図");
        add(LangUtil.screen("treasure_map.crystal_skull"), "至高の宝の地図");
        add(LangUtil.screen("treasure_map.bone"), "逸品の宝の地図");
        add(LangUtil.screen("treasure_map.cauldron"), "至高の宝の地図");
        add(LangUtil.screen("treasure_map.wither"), "逸品の宝の地図");

        /*
         * chat
         */
        // keys
        add(LangUtil.chat("key.key_break"), "開錠しようとしたがカギが壊れてしまった！");
        add(LangUtil.chat("key.key_not_fit"), "カギが合わないようだ");
        add(LangUtil.chat("key.key_unable_unlock"), "開錠に失敗した！");

        /*
         * patchouli
         */
        add("book.treasure2.landing", "Treasure2はマインクラフトに新たな発見のスリルと楽しみを追加します。このMODは、様々な新しいレアな宝箱と財宝アイテムを追加します。宝箱は様々なロックで保護されており、解除するには対応するカギを見つける必要があります。");
    }
}
