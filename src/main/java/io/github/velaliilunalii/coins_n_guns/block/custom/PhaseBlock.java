package io.github.velaliilunalii.coins_n_guns.block.custom;

import io.github.velaliilunalii.coins_n_guns.block.ModBlockEntities;
import io.github.velaliilunalii.coins_n_guns.block.block_entity.MagnetiteBlockEntity;
import io.github.velaliilunalii.coins_n_guns.block.block_entity.PhaseBlockEntity;
import io.github.velaliilunalii.coins_n_guns.entity.custom.MagneticFieldEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.*;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PhaseBlock extends BlockWithEntity {
	public static final BooleanProperty POWERED;
	protected static final VoxelShape UNPOWERED_SHAPE;
	protected static final VoxelShape POWERED_SHAPE;

	public PhaseBlock(Settings settings) {
		super(settings);
		this.setDefaultState((BlockState)this.getDefaultState().with(POWERED, false));
	}

	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		if ((Boolean)state.get(POWERED)) {
			return POWERED_SHAPE;
		} else {
			return UNPOWERED_SHAPE;
		}
	}

	@Nullable
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return (BlockState)this.getDefaultState().with(POWERED, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos()));
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(new Property[]{POWERED});
	}

	public boolean emitsRedstonePower(BlockState state) {
		return (Boolean)state.get(POWERED);
	}

	public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
		return (Boolean)state.get(POWERED) ? 1 : 0;
	}

	static {
		POWERED = Properties.OPEN;
		UNPOWERED_SHAPE = Block.createCuboidShape((double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F, (double).0F);
		POWERED_SHAPE = Block.createCuboidShape((double)0.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)16.0F, (double)16.0F);
	}

	@Override
	public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new PhaseBlockEntity(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return checkType(type, ModBlockEntities.PHASE_BLOCK_ENTITY, (world1, pos, state1, blockEntity)
			-> blockEntity.tick(world1, pos, state1, blockEntity));
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
		return true;
	}

	@Override
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
		return 1.0f;
	}
}
