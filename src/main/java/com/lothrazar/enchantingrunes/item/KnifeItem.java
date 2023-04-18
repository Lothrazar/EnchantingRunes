package com.lothrazar.enchantingrunes.item;

import com.lothrazar.enchantingrunes.RegistryRunes;
import com.lothrazar.library.block.BlockLayering;
import com.lothrazar.library.item.ItemFlib;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class KnifeItem extends ItemFlib {

  private static final int COOLDOWN = 8;

  public KnifeItem(Properties properties) {
    super(properties.durability(64 * 2));
  }

  @Override
  public InteractionResult useOn(UseOnContext context) {
    Player player = context.getPlayer();
    if (player.getCooldowns().isOnCooldown(this)) {
      return InteractionResult.PASS;
    }
    if (context.getClickedFace() == Direction.DOWN) {
      //sides and top ok
      return InteractionResult.PASS;
    }
    Level world = context.getLevel();
    BlockPos pos = context.getClickedPos();
    BlockState state = world.getBlockState(pos);
    ItemStack held = context.getItemInHand();
    boolean valid = this.isValid(context);
    if (valid) {
      player.getCooldowns().addCooldown(this, COOLDOWN);
      player.swing(context.getHand());
      world.playSound(player, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 1, 1);
      held.hurtAndBreak(1, player, p -> p.setItemInHand(context.getHand(), ItemStack.EMPTY));
      dropItem(context);
      reduceLayer(world, pos, state);
      return InteractionResult.SUCCESS;
    }
    return InteractionResult.PASS;
  }

  private boolean isValid(UseOnContext context) {
    Level world = context.getLevel();
    BlockPos pos = context.getClickedPos();
    BlockState state = world.getBlockState(pos);
    boolean validStone = state.getBlock() == Blocks.STONE_SLAB || state.getBlock() == Blocks.STONE || state.getBlock() == RegistryRunes.STONE_LAYERS.get();
    return validStone;
  }

  private void reduceLayer(Level world, BlockPos pos, BlockState state) {
    final int fullLay = 8;
    int oldLayer = state.hasProperty(BlockLayering.LAYERS) ? state.getValue(BlockLayering.LAYERS) : fullLay;
    if (state.getBlock() == Blocks.STONE_SLAB) {
      oldLayer = fullLay / 2;
    }
    int newLayer = oldLayer - 1;
    if (newLayer > 0) {
      world.setBlockAndUpdate(pos, RegistryRunes.STONE_LAYERS.get().defaultBlockState().setValue(BlockLayering.LAYERS, newLayer));
    }
    else {
      world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
    }
  }

  private void dropItem(UseOnContext context) { //flip a coin; if pass then drop one
    Level world = context.getLevel();
    BlockPos pos = context.getClickedPos();
    dropHere(world, pos, new ItemStack(RegistryRunes.RUNE_BLANK.get()));
  }

  private void dropHere(Level world, BlockPos pos, ItemStack rune) {
    if (!world.isClientSide) {
      world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, rune));
    }
  }
}
