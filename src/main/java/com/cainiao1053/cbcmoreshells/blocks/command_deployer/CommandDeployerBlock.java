package com.cainiao1053.cbcmoreshells.blocks.command_deployer;

import com.cainiao1053.cbcmoreshells.Cbcmoreshells;
import com.cainiao1053.cbcmoreshells.index.CBCMSBlockEntities;
import com.cainiao1053.cbcmoreshells.munitions.dual_cannon.combat_command.CombatCommandBaseItem;
import com.simibubi.create.content.kinetics.base.DirectionalAxisKineticBlock;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.foundation.block.IBE;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Vector3dc;
import org.slf4j.Logger;
import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.mod.common.VSGameUtilsKt;

public class CommandDeployerBlock extends Block implements IBE<CommandDeployerBlockEntity> {

    public CommandDeployerBlock(Properties properties) {
        super(properties);
        //registerDefaultState(defaultBlockState());
        registerDefaultState(defaultBlockState().setValue(POWERED, true));
    }

    Logger LOGGER = Cbcmoreshells.LOGGER;

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
        builder.add(FACING);
        super.createBlockStateDefinition(builder);
    }

    public static Direction getFacing(BlockState state) {
        return state.getValue(FACING);
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_,
                               CollisionContext p_220053_4_) {
        return Block.box(0, 0, 0, 16, 6, 16);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState p_220071_1_, BlockGetter p_220071_2_, BlockPos p_220071_3_,
                                        CollisionContext p_220071_4_) {
        return Block.box(0, 0, 0, 16, 6, 16);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource pRandom) {
        boolean previouslyPowered = state.getValue(POWERED);
        if (previouslyPowered != level.hasNeighborSignal(pos)){
            level.setBlock(pos, state.cycle(POWERED), 2);
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block p_220069_4_,
                                BlockPos neighbourPos, boolean p_220069_6_) {
        boolean previouslyPowered = state.getValue(POWERED);
        if (previouslyPowered != level.hasNeighborSignal(pos)) {
            level.setBlock(pos, state.cycle(POWERED), 2);
            if(!previouslyPowered) {
                CommandDeployerBlockEntity be =(CommandDeployerBlockEntity) level.getBlockEntity(pos);
                ItemStack stack = be.getInventory().getStackInSlot(0);
                if(stack.isEmpty() || !(stack.getItem() instanceof CombatCommandBaseItem)){
                    return;
                }
                be.activateCannons();
            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if(level.isClientSide){
            return InteractionResult.SUCCESS;
        }
        ItemStack stack = player.getItemInHand(hand);
        CommandDeployerBlockEntity be =(CommandDeployerBlockEntity) level.getBlockEntity(pos);
        if(stack.isEmpty()){
            player.setItemInHand(hand, be.extractStack(0));
            return InteractionResult.CONSUME;
        }
        if (be != null && (stack.getItem() instanceof CombatCommandBaseItem)) {
            ItemStack remain = be.insertStack(stack.copy(), 0, false);
            if (remain.getCount() != stack.getCount()) {
                player.setItemInHand(hand, remain);
//					level.playSound(null, pos, SoundEvents.ITEM_PICKUP,
//							SoundSource.BLOCKS, 0.25f, 1.0f);
                return InteractionResult.CONSUME;
            }
        }
        return super.use(state, level, pos, player, hand, result);
    }

    public BlockState getStateForPlacement(BlockPlaceContext p_196258_1_) {
//        return super.getStateForPlacement(p_196258_1_).setValue(POWERED, p_196258_1_.getLevel()
//                .hasNeighborSignal(p_196258_1_.getClickedPos())).setValue(FACING, p_196258_1_.getNearestLookingDirection().getOpposite());
        return super.getStateForPlacement(p_196258_1_).setValue(POWERED, p_196258_1_.getLevel()
                .hasNeighborSignal(p_196258_1_.getClickedPos())).setValue(FACING, p_196258_1_.getHorizontalDirection().getOpposite());
    }
    
    @Override
    public Class<CommandDeployerBlockEntity> getBlockEntityClass() {
        return CommandDeployerBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends CommandDeployerBlockEntity> getBlockEntityType() {
        return CBCMSBlockEntities.COMMAND_DEPLOYER.get();
    }

}