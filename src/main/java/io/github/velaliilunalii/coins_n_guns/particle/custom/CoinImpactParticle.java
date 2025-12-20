package io.github.velaliilunalii.coins_n_guns.particle.custom;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

public class CoinImpactParticle extends SpriteBillboardParticle {
	private boolean reachedGround;
	private final SpriteProvider spriteProvider;

	CoinImpactParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
		super(world, x, y, z);
		this.velocityMultiplier = 0.96F;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.velocityZ = velocityZ;
		this.colorRed = 1;
		this.colorGreen = 1;
		this.colorBlue = 1;
		this.scale *= 3F;
		this.maxAge = 10;//(int)((double)20.0F / ((double)this.random.nextFloat() * 0.8 + 0.2));
		this.reachedGround = false;
		this.collidesWithWorld = false;
		this.spriteProvider = spriteProvider;
		this.setSpriteForAge(spriteProvider);
	}

	public void tick() {
		this.prevPosX = this.x;
		this.prevPosY = this.y;
		this.prevPosZ = this.z;
		if (this.age++ >= this.maxAge) {
			this.markDead();
		} else {
			this.setSpriteForAge(this.spriteProvider);
			if (this.onGround) {
				this.velocityY = (double)0.0F;
				this.reachedGround = true;
			}

			if (this.reachedGround) {
				this.velocityY += 0.002;
			}

			this.move(this.velocityX, this.velocityY, this.velocityZ);
			if (this.y == this.prevPosY) {
				this.velocityX *= 1.1;
				this.velocityZ *= 1.1;
			}

			this.velocityX *= (double)this.velocityMultiplier;
			this.velocityZ *= (double)this.velocityMultiplier;
			if (this.reachedGround) {
				this.velocityY *= (double)this.velocityMultiplier;
			}

		}
	}

	public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
		Vec3d vec3d = camera.getPos();
		Vec3d directionVector = Vec3d.fromPolar(camera.getPitch(), camera.getYaw()).multiply(0.2);
		float f = (float)(MathHelper.lerp((double)tickDelta, this.prevPosX, this.x) - vec3d.getX() - directionVector.x);
		float g = (float)(MathHelper.lerp((double)tickDelta, this.prevPosY, this.y) - vec3d.getY() - directionVector.y);
		float h = (float)(MathHelper.lerp((double)tickDelta, this.prevPosZ, this.z) - vec3d.getZ() - directionVector.z);
		Quaternion quaternion;
		if (this.angle == 0.0F) {
			quaternion = camera.getRotation();
		} else {
			quaternion = new Quaternion(camera.getRotation());
			float i = MathHelper.lerp(tickDelta, this.prevAngle, this.angle);
			quaternion.hamiltonProduct(Vec3f.POSITIVE_Z.getRadialQuaternion(i));
		}

		Vec3f vec3f = new Vec3f(-1.0F, -1.0F, 0.0F);
		vec3f.rotate(quaternion);
		Vec3f[] vec3fs = new Vec3f[]{new Vec3f(-1.0F, -1.0F, 0.0F), new Vec3f(-1.0F, 1.0F, 0.0F), new Vec3f(1.0F, 1.0F, 0.0F), new Vec3f(1.0F, -1.0F, 0.0F)};
		float j = this.getSize(tickDelta);

		for(int k = 0; k < 4; ++k) {
			Vec3f vec3f2 = vec3fs[k];
			vec3f2.rotate(quaternion);
			vec3f2.scale(j);
			vec3f2.add(f, g, h);
		}

		float l = this.getMinU();
		float m = this.getMaxU();
		float n = this.getMinV();
		float o = this.getMaxV();
		int p = this.getBrightness(tickDelta);
		vertexConsumer.vertex((double)vec3fs[0].getX(), (double)vec3fs[0].getY(), (double)vec3fs[0].getZ()).uv(m, o).color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha).light(p).next();
		vertexConsumer.vertex((double)vec3fs[1].getX(), (double)vec3fs[1].getY(), (double)vec3fs[1].getZ()).uv(m, n).color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha).light(p).next();
		vertexConsumer.vertex((double)vec3fs[2].getX(), (double)vec3fs[2].getY(), (double)vec3fs[2].getZ()).uv(l, n).color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha).light(p).next();
		vertexConsumer.vertex((double)vec3fs[3].getX(), (double)vec3fs[3].getY(), (double)vec3fs[3].getZ()).uv(l, o).color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha).light(p).next();
	}

	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
	}

	public float getSize(float tickDelta) {
		return this.scale * MathHelper.clamp(((float)this.age + tickDelta) / (float)this.maxAge * 32.0F, 0.0F, 1.0F);
	}

	@Environment(EnvType.CLIENT)
	public static class Factory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public Factory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			return new CoinImpactParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
		}
	}
}
