package com.lothrazar.enchantingrunes.runes;

import net.minecraft.resources.Identifier;

public class RuneEnch {

  private int lvl;
  private Identifier id;

  public RuneEnch(int lvl, Identifier id) {
    super();
    this.lvl = lvl;
    this.id = id;
  }

  public int getLvl() {
    return lvl;
  }

  public void setLvl(int lvl) {
    this.lvl = lvl;
  }

  public Identifier getId() {
    return id;
  }

  public void setId(Identifier id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return (lvl == 1) ? id.toString() : id.toString() + ":" + lvl;
  }
}
