package com.lothrazar.enchantingrunes.event;

import java.util.HashMap;
import java.util.Map;
import com.lothrazar.enchantingrunes.ModMainRunes;
import com.lothrazar.enchantingrunes.runes.RuneEnch;
import com.lothrazar.enchantingrunes.runes.RuneType;
import com.lothrazar.enchantingrunes.runes.RuneWord;
import com.lothrazar.library.util.ItemStackUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
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
        ItemStackUtil.applyRandomEnch(event.getEntity().level().random, crafting);
        //no lore 
        ItemStackUtil.addLoreToStack(crafting, "-", null);
        //done now check damage
      }
      else {
        EnchantmentHelper.setEnchantments(doIt, crafting);
        ItemStackUtil.addLoreToStack(crafting, lore, null);
      }
    }
    else {
      //gotta go random
      ItemStackUtil.applyRandomEnch(event.getEntity().level().random, crafting);
    }
  }
}
