package com.lothrazar.enchantingrunes.item;

import com.lothrazar.enchantingrunes.RegistryRunes;
import com.lothrazar.enchantingrunes.runes.RuneType;
import com.lothrazar.enchantingrunes.runes.RuneWord;
import com.lothrazar.library.item.ItemFlib;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;

public class RuneItem extends ItemFlib {

  private static final int COOLDOWN = 8;

  public RuneItem(Properties properties) {
    super(properties);
  }

  @Override
  public Rarity getRarity(ItemStack stack) {
    return (this == RegistryRunes.RUNE_BLANK.get()) ? super.getRarity(stack) : Rarity.EPIC;
  }

  @Override
  public InteractionResult useOn(UseOnContext context) {
    Player player = context.getPlayer();
    if (!player.level().isClientSide || player.getCooldowns().isOnCooldown(this)) {
      return InteractionResult.PASS;
    }
    //get all runes for this 
    player.sendSystemMessage(context.getItemInHand().getHoverName());
    for (RuneWord w : RuneType.WORDS) {
      //do i match it
      if (w.contains(this)) {
        player.sendSystemMessage(w.getMessage());
      }
    }
    player.getCooldowns().addCooldown(this, COOLDOWN);
    return super.useOn(context);
  }
}
