package io.github.velaliilunalii.coins_n_guns.block.block_entity;

import io.github.velaliilunalii.coins_n_guns.block.ModBlockEntities;
import io.github.velaliilunalii.coins_n_guns.block.ModBlocks;
import io.github.velaliilunalii.coins_n_guns.block.custom.MagnetiteBlock;
import io.github.velaliilunalii.coins_n_guns.entity.custom.MagneticFieldEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.ArrayList;

import static io.github.velaliilunalii.coins_n_guns.block.custom.MagnetiteBlock.LIT;

public class MagnetiteBlockEntity extends BlockEntity implements BlockEntityTicker<MagnetiteBlockEntity> {
	public MagnetiteBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.MAGNETITE_BLOCK_ENTITY, pos, state);
	}

	@Override
	public void tick(World world, BlockPos blockPos, BlockState blockState, MagnetiteBlockEntity blockEntity) {
		if (this.world == null) return;
		if (!this.world.isClient && this.world.getTime() % 100 == 0 && blockState.get(LIT)) {
			Direction inRedstoneDirection = receivingRedstonePowerDirection(world, pos);
			if (inRedstoneDirection != null && isCoilComplete(world, pos, inRedstoneDirection.getOpposite())) {
				if (world instanceof ServerWorld serverWorld) {
					serverWorld.spawnParticles(
						ParticleTypes.SCULK_CHARGE_POP,
						pos.getX(), pos.getY() +1, pos.getZ(),
						1,
						0.2, 0.2, 0.2,
						0.1
					);
				}
			}
		}
	}

	public boolean isCoilComplete(World world, BlockPos startingPos, Direction direction){
		BlockPos runningPos = startingPos.offset(direction);
		Direction endDirection = checkPerpendicular(world, startingPos, direction);
		int edgeCount = 0;
		BlockState[] cornerList = new BlockState[3];
		ArrayList<Block> weakCoilList = new ArrayList<>();
		ArrayList<Block> strongCoilList = new ArrayList<>();
		if (endDirection != null) {
			endDirection = endDirection.getOpposite();
			Direction runningOffset = direction;
			for (int i = 0; i < 4; ++i) {
				int newEdgeCount = 0;
				Block coil = world.getBlockState(runningPos).getBlock();
				while (coil.equals(ModBlocks.MAGNETIC_COIL) || coil.equals(ModBlocks.WEAK_MAGNETIC_COIL) ||
					coil.equals(ModBlocks.STRONG_MAGNETIC_COIL)){
					newEdgeCount++;
					if (coil.equals(ModBlocks.WEAK_MAGNETIC_COIL)) weakCoilList.add(coil);
					if (coil.equals(ModBlocks.STRONG_MAGNETIC_COIL)) strongCoilList.add(coil);
					runningPos = runningPos.offset(runningOffset);
					coil = world.getBlockState(runningPos).getBlock();
				}

				if (i == 0){
					edgeCount = newEdgeCount;
				}else{
					if (newEdgeCount != edgeCount) return false;
				}
				if (i != 3){
					BlockState blockState = world.getBlockState(runningPos);
					if(blockState.getBlock() instanceof MagnetiteBlock && blockState.get(LIT)) return false;
					cornerList[i] = blockState;
				}

				if (i == 0) runningOffset = endDirection.getOpposite();
				if (i == 1) runningOffset = direction.getOpposite();
				if (i == 2) runningOffset = endDirection;
				if (i != 3) runningPos = runningPos.offset(runningOffset);
			}
		}
		if(runningPos.equals(startingPos)) {
			Vec3d fieldPos = new Vec3d(startingPos.getX(), startingPos.getY(), startingPos.getZ())
				.add(0.5, 0.25, 0.5)
				.relative(direction, (double) (edgeCount + 1) /2)
				.relative(endDirection.getOpposite(), (double) (edgeCount + 1) /2);
			Vec3i orthogonal = getOrthogonal(direction, endDirection.getOpposite());
			double d = orthogonal.getY() == 0 ? 1 : 0;
			float yaw = ((float)(MathHelper.atan2(orthogonal.getX(), orthogonal.getZ()) * (double)(180F / (float)Math.PI)));
			float pitch = ((float)(MathHelper.atan2(orthogonal.getY(), d) * (double)(180F / (float)Math.PI)));

			int lengthIncrease = 0;
			for (BlockState blockState : cornerList){
				Block block = blockState.getBlock();
				if (isCopperBlocks(block)) lengthIncrease += 2;
			}

			float xLength = orthogonal.getX() == 0 ? edgeCount : 1 + lengthIncrease;
			float yLength = orthogonal.getY() == 0 ? edgeCount : 1 + lengthIncrease;
			float zLength = orthogonal.getZ() == 0 ? edgeCount : 1 + lengthIncrease;

			int coilAmount = (edgeCount * 4);

			float weakCoilRatio = (float) weakCoilList.size() /coilAmount;
			float direction_multiplicator = ((1 - weakCoilRatio) * 0.4F) + 0.1F;

			float strongCoilRatio = (float) strongCoilList.size() /coilAmount;
			float centerStrength = strongCoilRatio * 0.2F;

			MagneticFieldEntity magneticFieldEntity = new MagneticFieldEntity(world, fieldPos, direction_multiplicator, yaw, pitch, 100, xLength, yLength, zLength, centerStrength, coilAmount);
			world.spawnEntity(magneticFieldEntity);
		}
		return runningPos.equals(startingPos);

	}

	public static boolean isCopperBlocks(Block block){
		return block.equals(Blocks.COPPER_BLOCK) || block.equals(Blocks.WAXED_COPPER_BLOCK) ||
			block.equals(Blocks.EXPOSED_COPPER) || block.equals(Blocks.WAXED_EXPOSED_COPPER) ||
			block.equals(Blocks.WEATHERED_COPPER) || block.equals(Blocks.WAXED_WEATHERED_COPPER) ||
			block.equals(Blocks.OXIDIZED_COPPER) || block.equals(Blocks.WAXED_OXIDIZED_COPPER) ||
			block.equals(Blocks.IRON_BLOCK) || block.equals(Blocks.NETHERITE_BLOCK);
	}

	public Vec3i getOrthogonal(Direction direction1, Direction direction2){
		Direction orthogonal = null;
		for (Direction direction : Direction.values()){
			if (!direction.getAxis().equals(direction1.getAxis()) && !direction.getAxis().equals(direction2.getAxis())){
				orthogonal = (direction1.getDirection().offset() * direction2.getDirection().offset() == direction.getDirection().offset()) ? direction : direction.getOpposite();
			}
		}
		return orthogonal.getVector();
	}

	public Direction checkPerpendicular(World world, BlockPos pos, Direction originalDirection){
		for (Direction direction : Direction.values()){
			if (direction != originalDirection && direction != originalDirection.getOpposite()){
				Block block = world.getBlockState(pos.offset(direction)).getBlock();
				if(block == ModBlocks.MAGNETIC_COIL || block == ModBlocks.WEAK_MAGNETIC_COIL || block == ModBlocks.STRONG_MAGNETIC_COIL) return direction;
			}
		}
		return null;
	}

	public Direction receivingRedstonePowerDirection(World world, BlockPos pos) {
		if (world.getEmittedRedstonePower(pos.down(), Direction.DOWN) > 0) {
			return Direction.DOWN;
		} else if (world.getEmittedRedstonePower(pos.up(), Direction.UP) > 0) {
			return Direction.UP;
		} else if (world.getEmittedRedstonePower(pos.north(), Direction.NORTH) > 0) {
			return Direction.NORTH;
		} else if (world.getEmittedRedstonePower(pos.south(), Direction.SOUTH) > 0) {
			return Direction.SOUTH;
		} else if (world.getEmittedRedstonePower(pos.west(), Direction.WEST) > 0) {
			return Direction.WEST;
		} else if (world.getEmittedRedstonePower(pos.east(), Direction.EAST) > 0) {
			return Direction.EAST;
		}else return null;
	}
}
