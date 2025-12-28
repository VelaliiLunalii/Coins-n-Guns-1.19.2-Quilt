package io.github.velaliilunalii.coins_n_guns.block;

import io.github.velaliilunalii.coins_n_guns.block.custom.*;
import io.github.velaliilunalii.coins_n_guns.item.ModItemGroups;
import io.github.velaliilunalii.coins_n_guns.sound.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.HashMap;
import java.util.Map;

public class ModBlocks {
	public enum WoodVariants{
		SPRUCE("spruce"),
		OAK("oak"),
		ACACIA("acacia"),
		BIRCH("birch"),
		CRIMSON("crimson"),
		DARK_OAK("dark_oak"),
		JUNGLE("jungle"),
		MANGROVE("mangrove"),
		WARPED("warped");

		private final String name;

		WoodVariants(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public static final Map<WoodVariants, Block> WOODEN_CRATES = new HashMap<>();
	public static final Map<WoodVariants, Block> MOSSY_CRATES = new HashMap<>();

	private static void wooden_and_mossy_crates_maker(ModContainer mod){
		for (WoodVariants wood_variant : WoodVariants.values()){
			Block block = new WoodenCrateBlock(QuiltBlockSettings.copyOf(Blocks.OAK_PLANKS));
			String name = wood_variant.getName().concat("_crate");

			Registry.register(Registry.BLOCK, new Identifier(mod.metadata().id(), name), block);
			Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), name),
				new BlockItem(block, new QuiltItemSettings().group(ModItemGroups.COINS_N_GUNS_GROUP)));

			WOODEN_CRATES.put(wood_variant, block);

			block = new MossyCrateBlock(QuiltBlockSettings.copyOf(Blocks.OAK_PLANKS));

