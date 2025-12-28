package io.github.velaliilunalii.coins_n_guns.block.block_entity;

import io.github.velaliilunalii.coins_n_guns.block.ModBlockEntities;
import io.github.velaliilunalii.coins_n_guns.entity.custom.MagneticFieldEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.List;

import static io.github.velaliilunalii.coins_n_guns.block.custom.PhaseBlock.POWERED;
import static io.github.velaliilunalii.coins_n_guns.entity.custom.MagneticFieldEntity.isMagneticAffected;

public class PhaseBlockEntity  extends BlockEntity implements BlockEntityTicker<PhaseBlockEntity> {
	public PhaseBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.PHASE_BLOCK_ENTITY, pos, state);
	}

	@Override
	public void tick(World world, BlockPos blockPos, BlockState blockState, PhaseBlockEntity blockEntity) {
		if (this.world == null) return;
		if (!this.world.isClient && this.world.getTime() % 20 == 0) {
			BlockPos startPos = pos.subtract(new Vec3i(100, 100, 100));
			BlockPos endPos = pos.add(new Vec3i(100, 100, 100));
			Box box = new Box(startPos, endPos);
			List<MagneticFieldEntity> entityList = world.getEntitiesByClass(
				MagneticFieldEntity.class,
				box,
				Entity -> isMagneticAffected(Entity, pos)
			);
			boolean powered = (Boolean)blockState.get(POWERED);
			if ((entityList.isEmpty() && powered) || (!entityList.isEmpty() && !powered))
				world.setBlockState(pos, (BlockState)blockState.cycle(POWERED), 2); world.updateNeighborsAlways(pos, blockState.getBlock());
		}
	}
}
