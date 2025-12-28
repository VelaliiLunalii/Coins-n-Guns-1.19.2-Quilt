package io.github.velaliilunalii.coins_n_guns.item.custom;

public class SilverPistolItem extends PistolItem {
	public SilverPistolItem(Settings settings) {
		super(settings);
	}

	@Override
	public int getMaxAmmo() {
		return 2;
	}
}
