package io.github.velaliilunalii.coins_n_guns.item.custom;

import io.github.velaliilunalii.coins_n_guns.item.ModItems;
import io.github.velaliilunalii.coins_n_guns.sound.ModSounds;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CoinPouchItem extends Item {
	public static final int MAX_CAPACITY = 1728; //9x3 stacks

	public CoinPouchItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack pouch = user.getStackInHand(hand);
		if(user.isSneaking()){
			world.playSound((PlayerEntity)null, user.getX(), user.getY(), user.getZ(), ModSounds.COIN_SHUFFLE, SoundCategory.PLAYERS, 1.0F, 1F);
			rotateSelectedCoin(pouch);
		}else {
			world.playSound((PlayerEntity)null, user.getX(), user.getY(), user.getZ(), ModSounds.COIN_SHUFFLE, SoundCategory.PLAYERS, 1.0F, 0.8F);
			for (int i = 0; i < user.getInventory().size(); ++i) {
				ItemStack inventoryStack = user.getInventory().getStack(i);
				if (PistolItem.PISTOL_AMMO.test(inventoryStack)) {
					int to_decrement = inventoryStack.getCount();
					if (inventoryStack.isOf(ModItems.COPPER_COIN)) {
						to_decrement -= incrementCopperAmmo(pouch, inventoryStack.getCount());
					}
					if (inventoryStack.isOf(ModItems.IRON_COIN)) {
						to_decrement -= incrementIronAmmo(pouch, inventoryStack.getCount());
					}
					if (inventoryStack.isOf(ModItems.GOLD_COIN)) {
						to_decrement -= incrementGoldAmmo(pouch, inventoryStack.getCount());
					}
					inventoryStack.decrement(to_decrement);
				}
			}
		}
		return TypedActionResult.success(pouch);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		PlayerEntity player = context.getPlayer();
		Vec3d pos = player.getPos();
		world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), ModSounds.COIN_SHUFFLE, SoundCategory.PLAYERS, 1.0F, 1.1F);
		ItemStack pouch = context.getStack();

		int copperAmmo = getCopperAmmo(pouch);
		int ironAmmo = getIronAmmo(pouch);
		int goldAmmo = getGoldAmmo(pouch);

		if (copperAmmo > 0 && getSelectedCoin(pouch) == 1) {
			int remainingCopper = copperAmmo;
			while (remainingCopper > 0) {
				int stackSize = Math.min(remainingCopper, 64);
				player.dropItem(new ItemStack(ModItems.COPPER_COIN, stackSize), false);
				decrementCopperAmmo(pouch, stackSize);
				remainingCopper -= stackSize;
			}
		}

		if (ironAmmo > 0 && getSelectedCoin(pouch) == 2) {
			int remainingIron = ironAmmo;
			while (remainingIron > 0) {
				int stackSize = Math.min(remainingIron, 64);
				player.dropItem(new ItemStack(ModItems.IRON_COIN, stackSize), false);
				decrementIronAmmo(pouch, stackSize);
				remainingIron -= stackSize;
			}
		}

		if (goldAmmo > 0 && getSelectedCoin(pouch) == 3) {
			int remainingGold = goldAmmo;
			while (remainingGold > 0) {
				int stackSize = Math.min(remainingGold, 64);
				player.dropItem(new ItemStack(ModItems.GOLD_COIN, stackSize), false);
				decrementGoldAmmo(pouch, stackSize);
				remainingGold -= stackSize;
			}
		}

		return ActionResult.SUCCESS;
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		if (Screen.hasShiftDown()) {
			tooltip.add(Text.literal("Stores up to 27 stacks of coins").styled(style ->
				style.withColor(Formatting.GRAY)));
			if(getPouchTypeName(stack).equals("Crimson")){
				tooltip.add(Text.literal("Coins from this pouch ")
					.styled(style -> style.withColor(Formatting.GRAY))
					.append(Text.literal("deal +2 damage and burn")
						.styled(style -> style.withColor(pouchTypeToRGB(getPouchTypeName(stack))))));
			}
			if(getPouchTypeName(stack).equals("Ender")){
				tooltip.add(Text.literal("Coins from this pouch ")
					.styled(style -> style.withColor(Formatting.GRAY))
					.append(Text.literal("have no gravity,")
						.styled(style -> style.withColor(pouchTypeToRGB(getPouchTypeName(stack))))));
				tooltip.add(Text.literal("break blocks and brings back drops")
					.styled(style -> style.withColor(pouchTypeToRGB(getPouchTypeName(stack)))));
			}
			if(getPouchTypeName(stack).equals("Sculk")){
				tooltip.add(Text.literal("Coins from this pouch ")
					.styled(style -> style.withColor(Formatting.GRAY))
					.append(Text.literal("directs all other shot coins")
						.styled(style -> style.withColor(pouchTypeToRGB(getPouchTypeName(stack))))));
				tooltip.add(Text.literal("to the hit target")
					.styled(style -> style.withColor(pouchTypeToRGB(getPouchTypeName(stack)))));
			}
		} else if (Screen.hasControlDown()) {
			tooltip.add(Text.literal("Right click")
				.styled(style -> style.withColor(pouchTypeToRGB(getPouchTypeName(stack))))
				.append(Text.literal(" in the air to store ")
					.styled(style -> style.withColor(Formatting.GRAY)))
				.append(Text.literal("coins")
					.styled(style -> style.withColor(pouchTypeToRGB(getPouchTypeName(stack))))));
			tooltip.add(Text.literal("Right click")
				.styled(style -> style.withColor(pouchTypeToRGB(getPouchTypeName(stack))))
				.append(Text.literal(" facing a block to drop ")
					.styled(style -> style.withColor(Formatting.GRAY)))
				.append(Text.literal("coins")
					.styled(style -> style.withColor(pouchTypeToRGB(getPouchTypeName(stack))))));
			tooltip.add(Text.literal("Shift right click")
				.styled(style -> style.withColor(pouchTypeToRGB(getPouchTypeName(stack))))
				.append(Text.literal(" to change selected ")
				.styled(style -> style.withColor(Formatting.GRAY)))
				.append(Text.literal("coin")
					.styled(style -> style.withColor(pouchTypeToRGB(getPouchTypeName(stack))))));
		}else {
			String copper = String.valueOf(getCopperAmmo(stack));
			String iron = String.valueOf(getIronAmmo(stack));
			String gold = String.valueOf(getGoldAmmo(stack));
			tooltip.add(Text.literal("Copper coin count: ")
				.styled(style -> style.withColor(Formatting.GRAY))
				.append(Text.literal(copper)
					.styled(style -> style.withColor(0xe77c56))));
			tooltip.add(Text.literal("Iron coin count: ")
				.styled(style -> style.withColor(Formatting.GRAY))
				.append(Text.literal(iron)
					.styled(style -> style.withColor(0xececec))));
			tooltip.add(Text.literal("Gold coin count: ")
				.styled(style -> style.withColor(Formatting.GRAY))
				.append(Text.literal(gold)
					.styled(style -> style.withColor(0xfdf55f))));
			tooltip.add(Text.literal(""));
			tooltip.add(Text.literal("Press ")
				.styled(style -> style.withColor(Formatting.GRAY))
				.append(Text.literal("SHIFT")
					.styled(style -> style.withColor(pouchTypeToRGB(getPouchTypeName(stack)))))
				.append(Text.literal(" for pouch info"))
				.styled(style -> style.withColor(Formatting.GRAY)));
			tooltip.add(Text.literal("Press ")
				.styled(style -> style.withColor(Formatting.GRAY))
				.append(Text.literal("CTRL")
					.styled(style -> style.withColor(pouchTypeToRGB(getPouchTypeName(stack)))))
				.append(Text.literal(" for pouch use info"))
				.styled(style -> style.withColor(Formatting.GRAY)));
		}
	}

	public static int pouchTypeToRGB(String pouchType){
		if(pouchType.equals("Leather")){
			return 0xFF8C19;
		}
		if(pouchType.equals("Crimson")){
			return 0x990000;
		}
		if(pouchType.equals("Ender")){
			return 0x990099;
		}
		if(pouchType.equals("Sculk")){
			return 0x29dfeb;
		}
		return 0xFFFFFF;
	}

	public static int getSelectedCoin(ItemStack stack){
		NbtCompound nbtCompound = stack.getOrCreateNbt();
		int selected = nbtCompound.getInt("Selected_Coin");
		if (selected != 1 && selected != 2 && selected != 3){
			nbtCompound.putInt("Selected_Coin", 1);
			selected = 1;
		}
		return selected;
	}

	public static boolean isSelectedEmpty(ItemStack stack){
		boolean isSelectedEmpty = false;
		switch (getSelectedCoin(stack)){
			case 1: if (getCopperAmmo(stack)==0) return true; break;
			case 2: if (getIronAmmo(stack)==0) return true; break;
			case 3: if (getGoldAmmo(stack)==0) return true; break;
		}
		return isSelectedEmpty;
	}

	public static boolean isCopperEmpty(ItemStack stack){
		return getCopperAmmo(stack)==0;
	}
	public static boolean isIronEmpty(ItemStack stack){
		return getIronAmmo(stack)==0;
	}
	public static boolean isGoldEmpty(ItemStack stack){
		return getGoldAmmo(stack)==0;
	}

	public static void rotateSelectedCoin(ItemStack stack){
		NbtCompound nbtCompound = stack.getOrCreateNbt();
		int selected = getSelectedCoin(stack);
		nbtCompound.putInt("Selected_Coin", ((selected) % 3) + 1);
	}

	public static ItemStack decrementAndGetSelectedAmmo(ItemStack stack, int ammo, boolean should_consume){
		ItemStack projectile = ItemStack.EMPTY;
		int coin_type = getSelectedCoin(stack);
		if (coin_type == 1){
			if (should_consume) decrementCopperAmmo(stack, ammo);
			projectile = new ItemStack(ModItems.COPPER_COIN, ammo);
		}
		if (coin_type == 2){
			if (should_consume) decrementIronAmmo(stack, ammo);
			projectile = new ItemStack(ModItems.IRON_COIN, ammo);
		}
		if (coin_type == 3){
			if (should_consume) decrementGoldAmmo(stack, ammo);
			projectile = new ItemStack(ModItems.GOLD_COIN, ammo);
		}
		NbtCompound nbtCompound = projectile.getOrCreateNbt();
		nbtCompound.putString("Pouch_type", getPouchTypeName(stack));
		return projectile;
	}

	public static String getPouchTypeName(ItemStack pouch){
		if(pouch.isOf(ModItems.LEATHER_COIN_POUCH)){
			return "Leather";
		}
		if(pouch.isOf(ModItems.CRIMSON_COIN_POUCH)){
			return "Crimson";
		}
		if(pouch.isOf(ModItems.ENDER_COIN_POUCH)){
			return "Ender";
		}
		if(pouch.isOf(ModItems.SCULK_COIN_POUCH)){
			return "Sculk";
		}
		return " ";
	}

	public static String getPouchType(ItemStack stack) {
		NbtCompound nbtCompound = stack.getNbt();
		if (nbtCompound == null) return "";
		return nbtCompound.getString("Pouch_type");
	}

	public static int getTotalAmmo(ItemStack stack) {
		NbtCompound nbtCompound = stack.getNbt();
		if (nbtCompound == null) return 0;
		return nbtCompound.getInt("Copper_Ammo") + nbtCompound.getInt("Iron_Ammo") + nbtCompound.getInt("Gold_Ammo");
	}

	public static int getCopperAmmo(ItemStack stack) {
		NbtCompound nbtCompound = stack.getNbt();
		if (nbtCompound == null) return 0;
		return nbtCompound.getInt("Copper_Ammo");
	}

	public static void decrementCopperAmmo(ItemStack stack, int ammo) {
		NbtCompound nbtCompound = stack.getOrCreateNbt();
		nbtCompound.putInt("Copper_Ammo", Math.max(nbtCompound.getInt("Copper_Ammo") - ammo, 0));
	}

	public static int incrementCopperAmmo(ItemStack stack, int ammo) {
		NbtCompound nbtCompound = stack.getOrCreateNbt();
		int extra = Math.max(getTotalAmmo(stack) + ammo - MAX_CAPACITY, 0);
		nbtCompound.putInt("Copper_Ammo", getCopperAmmo(stack) + ammo - extra);
		return extra;
	}

	public static int getIronAmmo(ItemStack stack) {
		NbtCompound nbtCompound = stack.getNbt();
		if (nbtCompound == null) return 0;
		return nbtCompound.getInt("Iron_Ammo");
	}

	public static void decrementIronAmmo(ItemStack stack, int ammo) {
		NbtCompound nbtCompound = stack.getOrCreateNbt();
		nbtCompound.putInt("Iron_Ammo", Math.max(nbtCompound.getInt("Iron_Ammo") - ammo, 0));
	}

	public static int incrementIronAmmo(ItemStack stack, int ammo) {
		NbtCompound nbtCompound = stack.getOrCreateNbt();
		int extra = Math.max(getTotalAmmo(stack) + ammo - MAX_CAPACITY, 0);
		nbtCompound.putInt("Iron_Ammo", getIronAmmo(stack) + ammo - extra);
		return extra;
	}

	public static int getGoldAmmo(ItemStack stack) {
		NbtCompound nbtCompound = stack.getNbt();
		if (nbtCompound == null) return 0;
		return nbtCompound.getInt("Gold_Ammo");
	}

	public static void decrementGoldAmmo(ItemStack stack, int ammo) {
		NbtCompound nbtCompound = stack.getOrCreateNbt();
		nbtCompound.putInt("Gold_Ammo", Math.max(nbtCompound.getInt("Gold_Ammo") - ammo, 0));
	}

	public static int incrementGoldAmmo(ItemStack stack, int ammo) {
		NbtCompound nbtCompound = stack.getOrCreateNbt();
		int extra = Math.max(getTotalAmmo(stack) + ammo - MAX_CAPACITY, 0);
		nbtCompound.putInt("Gold_Ammo", getGoldAmmo(stack) + ammo - extra);
		return extra;
	}
}
