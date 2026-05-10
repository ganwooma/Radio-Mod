package dev.ganwooma.radiomod.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {
    public static BarrelLootConfig GOLD;

    public static BarrelLootConfig IRON;

    private static final Gson GSON =
            new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

    public static void load() {

        try {

            Path folder =
                    FabricLoader.getInstance()
                            .getConfigDir()
                            .resolve("radiomod");

            Files.createDirectories(folder);

            Path goldPath =
                    folder.resolve("gold_barrel.json");

            Path ironPath =
                    folder.resolve("iron_barrel.json");

            if (!Files.exists(goldPath)) {
                createGoldDefault(goldPath);
            }

            if (!Files.exists(ironPath)) {
                createIronDefault(ironPath);
            }

            try (Reader reader =
                         Files.newBufferedReader(goldPath)) {

                GOLD = GSON.fromJson(
                        reader,
                        BarrelLootConfig.class
                );
            }

            try (Reader reader =
                         Files.newBufferedReader(ironPath)) {

                IRON = GSON.fromJson(
                        reader,
                        BarrelLootConfig.class
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createGoldDefault(Path path)
            throws Exception {

        BarrelLootConfig config =
                new BarrelLootConfig();

        LootEntry diamond =
                new LootEntry();

        diamond.item = "minecraft:diamond";
        diamond.weight = 1;
        diamond.min = 1;
        diamond.max = 2;

        config.items.add(diamond);

        LootEntry gold =
                new LootEntry();

        gold.item = "minecraft:gold_ingot";
        gold.weight = 20;
        gold.min = 3;
        gold.max = 12;

        config.items.add(gold);

        try (Writer writer =
                     Files.newBufferedWriter(path)) {

            GSON.toJson(config, writer);
        }
    }

    private static void createIronDefault(Path path)
            throws Exception {

        BarrelLootConfig config =
                new BarrelLootConfig();

        LootEntry iron =
                new LootEntry();

        iron.item = "minecraft:iron_ingot";
        iron.weight = 50;
        iron.min = 3;
        iron.max = 16;

        config.items.add(iron);

        LootEntry redstone =
                new LootEntry();

        redstone.item = "minecraft:redstone";
        redstone.weight = 20;
        redstone.min = 2;
        redstone.max = 10;

        config.items.add(redstone);

        try (Writer writer =
                     Files.newBufferedWriter(path)) {

            GSON.toJson(config, writer);
        }
    }
}
