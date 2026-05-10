package dev.ganwooma.radiomod;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;

import net.minecraft.entity.player.PlayerEntity;

import net.minecraft.item.ItemStack;

import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;

public class BandageHealHandler {

    public static void register() {

        AttackEntityCallback.EVENT.register((
                player,
                world,
                hand,
                entity,
                hitResult
        ) -> {

            if (world.isClient) {
                return ActionResult.PASS;
            }

            // 플레이어만
            if (!(entity instanceof PlayerEntity target)) {
                return ActionResult.PASS;
            }

            ItemStack stack =
                    player.getStackInHand(hand);

            // BANDAGE 확인
            if (!stack.isOf(Radiomod.BANDAGE)) {
                return ActionResult.PASS;
            }

            // 강한 공격만
            if (player.getAttackCooldownProgress(0.5F)
                    < 1.0F) {
                world.playSound(
                        null,
                        player.getBlockPos(),
                        SoundEvents.BLOCK_BEACON_POWER_SELECT,
                        SoundCategory.PLAYERS,
                        0.2F,
                        2.0F
                );
                return ActionResult.FAIL;
            }

            // 공격 취소
            target.timeUntilRegen = 0;

            // 힐
            target.heal(0.5F);
            world.playSound(
                    null,
                    player.getBlockPos(),
                    SoundEvents.ITEM_HONEYCOMB_WAX_ON,
                    SoundCategory.PLAYERS,
                    1.0F,
                    1.0F
            );

            return ActionResult.FAIL;
        });
    }
}