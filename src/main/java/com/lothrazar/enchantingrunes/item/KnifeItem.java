package com.lothrazar.enchantingrunes.item;

import com.lothrazar.enchantingrunes.RuneRegistry;
import com.lothrazar.enchantingrunes.block.BlockLayering;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class KnifeItem extends Item {

  private static final double CHANCE_MAKE_RUNESTONE = 0.90; // when using blade
  private static final int COOLDOWN = 8;

  public KnifeItem(Properties properties) {
    super(properties.maxDamage(64 * 2));
  }

  @Override
  public ActionResultType onItemUse(ItemUseContext context) {
    PlayerEntity player = context.getPlayer();
    if (player.getCooldownTracker().hasCooldown(this)) {
      return ActionResultType.PASS;
    }
    if (context.getFace() == Direction.DOWN) {
      //sides and top ok
      return ActionResultType.PASS;
    }
    World world = context.getWorld();
    BlockPos pos = context.getPos();
    BlockState state = world.getBlockState(pos);
    ItemStack held = context.getItem();
    boolean valid = this.isValid(context);
    if (valid) {
      player.getCooldownTracker().setCooldown(this, COOLDOWN);
      player.swingArm(context.getHand());
      world.playSound(player, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundCategory.BLOCKS, 1, 1);
      held.damageItem(1, player, p -> p.setHeldItem(context.getHand(), ItemStack.EMPTY));
      dropItem(context);
      reduceLayer(world, pos, state);
      return ActionResultType.SUCCESS;
    }
    return ActionResultType.PASS;
  }

  private boolean isValid(ItemUseContext context) {
    World world = context.getWorld();
    BlockPos pos = context.getPos();
    BlockState state = world.getBlockState(pos);
    boolean validStone = state.getBlock() == Blocks.STONE_SLAB || state.getBlock() == Blocks.STONE || state.getBlock() == RuneRegistry.STONE_LAYERS.get();
    return validStone;
  }

  private void reduceLayer(World world, BlockPos pos, BlockState state) {
    final int fullLay = 8;
    int oldLayer = state.hasProperty(BlockLayering.LAYERS) ? state.get(BlockLayering.LAYERS) : fullLay;
    if (state.getBlock() == Blocks.STONE_SLAB) {
      oldLayer = fullLay / 2;
    }
    int newLayer = oldLayer - 1;
    if (newLayer > 0) {
      world.setBlockState(pos, RuneRegistry.STONE_LAYERS.get().getDefaultState().with(BlockLayering.LAYERS, newLayer));
    }
    else {
      world.setBlockState(pos, Blocks.AIR.getDefaultState());
    }
  }

  private void dropItem(ItemUseContext context) { //flip a coin; if pass then drop one
    World world = context.getWorld();
    BlockPos pos = context.getPos();
    Biome b = world.getBiome(pos);
    System.out.println("b " + b);
    System.out.println("cat " + b.getCategory());
    ItemStack rune = new ItemStack(RuneRegistry.STONE_LAYERS_I.get());
    if (world.rand.nextDouble() < CHANCE_MAKE_RUNESTONE) {
      // 10 % chance it stays as stone layer item
      Item[] pick = new Item[] { RuneRegistry.RUNE_A.get(), RuneRegistry.RUNE_E.get(), RuneRegistry.RUNE_I.get(), RuneRegistry.RUNE_O.get(), RuneRegistry.RUNE_U.get(), RuneRegistry.RUNE_Y.get(), RuneRegistry.RUNE_ITH.get() };
      switch (b.getCategory()) {
        case BEACH:
        case JUNGLE:
          pick = new Item[] { RuneRegistry.RUNE_A.get(), RuneRegistry.RUNE_E.get(), RuneRegistry.RUNE_I.get(), RuneRegistry.RUNE_O.get() };
        break;
        case EXTREME_HILLS:
        case FOREST:
          pick = new Item[] { RuneRegistry.RUNE_E.get(), RuneRegistry.RUNE_I.get(), RuneRegistry.RUNE_O.get(), RuneRegistry.RUNE_U.get() };
        break;
        case ICY:
          pick = new Item[] { RuneRegistry.RUNE_A.get(), RuneRegistry.RUNE_O.get(), RuneRegistry.RUNE_U.get(), RuneRegistry.RUNE_U.get() };
        break;
        case DESERT:
        case MESA:
        case SAVANNA:
          pick = new Item[] { RuneRegistry.RUNE_E.get(), RuneRegistry.RUNE_E.get(), RuneRegistry.RUNE_U.get(), RuneRegistry.RUNE_Y.get() };
        break;
        case MUSHROOM:
          pick = new Item[] { RuneRegistry.RUNE_I.get(), RuneRegistry.RUNE_O.get(), RuneRegistry.RUNE_Y.get(), RuneRegistry.RUNE_Y.get(), RuneRegistry.RUNE_Y.get(), RuneRegistry.RUNE_ITH.get() };
        break;
        case NETHER:
        case NONE:
          pick = new Item[] { RuneRegistry.RUNE_A.get(), RuneRegistry.RUNE_A.get(), RuneRegistry.RUNE_U.get(), RuneRegistry.RUNE_ITH.get() };
        break;
        case PLAINS:
          pick = new Item[] { RuneRegistry.RUNE_I.get(), RuneRegistry.RUNE_I.get(), RuneRegistry.RUNE_Y.get(), RuneRegistry.RUNE_ITH.get() };
        break;
        case OCEAN:
        case RIVER:
          pick = new Item[] { RuneRegistry.RUNE_E.get(), RuneRegistry.RUNE_I.get(), RuneRegistry.RUNE_I.get(), RuneRegistry.RUNE_I.get(), RuneRegistry.RUNE_O.get() };
        break;
        case SWAMP:
        case TAIGA:
          pick = new Item[] { RuneRegistry.RUNE_E.get(), RuneRegistry.RUNE_U.get(), RuneRegistry.RUNE_U.get(), RuneRegistry.RUNE_Y.get() };
        break;
        case THEEND:
          pick = new Item[] { RuneRegistry.RUNE_O.get(), RuneRegistry.RUNE_Y.get(), RuneRegistry.RUNE_ITH.get(), RuneRegistry.RUNE_ITH.get(), RuneRegistry.RUNE_ITH.get() };
        break;
      }
      rune = new ItemStack(pick[world.rand.nextInt(pick.length)]);
    }
    if (!world.isRemote) {
      world.addEntity(new ItemEntity(world, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, rune));
    }
  }
}
