package io.github.velaliilunalii.coins_n_guns.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.velaliilunalii.coins_n_guns.CoinsNGuns;
import io.github.velaliilunalii.coins_n_guns.item.ModItems;
import io.github.velaliilunalii.coins_n_guns.item.custom.CoinPouchItem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class CoinSelectionGUI implements HudRenderCallback {
	private static final Identifier COPPER_COIN_SELECTED = new Identifier(CoinsNGuns.MOD_ID,
		"textures/gui/copper_coin_selected.png");
	private static final Identifier COPPER_COIN_SELECTED_EMPTY = new Identifier(CoinsNGuns.MOD_ID,
		"textures/gui/copper_coin_selected_empty.png");
	private static final Identifier COPPER_COIN = new Identifier(CoinsNGuns.MOD_ID,
		"textures/gui/copper_coin.png");
	private static final Identifier COPPER_COIN_EMPTY = new Identifier(CoinsNGuns.MOD_ID,
		"textures/gui/copper_coin_empty.png");
	private static final Identifier IRON_COIN_SELECTED = new Identifier(CoinsNGuns.MOD_ID,
		"textures/gui/iron_coin_selected.png");
	private static final Identifier IRON_COIN_SELECTED_EMPTY = new Identifier(CoinsNGuns.MOD_ID,
		"textures/gui/iron_coin_selected_empty.png");
	private static final Identifier IRON_COIN = new Identifier(CoinsNGuns.MOD_ID,
		"textures/gui/iron_coin.png");
	private static final Identifier IRON_COIN_EMPTY = new Identifier(CoinsNGuns.MOD_ID,
		"textures/gui/iron_coin_empty.png");
	private static final Identifier GOLD_COIN_SELECTED = new Identifier(CoinsNGuns.MOD_ID,
		"textures/gui/gold_coin_selected.png");
	private static final Identifier GOLD_COIN_SELECTED_EMPTY = new Identifier(CoinsNGuns.MOD_ID,
		"textures/gui/gold_coin_selected_empty.png");
	private static final Identifier GOLD_COIN = new Identifier(CoinsNGuns.MOD_ID,
		"textures/gui/gold_coin.png");
	private static final Identifier GOLD_COIN_EMPTY = new Identifier(CoinsNGuns.MOD_ID,
		"textures/gui/gold_coin_empty.png");

	private static ItemStack getCoinPouchStack(PlayerEntity player){
		for (int i = 0; i < player.getInventory().size(); ++i) {
			ItemStack inventoryStack = player.getInventory().getStack(i);
			if (inventoryStack.getItem() instanceof CoinPouchItem) {
				return inventoryStack;
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public void onHudRender(MatrixStack matrixStack, float tickDelta) {
		int x = 0;
		int y = 0;
		MinecraftClient client = MinecraftClient.getInstance();
		if (client != null) {
			PlayerEntity player = MinecraftClient.getInstance().player;
			ItemStack stack = getCoinPouchStack(player);
			if((player.getMainHandStack().isOf(ModItems.GILDED_PISTOL) || player.getMainHandStack().getItem() instanceof CoinPouchItem
				|| player.getOffHandStack().isOf(ModItems.GILDED_PISTOL) || player.getOffHandStack().getItem() instanceof CoinPouchItem)
				&& !stack.isEmpty()) {
				int width = client.getWindow().getScaledWidth();
				int height = client.getWindow().getScaledHeight();
				x = width / 2;
				y = height / 2;
				RenderSystem.setShader(GameRenderer::getPositionTexShader);
				RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

				int selectedCoin = CoinPouchItem.getSelectedCoin(stack);
				boolean copperEmpty = CoinPouchItem.isCopperEmpty(stack);
				boolean ironEmpty = CoinPouchItem.isIronEmpty(stack);
				boolean goldEmpty = CoinPouchItem.isGoldEmpty(stack);

				int copperOffset;int ironOffset;int goldOffset;
				int left = -20; int center = -8; int right = 4;

				if (selectedCoin == 1) {
					goldOffset = left;
					copperOffset = center;
					ironOffset = right;
					RenderSystem.setShaderTexture(0, copperEmpty? COPPER_COIN_SELECTED_EMPTY : COPPER_COIN_SELECTED);
					DrawableHelper.drawTexture(matrixStack, x + copperOffset, y + 12, 0, 0, 16, 16, 16, 16);
					RenderSystem.setShaderTexture(0, ironEmpty? IRON_COIN_EMPTY : IRON_COIN);
					DrawableHelper.drawTexture(matrixStack, x + ironOffset, y + 12, 0, 0, 16, 16, 16, 16);
					RenderSystem.setShaderTexture(0, goldEmpty? GOLD_COIN_EMPTY : GOLD_COIN);
					DrawableHelper.drawTexture(matrixStack, x + goldOffset, y + 12, 0, 0, 16, 16, 16, 16);
				} else if (selectedCoin == 2) {
					copperOffset = left;
					ironOffset = center;
					goldOffset = right;
					RenderSystem.setShaderTexture(0, copperEmpty? COPPER_COIN_EMPTY : COPPER_COIN);
					DrawableHelper.drawTexture(matrixStack, x + copperOffset, y + 12, 0, 0, 16, 16, 16, 16);
					RenderSystem.setShaderTexture(0, ironEmpty? IRON_COIN_SELECTED_EMPTY : IRON_COIN_SELECTED);
					DrawableHelper.drawTexture(matrixStack, x + ironOffset, y + 12, 0, 0, 16, 16, 16, 16);
					RenderSystem.setShaderTexture(0, goldEmpty? GOLD_COIN_EMPTY : GOLD_COIN);
					DrawableHelper.drawTexture(matrixStack, x + goldOffset, y + 12, 0, 0, 16, 16, 16, 16);
				} else if (selectedCoin == 3) {
					ironOffset = left;
					goldOffset = center;
					copperOffset = right;
					RenderSystem.setShaderTexture(0, copperEmpty? COPPER_COIN_EMPTY : COPPER_COIN);
					DrawableHelper.drawTexture(matrixStack, x + copperOffset, y + 12, 0, 0, 16, 16, 16, 16);
					RenderSystem.setShaderTexture(0, ironEmpty? IRON_COIN_EMPTY : IRON_COIN);
					DrawableHelper.drawTexture(matrixStack, x + ironOffset, y + 12, 0, 0, 16, 16, 16, 16);
					RenderSystem.setShaderTexture(0, goldEmpty? GOLD_COIN_SELECTED_EMPTY : GOLD_COIN_SELECTED);
					DrawableHelper.drawTexture(matrixStack, x + goldOffset, y + 12, 0, 0, 16, 16, 16, 16);
				}
			}
		}
	}
}
