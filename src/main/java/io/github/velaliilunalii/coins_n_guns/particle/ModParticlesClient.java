package io.github.velaliilunalii.coins_n_guns.particle;

import io.github.velaliilunalii.coins_n_guns.particle.custom.CoinImpactParticle;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public class ModParticlesClient {
	public static void register() {
		ParticleFactoryRegistry.getInstance().register(ModParticles.GOLD_COIN_IMPACT_PARTICLE,
			CoinImpactParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticles.IRON_COIN_IMPACT_PARTICLE,
			CoinImpactParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticles.COPPER_COIN_IMPACT_PARTICLE,
			CoinImpactParticle.Factory::new);
	}
}
