package io.github.velaliilunalii.coins_n_guns.item;

import io.github.velaliilunalii.coins_n_guns.CoinsNGuns;
import io.github.velaliilunalii.coins_n_guns.item.custom.PistolItem;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class ModModelPredicateProvider {
	public static void register() {
		ModelPredicateProviderRegistry.register(ModItems.MAGNETIC_FISHING_ROD, new Identifier(CoinsNGuns.MOD_ID, "cast"),
			(itemStack, clientWorld, livingEntity, seed) -> {
				return livingEntity instanceof PlayerEntity player && player.fishHook != null ? 1.0F : 0.0F;
			});
		ModelPredicateProviderRegistry.register(ModItems.GILDED_PISTOL, new Identifier(CoinsNGuns.MOD_ID, "pulling"),
			(itemStack, clientWorld, livingEntity, seed) -> {
				return livingEntity instanceof PlayerEntity player && player.getItemUseTime() > 0 ? 1.0F : 0.0F;
			});
		ModelPredicateProviderRegistry.register(ModItems.GILDED_PISTOL, new Identifier(CoinsNGuns.MOD_ID, "pull"),
			(itemStack, clientWorld, livingEntity, seed) -> {
				return livingEntity instanceof PlayerEntity player ?
					PistolItem.getPullProgress(player.getItemUseTime(), itemStack) : 0.0F;
			});
		ModelPredicateProviderRegistry.register(ModItems.GILDED_PISTOL, new Identifier(CoinsNGuns.MOD_ID, "charged"),
			(itemStack, clientWorld, livingEntity, seed) -> {
				return PistolItem.isCharged(itemStack) ? 1.0F : 0.0F;
			});
		ModelPredicateProviderRegistry.register(ModItems.GILDED_PISTOL, new Identifier(CoinsNGuns.MOD_ID, "one_ammo"),
			(itemStack, clientWorld, livingEntity, seed) -> {
				return PistolItem.getAmmo(itemStack) == 1 ? 1.0F : 0.0F;
			});
		ModelPredicateProviderRegistry.register(ModItems.GILDED_PISTOL, new Identifier(CoinsNGuns.MOD_ID, "two_ammo"),
			(itemStack, clientWorld, livingEntity, seed) -> {
				return PistolItem.getAmmo(itemStack) == 2 ? 1.0F : 0.0F;
			});

		ModelPredicateProviderRegistry.register(ModItems.SILVER_PISTOL, new Identifier(CoinsNGuns.MOD_ID, "pulling"),
			(itemStack, clientWorld, livingEntity, seed) -> {
				return livingEntity instanceof PlayerEntity player && player.getItemUseTime() > 0 ? 1.0F : 0.0F;
			});
		ModelPredicateProviderRegistry.register(ModItems.SILVER_PISTOL, new Identifier(CoinsNGuns.MOD_ID, "pull"),
			(itemStack, clientWorld, livingEntity, seed) -> {
				return livingEntity instanceof PlayerEntity player ?
					PistolItem.getPullProgress(player.getItemUseTime(), itemStack) : 0.0F;
			});
		ModelPredicateProviderRegistry.register(ModItems.SILVER_PISTOL, new Identifier(CoinsNGuns.MOD_ID, "charged"),
			(itemStack, clientWorld, livingEntity, seed) -> {
				return PistolItem.isCharged(itemStack) ? 1.0F : 0.0F;
			});
		ModelPredicateProviderRegistry.register(ModItems.SILVER_PISTOL, new Identifier(CoinsNGuns.MOD_ID, "one_ammo"),
			(itemStack, clientWorld, livingEntity, seed) -> {
				return PistolItem.getAmmo(itemStack) == 1 ? 1.0F : 0.0F;
			});
	}
}
