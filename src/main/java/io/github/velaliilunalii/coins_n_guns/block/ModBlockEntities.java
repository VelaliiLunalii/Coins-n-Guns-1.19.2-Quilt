package io.github.velaliilunalii.coins_n_guns.block;

import io.github.velaliilunalii.coins_n_guns.CoinsNGuns;
import io.github.velaliilunalii.coins_n_guns.block.block_entity.InvertedPhaseBlockEntity;
import io.github.velaliilunalii.coins_n_guns.block.block_entity.MagnetiteBlockEntity;
import io.github.velaliilunalii.coins_n_guns.block.block_entity.PhaseBlockEntity;
import io.github.velaliilunalii.coins_n_guns.block.block_entity.ResonatorBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockEntities {
	public static final BlockEntityType<MagnetiteBlockEntity> MAGNETITE_BLOCK_ENTITY =
		Registry.register(
			Registry.BLOCK_ENTITY_TYPE,
			new Identifier(CoinsNGuns.MOD_ID, "magnetite_block"),
			BlockEntityType.Builder.create(
				MagnetiteBlockEntity::new,
				ModBlocks.MAGNETITE_BLOCK
			).build(null)
		);

	public static final BlockEntityType<PhaseBlockEntity> PHASE_BLOCK_ENTITY =
		Registry.register(
			Registry.BLOCK_ENTITY_TYPE,
			new Identifier(CoinsNGuns.MOD_ID, "phase_block"),
			BlockEntityType.Builder.create(
				PhaseBlockEntity::new,
				ModBlocks.PHASE_BLOCK
			).build(null)
		);

	public static final BlockEntityType<InvertedPhaseBlockEntity> INVERTED_PHASE_BLOCK_ENTITY =
		Registry.register(
			Registry.BLOCK_ENTITY_TYPE,
			new Identifier(CoinsNGuns.MOD_ID, "inverted_phase_block"),
			BlockEntityType.Builder.create(
				InvertedPhaseBlockEntity::new,
				ModBlocks.INVERTED_PHASE_BLOCK
			).build(null)
		);

	public static final BlockEntityType<ResonatorBlockEntity> RESONATOR_BLOCK_ENTITY =
		Registry.register(
			Registry.BLOCK_ENTITY_TYPE,
			new Identifier(CoinsNGuns.MOD_ID, "resonator_block"),
			BlockEntityType.Builder.create(
				ResonatorBlockEntity::new,
				ModBlocks.RESONATOR_BLOCK
			).build(null)
		);

	public static void register() {
	}
}
