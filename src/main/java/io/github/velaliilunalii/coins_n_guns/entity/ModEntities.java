package io.github.velaliilunalii.coins_n_guns.entity;

import io.github.velaliilunalii.coins_n_guns.CoinsNGuns;
import io.github.velaliilunalii.coins_n_guns.entity.custom.CopperCoinProjectileEntity;
import io.github.velaliilunalii.coins_n_guns.entity.custom.GoldCoinProjectileEntity;
import io.github.velaliilunalii.coins_n_guns.entity.custom.IronCoinProjectileEntity;
import io.github.velaliilunalii.coins_n_guns.entity.custom.MagneticFishingRodBobberEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntities {
	public static final EntityType<MagneticFishingRodBobberEntity> MAGNETIC_FISHING_ROD_BOBBER =
		Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(CoinsNGuns.MOD_ID, "magnetic_fishing_rod_bobber"),
			FabricEntityTypeBuilder.<MagneticFishingRodBobberEntity>create(
					SpawnGroup.MISC, MagneticFishingRodBobberEntity::new)
				.dimensions(EntityDimensions.fixed(0.25F, 0.25F))
				.trackRangeChunks(4).trackedUpdateRate(10)
				.build()
		);

	public static final EntityType<GoldCoinProjectileEntity> GOLD_COIN_PROJECTILE =
		Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(CoinsNGuns.MOD_ID, "gold_coin_projectile"),
			FabricEntityTypeBuilder.<GoldCoinProjectileEntity>create(
					SpawnGroup.MISC, GoldCoinProjectileEntity::new)
				.dimensions(EntityDimensions.fixed(0.25F, 0.25F))
				.trackRangeChunks(4).trackedUpdateRate(10)
				.build()
		);
	public static final EntityType<IronCoinProjectileEntity> IRON_COIN_PROJECTILE =
		Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(CoinsNGuns.MOD_ID, "iron_coin_projectile"),
			FabricEntityTypeBuilder.<IronCoinProjectileEntity>create(
					SpawnGroup.MISC, IronCoinProjectileEntity::new)
				.dimensions(EntityDimensions.fixed(0.25F, 0.25F))
				.trackRangeChunks(4).trackedUpdateRate(10)
				.build()
		);
	public static final EntityType<CopperCoinProjectileEntity> COPPER_COIN_PROJECTILE =
		Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(CoinsNGuns.MOD_ID, "copper_coin_projectile"),
			FabricEntityTypeBuilder.<CopperCoinProjectileEntity>create(
					SpawnGroup.MISC, CopperCoinProjectileEntity::new)
				.dimensions(EntityDimensions.fixed(0.25F, 0.25F))
				.trackRangeChunks(4).trackedUpdateRate(10)
				.build()
		);

	public static void register() {
	}
}
