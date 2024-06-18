package com.iafenvoy.avaritia.item.tool;

import com.iafenvoy.avaritia.registry.AvaritiaGameRules;
import com.iafenvoy.avaritia.util.ThreadUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;

public class InfinityHoeItem extends HoeItem {
    public InfinityHoeItem() {
        super(InfinityMaterial.MATERIAL, 0, -2.2F, new Settings().rarity(Rarity.EPIC));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ItemStack stack = context.getStack();
        if (stack.isOf(this) && context.getWorld() instanceof ServerWorld serverWorld) {
            if (context.getPlayer() == null || !context.getPlayer().isSneaking())
                ThreadUtil.execute(() -> {
                    final int r = serverWorld.getGameRules().getInt(AvaritiaGameRules.INFINITY_HOE_RANGE);
                    final BlockPos pos = context.getBlockPos();
                    for (int i = pos.getX() - r; i <= pos.getX() + r; i++)
                        for (int j = pos.getZ() - r; j <= pos.getZ() + r; j++)
                            for (int k = pos.getY() - r; k <= pos.getY() + r; k++) {
                                BlockPos blockPos = new BlockPos(i, k, j);
                                BlockState block = serverWorld.getBlockState(blockPos);
                                if (!TILLING_ACTIONS.containsKey(block.getBlock()) || !TILLING_ACTIONS.get(block.getBlock()).getFirst().test(context))
                                    continue;
                                BlockState above = serverWorld.getBlockState(blockPos.up());
                                if (above.getBlock().getHardness() <= 0.2) {
                                    if (!above.isOf(Blocks.AIR))
                                        serverWorld.breakBlock(blockPos.up(), true);
                                    serverWorld.setBlockState(blockPos, Blocks.FARMLAND.getDefaultState().with(FarmlandBlock.MOISTURE, 7));
                                }
                            }
                }, serverWorld.getGameRules().getBoolean(AvaritiaGameRules.SYNC_BREAK));
        }
        return super.useOnBlock(context);
    }
}
