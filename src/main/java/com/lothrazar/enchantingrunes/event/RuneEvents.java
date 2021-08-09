package com.lothrazar.enchantingrunes.event;

import com.lothrazar.enchantingrunes.ModMainRunes;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RuneEvents {

  public static final ITag.INamedTag<Item> RUNESTONE = ItemTags.createOptional(new ResourceLocation(ModMainRunes.MODID, "runes/stone"));

  @SubscribeEvent
  public void test(ItemCraftedEvent event) {
    ItemStack crafting = event.getCrafting();
    if (crafting.getTag() != null && crafting.getTag().contains(ModMainRunes.MODID)) {
      //maybe the runes say apply random stuff
      if (crafting.getChildTag(ModMainRunes.MODID).contains("level")) {
        this.applyRunesRandomly(event, crafting);
      }
      //random or not, check what the lore says
      if (crafting.getChildTag(ModMainRunes.MODID).contains("lore") && crafting.getChildTag(ModMainRunes.MODID).getBoolean("lore")) {
        this.applyRunelore(event, crafting);
      }
      //onlyh remove after done with it
      crafting.getTag().remove(ModMainRunes.MODID);
    }
  }

  private void applyRunesRandomly(ItemCraftedEvent event, ItemStack crafting) {
    //maybe
    Map<Enchantment, Integer> oldEnch = EnchantmentHelper.getEnchantments(crafting);
    EnchantmentHelper.setEnchantments(new HashMap<Enchantment, Integer>(), crafting);
    applyRandomEnch(event, crafting);
    Map<Enchantment, Integer> newTest = EnchantmentHelper.getEnchantments(crafting);
    if (oldEnch != null && !oldEnch.isEmpty()) {
      this.merge(oldEnch, crafting);
    }
    //it was a random one, remove the lore
    crafting.getTag().remove("display");
  }

  private void applyRunelore(ItemCraftedEvent event, ItemStack crafting) {
    if (event.getInventory() instanceof CraftingInventory) {
      String lore = "";
      CraftingInventory test = (CraftingInventory) event.getInventory();
      for (int i = 0; i < test.getSizeInventory(); i++) {
        ItemStack stak = test.getStackInSlot(i);
        if (stak.getItem().isIn(RUNESTONE)) {
          lore += stak.getDisplayName().getString();
        }
      }
      CompoundNBT displayTag = new CompoundNBT();
      //      displayTag.pu
      ListNBT tagList = new ListNBT();
      String escaped = "{\"text\":\"" + lore + "\",\"color\":\"gold\"}";
      tagList.add(StringNBT.valueOf(escaped));
      displayTag.put("Lore", tagList);
      displayTag.putString("Name", "TEST");
      crafting.getTag().put("display", displayTag);
    }
    //    "display": {
    //      "Lore": [
    //        "[{\"translate\":\"item.enchantingrunes.rune_a\",\"color\":\"gold\"},{\"translate\":\"item.enchantingrunes.rune_c\",\"color\":\"gold\"}]"
    //      ]
    //    },
  }
  //  private void tryRuneword(ItemCraftedEvent event) {
  //    if (event.getInventory() instanceof CraftingInventory) {
  //      CraftingInventory test = (CraftingInventory) event.getInventory();
  //      for (int i = 0; i < test.getSizeInventory(); i++) {
  //        ItemStack stak = test.getStackInSlot(0);
  //        if (stak.getItem().isIn(RUNESTONE)) {
  //       println(i + " wsas input " + stak);
  //        }
  //      }
  //    }
  //  }

  private void applyRandomEnch(ItemCraftedEvent event, ItemStack crafting) {
    CompoundNBT tag = crafting.getChildTag(ModMainRunes.MODID);
    crafting = EnchantmentHelper.addRandomEnchantment(event.getPlayer().world.rand, crafting, tag.getInt("level"), tag.getBoolean("treasure"));
  }

  private void merge(Map<Enchantment, Integer> oldEnch, ItemStack crafting) {
    Map<Enchantment, Integer> newEnch = EnchantmentHelper.getEnchantments(crafting);
    //anything in new thats also in old, merge it over
    for (Entry<Enchantment, Integer> newEntry : newEnch.entrySet()) {
      //
      //if this exists in the old list, merge into new
      if (oldEnch.containsKey(newEntry.getKey())) {
        //take max of each
        newEnch.put(newEntry.getKey(), Math.max(newEntry.getValue(), oldEnch.get(newEntry.getKey())));
      }
    }
    //anything in old thats NOT in new
    for (Entry<Enchantment, Integer> oldEntry : oldEnch.entrySet()) {
      if (!newEnch.containsKey(oldEntry.getKey())) {
        //new list does NOT hvae this thing from old
        newEnch.put(oldEntry.getKey(), oldEntry.getValue());
      }
    }
    EnchantmentHelper.setEnchantments(newEnch, crafting);
  }

  //  pub
  @SubscribeEvent
  public void onLootLoad(LootTableLoadEvent event) {
    if (event.getName().equals(new ResourceLocation("minecraft", "granite"))) {
      event.getTable().addPool(LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(ModMainRunes.MODID, "granite"))).build());
    }
  }
}
