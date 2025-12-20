package io.github.velaliilunalii.coins_n_guns.item.custom;

import io.github.velaliilunalii.coins_n_guns.item.ModItems;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PhilosophersStoneItem extends Item {
	public PhilosophersStoneItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack other_stack = hand == Hand.MAIN_HAND ? user.getOffHandStack() : user.getMainHandStack();
		if (other_stack.getItem() instanceof CoinItem) {
			int lost_amount = 0;
			int gained_amount = 0;
			boolean success = false;
			if (other_stack.isOf(ModItems.COPPER_COIN) && other_stack.getCount()>=4) {
				lost_amount = 4;
				gained_amount = 1;
				success = true;
			}else if (other_stack.isOf(ModItems.IRON_COIN)) {
				lost_amount = 1;
				gained_amount = 1;
				success = true;
			}else if (other_stack.isOf(ModItems.GOLD_COIN)){
				lost_amount = 1;
				gained_amount = 4;
				success = true;
			}

			if(success) {
				if (!user.getAbilities().creativeMode) {
					other_stack.decrement(lost_amount);
				}
				user.giveItemStack(new ItemStack(Items.EMERALD, gained_amount));
				end(world, user, hand);
			}
		} else if (other_stack.isOf(Items.IRON_INGOT)) {
			if (!user.getAbilities().creativeMode) {
				other_stack.decrement(1);
			}
			user.giveItemStack(new ItemStack(Items.FEATHER, 4));
			end(world, user, hand);
		}else if (other_stack.isOf(Items.FEATHER)){
			if (!user.getAbilities().creativeMode) {
				other_stack.decrement(1);
			}
			user.giveItemStack(new ItemStack(Items.IRON_NUGGET, 1));
			end(world, user, hand);
		}else if (other_stack.isOf(ModItems.PHILOSOPHERS_STONE)){
			if (!user.getAbilities().creativeMode) {
				other_stack.decrement(1);
			}
			user.giveItemStack(new ItemStack(ModItems.GOLD_COIN, 32));
			end(world, user, hand);
		}
		return super.use(world, user, hand);
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);
		tooltip.add(Text.literal("Turns coins to emeralds,")
			.styled(style -> style.withColor(Formatting.GRAY)));
		tooltip.add(Text.literal("iron ingots to feathers and vice versa")
			.styled(style -> style.withColor(Formatting.GRAY)));
	}

	private void end(World world, PlayerEntity user, Hand hand){
		user.swingHand(hand);
		user.getStackInHand(hand).damage(1, user, (e) -> e.sendToolBreakStatus(hand));
		//TODO particles
		Vec3d pos = user.getPos();
		world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.PLAYERS, 3.0F, 1.0F);
	}
}
