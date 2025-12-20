package io.github.velaliilunalii.coins_n_guns.enchantement;

import io.github.velaliilunalii.coins_n_guns.CoinsNGuns;
import io.github.velaliilunalii.coins_n_guns.enchantement.custom.BootyEnchantment;
import io.github.velaliilunalii.coins_n_guns.enchantement.custom.CashDashEnchantment;
import io.github.velaliilunalii.coins_n_guns.enchantement.custom.GamblingEnchantment;
import io.github.velaliilunalii.coins_n_guns.enchantement.custom.TaxEvasionEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEnchantments {
	public static final Enchantment GAMBLING = registerEnchantment("gambling", new GamblingEnchantment());
	public static final Enchantment BOOTY = registerEnchantment("booty", new BootyEnchantment());
	public static final Enchantment CASH_DASH = registerEnchantment("cash_dash", new CashDashEnchantment());
	public static final Enchantment TAX_EVASION = registerEnchantment("tax_evasion", new TaxEvasionEnchantment());

	private static Enchantment registerEnchantment(String name, Enchantment enchantment) {
		return Registry.register(Registry.ENCHANTMENT, new Identifier(CoinsNGuns.MOD_ID, name), enchantment);
	}

	public static void register() {
	}
}
