package io.github.velaliilunalii.coins_n_guns.item.custom;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CoinItem extends Item {
	private final Block coinPile;

	public CoinItem(Settings settings, Block coinPile) {
		super(settings);
		this.coinPile = coinPile;
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos pos = new BlockPos(context.getHitPos());
		if (!world.isClient && !world.isAir(pos.down()) && world.isAir(pos)){
			world.setBlockState(pos, coinPile.getDefaultState(), Block.NOTIFY_ALL);
			context.getStack().decrement(1);
			return ActionResult.CONSUME;
		}
		context.getPlayer().swingHand(Hand.MAIN_HAND);
		return super.useOnBlock(context);
	}
}
