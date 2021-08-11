package com.lothrazar.enchantingrunes.runes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class RuneWord {

  private List<RuneType> runes = new ArrayList<>();
  private List<RuneEnch> enchants = new ArrayList<>();

  public RuneWord() {}

  public RuneWord(ResourceLocation id, List<RuneType> runes, RuneEnch... enchants) {
    this.runes = runes;
    this.enchants = Arrays.asList(enchants);
  }

  public RuneWord rune(RuneType... type) {
    runes.addAll(Arrays.asList(type));
    return this;
  }

  public RuneWord ench(String... ids) {
    return ench(1, ids);
  }

  public RuneWord ench(int level, String... ids) {
    for (String id : ids) {
      ench(level, id);
    }
    return this;
  }

  public RuneWord ench(int lvl, String id) {
    enchants.add(new RuneEnch(lvl, ResourceLocation.tryCreate(id)));
    return this;
  }

  public List<RuneType> getRunes() {
    return runes;
  }

  public void setRunes(List<RuneType> runes) {
    this.runes = runes;
  }

  public List<RuneEnch> getEnchants() {
    return enchants;
  }

  public void setEnchants(List<RuneEnch> enchants) {
    this.enchants = enchants;
  }

  public String hash() {
    String s = "";
    for (RuneType r : this.runes) {
      s += r.toString();
    }
    return s;
  }

  public boolean matches(CraftingInventory test, ItemStack crafting, Map<Integer, Boolean> used) {
    //stack being crafted can&will have enchants from previous runeword in this single craft
    int usedHere = 0;
    for (RuneEnch e : this.enchants) {
      //is it valid
      //           is(e.getId())
      Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(e.getId());
      if (!enchantment.canApply(crafting)) {
        //ok then yes
        System.out.println("an NO CANNOT apply to " + enchantment);
        return false;
      }
    }
    for (RuneType recipeRune : this.runes) {
      //we MUST find an R in this list. or else we return false.
      for (int i = 0; i < test.getSizeInventory(); i++) {
        if (usedHere >= this.runes.size()) {
          break; // we needed 3 runes for this word, already matched three, dont consume overflow
        }
        if (used.containsKey(i)) {
          continue; // used skip it
        }
        if (recipeRune.matches(test.getStackInSlot(i))) {
          //yes its used
          used.put(i, true);
          usedHere++;
          break;
        }
      }
    }
    // if i have 3 runes, at least that many must have been used/matched
    return usedHere >= this.runes.size();
  }

  public String getDisplayName() {
    String lore = "";
    for (RuneType r : this.runes) {
      lore += new ItemStack(r.getItem()).getDisplayName().getString();
    }
    return lore;
  }
}
