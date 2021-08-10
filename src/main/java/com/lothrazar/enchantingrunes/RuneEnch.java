package com.lothrazar.enchantingrunes;

import net.minecraft.util.ResourceLocation;

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
}
