package dev.ganwooma.radiomod;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import dev.ganwooma.radiomod.config.ConfigManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
import net.minecraft.util.math.BlockPos;

public class Radiomod implements ModInitializer {

    public static final Item BROKEN_RADIO = new Item(new FabricItemSettings());
    public static final Item RADIO = new Ending(new FabricItemSettings());
    public static final Item DEAD_RADIO = new Item(new FabricItemSettings());
    public static final Item COPPER_WIRE = new Item(new FabricItemSettings());
    public static final Item COPPER_THREAD = new Item(new FabricItemSettings());
    public static final Item ANTENNA = new Item(new FabricItemSettings());
    public static final Item BATTERY = new Item(new FabricItemSettings());
    public static final Item FIRST_AID_KIT = new FirstAidKitHealHandler(new FabricItemSettings());

    public static final Item MINER_PICKAXE = Registry.register(
            Registries.ITEM,
            new Identifier("radiomod", "miner_pickaxe"),
            new PickaxeItem(
                    new MinerPickaxeToolMaterial(),
                    1,
                    -2.8F,
                    new FabricItemSettings()
            )
    );

    public static final Item LONG_SWORD = Registry.register(
            Registries.ITEM,
            new Identifier("radiomod", "long_sword"),
            new SwordItem(
                    new LongSwordToolMaterial(),
                    1,
                    -3.4F,
                    new FabricItemSettings()
            )
    );

    public static final Item BAT = Registry.register(
            Registries.ITEM,
            new Identifier("radiomod", "bat"),
            new SwordItem(
                    new BatToolMaterial(),
                    1,
                    -3.4F,
                    new FabricItemSettings()
            )
    );

    public static final Item FIELD_SHOVEL = Registry.register(
            Registries.ITEM,
            new Identifier("radiomod", "field_shovel"),
            new SwordItem(
                    new FieldShovelToolMaterial(),
                    1,
                    -2.8F,
                    new FabricItemSettings()
            )
    );

    public static final Item BANDAGE = Registry.register(
            Registries.ITEM,
            new Identifier("radiomod", "bandage"),
            new SwordItem(
                    new FieldShovelToolMaterial(),
                    1,
                    -2.8F,
                    new FabricItemSettings()
            )
    );

    public static final Item HAMMER = Registry.register(
            Registries.ITEM,
            new Identifier("radiomod", "hammer"),
            new SwordItem(
                    new HammerToolMaterial(),
                    2,
                    -3F,
                    new FabricItemSettings()
            )
    );

    public static final Block GOLD_BARREL = Registry.register(
            Registries.BLOCK,
            new Identifier("radiomod", "gold_barrel"),
            new GoldBarrelBlock(
                    AbstractBlock.Settings.copy(Blocks.BARREL)
            )
    );

    public static final Item GOLD_BARREL_ITEM = Registry.register(
            Registries.ITEM,
            new Identifier("radiomod", "gold_barrel"),
            new BlockItem(GOLD_BARREL, new FabricItemSettings())
    );

    public static final Block IRON_BARREL = Registry.register(
            Registries.BLOCK,
            new Identifier("radiomod", "iron_barrel"),
            new IronBarrelBlock(
                    AbstractBlock.Settings.copy(Blocks.BARREL)
            )
    );

    public static final Item IRON_BARREL_ITEM = Registry.register(
            Registries.ITEM,
            new Identifier("radiomod", "iron_barrel"),
            new BlockItem(IRON_BARREL, new FabricItemSettings())
    );


