package com.lothrazar.enchantingrunes.runes;

import com.lothrazar.enchantingrunes.ModMainRunes;
import com.lothrazar.enchantingrunes.event.ConfigRuneManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public enum RuneType {

  A, E, I, O, U, Y, T;

  public static final List<RuneWord> words = new ArrayList<>();
  public static final Set<String> taken = new HashSet<>();

  public static void addWord(RuneWord w) {
    if (taken.contains(w.hash())) {
      throw new IllegalArgumentException("Error: runeword already exists " + w.hash());
    }
    words.add(w);
    taken.add(w.hash());
  }

  public static void initWords() {
    //    "enchant:id" ->  
    //    addWord(new RuneWord().ench("minecraft:fortune").rune(E).rune(U).rune(T));
    //    addWord(new RuneWord().ench("minecraft:silk_touch").rune(E).rune(T).rune(A));
    //    addWord(new RuneWord().ench("minecraft:sweeping").rune(T).rune(Y));
    //    addWord(new RuneWord().ench("minecraft:smite").rune(O));
    //    addWord(new RuneWord().ench("cyclic:auto_smelt").rune(E).rune(E).rune(I));
    //    addWord(new RuneWord().ench("cyclic:beheading").rune(E).rune(Y));
    //    addWord(new RuneWord().ench("cyclic:life_leech").rune(A).rune(T));
    //    addWord(new RuneWord().ench("cyclic:disarm").rune(U).rune(E));
    //    addWord(new RuneWord().ench("cyclic:ender").rune(T).rune(E));
    //    addWord(new RuneWord().ench("cyclic:growth").rune(T).rune(A).rune(O));
    for (ResourceLocation e : ForgeRegistries.ENCHANTMENTS.getKeys()) {
      System.out.println(e.toString());
    }
    System.out.println("====" + ForgeRegistries.ENCHANTMENTS.getKeys().size());
    for (String s : ConfigRuneManager.THEWORDS.get()) {
      System.out.println("=" + s);
      try {
        parseWord(s);
      }
      catch (Exception e) {
        ModMainRunes.LOGGER.info("Invalid runeword error", e);
      }
    }
    System.out.println("w" + words.size());
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
        return RuneRegistry.RUNE_A.get();
      case E:
        return RuneRegistry.RUNE_E.get();
      case I:
        return RuneRegistry.RUNE_I.get();
      case T:
        return RuneRegistry.RUNE_TH.get();
      case O:
        return RuneRegistry.RUNE_O.get();
      case U:
        return RuneRegistry.RUNE_U.get();
      case Y:
        return RuneRegistry.RUNE_Y.get();
      default:
      break;
    }
    return null;
  }
}
