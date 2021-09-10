package com.lothrazar.enchantingrunes.item;

import com.lothrazar.enchantingrunes.RuneRegistry;
import com.lothrazar.enchantingrunes.block.BlockLayering;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class KnifeItem extends Item {

  private static final int COOLDOWN = 8;

  public KnifeItem(Properties properties) {
    super(properties.durability(64 * 2));
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
    tooltip.add(new TranslatableComponent(this.getDescriptionId() + ".tooltip").withStyle(ChatFormatting.GRAY));
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
    boolean validStone = state.getBlock() == Blocks.STONE_SLAB || state.getBlock() == Blocks.STONE || state.getBlock() == RuneRegistry.STONE_LAYERS.get();
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
      world.setBlockAndUpdate(pos, RuneRegistry.STONE_LAYERS.get().defaultBlockState().setValue(BlockLayering.LAYERS, newLayer));
    }
    else {
      world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
    }
  }

  private void dropItem(UseOnContext context) { //flip a coin; if pass then drop one
    Level world = context.getLevel();
    BlockPos pos = context.getClickedPos();
    dropHere(world, pos, new ItemStack(RuneRegistry.RUNE_BLANK.get()));
    //TODO: reuse biome RNG stuff in future maybe
    //    final double dice = world.rand.nextDouble();
    //    List<Item> pick = Arrays.asList(new Item[] {
    //        RuneRegistry.RUNE_A.get(), RuneRegistry.RUNE_A.get(), RuneRegistry.RUNE_A.get(), RuneRegistry.RUNE_A.get(), RuneRegistry.RUNE_A.get(),
    //        RuneRegistry.RUNE_E.get(), RuneRegistry.RUNE_E.get(), RuneRegistry.RUNE_E.get(), RuneRegistry.RUNE_E.get(), RuneRegistry.RUNE_E.get(),
    //        RuneRegistry.RUNE_I.get(), RuneRegistry.RUNE_I.get(),
    //        RuneRegistry.RUNE_O.get(), RuneRegistry.RUNE_O.get(),
    //        RuneRegistry.RUNE_U.get(), RuneRegistry.RUNE_U.get(),
    //        RuneRegistry.RUNE_Y.get(), RuneRegistry.RUNE_Y.get(),
    //        RuneRegistry.RUNE_TH.get()
    //    });
    //    if (dice < 0.01) {
    //      //first X% is stone layer
    //      dropHere(world, pos, new ItemStack(RuneRegistry.STONE_LAYERS_I.get()));
    //    }
    //    else {
    //      //first, you have a low chance to get ANY rune, weighted random defined in above array contents
    //      //doubly weight it based on biome bonus
    //      Biome biome = world.getBiome(pos);
    //      switch (biome.getCategory()) {
    //        case JUNGLE:
    //          //add weight
    //          pick.add(RuneRegistry.RUNE_TH.get());
    //          pick.add(RuneRegistry.RUNE_TH.get());
    //          pick.add(RuneRegistry.RUNE_U.get());
    //          pick.add(RuneRegistry.RUNE_U.get());
    //        break;
    //        case ICY:
    //          pick.add(RuneRegistry.RUNE_I.get());
    //          pick.add(RuneRegistry.RUNE_I.get());
    //          pick.add(RuneRegistry.RUNE_I.get());
    //          pick.add(RuneRegistry.RUNE_I.get());
    //        break;
    //        case DESERT:
    //        case MESA:
    //        case SAVANNA:
    //          pick.add(RuneRegistry.RUNE_O.get());
    //          pick.add(RuneRegistry.RUNE_O.get());
    //          pick.add(RuneRegistry.RUNE_O.get());
    //          pick.add(RuneRegistry.RUNE_O.get());
    //        break;
    //        case MUSHROOM:
    //          pick.add(RuneRegistry.RUNE_U.get());
    //          pick.add(RuneRegistry.RUNE_Y.get());
    //          pick.add(RuneRegistry.RUNE_U.get());
    //          pick.add(RuneRegistry.RUNE_Y.get());
    //          pick.add(RuneRegistry.RUNE_U.get());
    //          pick.add(RuneRegistry.RUNE_Y.get());
    //        break;
    //        case NETHER:
    //          pick.add(RuneRegistry.RUNE_TH.get());
    //          pick.add(RuneRegistry.RUNE_U.get());
    //          pick.add(RuneRegistry.RUNE_U.get());
    //        break;
    //        case SWAMP:
    //        case TAIGA:
    //          pick.add(RuneRegistry.RUNE_E.get());
    //          pick.add(RuneRegistry.RUNE_E.get());
    //          pick.add(RuneRegistry.RUNE_E.get());
    //          pick.add(RuneRegistry.RUNE_E.get());
    //        break;
    //        case THEEND:
    //          pick.add(RuneRegistry.RUNE_TH.get());
    //          pick.add(RuneRegistry.RUNE_TH.get());
    //          pick.add(RuneRegistry.RUNE_TH.get());
    //          pick.add(RuneRegistry.RUNE_TH.get());
    //        break;
    //        case BEACH:
    //        break;
    //        case EXTREME_HILLS:
    //        break;
    //        case FOREST:
    //        break;
    //        case NONE:
    //        break;
    //        case OCEAN:
    //        break;
    //        case PLAINS:
    //        break;
    //        case RIVER:
    //        break;
    //        default:
    //        break;
    //      }
    //      //final drop. exactly always one thing
    //      dropHere(world, pos, new ItemStack(pick.get(world.rand.nextInt(pick.size()))));
    //    }
  }

  private void dropHere(Level world, BlockPos pos, ItemStack rune) {
    if (!world.isClientSide) {
      world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, rune));
    }
  }
}
