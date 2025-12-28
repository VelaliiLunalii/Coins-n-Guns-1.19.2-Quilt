package io.github.velaliilunalii.coins_n_guns.entity.custom;

import io.github.velaliilunalii.coins_n_guns.entity.ModEntities;
import io.github.velaliilunalii.coins_n_guns.item.ModItems;
import io.github.velaliilunalii.coins_n_guns.particle.ModParticles;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class MagneticFieldEntity extends Entity {
	private int maxAge = 100;
	private float direction_multiplicator = 0.5F;
	private float xLength;
	private float yLength;
	private float zLength;
	private float centerStrength = 0;
	@Nullable
	private Entity owner;
	private int coilAmount = 0;

	public float getDirectionMultiplicator(){return direction_multiplicator;}
	public float getCenterStrength(){return centerStrength;}

	public MagneticFieldEntity(EntityType<?> type, World world) {
		super(type, world);
		this.noClip = true;
	}

	public MagneticFieldEntity(World world, Vec3d pos, float direction_multiplicator, float yaw, float pitch, int maxAge, float xLength, float yLength, float zLength, int coilAmount) {
		this(ModEntities.MAGNETIC_FIELD, world);
		this.setPosition(pos);
		this.direction_multiplicator = direction_multiplicator;
		this.setYaw(yaw);
		this.setPitch(pitch);
		this.maxAge = maxAge;
		this.xLength = xLength;
		this.yLength = yLength;
		this.zLength = zLength;
		this.coilAmount = coilAmount;
	}

	@Override
	public boolean doesRenderOnFire() {
		return false;
	}

	public MagneticFieldEntity(World world, Vec3d pos, float direction_multiplicator, float yaw, float pitch, int maxAge, float xLength, float yLength, float zLength, float centerStrength, int coilAmount) {
		this(world, pos, direction_multiplicator, yaw, pitch, maxAge, xLength, yLength, zLength, coilAmount);
		this.centerStrength = centerStrength;
	}

	public MagneticFieldEntity(World world, LivingEntity shooter, Vec3d pos, float direction_multiplicator, float yaw, float pitch, int maxAge, float xLength, float yLength, float zLength) {
		this(world, pos, direction_multiplicator, yaw, pitch, maxAge, xLength, yLength, zLength, 0);
		this.setOwner(shooter);
	}

	public void setOwner(@Nullable Entity entity) {
		if (entity != null) {
			this.owner = entity;
		}
	}

	public Box getEffectBox(){
		Vec3d startPos = this.getPos().subtract(xLength/2, yLength/2, zLength/2);
		Vec3d endPos = this.getPos().add(xLength/2, yLength/2, zLength/2);
		return new Box(startPos, endPos);
	}

	public static boolean isMagneticAffected(MagneticFieldEntity magneticField, BlockPos pos){
		return magneticField.getEffectBox().contains(Vec3d.of(pos).add(0.5, 0.5, 0.5));
	}

	public Vec3d getDirection(){
		return Vec3d.fromPolar(this.getPitch(), this.getYaw());
	}

	public float directionLength(){
		Vec3d direction = getDirection();
		if (Math.abs(direction.x) == 1) return xLength;
		if (Math.abs(direction.y) == 1) return yLength;
		return zLength;
	}

	@Override
	public void tick() {
		super.tick();
		Vec3d direction = getDirection();

		//TODO refactor to add shield and gun?
		if(world instanceof ServerWorld serverWorld){
			if(coilAmount != 0 && age % (80/coilAmount) == 0) {
				Vec3d startPos = getPos().subtract(direction.multiply(xLength / 2, yLength / 2, zLength / 2)).add(0, 0.25, 0);
				Random random = new Random();
				double x = Math.abs(direction.x) != 1 ? (random.nextFloat() - 0.5) * xLength : 0;
				double y = Math.abs(direction.y) != 1 ? (random.nextFloat() - 0.5) * yLength : 0;
				double z = Math.abs(direction.z) != 1 ? (random.nextFloat() - 0.5) * zLength : 0;
				Vec3d randomStartPos = startPos.add(x, y, z);
				double length = Math.abs(direction.x * xLength) + Math.abs(direction.y * yLength) + Math.abs(direction.z * zLength);
				serverWorld.spawnParticles(ModParticles.MAGNETIC_FIELD_PARTICLE,
					randomStartPos.x, randomStartPos.y, randomStartPos.z, 0,
					(length * direction.x) / 30, (length * direction.y) / 30, (length * direction.z) / 30, 1);
			}
		}

		if (this.age >= maxAge) this.kill();
		Box box = getEffectBox();
		List<Entity> entityList = world.getEntitiesByClass(
			Entity.class,
			box,
			this::isCorrectEntity
		);
		for (Entity entity : entityList) {
			Vec3d entityToField = getPos().subtract(entity.getPos()).normalize();
			Vec3d centerPull = entityToField.multiply(centerStrength);

			float strengh = direction_multiplicator;
			if (entity instanceof ProjectileEntity || entity instanceof AbstractMinecartEntity)
				strengh *= 2;
			double speed = entity.getVelocity().dotProduct(new Vec3d(1, 1, 1));
			if (speed < 12) entity.setVelocity(entity.getVelocity().add(direction.multiply(strengh)).add(centerPull));
			if (entity instanceof CopperCoinProjectileEntity coin) {
				coin.setGravity(coin.getGravity() / 2);
				coin.setMagneticFieldIncrease(true);
			}
			if (entity instanceof IronCoinProjectileEntity coin) {
				coin.setGravity(coin.getGravity() / 2);
				coin.setMagneticFieldIncrease(true);
			}
			if (entity instanceof GoldCoinProjectileEntity coin) {
				coin.setGravity(coin.getGravity()/2);
				coin.setMagneticFieldIncrease(true);
			}
			entity.velocityModified = true;
			entity.velocityDirty = true;
		}
	}

	public boolean isCorrectEntity(Entity entity){
		if (entity == this.owner || ((owner != null && owner.hasVehicle() && owner.getVehicle() == entity))) return false;
		for (ItemStack itemStack : entity.getArmorItems()) {
			if (itemStack.getItem().equals(ModItems.MAGNETIC_BOOTS)) return false;
		}
		if (entity instanceof ProjectileEntity || entity instanceof ItemEntity || entity instanceof AbstractMinecartEntity)
			return true;
		for (ItemStack itemStack : entity.getArmorItems()){
			if (itemStack != null && itemStack.getItem() instanceof ArmorItem armorItem &&
				(armorItem.getMaterial() == ArmorMaterials.IRON || armorItem.getMaterial() == ArmorMaterials.NETHERITE))
				return true;
		}
		return  false;
	}

	public static float getYawFromDirection(Direction direction) {
		return switch (direction) {
			case NORTH -> 180.0f;
			case SOUTH -> 0.0f;
			case WEST -> 90.0f;
			case EAST -> 270.0f;
			case UP, DOWN -> 0.0f;
		};
	}

	public static float getPitchFromDirection(Direction direction) {
		return switch (direction) {
			case UP -> -90.0f;
			case DOWN -> 90.0f;
			case NORTH, SOUTH, EAST, WEST -> 0.0f;
		};
	}

	public static Direction vec3dToDirection(Vec3d vec){
		if (Math.abs(vec.x) > Math.abs(vec.y) && Math.abs(vec.x) > Math.abs(vec.z)) {
			return vec.x > 0 ? Direction.EAST : Direction.WEST;
		}
		if (Math.abs(vec.y) > Math.abs(vec.z)) {
			return vec.y > 0 ? Direction.UP : Direction.DOWN;
		}
		return vec.z > 0 ? Direction.SOUTH : Direction.NORTH;
	}

	@Override
	public boolean collides() {
		return false;
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	protected void initDataTracker() {

	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) {

	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt) {

	}

	@Override
	public Packet<?> createSpawnPacket() {
		return new EntitySpawnS2CPacket(this);
	}
}