			Registry.register(Registry.BLOCK, new Identifier(mod.metadata().id(), "mossy_".concat(name)), block);
			Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "mossy_".concat(name)),
				new BlockItem(block, new QuiltItemSettings().group(ModItemGroups.COINS_N_GUNS_GROUP)));

			MOSSY_CRATES.put(wood_variant, block);
		}
	}

	public static final Block IRON_CRATE = new IronCrateBlock(QuiltBlockSettings.copyOf(Blocks.IRON_BLOCK));
	public static final Block WEIGHTED_COMPANION_CRATE = new WeightedCompanionCrateClass(QuiltBlockSettings.copyOf(Blocks.IRON_BLOCK));
	public static final Block COPPER_COIN_PILE = new CoinPileBlock(QuiltBlockSettings.copyOf(Blocks.MOSS_CARPET).hardness(0.01f).sounds(ModSounds.COIN_BREAK_GROUP), CoinPileBlock.CoinTypes.COPPER);
	public static final Block IRON_COIN_PILE = new CoinPileBlock(QuiltBlockSettings.copyOf(Blocks.MOSS_CARPET).hardness(0.01f).sounds(ModSounds.COIN_BREAK_GROUP), CoinPileBlock.CoinTypes.IRON);
	public static final Block GOLD_COIN_PILE = new CoinPileBlock(QuiltBlockSettings.copyOf(Blocks.MOSS_CARPET).hardness(0.01f).sounds(ModSounds.COIN_BREAK_GROUP), CoinPileBlock.CoinTypes.GOLD);

	public static final Block MAGNETITE_BLOCK = new MagnetiteBlock(QuiltBlockSettings.copyOf(Blocks.IRON_BLOCK));
	public static final Block MAGNETIC_COIL = new PillarBlock(QuiltBlockSettings.copyOf(Blocks.IRON_BLOCK));
	public static final Block WEAK_MAGNETIC_COIL = new PillarBlock(QuiltBlockSettings.copyOf(Blocks.IRON_BLOCK));
	public static final Block STRONG_MAGNETIC_COIL = new PillarBlock(QuiltBlockSettings.copyOf(Blocks.IRON_BLOCK));
	public static final Block PHASE_BLOCK = new PhaseBlock(QuiltBlockSettings.copyOf(Blocks.GLASS).nonOpaque());
	public static final Block INVERTED_PHASE_BLOCK = new InvertedPhaseBlock(QuiltBlockSettings.copyOf(Blocks.GLASS).nonOpaque());
	public static final Block RESONATOR_BLOCK = new ResonatorBlock(QuiltBlockSettings.copyOf(Blocks.IRON_BLOCK));

	public static void register(ModContainer mod) {
		wooden_and_mossy_crates_maker(mod);

		Registry.register(Registry.BLOCK, new Identifier(mod.metadata().id(), "iron_crate"), IRON_CRATE);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "iron_crate"),
			new BlockItem(IRON_CRATE, new QuiltItemSettings().group(ModItemGroups.COINS_N_GUNS_GROUP)));
		Registry.register(Registry.BLOCK, new Identifier(mod.metadata().id(), "weighted_companion_crate"), WEIGHTED_COMPANION_CRATE);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "weighted_companion_crate"),
			new BlockItem(WEIGHTED_COMPANION_CRATE, new QuiltItemSettings()));

		Registry.register(Registry.BLOCK, new Identifier(mod.metadata().id(), "copper_coin_pile"), COPPER_COIN_PILE);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "copper_coin_pile"),
			new BlockItem(COPPER_COIN_PILE, new QuiltItemSettings()));
		Registry.register(Registry.BLOCK, new Identifier(mod.metadata().id(), "iron_coin_pile"), IRON_COIN_PILE);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "iron_coin_pile"),
			new BlockItem(IRON_COIN_PILE, new QuiltItemSettings()));
		Registry.register(Registry.BLOCK, new Identifier(mod.metadata().id(), "gold_coin_pile"), GOLD_COIN_PILE);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "gold_coin_pile"),
			new BlockItem(GOLD_COIN_PILE, new QuiltItemSettings()));

		Registry.register(Registry.BLOCK, new Identifier(mod.metadata().id(), "magnetite_block"), MAGNETITE_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "magnetite_block"),
			new BlockItem(MAGNETITE_BLOCK, new QuiltItemSettings().group(ModItemGroups.COINS_N_GUNS_GROUP)));
		Registry.register(Registry.BLOCK, new Identifier(mod.metadata().id(), "magnetic_coil"), MAGNETIC_COIL);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "magnetic_coil"),
			new BlockItem(MAGNETIC_COIL, new QuiltItemSettings().group(ModItemGroups.COINS_N_GUNS_GROUP)));
		Registry.register(Registry.BLOCK, new Identifier(mod.metadata().id(), "weak_magnetic_coil"), WEAK_MAGNETIC_COIL);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "weak_magnetic_coil"),
			new BlockItem(WEAK_MAGNETIC_COIL, new QuiltItemSettings().group(ModItemGroups.COINS_N_GUNS_GROUP)));
		Registry.register(Registry.BLOCK, new Identifier(mod.metadata().id(), "strong_magnetic_coil"), STRONG_MAGNETIC_COIL);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "strong_magnetic_coil"),
			new BlockItem(STRONG_MAGNETIC_COIL, new QuiltItemSettings().group(ModItemGroups.COINS_N_GUNS_GROUP)));

		Registry.register(Registry.BLOCK, new Identifier(mod.metadata().id(), "phase_block"), PHASE_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "phase_block"),
			new BlockItem(PHASE_BLOCK, new QuiltItemSettings().group(ModItemGroups.COINS_N_GUNS_GROUP)));
		Registry.register(Registry.BLOCK, new Identifier(mod.metadata().id(), "inverted_phase_block"), INVERTED_PHASE_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "inverted_phase_block"),
			new BlockItem(INVERTED_PHASE_BLOCK, new QuiltItemSettings().group(ModItemGroups.COINS_N_GUNS_GROUP)));
		Registry.register(Registry.BLOCK, new Identifier(mod.metadata().id(), "resonator_block"), RESONATOR_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "resonator_block"),
			new BlockItem(RESONATOR_BLOCK, new QuiltItemSettings().group(ModItemGroups.COINS_N_GUNS_GROUP)));
	}
}
