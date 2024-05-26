package com.iafenvoy.avaritia.item.tool;

import com.iafenvoy.avaritia.AvaritiaReborn;
import com.iafenvoy.avaritia.item.MatterClusterItem;
import com.iafenvoy.avaritia.util.ToolMaterialUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class InfinityShovelItem extends ShovelItem {
    public static final String DESTROYER_NBT = AvaritiaReborn.MOD_ID + ":destroyer";

    public InfinityShovelItem() {
        super(InfinityMaterial.MATERIAL, 1.0F, -2.2F, new Settings().rarity(Rarity.EPIC));
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        if (stack.isOf(this) && !stack.getOrCreateNbt().getBoolean(DESTROYER_NBT))
            return 9999;
        return super.getMiningSpeedMultiplier(stack, state);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (stack.isOf(this) && user.isSneaking()) {
            boolean isDestroyer = stack.getOrCreateNbt().getBoolean(DESTROYER_NBT);
            stack.getOrCreateNbt().putBoolean(DESTROYER_NBT, !isDestroyer);
        }
        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.literal(stack.getOrCreateNbt().getBoolean(DESTROYER_NBT) ? "Destroyer Mode" : "Shovel Mode"));
    }


    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (stack.isOf(this) && world instanceof ServerWorld serverWorld) {
            boolean isDestroyer = stack.getOrCreateNbt().getBoolean(DESTROYER_NBT);
            if (isDestroyer) {
                new Thread(() -> {
                    final int r = 8;
                    List<ItemStack> packed = new ArrayList<>();
                    for (int i = pos.getX() - r; i <= pos.getX() + r; i++)
                        for (int j = pos.getZ() - r; j <= pos.getZ() + r; j++)
                            for (int k = pos.getY(); k >= pos.getY() - r * 2 && k >= world.getBottomY(); k--) {
                                BlockPos blockPos = new BlockPos(i, k, j);
                                BlockState block = world.getBlockState(blockPos);
                                if (!block.isIn(BlockTags.SHOVEL_MINEABLE)) continue;
                                BlockEntity blockEntity = world.getBlockEntity(blockPos);
                                List<ItemStack> drops = Block.getDroppedStacks(block, serverWorld, pos, blockEntity);
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
                                serverWorld.breakBlock(blockPos, false);
                            }
                    ItemStack result = MatterClusterItem.create(packed);
                    miner.dropStack(result);
                }).start();
            }
        }
        return super.postMine(stack, world, state, pos, miner);
    }
}
