package dev.ganwooma.radiomod;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import static dev.ganwooma.radiomod.Radiomod.DEAD_RADIO;

public class Ending extends Item {
    public Ending(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            MinecraftServer server = world.getServer();
            ItemStack newStack = new ItemStack(DEAD_RADIO);
            user.setStackInHand(hand, newStack);
            if (server != null) {
                for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                    world.playSound(
                            null,
                            user.getX(), user.getY(), user.getZ(),
                            SoundEvents.ITEM_TOTEM_USE,
                            SoundCategory.PLAYERS,
                            1.0F,
                            1.0F
                    );
                    player.networkHandler.sendPacket(
                            new GameStateChangeS2CPacket(
                                    GameStateChangeS2CPacket.GAME_WON,
                                    1.0f
                            )
                    );
                }
            }
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
