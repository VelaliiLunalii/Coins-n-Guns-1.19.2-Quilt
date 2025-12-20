package io.github.velaliilunalii.coins_n_guns.entity.client;

import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.velaliilunalii.coins_n_guns.entity.custom.GoldCoinProjectileEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

public class GoldCoinProjectileRenderer extends EntityRenderer<GoldCoinProjectileEntity> {
	protected GoldCoinProjectileModel model;

	public GoldCoinProjectileRenderer(EntityRendererFactory.Context ctx) {
		super(ctx);
		this.model = new GoldCoinProjectileModel(ctx.getPart(GoldCoinProjectileModel.COIN));
	}

	@Override
	public Identifier getTexture(GoldCoinProjectileEntity entity) {
		return GoldCoinProjectileModel.TEXTURE;
	}

	@Override
	public void render(GoldCoinProjectileEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		matrices.push();

		matrices.translate(0.0D, 0.0D, 0.0D);

		float f = MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw());
		float g = MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch());

		matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(f - 90.0F));

		VertexConsumer vertexconsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(GoldCoinProjectileModel.TEXTURE));
		this.model.render(matrices, vertexconsumer, light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);

		matrices.pop();
		super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
	}
}