    public static final ItemGroup RADIO_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of("radio", "radio_group"), FabricItemGroup.builder().icon(() ->
                    new ItemStack(BROKEN_RADIO)).displayName(Text.translatable("itemGroup.radiomod.radiomod")).build());
    public static final RegistryKey<ItemGroup> RADIO_GROUP_KEY =
            RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of("radio", "radio_group"));

    private static int scanTimer = 0;

    @Override
    public void onInitialize() {

        ConfigManager.load();
        BandageHealHandler.register();

        Registry.register(Registries.ITEM, Identifier.of("radiomod", "broken_radio"), BROKEN_RADIO);
        ItemGroupEvents.modifyEntriesEvent(RADIO_GROUP_KEY).register(entries -> {entries.add(BROKEN_RADIO);});
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SEARCH).register(entries -> {entries.add(BROKEN_RADIO);});

        Registry.register(Registries.ITEM, Identifier.of("radiomod", "radio"), RADIO);
        ItemGroupEvents.modifyEntriesEvent(RADIO_GROUP_KEY).register(entries -> {entries.add(RADIO);});
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SEARCH).register(entries -> {entries.add(RADIO);});

        Registry.register(Registries.ITEM, Identifier.of("radiomod", "dead_radio"), DEAD_RADIO);
        ItemGroupEvents.modifyEntriesEvent(RADIO_GROUP_KEY).register(entries -> {entries.add(DEAD_RADIO);});
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SEARCH).register(entries -> {entries.add(DEAD_RADIO);});

        Registry.register(Registries.ITEM, Identifier.of("radiomod", "copper_thread"), COPPER_THREAD);
        ItemGroupEvents.modifyEntriesEvent(RADIO_GROUP_KEY).register(entries -> {entries.add(COPPER_THREAD);});
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SEARCH).register(entries -> {entries.add(COPPER_THREAD);});

        Registry.register(Registries.ITEM, Identifier.of("radiomod", "copper_wire"), COPPER_WIRE);
        ItemGroupEvents.modifyEntriesEvent(RADIO_GROUP_KEY).register(entries -> {entries.add(COPPER_WIRE);});
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SEARCH).register(entries -> {entries.add(COPPER_WIRE);});

        Registry.register(Registries.ITEM, Identifier.of("radiomod", "antenna"), ANTENNA);
        ItemGroupEvents.modifyEntriesEvent(RADIO_GROUP_KEY).register(entries -> {entries.add(ANTENNA);});
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SEARCH).register(entries -> {entries.add(ANTENNA);});

        Registry.register(Registries.ITEM, Identifier.of("radiomod", "battery"), BATTERY);
        ItemGroupEvents.modifyEntriesEvent(RADIO_GROUP_KEY).register(entries -> {entries.add(BATTERY);});
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SEARCH).register(entries -> {entries.add(BATTERY);});

        Registry.register(Registries.ITEM, Identifier.of("radiomod", "first_aid_kit"), FIRST_AID_KIT);
        ItemGroupEvents.modifyEntriesEvent(RADIO_GROUP_KEY).register(entries -> {entries.add(FIRST_AID_KIT);});
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SEARCH).register(entries -> {entries.add(FIRST_AID_KIT);});

        ItemGroupEvents.modifyEntriesEvent(RADIO_GROUP_KEY).register(entries -> {entries.add(MINER_PICKAXE);});
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SEARCH).register(entries -> {entries.add(MINER_PICKAXE);});

        ItemGroupEvents.modifyEntriesEvent(RADIO_GROUP_KEY).register(entries -> {entries.add(LONG_SWORD);});
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SEARCH).register(entries -> {entries.add(LONG_SWORD);});

        ItemGroupEvents.modifyEntriesEvent(RADIO_GROUP_KEY).register(entries -> {entries.add(BAT);});
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SEARCH).register(entries -> {entries.add(BAT);});

        ItemGroupEvents.modifyEntriesEvent(RADIO_GROUP_KEY).register(entries -> {entries.add(FIELD_SHOVEL);});
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SEARCH).register(entries -> {entries.add(FIELD_SHOVEL);});

        ItemGroupEvents.modifyEntriesEvent(RADIO_GROUP_KEY).register(entries -> {entries.add(BANDAGE);});
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SEARCH).register(entries -> {entries.add(BANDAGE);});

        ItemGroupEvents.modifyEntriesEvent(RADIO_GROUP_KEY).register(entries -> {entries.add(HAMMER);});
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SEARCH).register(entries -> {entries.add(HAMMER);});

        ItemGroupEvents.modifyEntriesEvent(RADIO_GROUP_KEY).register(entries -> {entries.add(GOLD_BARREL_ITEM);});
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SEARCH).register(entries -> {entries.add(GOLD_BARREL_ITEM);});

        ItemGroupEvents.modifyEntriesEvent(RADIO_GROUP_KEY).register(entries -> {entries.add(IRON_BARREL_ITEM);});
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SEARCH).register(entries -> {entries.add(IRON_BARREL_ITEM);});

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {

            dispatcher.register(CommandManager.literal("transformbarrels")
                    .requires(source -> source.hasPermissionLevel(2))
                    .executes(context -> {

                        ServerPlayerEntity player = context.getSource().getPlayer();

                        BarrelScanner.scan(
                                player.getServerWorld(),
                                player.getBlockPos()
                        );

                        return 1;
                    }));
        });
        ServerTickEvents.END_SERVER_TICK.register(server -> {

            int maxPerTick = 2;

            for (int i = 0; i < maxPerTick; i++) {

                BlockPos pos =
                        BarrelQueueProcessor.QUEUE.poll();

                if (pos == null) {
                    return;
                }

                ServerWorld world =
                        server.getOverworld();

                BarrelTransformer.transform(world, pos);
                BarrelQueueProcessor.KNOWN.remove(pos);
            }
        });
        ServerTickEvents.END_SERVER_TICK.register(server -> {

            scanTimer++;

            if (scanTimer < 20) {
                return;
            }

            scanTimer = 0;

            for (ServerPlayerEntity player :
                    server.getPlayerManager().getPlayerList()) {

                BarrelScanner.scan(
                        player.getServerWorld(),
                        player.getBlockPos()
                );
            }
        });
    }
}
