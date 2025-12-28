package io.github.velaliilunalii.coins_n_guns.particle;

import io.github.velaliilunalii.coins_n_guns.particle.custom.CoinImpactParticle;
import io.github.velaliilunalii.coins_n_guns.particle.custom.MagneticCoilParticle;
import io.github.velaliilunalii.coins_n_guns.particle.custom.MagneticFieldParticle;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public class ModParticlesClient {
	public static void register() {
		ParticleFactoryRegistry.getInstance().register(ModParticles.GOLD_COIN_IMPACT_PARTICLE,
			CoinImpactParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticles.IRON_COIN_IMPACT_PARTICLE,
			CoinImpactParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticles.COPPER_COIN_IMPACT_PARTICLE,
			CoinImpactParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticles.MAGNETIC_FIELD_PARTICLE,
			MagneticFieldParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticles.MAGNETIC_COIL_PARTICLE,
			MagneticCoilParticle.Factory::new);
	}
}
