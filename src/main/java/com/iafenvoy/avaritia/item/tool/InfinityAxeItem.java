package com.iafenvoy.avaritia.item.tool;

import com.iafenvoy.avaritia.item.MatterClusterItem;
import com.iafenvoy.avaritia.registry.ModGameRules;
import com.iafenvoy.avaritia.util.Consumer2;
import com.iafenvoy.avaritia.util.Pair;
import com.iafenvoy.avaritia.util.ThreadUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
        if (world instanceof ServerWorld serverWorld && !miner.isSneaking()) {
            ThreadUtil.execute(() -> {
                List<ItemStack> packed = new ArrayList<>();
                final LinkedList<Pair<BlockPos, Integer>> queue = new LinkedList<>();
                final Consumer2<BlockPos, Integer> allPos = (p, l) -> {
                    if (l < 0) return;
                    queue.add(Pair.of(p.up(), l));
                    queue.add(Pair.of(p.down(), l));
                    queue.add(Pair.of(p.east(), l));
                    queue.add(Pair.of(p.south(), l));
                    queue.add(Pair.of(p.west(), l));
                    queue.add(Pair.of(p.north(), l));
                };
                allPos.accept(pos, serverWorld.getGameRules().getInt(ModGameRules.INFINITY_AXE_RANGE));
                while (!queue.isEmpty()) {
                    Pair<BlockPos, Integer> p = queue.remove();
                    BlockState s = serverWorld.getBlockState(p.first());
                    if (!canBeBreak(s)) continue;
                    BlockEntity blockEntity = serverWorld.getBlockEntity(p.first());
                    List<ItemStack> drops = Block.getDroppedStacks(s, serverWorld, pos, blockEntity);
                    for (ItemStack drop : drops) {
                        boolean inserted = false;
                        for (ItemStack itemStack : packed)
                            if (ItemStack.canCombine(itemStack, drop)) {
                                itemStack.increment(drop.getCount());
                                inserted = true;
                                break;
                            }
                        if (!inserted)
                            packed.add(drop);
                    }
                    serverWorld.breakBlock(p.first(), false);
                    allPos.accept(p.first(), p.second() - 1);
                }
                miner.dropStack(MatterClusterItem.create(packed));
            }, serverWorld.getGameRules().getBoolean(ModGameRules.SYNC_BREAK));
        }
        return super.postMine(stack, world, state, pos, miner);
    }

    private static boolean canBeBreak(BlockState state) {
        return state.isIn(BlockTags.LOGS) || state.isIn(BlockTags.LEAVES);
    }
}
