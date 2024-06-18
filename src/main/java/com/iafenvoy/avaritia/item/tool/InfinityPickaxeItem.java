package com.iafenvoy.avaritia.item.tool;

import com.iafenvoy.avaritia.AvaritiaReborn;
import com.iafenvoy.avaritia.item.MatterClusterItem;
import com.iafenvoy.avaritia.registry.ModGameRules;
import com.iafenvoy.avaritia.util.ThreadUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
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
import java.util.Map;

public class InfinityPickaxeItem extends PickaxeItem {
    public static final String HAMMER_NBT = AvaritiaReborn.MOD_ID + ":hammer";

    public InfinityPickaxeItem() {
        super(InfinityMaterial.MATERIAL, 1, -2.2F, new Settings().rarity(Rarity.EPIC));
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack stack = new ItemStack(this);
        EnchantmentHelper.set(Map.of(Enchantments.FORTUNE, 10), stack);
        return stack;
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        if (stack.isOf(this) && !stack.getOrCreateNbt().getBoolean(HAMMER_NBT))
            return 9999;
        return super.getMiningSpeedMultiplier(stack, state);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (stack.isOf(this) && user.isSneaking()) {
            boolean isHammer = stack.getOrCreateNbt().getBoolean(HAMMER_NBT);
            stack.getOrCreateNbt().putBoolean(HAMMER_NBT, !isHammer);
            EnchantmentHelper.set(Map.of(Enchantments.FORTUNE, 10), stack);
        }
        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.literal(stack.getOrCreateNbt().getBoolean(HAMMER_NBT) ? "Hammer Mode" : "Pickaxe Mode"));
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (stack.isOf(this) && world instanceof ServerWorld serverWorld) {
            boolean isHammer = stack.getOrCreateNbt().getBoolean(HAMMER_NBT);
            if (isHammer) {
                ThreadUtil.execute(() -> {
                    final int r = serverWorld.getGameRules().getInt(ModGameRules.INFINITY_PICKAXE_RANGE);
                    List<ItemStack> packed = new ArrayList<>();
                    for (int i = pos.getX() - r; i <= pos.getX() + r; i++)
                        for (int j = pos.getZ() - r; j <= pos.getZ() + r; j++)
                            for (int k = pos.getY(); k >= pos.getY() - r * 2 && k >= serverWorld.getBottomY(); k--) {
                                BlockPos blockPos = new BlockPos(i, k, j);
                                BlockState block = serverWorld.getBlockState(blockPos);
                                if (!block.isIn(BlockTags.PICKAXE_MINEABLE)) continue;
                                BlockEntity blockEntity = serverWorld.getBlockEntity(blockPos);
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
                    miner.dropStack(MatterClusterItem.create(packed));
                }, serverWorld.getGameRules().getBoolean(ModGameRules.SYNC_BREAK));
            }
        }
        return super.postMine(stack, world, state, pos, miner);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return super.postHit(stack, target, attacker);
    }
}
