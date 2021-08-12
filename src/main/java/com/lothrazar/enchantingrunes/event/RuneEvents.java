package com.lothrazar.enchantingrunes.event;

import com.lothrazar.enchantingrunes.ModMainRunes;
import com.lothrazar.enchantingrunes.runes.RuneEnch;
import com.lothrazar.enchantingrunes.runes.RuneType;
import com.lothrazar.enchantingrunes.runes.RuneWord;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class RuneEvents {

  private static final String NBT_LORE = "Lore";
  private static final String NBT_DISPLAY = "display";
  public static final ITag.INamedTag<Item> RUNESTONE = ItemTags.createOptional(new ResourceLocation(ModMainRunes.MODID, "runes/stone"));

  @SubscribeEvent
  public void test(ItemCraftedEvent event) {
    ItemStack crafting = event.getCrafting();
    if (crafting.getTag() != null && crafting.getTag().contains(ModMainRunes.MODID)) {
      //maybe the runes say apply random stuff
      this.applyRunelore(event, crafting);
      //only remove after done with it
      crafting.getTag().remove(ModMainRunes.MODID);
    }
  }

  private void applyRunelore(ItemCraftedEvent event, ItemStack crafting) {
    if (event.getInventory() instanceof CraftingInventory) {
      CraftingInventory test = (CraftingInventory) event.getInventory();
      HashMap<Enchantment, Integer> doIt = new HashMap<Enchantment, Integer>();
      String lore = "";
      Map<Integer, Boolean> used = new HashMap<>();
      for (RuneWord word : RuneType.WORDS) {
        // does it match lol
        if (word.matches(test, crafting, used)) {
          //.println("  words matched " + word.getDisplayName() + " used so far " + used.keySet().size());
          //apply this
          for (RuneEnch ench : word.getEnchants()) {
            //.println("   add enchant from match" + ench.getId());
            doIt.put(ForgeRegistries.ENCHANTMENTS.getValue(ench.getId()), ench.getLvl());
          }
          lore += word.getDisplayName();
          lore += " ";
        }
        else {
          //.println("  NO matched " + word.getDisplayName() + " used so far " + used.keySet().size());
        }
      }
      //copy damage, random or not
      for (int i = 0; i < test.getSizeInventory(); i++) {
        ItemStack oldStack = test.getStackInSlot(i);
        if (oldStack.getItem() == crafting.getItem()) {
          System.out.println("Durability test; old is " + oldStack.getDamage());
          crafting.setDamage(oldStack.getDamage());
        }
      }
      if (doIt.size() == 0) {
        //.println("NO words matched go random");
        //gotta go random
        this.applyRandomEnch(event, crafting);
        //no lore
        //        crafting.getTag().remove(NBT_DISPLAY);
        addLoreToStack(crafting, "-");
        //done now check damage
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
}
