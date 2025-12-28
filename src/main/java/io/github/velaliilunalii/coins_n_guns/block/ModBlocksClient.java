package io.github.velaliilunalii.coins_n_guns.block;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import org.quiltmc.loader.api.ModContainer;

import static io.github.velaliilunalii.coins_n_guns.block.ModBlocks.*;

public class ModBlocksClient {
	public static void register(ModContainer mod) {
		BlockRenderLayerMap.INSTANCE.putBlock(COPPER_COIN_PILE, RenderLayer.getCutoutMipped());
		BlockRenderLayerMap.INSTANCE.putBlock(IRON_COIN_PILE, RenderLayer.getCutoutMipped());
		BlockRenderLayerMap.INSTANCE.putBlock(GOLD_COIN_PILE, RenderLayer.getCutoutMipped());
		BlockRenderLayerMap.INSTANCE.putBlock(PHASE_BLOCK, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(INVERTED_PHASE_BLOCK, RenderLayer.getTranslucent());
	}
}
