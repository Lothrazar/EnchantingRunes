package com.lothrazar.enchantingrunes;

import com.lothrazar.enchantingrunes.item.KnifeItem;
import com.lothrazar.enchantingrunes.item.RuneItem;
import com.lothrazar.library.block.BlockLayering;
import com.lothrazar.library.item.BlockItemFlib;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryRunes {

  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModMainRunes.MODID);
  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ModMainRunes.MODID);
  private static final ResourceKey<CreativeModeTab> TAB = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(ModMainRunes.MODID, "tab"));

  @SubscribeEvent
  public static void onCreativeModeTabRegister(RegisterEvent event) {
    event.register(Registries.CREATIVE_MODE_TAB, helper -> {
      helper.register(TAB, CreativeModeTab.builder().icon(() -> new ItemStack(RUNE_TH.get()))
          .title(Component.translatable("itemGroup." + ModMainRunes.MODID))
          .displayItems((enabledFlags, populator) -> {
            for (RegistryObject<Item> entry : ITEMS.getEntries()) {
              populator.accept(entry.get());
            }
          }).build());
    });
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
  public static final RegistryObject<Block> STONE_LAYERS = BLOCKS.register("stone_layer", () -> new BlockLayering(Blocks.STONE, BlockBehaviour.Properties.of()));
  public static final RegistryObject<Item> STONE_LAYERS_I = ITEMS.register("stone_layer", () -> new BlockItemFlib(STONE_LAYERS.get(), new Item.Properties()));
}
