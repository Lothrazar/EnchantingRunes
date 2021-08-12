package com.lothrazar.enchantingrunes;

import com.lothrazar.enchantingrunes.block.BlockLayering;
import com.lothrazar.enchantingrunes.item.RuneItem;
import com.lothrazar.enchantingrunes.item.KnifeItem;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RuneRegistry {

  public static final ItemGroup TAB = new ItemGroup(ModMainRunes.MODID) {

    @Override
    public ItemStack createIcon() {
      return new ItemStack(RUNE_TH.get());
    }
  };
  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModMainRunes.MODID);
  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ModMainRunes.MODID);
  public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ModMainRunes.MODID);
  //  public static final RegistryObject<Item> BALANCE_RUNE = ITEMS.register("balance_rune", () -> new Item(new Item.Properties().group(TAB)));
  //  public static final RegistryObject<Item> BLOOD_RUNE = ITEMS.register("blood_rune", () -> new Item(new Item.Properties().group(TAB)));
  //  public static final RegistryObject<Item> CHAOS_RUNE = ITEMS.register("chaos_rune", () -> new Item(new Item.Properties().group(TAB)));
  //  public static final RegistryObject<Item> COSMIC_RUNE = ITEMS.register("cosmic_rune", () -> new Item(new Item.Properties().group(TAB)));
  //  public static final RegistryObject<Item> HUMILITY_RUNE = ITEMS.register("humility_rune", () -> new Item(new Item.Properties().group(TAB)));
  //  public static final RegistryObject<Item> RADIANCE_RUNE = ITEMS.register("radiance_rune", () -> new Item(new Item.Properties().group(TAB)));
  //  public static final RegistryObject<Item> VOID_RUNE = ITEMS.register("void_rune", () -> new Item(new Item.Properties().group(TAB)));
  //  public static final RegistryObject<Item> WISDOM_RUNE = ITEMS.register("wisdom_rune", () -> new Item(new Item.Properties().group(TAB)));
  public static final RegistryObject<RuneItem> RUNE_A = ITEMS.register("rune_a", () -> new RuneItem(new Item.Properties().group(TAB)));
  public static final RegistryObject<RuneItem> RUNE_E = ITEMS.register("rune_e", () -> new RuneItem(new Item.Properties().group(TAB)));
  public static final RegistryObject<RuneItem> RUNE_I = ITEMS.register("rune_i", () -> new RuneItem(new Item.Properties().group(TAB)));
  public static final RegistryObject<RuneItem> RUNE_O = ITEMS.register("rune_o", () -> new RuneItem(new Item.Properties().group(TAB)));
  public static final RegistryObject<RuneItem> RUNE_U = ITEMS.register("rune_u", () -> new RuneItem(new Item.Properties().group(TAB)));
  public static final RegistryObject<RuneItem> RUNE_Y = ITEMS.register("rune_y", () -> new RuneItem(new Item.Properties().group(TAB)));
  public static final RegistryObject<RuneItem> RUNE_TH = ITEMS.register("rune_th", () -> new RuneItem(new Item.Properties().group(TAB)));
  public static final RegistryObject<RuneItem> RUNE_BLANK = ITEMS.register("rune_blank", () -> new RuneItem(new Item.Properties().group(TAB)));
  //stone
  public static final RegistryObject<Item> BLADE_MASON = ITEMS.register("masonry_blade", () -> new KnifeItem(new Item.Properties().group(TAB)));
  public static final RegistryObject<Block> STONE_LAYERS = BLOCKS.register("stone_layer", () -> new BlockLayering(AbstractBlock.Properties.create(Material.ROCK)));
  public static final RegistryObject<Item> STONE_LAYERS_I = ITEMS.register("stone_layer", () -> new BlockItem(STONE_LAYERS.get(), new Item.Properties().group(TAB)));
}
