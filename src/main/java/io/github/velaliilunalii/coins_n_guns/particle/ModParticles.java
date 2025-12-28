package io.github.velaliilunalii.coins_n_guns.particle;

import io.github.velaliilunalii.coins_n_guns.CoinsNGuns;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModParticles {
	public static final DefaultParticleType GOLD_COIN_IMPACT_PARTICLE = FabricParticleTypes.simple();
	public static final DefaultParticleType IRON_COIN_IMPACT_PARTICLE = FabricParticleTypes.simple();
	public static final DefaultParticleType COPPER_COIN_IMPACT_PARTICLE = FabricParticleTypes.simple();
	public static final DefaultParticleType MAGNETIC_FIELD_PARTICLE = FabricParticleTypes.simple();
	public static final DefaultParticleType MAGNETIC_COIL_PARTICLE = FabricParticleTypes.simple();

	public static void register() {
		Registry.register(Registry.PARTICLE_TYPE, new Identifier(CoinsNGuns.MOD_ID, "gold_coin_impact_particle"),
			GOLD_COIN_IMPACT_PARTICLE);
		Registry.register(Registry.PARTICLE_TYPE, new Identifier(CoinsNGuns.MOD_ID, "iron_coin_impact_particle"),
			IRON_COIN_IMPACT_PARTICLE);
		Registry.register(Registry.PARTICLE_TYPE, new Identifier(CoinsNGuns.MOD_ID, "copper_coin_impact_particle"),
			COPPER_COIN_IMPACT_PARTICLE);
		Registry.register(Registry.PARTICLE_TYPE, new Identifier(CoinsNGuns.MOD_ID, "magnetic_field_particle"),
			MAGNETIC_FIELD_PARTICLE);
		Registry.register(Registry.PARTICLE_TYPE, new Identifier(CoinsNGuns.MOD_ID, "magnetic_coil_particle"),
			MAGNETIC_COIL_PARTICLE);
	}
}
