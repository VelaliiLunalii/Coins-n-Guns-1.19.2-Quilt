package io.github.velaliilunalii.coins_n_guns.entity.custom;

import io.github.velaliilunalii.coins_n_guns.entity.ModEntities;
import io.github.velaliilunalii.coins_n_guns.particle.ModParticles;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;

public class CopperCoinProjectileEntity extends ThrownEntity {
	public CopperCoinProjectileEntity(LivingEntity shooter, World world) {
		super(ModEntities.COPPER_COIN_PROJECTILE, shooter, world);
	}

	public float getSpeed(){return 3F;}
	@Override
	public float getGravity(){return 0.03F;}
	public float getDamage(){
		int crimson_pouch_bonus = getPouchType().equalsIgnoreCase("Crimson") ? 2 : 0;
		int multishot_copy_penalty = getMultishotCopy() ? -2 : 0;
		return 4F + crimson_pouch_bonus + multishot_copy_penalty;
	}

	public CopperCoinProjectileEntity(EntityType<CopperCoinProjectileEntity> entityType, World world) {
		super(entityType, world);
	}

	protected CopperCoinProjectileEntity(EntityType<? extends ThrownEntity> type, double x, double y, double z, World world) {
		super(type, x, y, z, world);
	}

	public void setVelocity(double x, double y, double z, float divergence) {
		Vec3d vec3d = (new Vec3d(x, y, z)).normalize().add(this.random.nextTriangular((double)0.0F,
			0.0172275 * (double)divergence), this.random.nextTriangular((double)0.0F,
			0.0172275 * (double)divergence), this.random.nextTriangular((double)0.0F,
			0.0172275 * (double)divergence)).multiply((double)this.getSpeed());
		this.setVelocity(vec3d);
		double d = vec3d.horizontalLength();
		this.setYaw((float)(MathHelper.atan2(vec3d.x, vec3d.z) * (double)(180F / (float)Math.PI)));
		this.setPitch((float)(MathHelper.atan2(vec3d.y, d) * (double)(180F / (float)Math.PI)));
		this.prevYaw = this.getYaw();
		this.prevPitch = this.getPitch();
	}

	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		Entity entity = entityHitResult.getEntity();
		Vec3d pos = entityHitResult.getPos();
		entity.timeUntilRegen = 0;
		entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), (float)this.getDamage());
		if (getPouchType().equalsIgnoreCase("Crimson")) entity.setOnFireFor(2);
		if(getPouchType().equalsIgnoreCase("Ender") && !world.isClient && getOwner() instanceof PlayerEntity player) {
			double radius = 1.0;
			Box box = new Box(
				pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius,
				pos.getX() + radius, pos.getY() + radius, pos.getZ() + radius
			);

			List<ItemEntity> nearbyItems = world.getEntitiesByClass(
				ItemEntity.class,
				box,
				itemEntity -> true
			);

			for (ItemEntity item : nearbyItems) {
				item.setPosition(player.getPos());
			}
		}
		if(getPouchType().equalsIgnoreCase("Sculk") && !world.isClient) {
			double radius = 1000.0;
			Box box = new Box(
				pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius,
				pos.getX() + radius, pos.getY() + radius, pos.getZ() + radius
			);

			List<CopperCoinProjectileEntity> coinProjectileEntityList = world.getEntitiesByClass(
				CopperCoinProjectileEntity.class,
				box,
				Entity -> !Entity.equals(this) && Entity.getOwner() == this.getOwner()
			);

			for (CopperCoinProjectileEntity coin : coinProjectileEntityList) {
				Vec3d directionVector = pos.subtract(coin.getPos()).normalize();
				coin.setVelocity(directionVector);
				coin.velocityModified = true;
				coin.setNoGravity(true);
			}
		}
	}

	@Override
	public void tick() {
		this.baseTick();

		HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
		boolean bl = false;
		if (hitResult.getType() == HitResult.Type.BLOCK) {
			BlockPos blockPos = ((BlockHitResult)hitResult).getBlockPos();
			BlockState blockState = this.world.getBlockState(blockPos);
			if (blockState.isOf(Blocks.NETHER_PORTAL)) {
				this.setInNetherPortal(blockPos);
				bl = true;
			} else if (blockState.isOf(Blocks.END_GATEWAY)) {
				BlockEntity blockEntity = this.world.getBlockEntity(blockPos);
				if (blockEntity instanceof EndGatewayBlockEntity && EndGatewayBlockEntity.canTeleport(this)) {
					EndGatewayBlockEntity.tryTeleportingEntity(this.world, blockPos, blockState, this, (EndGatewayBlockEntity)blockEntity);
				}

				bl = true;
			}
		}

		if (hitResult.getType() != HitResult.Type.MISS && !bl) {
			this.onCollision(hitResult);
		}

		this.checkBlockCollision();
		Vec3d vec3d = this.getVelocity();
		double d = this.getX() + vec3d.x;
		double e = this.getY() + vec3d.y;
		double f = this.getZ() + vec3d.z;
		this.updateRotation();
		float h;
		if (this.isTouchingWater()) {
			for(int i = 0; i < 4; ++i) {
				this.world.addParticle(ParticleTypes.BUBBLE, d - vec3d.x * (double)0.25F, e - vec3d.y * (double)0.25F, f - vec3d.z * (double)0.25F, vec3d.x, vec3d.y, vec3d.z);
			}

			h = getPouchType().equalsIgnoreCase("Ender") ? 1 : 0.8F;
		} else {
			h = getPouchType().equalsIgnoreCase("Ender") ? 1 : 0.99F;
		}

		this.setVelocity(vec3d.multiply((double)h));
		if (!this.hasNoGravity()) {
			Vec3d vec3d2 = this.getVelocity();
			this.setVelocity(vec3d2.x, vec3d2.y - (double)this.getGravity(), vec3d2.z);
		}

		this.setPosition(d, e, f);

		if (this.age % 20 == 0 || this.age == 2) {
			if(getPouchType().equalsIgnoreCase("Crimson")) {
				if(world instanceof ServerWorld serverWorld) {
					serverWorld.spawnParticles(
						ParticleTypes.SMALL_FLAME,
						this.getX(), this.getY(), this.getZ(),
						1,
						0.2, 0.2, 0.2,
						0.1
					);
				}
			}
			if(getPouchType().equalsIgnoreCase("Sculk")) {
				if(world instanceof ServerWorld serverWorld) {
					serverWorld.spawnParticles(
						ParticleTypes.SCULK_CHARGE_POP,
						this.getX(), this.getY(), this.getZ(),
						1,
						0.2, 0.2, 0.2,
						0.1
					);
				}
			}
			if(getPouchType().equalsIgnoreCase("Ender")) {
				if(world instanceof ServerWorld serverWorld) {
					serverWorld.spawnParticles(
						ParticleTypes.REVERSE_PORTAL,
						this.getX(), this.getY(), this.getZ(),
						1,
						0.2, 0.2, 0.2,
						0.1
					);
				}
			}
		}
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		super.onBlockHit(blockHitResult);
		BlockPos blockPos = blockHitResult.getBlockPos();
		BlockState blockState = this.world.getBlockState(blockPos);
		if(world instanceof ServerWorld serverWorld && getPouchType().equalsIgnoreCase("Ender")
			&& getOwner() instanceof PlayerEntity player){
			BlockEntity blockEntity = blockState.hasBlockEntity() ? world.getBlockEntity(blockPos) : null;
			if(blockState.getHardness(serverWorld, blockPos) < 20) {
				List<ItemStack> droppedStacks = Block.getDroppedStacks(blockState, serverWorld, blockPos, blockEntity, getOwner(), ItemStack.EMPTY);
				for (ItemStack itemStack : droppedStacks) {
					player.giveItemStack(itemStack);
				}
				world.breakBlock(blockPos, false);
			}
		}
	}

	@Override
	protected void onCollision(HitResult hitResult) {
		HitResult.Type type = hitResult.getType();
		if (type == HitResult.Type.ENTITY) {
			this.onEntityHit((EntityHitResult)hitResult);
			if(getPierce() >= max_pierce) {
				this.world.emitGameEvent(GameEvent.PROJECTILE_LAND, hitResult.getPos(), GameEvent.Context.create(this, (BlockState) null));
			}
		} else if (type == HitResult.Type.BLOCK) {
			BlockHitResult blockHitResult = (BlockHitResult)hitResult;
			this.onBlockHit(blockHitResult);
			BlockPos blockPos = blockHitResult.getBlockPos();
			this.world.emitGameEvent(GameEvent.PROJECTILE_LAND, blockPos, GameEvent.Context.create(this, this.world.getBlockState(blockPos)));
		}
		if (!this.world.isClient) {
			if ((type == HitResult.Type.ENTITY && getPierce() >= max_pierce) || (type == HitResult.Type.BLOCK)) {
				this.world.sendEntityStatus(this, (byte) 3);
				this.discard();
			}else if(type == HitResult.Type.ENTITY){
				setPierce(getPierce()+1);
			}
		}
		if(world instanceof ServerWorld serverWorld){
			Vec3d hit_pos = this.getPos();
			serverWorld.spawnParticles(ModParticles.COPPER_COIN_IMPACT_PARTICLE, hit_pos.x, hit_pos.y, hit_pos.z, 1, 0, 0, 0, 0);
		}
	}

	private int max_pierce = 0;

	public void setPierceLevel(int level) {
		max_pierce = level;
	}

	private static final TrackedData<Boolean> MULTISHOT_COPY = DataTracker.registerData(CopperCoinProjectileEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	private static final TrackedData<Integer> PIERCE = DataTracker.registerData(CopperCoinProjectileEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private static final TrackedData<String> POUCH_TYPE = DataTracker.registerData(CopperCoinProjectileEntity.class, TrackedDataHandlerRegistry.STRING);
	@Override
	protected void initDataTracker() {
		this.dataTracker.startTracking(MULTISHOT_COPY, false);
		this.dataTracker.startTracking(PIERCE, 0);
		this.dataTracker.startTracking(POUCH_TYPE, "");
	}
	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putBoolean("Multishot_copy", this.dataTracker.get(MULTISHOT_COPY));
		nbt.putInt("Pierce", this.dataTracker.get(PIERCE));
		nbt.putString("Pouch_type", this.dataTracker.get(POUCH_TYPE));

	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.dataTracker.set(MULTISHOT_COPY, nbt.getBoolean("Multishot_copy"));
		this.dataTracker.set(PIERCE, nbt.getInt("Pierce"));
		this.dataTracker.set(POUCH_TYPE, nbt.getString("Pouch_type"));
	}

	public boolean getMultishotCopy() {return this.dataTracker.get(MULTISHOT_COPY);}

	public void setMultishotCopy(boolean multishotCopy) {this.dataTracker.set(MULTISHOT_COPY, multishotCopy);}

	public int getPierce() {
		return this.dataTracker.get(PIERCE);
	}

	public void setPierce(int pierce) {
		this.dataTracker.set(PIERCE, pierce);
	}

	public String getPouchType() {
		return this.dataTracker.get(POUCH_TYPE);
	}

	public void setPouchType(String pouchType) {
		this.dataTracker.set(POUCH_TYPE, pouchType);
	}
}
