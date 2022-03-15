package com.lothrazar.enchantingrunes.event;

import com.lothrazar.enchantingrunes.ModMainRunes;
import com.lothrazar.enchantingrunes.runes.RuneEnch;
import com.lothrazar.enchantingrunes.runes.RuneType;
import com.lothrazar.enchantingrunes.runes.RuneWord;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class RuneEvents {

  private static final String NBT_LORE = "Lore";
  private static final String NBT_DISPLAY = "display";
  public static final TagKey<Item> RUNESTONE = ItemTags.create(new ResourceLocation(ModMainRunes.MODID, "runes/stone"));

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
    if (event.getInventory() instanceof CraftingContainer) {
      CraftingContainer test = (CraftingContainer) event.getInventory();
      HashMap<Enchantment, Integer> doIt = new HashMap<Enchantment, Integer>();
      String lore = "";
      Map<Integer, Boolean> used = new HashMap<>();
      for (RuneWord word : RuneType.WORDS) {
        if (word == null) {
          continue; // ??
        }
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
      for (int i = 0; i < test.getContainerSize(); i++) {
        ItemStack oldStack = test.getItem(i);
        if (oldStack.getItem() == crafting.getItem()) {
          //.println("Durability test; old is " + oldStack.getDamage());
          crafting.setDamageValue(oldStack.getDamageValue());
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
    CompoundTag displayTag = new CompoundTag();
    //      displayTag.pu
    ListTag tagList = new ListTag();
    String escaped = "{\"text\":\"" + lore + "\",\"color\":\"gold\"}";
    tagList.add(StringTag.valueOf(escaped));
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
    crafting = EnchantmentHelper.enchantItem(event.getPlayer().level.random, crafting, 1, false);
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
