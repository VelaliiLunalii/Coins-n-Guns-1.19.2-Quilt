package io.github.velaliilunalii.coins_n_guns;

import io.github.velaliilunalii.coins_n_guns.block.ModBlocksClient;
import io.github.velaliilunalii.coins_n_guns.entity.ModEntityRenderers;
import io.github.velaliilunalii.coins_n_guns.gui.CoinSelectionGUI;
import io.github.velaliilunalii.coins_n_guns.item.ModModelPredicateProvider;
import io.github.velaliilunalii.coins_n_guns.particle.ModParticlesClient;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class CoinsNGunsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer mod) {
		ModModelPredicateProvider.register();
		ModBlocksClient.register(mod);
		ModEntityRenderers.register();
		HudRenderCallback.EVENT.register(new CoinSelectionGUI());
		ModParticlesClient.register();
	}
}
