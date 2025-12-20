package io.github.velaliilunalii.coins_n_guns.mixin;

import io.github.velaliilunalii.coins_n_guns.CoinsNGuns;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
	@Inject(method = "init", at = @At("TAIL"))
	public void exampleMod$onInit(CallbackInfo ci) {
		CoinsNGuns.LOGGER.info("This line is printed by a Coins 'n' Guns mixin!");
	}
}
