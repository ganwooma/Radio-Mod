package dev.ganwooma.radiomod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;

public class Radiomod implements ModInitializer {

    public static final Item BROKEN_RADIO = new Item(new FabricItemSettings());
    public static final Item RADIO = new Ending(new FabricItemSettings());
    public static final Item DEAD_RADIO = new Item(new FabricItemSettings());
    public static final Item COPPER_WIRE = new Item(new FabricItemSettings());
    public static final Item COPPER_THREAD = new Item(new FabricItemSettings());
    public static final Item ANTENNA = new Item(new FabricItemSettings());
    public static final Item BATTERY = new Item(new FabricItemSettings());
    public static final ItemGroup RADIO_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of("radio", "radio_group"), FabricItemGroup.builder().icon(() ->
                    new ItemStack(BROKEN_RADIO)).displayName(Text.translatable("itemGroup.radiomod.radiomod")).build());
    public static final RegistryKey<ItemGroup> RADIO_GROUP_KEY =
            RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of("radio", "radio_group"));

    @Override
    public void onInitialize() {
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
    }

}
