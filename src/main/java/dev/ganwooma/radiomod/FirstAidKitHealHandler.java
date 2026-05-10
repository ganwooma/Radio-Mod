package dev.ganwooma.radiomod;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;

import net.minecraft.world.World;

public class FirstAidKitHealHandler extends Item {

    public FirstAidKitHealHandler(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(
            World world,
            PlayerEntity user,
            Hand hand
    ) {

        ItemStack stack =
                user.getStackInHand(hand);

        if (!world.isClient) {

            // 재생 3 (0=1, 1=2, 2=3)
            user.addStatusEffect(
                    new StatusEffectInstance(
                            StatusEffects.REGENERATION,
                            20 * 3,
                            6
                    )
            );

            // 소리
            world.playSound(
                    null,
                    user.getBlockPos(),
                    SoundEvents.ITEM_HONEY_BOTTLE_DRINK,
                    SoundCategory.PLAYERS,
                    1.0F,
                    1.0F
            );

            // 크리에이티브 아니면 1개 소비
            if (!user.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }

        return TypedActionResult.success(
                stack,
                world.isClient()
        );
    }
}