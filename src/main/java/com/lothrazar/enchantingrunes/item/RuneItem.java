package com.lothrazar.enchantingrunes.item;

import com.lothrazar.enchantingrunes.RuneRegistry;
import com.lothrazar.enchantingrunes.runes.RuneType;
import com.lothrazar.enchantingrunes.runes.RuneWord;
import java.util.List;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RuneItem extends Item {

  private static final int COOLDOWN = 8;

  public RuneItem(Properties properties) {
    super(properties);
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    tooltip.add(new TranslationTextComponent("item.enchantingrunes.rune.tooltip").mergeStyle(TextFormatting.DARK_GREEN));
  }

  @Override
  public Rarity getRarity(ItemStack stack) {
    return (this == RuneRegistry.RUNE_BLANK.get()) ? super.getRarity(stack) : Rarity.EPIC;
  }

  @Override
  public ActionResultType onItemUse(ItemUseContext context) {
    PlayerEntity player = context.getPlayer();
    if (!player.world.isRemote || player.getCooldownTracker().hasCooldown(this)) {
      return ActionResultType.PASS;
    }
    //get all runes for this 
    player.sendMessage(context.getItem().getDisplayName(), player.getUniqueID());
    for (RuneWord w : RuneType.WORDS) {
      //do i match it
      if (w.contains(this)) {
        player.sendMessage(w.getMessage(), player.getUniqueID());
      }
    }
    player.getCooldownTracker().setCooldown(this, COOLDOWN);
    return super.onItemUse(context);
  }
}
