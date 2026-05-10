package dev.ganwooma.radiomod;


import dev.ganwooma.radiomod.loot.LootHelper;
import net.minecraft.block.BlockState;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;


import java.util.Random;

import static dev.ganwooma.radiomod.Radiomod.GOLD_BARREL;
import static dev.ganwooma.radiomod.Radiomod.IRON_BARREL;

public class BarrelTransformer {

    private static final Random RANDOM =
            new Random();

    public static void transform(
            ServerWorld world,
            BlockPos pos
    ) {

        boolean gold = RANDOM.nextDouble() < 0.05;

        Direction facing =
                world.getBlockState(pos)
                        .get(Properties.HORIZONTAL_FACING);

        BlockState state =
                (gold ? GOLD_BARREL : IRON_BARREL)
                        .getDefaultState()
                        .with(Properties.FACING, facing);

        // 조용히 교체
        world.removeBlockEntity(pos);

        world.setBlockState(pos, state, 2);

        LootHelper.fillBarrel(world, pos, gold);
    }
}