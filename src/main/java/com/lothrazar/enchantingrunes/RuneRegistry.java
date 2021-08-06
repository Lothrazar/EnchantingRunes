package com.lothrazar.enchantingrunes;

import com.lothrazar.enchantingrunes.block.BlockLayering;
import com.lothrazar.enchantingrunes.item.KnifeItem;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RuneRegistry {

  public static final ItemGroup TAB = new ItemGroup(ModMainRunes.MODID) {

    @Override
    public ItemStack createIcon() {
      return new ItemStack(BLANK_RUNE.get());
    }
  };
  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModMainRunes.MODID);
  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ModMainRunes.MODID);
  //  public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ModMainRunes.MODID);
  public static final RegistryObject<Item> BLANK_RUNE = ITEMS.register("blank_rune", () -> new Item(new Item.Properties().group(TAB)));
  public static final RegistryObject<Item> RUNE_A = ITEMS.register("rune_a", () -> new Item(new Item.Properties().group(TAB)));
  public static final RegistryObject<Item> RUNE_B = ITEMS.register("rune_b", () -> new Item(new Item.Properties().group(TAB)));
  public static final RegistryObject<Item> RUNE_C = ITEMS.register("rune_c", () -> new Item(new Item.Properties().group(TAB)));
  public static final RegistryObject<Item> RUNE_D = ITEMS.register("rune_d", () -> new Item(new Item.Properties().group(TAB)));
  public static final RegistryObject<Item> RUNE_E = ITEMS.register("rune_e", () -> new Item(new Item.Properties().group(TAB)));
  public static final RegistryObject<Item> RUNE_F = ITEMS.register("rune_f", () -> new Item(new Item.Properties().group(TAB)));
  public static final RegistryObject<Item> RUNE_G = ITEMS.register("rune_g", () -> new Item(new Item.Properties().group(TAB)));
  public static final RegistryObject<Item> BLADE_MASON = ITEMS.register("blade_mason", () -> new KnifeItem(new Item.Properties().group(TAB)));
  public static final RegistryObject<Block> STONE_LAYERS = BLOCKS.register("stone_layer", () -> new BlockLayering(AbstractBlock.Properties.create(Material.ROCK)));
  public static final RegistryObject<Item> STONE_LAYERS_I = ITEMS.register("stone_layer", () -> new BlockItem(STONE_LAYERS.get(), new Item.Properties().group(TAB)));
  /**
   * 
   * fire water earth air = 4 void, life, time = 3 ? time/temporal/ghost cosmic. insight. truth. love. willpower. fear. blood. survival, creativity.
   */
  //  @SubscribeEvent
  //  public static void onContainerRegistry(final RegistryEvent.Register<ContainerType<?>> event) {
  //    // IForgeRegistry<ContainerType<?>> r = event.getRegistry();
  //  }
}
