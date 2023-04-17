package com.lothrazar.enchantingrunes;

import com.lothrazar.enchantingrunes.block.BlockLayering;
import com.lothrazar.enchantingrunes.item.KnifeItem;
import com.lothrazar.enchantingrunes.item.RuneItem;
import com.lothrazar.library.registry.RegistryFactory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryRunes {

  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModMainRunes.MODID);
  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ModMainRunes.MODID);

  @SubscribeEvent
  public static void buildContents(CreativeModeTabEvent.Register event) {
    RegistryFactory.buildTab(event, ModMainRunes.MODID, RUNE_TH.get().asItem(), ITEMS);
  }

  public static final RegistryObject<RuneItem> RUNE_A = ITEMS.register("rune_a", () -> new RuneItem(new Item.Properties()));
  public static final RegistryObject<RuneItem> RUNE_E = ITEMS.register("rune_e", () -> new RuneItem(new Item.Properties()));
  public static final RegistryObject<RuneItem> RUNE_I = ITEMS.register("rune_i", () -> new RuneItem(new Item.Properties()));
  public static final RegistryObject<RuneItem> RUNE_O = ITEMS.register("rune_o", () -> new RuneItem(new Item.Properties()));
  public static final RegistryObject<RuneItem> RUNE_U = ITEMS.register("rune_u", () -> new RuneItem(new Item.Properties()));
  public static final RegistryObject<RuneItem> RUNE_Y = ITEMS.register("rune_y", () -> new RuneItem(new Item.Properties()));
  public static final RegistryObject<RuneItem> RUNE_TH = ITEMS.register("rune_th", () -> new RuneItem(new Item.Properties()));
  public static final RegistryObject<RuneItem> RUNE_BLANK = ITEMS.register("rune_blank", () -> new RuneItem(new Item.Properties()));
  //stone
  public static final RegistryObject<Item> BLADE_MASON = ITEMS.register("masonry_blade", () -> new KnifeItem(new Item.Properties()));
  public static final RegistryObject<Block> STONE_LAYERS = BLOCKS.register("stone_layer", () -> new BlockLayering(BlockBehaviour.Properties.of(Material.STONE)));
  public static final RegistryObject<Item> STONE_LAYERS_I = ITEMS.register("stone_layer", () -> new BlockItem(STONE_LAYERS.get(), new Item.Properties()));
}
