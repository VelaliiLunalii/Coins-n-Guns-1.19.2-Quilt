package io.github.velaliilunalii.coins_n_guns.sound;

import io.github.velaliilunalii.coins_n_guns.CoinsNGuns;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSounds {
	public static final SoundEvent COIN_BREAK = registerSoundEvent("coin_break");
	public static final SoundEvent COIN_SHOT = registerSoundEvent("coin_shot");
	public static final SoundEvent COIN_SHUFFLE = registerSoundEvent("coin_shuffle");
	public static final SoundEvent COIN_INSERT = registerSoundEvent("coin_insert");

	public static final BlockSoundGroup COIN_BREAK_GROUP = new BlockSoundGroup(
		1.0F, 1.0F,
		COIN_BREAK, COIN_BREAK, COIN_BREAK, COIN_BREAK, COIN_BREAK
	);

	private static SoundEvent registerSoundEvent(String name) {
		Identifier id = new Identifier(CoinsNGuns.MOD_ID, name);
		return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
	}

	public static void register() {

	}
}
