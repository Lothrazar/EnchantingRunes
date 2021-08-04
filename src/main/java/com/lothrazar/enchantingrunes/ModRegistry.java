package com.lothrazar.enchantingrunes;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModRegistry {

  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModMain.MODID);
  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ModMain.MODID);
  public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ModMain.MODID);
  public static final RegistryObject<Item> RUNE_A = ITEMS.register("rune_a", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));
  public static final RegistryObject<Item> RUNE_B = ITEMS.register("rune_b", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));
  public static final RegistryObject<Item> RUNE_C = ITEMS.register("rune_c", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));
  public static final RegistryObject<Item> RUNE_D = ITEMS.register("rune_d", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));
  public static final RegistryObject<Item> RUNE_E = ITEMS.register("rune_e", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));
  //  @SubscribeEvent
  //  public static void onContainerRegistry(final RegistryEvent.Register<ContainerType<?>> event) {
  //    // IForgeRegistry<ContainerType<?>> r = event.getRegistry();
  //  }
}
