package com.iafenvoy.avaritia.item.tool;

import com.iafenvoy.avaritia.registry.ModGameRules;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class InfinityAxeItem extends AxeItem {
    public InfinityAxeItem() {
        super(InfinityMaterial.MATERIAL, 1.0F, -2.2F, new Settings().rarity(Rarity.EPIC));
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        if (stack.isOf(this) && canBeBreak(state))
            return 9999;
        return super.getMiningSpeedMultiplier(stack, state);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        new Thread(() -> dfsTree(world, pos, 0, world.getGameRules().getInt(ModGameRules.INFINITY_AXE_RANGE))).start();
        return super.postMine(stack, world, state, pos, miner);
    }

    private static void dfsTree(World world, BlockPos pos, int direction, int remain) {
        BlockState state = world.getBlockState(pos);
        if (direction > 0 && !canBeBreak(state) || remain < 0) return;
        world.breakBlock(pos, true);
        if (direction != 2) dfsTree(world, pos.up(), 1, remain - 1);
        if (direction != 1) dfsTree(world, pos.down(), 2, remain - 1);
        if (direction != 5) dfsTree(world, pos.east(), 3, remain - 1);
        if (direction != 6) dfsTree(world, pos.south(), 4, remain - 1);
        if (direction != 3) dfsTree(world, pos.west(), 5, remain - 1);
        if (direction != 4) dfsTree(world, pos.north(), 6, remain - 1);
    }

    private static boolean canBeBreak(BlockState state) {
        return state.isIn(BlockTags.LOGS) || state.isIn(BlockTags.LEAVES);
    }
}
