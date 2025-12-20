package io.github.velaliilunalii.coins_n_guns.item;

import io.github.velaliilunalii.coins_n_guns.CoinsNGuns;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModItemGroups {
	public static final ItemGroup COINS_N_GUNS_GROUP = FabricItemGroupBuilder.build(
		new Identifier(CoinsNGuns.MOD_ID, "coins_n_guns_group"),
		() -> new ItemStack(ModItems.GOLD_COIN)
	);
}
