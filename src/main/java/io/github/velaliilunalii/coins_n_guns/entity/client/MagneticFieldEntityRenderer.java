package io.github.velaliilunalii.coins_n_guns.entity.client;

import io.github.velaliilunalii.coins_n_guns.entity.custom.MagneticFieldEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class MagneticFieldEntityRenderer extends EntityRenderer<MagneticFieldEntity> {

	public MagneticFieldEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx);
	}

	@Override
	public Identifier getTexture(MagneticFieldEntity entity) {
		return null; // Pas de texture
	}

	@Override
	public void render(MagneticFieldEntity entity, float yaw, float tickDelta, MatrixStack matrices,
					   VertexConsumerProvider vertexConsumers, int light) {
		// Ne rien render - entité invisible

		// OPTIONNEL : Render des particules pour debug/visuel
		if (MinecraftClient.getInstance().getEntityRenderDispatcher().shouldRenderHitboxes()) {
			// Afficher la hitbox en mode debug (F3+B)
			super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
		}
	}
}
