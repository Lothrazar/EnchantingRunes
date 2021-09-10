package com.lothrazar.enchantingrunes.item;

import com.lothrazar.enchantingrunes.RuneRegistry;
import com.lothrazar.enchantingrunes.runes.RuneType;
import com.lothrazar.enchantingrunes.runes.RuneWord;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RuneItem extends Item {

  private static final int COOLDOWN = 8;

  public RuneItem(Properties properties) {
    super(properties);
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
    tooltip.add(new TranslatableComponent("item.enchantingrunes.rune.tooltip").withStyle(ChatFormatting.DARK_GREEN));
  }

  @Override
  public Rarity getRarity(ItemStack stack) {
    return (this == RuneRegistry.RUNE_BLANK.get()) ? super.getRarity(stack) : Rarity.EPIC;
  }

  @Override
  public InteractionResult useOn(UseOnContext context) {
    Player player = context.getPlayer();
    if (!player.level.isClientSide || player.getCooldowns().isOnCooldown(this)) {
      return InteractionResult.PASS;
    }
    //get all runes for this 
    player.sendMessage(context.getItemInHand().getHoverName(), player.getUUID());
    for (RuneWord w : RuneType.WORDS) {
      //do i match it
      if (w.contains(this)) {
        player.sendMessage(w.getMessage(), player.getUUID());
      }
    }
    player.getCooldowns().addCooldown(this, COOLDOWN);
    return super.useOn(context);
  }
}
