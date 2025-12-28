package io.github.velaliilunalii.coins_n_guns.block.block_entity;

import io.github.velaliilunalii.coins_n_guns.block.ModBlockEntities;
import io.github.velaliilunalii.coins_n_guns.entity.custom.MagneticFieldEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.List;

import static io.github.velaliilunalii.coins_n_guns.block.custom.PhaseBlock.POWERED;
import static io.github.velaliilunalii.coins_n_guns.block.custom.ResonatorBlock.FACING;
import static io.github.velaliilunalii.coins_n_guns.entity.custom.MagneticFieldEntity.*;

public class ResonatorBlockEntity extends BlockEntity implements BlockEntityTicker<ResonatorBlockEntity> {
	public ResonatorBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.RESONATOR_BLOCK_ENTITY, pos, state);
	}

	@Override
	public void tick(World world, BlockPos blockPos, BlockState blockState, ResonatorBlockEntity blockEntity) {
		if (this.world == null) return;
		if (!this.world.isClient && this.world.getTime() % 100 == 10) {
			BlockPos startPos = pos.subtract(new Vec3i(100, 100, 100));
			BlockPos endPos = pos.add(new Vec3i(100, 100, 100));
			Box box = new Box(startPos, endPos);
			List<MagneticFieldEntity> entityList = world.getEntitiesByClass(
				MagneticFieldEntity.class,
				box,
				Entity -> isMagneticAffected(Entity, pos)
			);
			if (!entityList.isEmpty()){
				MagneticFieldEntity closest = entityList.get(0);
				for (MagneticFieldEntity entity : entityList){
					if (entity.getPos().distanceTo(Vec3d.of(pos)) < closest.getPos().distanceTo(Vec3d.of(pos))) closest = entity;
				}

				Direction direction = blockState.get(FACING);

				Direction originalFieldDirection = vec3dToDirection(closest.getDirection());
				Direction finalDirection = direction == originalFieldDirection.getOpposite()
					? direction
					: direction.getOpposite();
				if (originalFieldDirection.getAxis() == direction.getAxis()) finalDirection = finalDirection.getOpposite();
				float yaw = getYawFromDirection(finalDirection);
				float pitch = getPitchFromDirection(finalDirection);

				float directionLength = (closest.directionLength() + 1) * 4;
				float xLength = finalDirection.getAxis() == Direction.Axis.X ? directionLength : 1;
				float yLength = finalDirection.getAxis() == Direction.Axis.Y ? directionLength : 1;
				float zLength = finalDirection.getAxis() == Direction.Axis.Z ? directionLength : 1;

				Vec3d fieldPos = new Vec3d(pos.getX(), pos.getY(), pos.getZ())
					.add(0.5, 0.25, 0.5)
					.relative(direction, (directionLength/2)+0.5);

				float direction_multiplicator = closest.getDirectionMultiplicator();

				float centerStrength = closest.getCenterStrength();
				MagneticFieldEntity magneticFieldEntity = new MagneticFieldEntity(world, fieldPos, direction_multiplicator, yaw, pitch, 100, xLength, yLength, zLength, centerStrength, 4);
				world.spawnEntity(magneticFieldEntity);
			}
		}
	}
}
