package com.lothrazar.enchantingrunes.runes;

import net.minecraft.resources.ResourceLocation;

public class RuneEnch {

  private int lvl;
  private ResourceLocation id;

  public RuneEnch(int lvl, ResourceLocation id) {
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

  public ResourceLocation getId() {
    return id;
  }

  public void setId(ResourceLocation id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return (lvl == 1) ? id.toString() : id.toString() + ":" + lvl;
  }
}
