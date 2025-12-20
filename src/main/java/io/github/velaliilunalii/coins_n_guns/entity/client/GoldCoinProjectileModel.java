package io.github.velaliilunalii.coins_n_guns.entity.client;

import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.velaliilunalii.coins_n_guns.CoinsNGuns;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

// Made with Blockbench 5.0.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

public class GoldCoinProjectileModel extends EntityModel<Entity> {
	public static final Identifier TEXTURE = new Identifier(CoinsNGuns.MOD_ID, "textures/entity/coin_projectile/gold_coin_projectile.png");
	public static final EntityModelLayer COIN = new EntityModelLayer(TEXTURE, "main");
	private final ModelPart bone;

	public GoldCoinProjectileModel(ModelPart root) {
		this.bone = root.getChild("bone");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bone = modelPartData.addChild("bone", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, -2.5F, -1.5F, 1.0F, 2.0F, 3.0F, new Dilation(0.0F))
			.uv(0, 5).cuboid(-0.5F, 0.5F, -1.5F, 1.0F, 2.0F, 3.0F, new Dilation(0.0F))
			.uv(8, 5).cuboid(-0.5F, -1.5F, 0.5F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
			.uv(8, 0).cuboid(-0.5F, -1.5F, -2.5F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}
	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		bone.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}
