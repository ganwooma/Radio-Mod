package dev.ganwooma.radiomod.loot;

import dev.ganwooma.radiomod.config.BarrelLootConfig;
import dev.ganwooma.radiomod.config.ConfigManager;
import dev.ganwooma.radiomod.config.LootEntry;

import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.registry.Registries;

import net.minecraft.server.world.ServerWorld;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LootHelper {

    private static final Random RANDOM =
            new Random();

    public static void fillBarrel(
            ServerWorld world,
            BlockPos pos,
            boolean gold
    ) {

        BlockEntity be =
                world.getBlockEntity(pos);

        if (!(be instanceof BarrelBlockEntity barrel)) {
            return;
        }

        BarrelLootConfig config =
                gold
                        ? ConfigManager.GOLD
                        : ConfigManager.IRON;

        // rolls 만큼 아이템 생성
        for (int i = 0; i < config.rolls; i++) {

            LootEntry entry =
                    randomEntry(config.items);

            if (entry == null) {
                continue;
            }

            Item item =
                    Registries.ITEM.get(
                            new Identifier(entry.item)
                    );

            // AIR 방지
            if (item == null) {
                continue;
            }

            int count =
                    RANDOM.nextInt(
                            entry.max - entry.min + 1
                    ) + entry.min;

            ItemStack stack =
                    new ItemStack(item, count);

            if (entry.nbt != null
                    && !entry.nbt.isEmpty()) {

                try {

                    NbtCompound nbt =
                            StringNbtReader.parse(
                                    entry.nbt
                            );

                    stack.setNbt(nbt);

                } catch (Exception e) {

                    System.out.println(
                            "Invalid NBT: " + entry.nbt
                    );
                }
            }

            int slot = randomEmptySlot(barrel);

            if (slot != -1) {
                barrel.setStack(slot, stack);
            }
        }

        barrel.markDirty();
    }

    private static LootEntry randomEntry(
            List<LootEntry> entries
    ) {

        if (entries.isEmpty()) {
            return null;
        }

        int total = 0;

        for (LootEntry entry : entries) {
            total += entry.weight;
        }

        if (total <= 0) {
            return null;
        }

        int random =
                RANDOM.nextInt(total);

        int current = 0;

        for (LootEntry entry : entries) {

            current += entry.weight;

            if (random < current) {
                return entry;
            }
        }

        return entries.get(0);
    }

    private static int randomEmptySlot(
            BarrelBlockEntity barrel
    ) {

        List<Integer> empty =
                new ArrayList<>();

        for (int i = 0; i < barrel.size(); i++) {

            if (barrel.getStack(i).isEmpty()) {
                empty.add(i);
            }
        }

        if (empty.isEmpty()) {
            return -1;
        }

        return empty.get(
                RANDOM.nextInt(empty.size())
        );
    }
}