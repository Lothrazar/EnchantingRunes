package com.lothrazar.enchantingrunes.item;

import com.lothrazar.enchantingrunes.RuneRegistry;
import com.lothrazar.enchantingrunes.block.BlockLayering;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class KnifeItem extends Item {

  private static final int COOLDOWN = 10;

  public KnifeItem(Properties properties) {
    super(properties);
  }

  @Override
  public ActionResultType onItemUse(ItemUseContext context) {
    if (context.getPlayer().getCooldownTracker().hasCooldown(this)) {
      return ActionResultType.PASS;
    }
    World world = context.getWorld();
    BlockPos pos = context.getPos();
    BlockState state = world.getBlockState(pos);
    ItemStack held = context.getItem();
    boolean valid = state.getBlock() == Blocks.STONE || state.getBlock() == RuneRegistry.STONE_LAYERS.get();
    if (context.getFace() == Direction.UP && valid) {
      context.getPlayer().getCooldownTracker().setCooldown(this, COOLDOWN);
      ItemStack rune = new ItemStack(RuneRegistry.BLANK_RUNE.get());
      world.addEntity(new ItemEntity(world, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, rune));
      int oldLayer = state.hasProperty(BlockLayering.LAYERS) ? state.get(BlockLayering.LAYERS) : 8;
      int newLayer = oldLayer - 1;
      if (newLayer > 0) {
        world.setBlockState(pos, RuneRegistry.STONE_LAYERS.get().getDefaultState().with(BlockLayering.LAYERS, newLayer));
      }
      else {
        world.setBlockState(pos, Blocks.AIR.getDefaultState());
      }
      world.playSound(context.getPlayer(), pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundCategory.BLOCKS, 1, 1);
      held.damageItem(1, context.getPlayer(), p -> p.setHeldItem(context.getHand(), ItemStack.EMPTY));
    }
    return ActionResultType.PASS;
  }
}
