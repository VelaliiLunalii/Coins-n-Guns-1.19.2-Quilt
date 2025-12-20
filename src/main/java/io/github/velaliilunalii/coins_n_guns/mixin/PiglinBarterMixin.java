package io.github.velaliilunalii.coins_n_guns.mixin;

import io.github.velaliilunalii.coins_n_guns.item.ModItems;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinBrain.class)
public class PiglinBarterMixin {

	@Inject(method = "acceptsForBarter", at = @At("HEAD"), cancellable = true)
	private static void onAcceptsForBarter(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		Item item = stack.getItem();
		if (item == Items.GOLD_INGOT || item == ModItems.GOLD_COIN) {
			cir.setReturnValue(true);
		}
	}

	@Inject(method = "isGoldenItem", at = @At("HEAD"), cancellable = true)
	private static void onIsGoldenItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (stack.isOf(ModItems.GOLD_COIN) || stack.isIn(net.minecraft.tag.ItemTags.PIGLIN_LOVED)) {
			cir.setReturnValue(true);
		}
	}
}
