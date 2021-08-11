package com.lothrazar.enchantingrunes.event;

import com.lothrazar.enchantingrunes.ModMainRunes;
import com.lothrazar.enchantingrunes.RuneType;
import com.lothrazar.enchantingrunes.RuneWord;
import java.util.HashMap;
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

  private static final String NBT_LORE = "Lore";
  private static final String NBT_DISPLAY = "display";
  public static final ITag.INamedTag<Item> RUNESTONE = ItemTags.createOptional(new ResourceLocation(ModMainRunes.MODID, "runes/stone"));

  @SubscribeEvent
  public void test(ItemCraftedEvent event) {
    ItemStack crafting = event.getCrafting();
    if (crafting.getTag() != null && crafting.getTag().contains(ModMainRunes.MODID)) {
      //maybe the runes say apply random stuff
      if (crafting.getChildTag(ModMainRunes.MODID).contains("level")) {}
      //random or not, check what the lore says
      //      if (crafting.getChildTag(ModMainRunes.MODID).contains("lore") && crafting.getChildTag(ModMainRunes.MODID).getBoolean("lore")) {
      this.applyRunelore(event, crafting);
      //      }
      //onlyh remove after done with it
      crafting.getTag().remove(ModMainRunes.MODID);
    }
  }
  //
  //  private void applyRunesRandomly(ItemCraftedEvent event, ItemStack crafting) {
  //    //maybe
  //    Map<Enchantment, Integer> oldEnch = EnchantmentHelper.getEnchantments(crafting);
  //    EnchantmentHelper.setEnchantments(new HashMap<Enchantment, Integer>(), crafting);
  //    applyRandomEnch(event, crafting);
  //    //    Map<Enchantment, Integer> newTest = EnchantmentHelper.getEnchantments(crafting);
  //    if (oldEnch != null && !oldEnch.isEmpty()) {
  //      this.merge(oldEnch, crafting);
  //    }
  //    //it was a random one, remove the lore
  //    crafting.getTag().remove("display");
  //  }

  private void applyRunelore(ItemCraftedEvent event, ItemStack crafting) {
    if (event.getInventory() instanceof CraftingInventory) {
      CraftingInventory test = (CraftingInventory) event.getInventory();
      HashMap<Enchantment, Integer> doIt = new HashMap<Enchantment, Integer>();
      String lore = "";
      for (RuneWord word : RuneType.words) {
        // does it match lol
        if (word.matches(test)) {
          //apply this
          word.applyEnchants(doIt);
          lore += word.getDisplayName();
          lore += " ";
        }
      }
      if (doIt.size() == 0) {
        //gotta go random
        this.applyRandomEnch(event, crafting);
        //no lore
        //        crafting.getTag().remove(NBT_DISPLAY);
        addLoreToStack(crafting, "-");
      }
      else {
        EnchantmentHelper.setEnchantments(doIt, crafting);
        addLoreToStack(crafting, lore);
      }
    }
    else {
      //gotta go random
      this.applyRandomEnch(event, crafting);
    }
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

  public static void addLoreToStack(ItemStack crafting, String lore) {
    CompoundNBT displayTag = new CompoundNBT();
    //      displayTag.pu
    ListNBT tagList = new ListNBT();
    String escaped = "{\"text\":\"" + lore + "\",\"color\":\"gold\"}";
    tagList.add(StringNBT.valueOf(escaped));
    displayTag.put(NBT_LORE, tagList);
    //    displayTag.putString("Name", "TEST");
    crafting.getTag().put(NBT_DISPLAY, displayTag);
    //    "display": {
    //      "Lore": [
    //        "[{\"translate\":\"item.enchantingrunes.rune_a\",\"color\":\"gold\"},{\"translate\":\"item.enchantingrunes.rune_c\",\"color\":\"gold\"}]"
    //      ]
    //    },
  }

  private void applyRandomEnch(ItemCraftedEvent event, ItemStack crafting) {
    //    CompoundNBT tag = crafting.getChildTag(ModMainRunes.MODID);
    crafting = EnchantmentHelper.addRandomEnchantment(event.getPlayer().world.rand, crafting, 1, false);
  }
  //  private void merge(Map<Enchantment, Integer> oldEnch, ItemStack crafting) {
  //    Map<Enchantment, Integer> newEnch = EnchantmentHelper.getEnchantments(crafting);
  //    //anything in new thats also in old, merge it over
  //    for (Entry<Enchantment, Integer> newEntry : newEnch.entrySet()) {
  //      //
  //      //if this exists in the old list, merge into new
  //      if (oldEnch.containsKey(newEntry.getKey())) {
  //        //take max of each
  //        newEnch.put(newEntry.getKey(), Math.max(newEntry.getValue(), oldEnch.get(newEntry.getKey())));
  //      }
  //    }
  //    //anything in old thats NOT in new
  //    for (Entry<Enchantment, Integer> oldEntry : oldEnch.entrySet()) {
  //      if (!newEnch.containsKey(oldEntry.getKey())) {
  //        //new list does NOT hvae this thing from old
  //        newEnch.put(oldEntry.getKey(), oldEntry.getValue());
  //      }
  //    }
  //    EnchantmentHelper.setEnchantments(newEnch, crafting);
  //  }

  //  pub
  @SubscribeEvent
  public void onLootLoad(LootTableLoadEvent event) {
    if (event.getName().equals(new ResourceLocation("minecraft", "granite"))) {
      event.getTable().addPool(LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(ModMainRunes.MODID, "granite"))).build());
    }
  }
}
