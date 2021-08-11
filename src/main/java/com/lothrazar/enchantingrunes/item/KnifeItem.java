package com.lothrazar.enchantingrunes.item;

import com.lothrazar.enchantingrunes.RuneRegistry;
import com.lothrazar.enchantingrunes.block.BlockLayering;
import java.util.Arrays;
import java.util.List;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class KnifeItem extends Item {

  private static final int COOLDOWN = 8;

  public KnifeItem(Properties properties) {
    super(properties.maxDamage(64 * 2));
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    tooltip.add(new TranslationTextComponent(this.getTranslationKey() + ".tooltip").mergeStyle(TextFormatting.GRAY));
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
    final double dice = world.rand.nextDouble();
    List<Item> pick = Arrays.asList(new Item[] {
        RuneRegistry.RUNE_A.get(), RuneRegistry.RUNE_A.get(), RuneRegistry.RUNE_A.get(), RuneRegistry.RUNE_A.get(), RuneRegistry.RUNE_A.get(),
        RuneRegistry.RUNE_E.get(), RuneRegistry.RUNE_E.get(), RuneRegistry.RUNE_E.get(), RuneRegistry.RUNE_E.get(), RuneRegistry.RUNE_E.get(),
        RuneRegistry.RUNE_I.get(), RuneRegistry.RUNE_I.get(),
        RuneRegistry.RUNE_O.get(), RuneRegistry.RUNE_O.get(),
        RuneRegistry.RUNE_U.get(), RuneRegistry.RUNE_U.get(),
        RuneRegistry.RUNE_Y.get(), RuneRegistry.RUNE_Y.get(),
        RuneRegistry.RUNE_TH.get()
    });
    if (dice < 0.01) {
      //first X% is stone layer
      dropHere(world, pos, new ItemStack(RuneRegistry.STONE_LAYERS_I.get()));
    }
    else {
      //first, you have a low chance to get ANY rune, weighted random defined in above array contents
      //doubly weight it based on biome bonus
      Biome biome = world.getBiome(pos);
      switch (biome.getCategory()) {
        case JUNGLE:
          //add weight
          pick.add(RuneRegistry.RUNE_TH.get());
          pick.add(RuneRegistry.RUNE_TH.get());
          pick.add(RuneRegistry.RUNE_U.get());
          pick.add(RuneRegistry.RUNE_U.get());
        break;
        case ICY:
          pick.add(RuneRegistry.RUNE_I.get());
          pick.add(RuneRegistry.RUNE_I.get());
          pick.add(RuneRegistry.RUNE_I.get());
          pick.add(RuneRegistry.RUNE_I.get());
        break;
        case DESERT:
        case MESA:
        case SAVANNA:
          pick.add(RuneRegistry.RUNE_O.get());
          pick.add(RuneRegistry.RUNE_O.get());
          pick.add(RuneRegistry.RUNE_O.get());
          pick.add(RuneRegistry.RUNE_O.get());
        break;
        case MUSHROOM:
          pick.add(RuneRegistry.RUNE_U.get());
          pick.add(RuneRegistry.RUNE_Y.get());
          pick.add(RuneRegistry.RUNE_U.get());
          pick.add(RuneRegistry.RUNE_Y.get());
          pick.add(RuneRegistry.RUNE_U.get());
          pick.add(RuneRegistry.RUNE_Y.get());
        break;
        case NETHER:
          pick.add(RuneRegistry.RUNE_TH.get());
          pick.add(RuneRegistry.RUNE_U.get());
          pick.add(RuneRegistry.RUNE_U.get());
        break;
        case SWAMP:
        case TAIGA:
          pick.add(RuneRegistry.RUNE_E.get());
          pick.add(RuneRegistry.RUNE_E.get());
          pick.add(RuneRegistry.RUNE_E.get());
          pick.add(RuneRegistry.RUNE_E.get());
        break;
        case THEEND:
          pick.add(RuneRegistry.RUNE_TH.get());
          pick.add(RuneRegistry.RUNE_TH.get());
          pick.add(RuneRegistry.RUNE_TH.get());
          pick.add(RuneRegistry.RUNE_TH.get());
        break;
        case BEACH:
        break;
        case EXTREME_HILLS:
        break;
        case FOREST:
        break;
        case NONE:
        break;
        case OCEAN:
        break;
        case PLAINS:
        break;
        case RIVER:
        break;
        default:
        break;
      }
      //final drop. exactly always one thing
      dropHere(world, pos, new ItemStack(pick.get(world.rand.nextInt(pick.size()))));
    }
  }

  private void dropHere(World world, BlockPos pos, ItemStack rune) {
    if (!world.isRemote) {
      world.addEntity(new ItemEntity(world, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, rune));
    }
  }
}
