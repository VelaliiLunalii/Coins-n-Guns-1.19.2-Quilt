package io.github.velaliilunalii.coins_n_guns.block.custom;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class CoinPileBlock extends CarpetBlock {
	public enum CoinTypes{
		COPPER(Block.createCuboidShape((double)2.0F, (double)0.0F, (double)2.0F, (double)14.0F, (double)3.0F, (double)14.0F)),
		IRON(Block.createCuboidShape((double)5.0F, (double)0.0F, (double)5.0F, (double)11.0F, (double)5.0F, (double)12.0F)),
		GOLD(Block.createCuboidShape((double)7.0F, (double)0.0F, (double)7.0F, (double)12.0F, (double)1.0F, (double)12.0F));

		private final VoxelShape shape;

		CoinTypes(VoxelShape shape) {
			this.shape = shape;
		}

		public VoxelShape getShape() {return shape;}
	}
	protected final VoxelShape SHAPE;

	public CoinPileBlock(Settings settings, CoinTypes type) {
		super(settings);
		this.SHAPE = type.getShape();
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}
}
