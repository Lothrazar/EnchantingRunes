package com.lothrazar.enchantingrunes.runes;

import com.lothrazar.enchantingrunes.ConfigRegistryRunes;
import com.lothrazar.enchantingrunes.ModMainRunes;
import com.lothrazar.enchantingrunes.RegistryRunes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public enum RuneType {

  A, E, I, O, U, Y, T;

  public static final List<RuneWord> WORDS = new ArrayList<>();
  public static final Set<String> TAKEN = new HashSet<>();

  public static void addWord(RuneWord w) {
    if (TAKEN.contains(w.hash())) {
      throw new IllegalArgumentException("Error: runeword already exists " + w.hash());
    }
    WORDS.add(w);
    TAKEN.add(w.hash());
  }

  public static void initWords() {
    for (String s : ConfigRegistryRunes.THEWORDS.get()) {
      try {
        parseWord(s);
      }
      catch (Exception e) {
        ModMainRunes.LOGGER.info("Invalid runeword error " + s, e);
      }
    }
  }

  //  "minecraft:sharpness->aeiouyt"
  static void parseWord(String s) {
    String[] first = s.split("->");
    RuneWord rw = new RuneWord();
    String enchants = first[0];
    String theword = first[1];
    rw.ench(enchants.split(","));
    for (char c : theword.toCharArray()) {
      rw.rune(RuneType.fromChar(c));
    }
    addWord(rw);
  }

  static RuneType fromChar(char c) {
    //TODO: this is bad
    if (c == 'a') {
      return A;
    }
    if (c == 'e') {
      return E;
    }
    if (c == 'i') {
      return I;
    }
    if (c == 'o') {
      return O;
    }
    if (c == 'u') {
      return U;
    }
    if (c == 'y') {
      return Y;
    }
    if (c == 't') {
      return T;
    }
    return null;
  }

  @Override
  public String toString() {
    return name().toLowerCase();
  }

  public String lang() {
    return "item." + ModMainRunes.MODID + ".rune_" + this.toString();
  }

  boolean matches(ItemStack stack) {
    return stack.getItem() == this.getItem();
  }

  public Item getItem() {
    switch (this) {
      case A:
        return RegistryRunes.RUNE_A.get();
      case E:
        return RegistryRunes.RUNE_E.get();
      case I:
        return RegistryRunes.RUNE_I.get();
      case T:
        return RegistryRunes.RUNE_TH.get();
      case O:
        return RegistryRunes.RUNE_O.get();
      case U:
        return RegistryRunes.RUNE_U.get();
      case Y:
        return RegistryRunes.RUNE_Y.get();
      default:
      break;
    }
    return null;
  }
}
