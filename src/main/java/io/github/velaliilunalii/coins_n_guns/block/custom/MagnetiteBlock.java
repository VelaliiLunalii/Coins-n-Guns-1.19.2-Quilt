package io.github.velaliilunalii.coins_n_guns.block.custom;

import io.github.velaliilunalii.coins_n_guns.block.ModBlockEntities;
import io.github.velaliilunalii.coins_n_guns.block.block_entity.MagnetiteBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;


public class MagnetiteBlock extends BlockWithEntity {
	public MagnetiteBlock(Settings settings) {
		super(settings);
		this.setDefaultState((BlockState)this.getDefaultState().with(LIT, false));
	}

	public static final BooleanProperty LIT;

	@Nullable
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return (BlockState)this.getDefaultState().with(LIT, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos()));
	}

	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
		if (!world.isClient) {
			boolean bl = (Boolean)state.get(LIT);
			if (bl != world.isReceivingRedstonePower(pos)) {
				if (bl) {
					world.scheduleBlockTick(pos, this, 4);
				} else {
					world.setBlockState(pos, (BlockState)state.cycle(LIT), 2);
				}
			}

		}
	}

	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random) {
		if ((Boolean)state.get(LIT) && !world.isReceivingRedstonePower(pos)) {
			world.setBlockState(pos, (BlockState)state.cycle(LIT), 2);
		}
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(new Property[]{LIT});
	}

	static {
		LIT = RedstoneTorchBlock.LIT;
	}

	@Override
	public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new MagnetiteBlockEntity(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return checkType(type, ModBlockEntities.MAGNETITE_BLOCK_ENTITY, (world1, pos, state1, blockEntity)
			-> blockEntity.tick(world1, pos, state1, blockEntity));
	}

	@Override
	public @Nullable <T extends BlockEntity> GameEventListener getGameEventListener(ServerWorld world, T blockEntity) {
		return super.getGameEventListener(world, blockEntity);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
}
