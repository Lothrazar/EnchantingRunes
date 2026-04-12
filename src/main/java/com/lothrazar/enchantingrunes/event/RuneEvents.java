package com.lothrazar.enchantingrunes.event;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.lothrazar.enchantingrunes.ModMainRunes;
import com.lothrazar.enchantingrunes.runes.RuneEnch;
import com.lothrazar.enchantingrunes.runes.RuneType;
import com.lothrazar.enchantingrunes.runes.RuneWord;
import com.lothrazar.library.util.ItemStackUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.ItemCraftedEvent;

public class RuneEvents {

  public static final TagKey<Item> RUNESTONE = ItemTags.create(ResourceLocation.fromNamespaceAndPath(ModMainRunes.MODID, "runes/stone"));

  @SubscribeEvent
  public void test(ItemCraftedEvent event) {
    ItemStack crafting = event.getCrafting();
    CustomData customData = crafting.get(DataComponents.CUSTOM_DATA);
    if (customData != null && customData.contains(ModMainRunes.MODID)) {
      //maybe the runes say apply random stuff
      this.applyRunelore(event, crafting);
      //only remove after done with it
      CompoundTag tag = customData.copyTag();
      tag.remove(ModMainRunes.MODID);
      if (tag.isEmpty()) {
        crafting.remove(DataComponents.CUSTOM_DATA);
      }
      else {
        crafting.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
      }
    }
  }


  private void applyRunelore(ItemCraftedEvent event, ItemStack crafting) {
    int enchantLevel = 1; // default to a low player level, weak enchantments
    final var level = event.getEntity().level();
    Optional<? extends HolderSet<Enchantment>> enchFilter = Optional.empty(); // dont filter available enchs

    if (!(event.getInventory() instanceof CraftingContainer test)) {
      //gotta go random
//      ItemStackUtil.applyRandomEnch(event.getEntity().level().random, crafting);
      EnchantmentHelper.enchantItem(level.random, crafting, enchantLevel, level.registryAccess(), enchFilter);

      return;
    }
    Registry<Enchantment> enchRegistry = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
    HashMap<Holder<Enchantment>, Integer> doIt = new HashMap<>();
    StringBuilder lore = new StringBuilder();
    Map<Integer, Boolean> used = new HashMap<>();
    for (RuneWord word : RuneType.WORDS) {
      if (word == null) {
        continue; // ??
      }
      // does it match lol
      if (word.matches(test, crafting, used, enchRegistry)) {
        //apply this
        for (RuneEnch ench : word.getEnchants()) {
          ResourceKey<Enchantment> key = ResourceKey.create(Registries.ENCHANTMENT, ench.getId());
          enchRegistry.getHolder(key).ifPresent(holder -> doIt.put(holder, ench.getLvl()));
        }
        lore.append(word.getDisplayName());
        lore.append(" ");
      }
    }
    //copy damage, random or not
    for (int i = 0; i < test.getContainerSize(); i++) {
      ItemStack oldStack = test.getItem(i);
      if (oldStack.getItem() == crafting.getItem()) {
        crafting.setDamageValue(oldStack.getDamageValue());
      }
    }
    if (doIt.isEmpty()) {
      //gotta go random
      //new 1.21.1 feature: randomized power levels, not 30
      enchantLevel = Mth.nextInt(level.getRandom(), 4, 22);
      EnchantmentHelper.enchantItem(level.random, crafting, enchantLevel, level.registryAccess(), enchFilter);
      //no lore
      ItemStackUtil.addLoreToStack(crafting, "-", null);
      //done now check damage
    }
    else {
      ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(crafting.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY));
      for (var entry : doIt.entrySet()) {
        mutable.set(entry.getKey(), entry.getValue());
      }
      crafting.set(DataComponents.ENCHANTMENTS, mutable.toImmutable());
      ItemStackUtil.addLoreToStack(crafting, lore.toString(), null);
    }
  }
}
