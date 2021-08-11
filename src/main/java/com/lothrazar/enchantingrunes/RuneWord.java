package com.lothrazar.enchantingrunes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

  public boolean matches(CraftingInventory test) {
    Map<Integer, Boolean> used = new HashMap<>();
    for (RuneType r : this.runes) {
      //we MUST find an R in this list. or else we return false.
      for (int i = 0; i < test.getSizeInventory(); i++) {
        if (used.containsKey(i)) {
          continue; // used skip it
        }
        if (r.matches(test.getStackInSlot(i))) {
          //yes
          used.put(i, true);
          break;
        }
      }
    }
    // if i have 3 runes, at least that many must have been used/matched
    return used.keySet().size() >= this.runes.size();
  }

  public void applyEnchants(HashMap<Enchantment, Integer> doIt) {
    for (RuneEnch ench : this.enchants) {
      Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(ench.getId());
      doIt.put(enchantment, ench.getLvl());
    }
  }

  public String getDisplayName() {
    String lore = "";
    for (RuneType r : this.runes) {
      lore += new ItemStack(r.getItem()).getDisplayName().getString();
    }
    return lore;
  }
}
