package io.github.velaliilunalii.coins_n_guns.entity;

import io.github.velaliilunalii.coins_n_guns.entity.client.*;
import io.github.velaliilunalii.coins_n_guns.entity.client.MagneticFishingRodBobberRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ModEntityRenderers {
	public static void register() {
		EntityRendererRegistry.register(ModEntities.MAGNETIC_FISHING_ROD_BOBBER, MagneticFishingRodBobberRenderer::new);
		EntityRendererRegistry.register(ModEntities.GOLD_COIN_PROJECTILE, GoldCoinProjectileRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(GoldCoinProjectileModel.COIN, GoldCoinProjectileModel::getTexturedModelData);
		EntityRendererRegistry.register(ModEntities.IRON_COIN_PROJECTILE, IronCoinProjectileRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(IronCoinProjectileModel.COIN, IronCoinProjectileModel::getTexturedModelData);
		EntityRendererRegistry.register(ModEntities.COPPER_COIN_PROJECTILE, CopperCoinProjectileRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(CopperCoinProjectileModel.COIN, CopperCoinProjectileModel::getTexturedModelData);
	}
}
