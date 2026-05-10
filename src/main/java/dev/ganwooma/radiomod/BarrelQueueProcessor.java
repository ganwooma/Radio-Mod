package dev.ganwooma.radiomod;

import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class BarrelQueueProcessor {
    public static final Queue<BlockPos> QUEUE =
            new LinkedList<>();

    public static final Set<BlockPos> KNOWN =
            new HashSet<>();
}
