package io.github.velaliilunalii.coins_n_guns.loot;

import io.github.velaliilunalii.coins_n_guns.item.ModItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;

public class ModLootTableModifiers {
	public static final Identifier SHIPWRECK_SUPPLY = new Identifier("minecraft", "chests/shipwreck_supply");
	public static final Identifier SHIPWRECK_TREASURE = new Identifier("minecraft", "chests/shipwreck_treasure");
	public static final Identifier SHIPWRECK_MAP = new Identifier("minecraft", "chests/shipwreck_map");
	public static final Identifier BASTION_TREASURE = new Identifier("minecraft", "chests/bastion_treasure");

	public static void register() {
		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (id.equals(BASTION_TREASURE)) {
				LootPool pool = LootPool.builder()
					.with(ItemEntry.builder(ModItems.GILDED_PISTOL_BARREL))
					.rolls(ConstantLootNumberProvider.create(1))
					.build();
				tableBuilder.pool(pool);
			}
		});
	}
}
