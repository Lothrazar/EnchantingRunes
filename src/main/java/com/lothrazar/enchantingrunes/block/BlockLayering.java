package com.lothrazar.enchantingrunes.block;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class BlockLayering extends Block {

  public static final IntegerProperty LAYERS = SnowBlock.LAYERS;
  protected static final VoxelShape[] SHAPES = new VoxelShape[] { VoxelShapes.empty(), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
      Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
      Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
      Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D) };

  public BlockLayering(Block.Properties props) {
    super(props);
    this.setDefaultState(this.stateContainer.getBaseState().with(LAYERS, 1));
  }

  @Override
  protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
    builder.add(LAYERS);
  }

  @Override
  public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
    return SHAPES[state.get(LAYERS)];
  }

  @Override
  public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
    return SHAPES[state.get(LAYERS) - 1];
  }

  @Override
  public boolean isTransparent(BlockState state) {
    return true;
  }

  @SuppressWarnings("deprecation")
  @Override
  public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
    return !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
  }

  @Override
  public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
    int i = state.get(LAYERS);
    if (useContext.getItem().getItem() == this.asItem() && i < 8) {
      if (useContext.replacingClickedOnBlock()) {
        return useContext.getFace() == Direction.UP;
      }
      else {
        return true;
      }
    }
    else {
      return i == 1;
    }
  }

  @Override
  @Nullable
  public BlockState getStateForPlacement(BlockItemUseContext context) {
    BlockState blockstate = context.getWorld().getBlockState(context.getPos());
    if (blockstate.getBlock() == this) {
      int i = blockstate.get(LAYERS);
      return blockstate.with(LAYERS, Integer.valueOf(Math.min(8, i + 1)));
    }
    else {
      return super.getStateForPlacement(context);
    }
  }
}
