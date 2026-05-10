package dev.ganwooma.radiomod;

import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class BarrelScanner {
    public static void scan(
            ServerWorld world,
            BlockPos center
    ) {

        for (BlockPos pos : BlockPos.iterate(
                center.add(-10, -10, -10),
                center.add(10, 10, 10)
        )) {

            if (world.getBlockState(pos)
                    .isOf(Blocks.CHEST)) {

                BlockPos immutable =
                        pos.toImmutable();

                if (!BarrelQueueProcessor.KNOWN.contains(immutable)) {

                    BarrelQueueProcessor.KNOWN.add(immutable);

                    BarrelQueueProcessor.QUEUE.add(immutable);
                }
            }
        }
    }
}
