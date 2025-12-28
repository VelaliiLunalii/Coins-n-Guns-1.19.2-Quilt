package io.github.velaliilunalii.coins_n_guns.item;

import io.github.velaliilunalii.coins_n_guns.block.ModBlocks;
import io.github.velaliilunalii.coins_n_guns.item.custom.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

public class ModItems {
	public static final Item MAGNETIC_FISHING_ROD = new MagneticFishingRodItem(
		new QuiltItemSettings().maxDamage(64).group(ModItemGroups.COINS_N_GUNS_GROUP));
	public static final Item MAGNET = new MagnetItem(
		new QuiltItemSettings().maxCount(1).group(ModItemGroups.COINS_N_GUNS_GROUP));
	public static final Item MAGNETITE = new Item(
		new QuiltItemSettings().group(ModItemGroups.COINS_N_GUNS_GROUP));
	public static final Item GILDED_PISTOL = new PistolItem(
		new QuiltItemSettings().maxCount(1).group(ModItemGroups.COINS_N_GUNS_GROUP));
	public static final Item SILVER_PISTOL = new SilverPistolItem(
		new QuiltItemSettings().maxCount(1).group(ModItemGroups.COINS_N_GUNS_GROUP));
	public static final Item COPPER_COIN = new CoinItem(
		new QuiltItemSettings().group(ModItemGroups.COINS_N_GUNS_GROUP), ModBlocks.COPPER_COIN_PILE);
	public static final Item IRON_COIN = new CoinItem(
		new QuiltItemSettings().group(ModItemGroups.COINS_N_GUNS_GROUP), ModBlocks.IRON_COIN_PILE);
	public static final Item GOLD_COIN = new CoinItem(
		new QuiltItemSettings().group(ModItemGroups.COINS_N_GUNS_GROUP), ModBlocks.GOLD_COIN_PILE);
	public static final Item LEATHER_COIN_POUCH = new CoinPouchItem(
		new QuiltItemSettings().maxCount(1).group(ModItemGroups.COINS_N_GUNS_GROUP));
	public static final Item CRIMSON_COIN_POUCH = new CoinPouchItem(
		new QuiltItemSettings().maxCount(1).group(ModItemGroups.COINS_N_GUNS_GROUP));
	public static final Item ENDER_COIN_POUCH = new CoinPouchItem(
		new QuiltItemSettings().maxCount(1).group(ModItemGroups.COINS_N_GUNS_GROUP));
	public static final Item SCULK_COIN_POUCH = new CoinPouchItem(
		new QuiltItemSettings().maxCount(1).group(ModItemGroups.COINS_N_GUNS_GROUP));
	public static final Item CROWBAR = new CrowbarItem(
		new QuiltItemSettings().group(ModItemGroups.COINS_N_GUNS_GROUP));
	public static final Item GILDED_PISTOL_GRIP = new Item(
		new QuiltItemSettings().group(ModItemGroups.COINS_N_GUNS_GROUP));
	public static final Item GILDED_PISTOL_BARREL = new Item(
		new QuiltItemSettings().group(ModItemGroups.COINS_N_GUNS_GROUP));
	public static final Item PHILOSOPHERS_STONE = new PhilosophersStoneItem(
		new QuiltItemSettings().group(ModItemGroups.COINS_N_GUNS_GROUP).maxCount(1).maxDamage(128));
	public static final Item MAGNETIC_SHIELD = new MagneticShieldItem(
		new QuiltItemSettings().group(ModItemGroups.COINS_N_GUNS_GROUP).maxCount(1));
	public static final Item MAGNETIC_BOOTS = new MagneticArmorItem(ArmorMaterials.IRON, EquipmentSlot.FEET,
		new QuiltItemSettings().group(ModItemGroups.COINS_N_GUNS_GROUP));


	public static void register(ModContainer mod) {
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "magnetic_fishing_rod"), MAGNETIC_FISHING_ROD);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "magnet"), MAGNET);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "magnetite"), MAGNETITE);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "gilded_pistol"), GILDED_PISTOL);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "silver_pistol"), SILVER_PISTOL);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "copper_coin"), COPPER_COIN);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "iron_coin"), IRON_COIN);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "gold_coin"), GOLD_COIN);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "leather_coin_pouch"), LEATHER_COIN_POUCH);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "crimson_coin_pouch"), CRIMSON_COIN_POUCH);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "ender_coin_pouch"), ENDER_COIN_POUCH);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "sculk_coin_pouch"), SCULK_COIN_POUCH);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "crowbar"), CROWBAR);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "gilded_pistol_grip"), GILDED_PISTOL_GRIP);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "gilded_pistol_barrel"), GILDED_PISTOL_BARREL);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "philosophers_stone"), PHILOSOPHERS_STONE);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "magnetic_shield"), MAGNETIC_SHIELD);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "magnetic_boots"), MAGNETIC_BOOTS);
	}
}
