package com.iafenvoy.avaritia.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidDrainable;
import net.minecraft.block.FluidFillable;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InfinityBucketItem extends Item {
    public static final String FLUIDS_NBT = "Fluids";
    public static final String FLUID_ID_KEY = "Id";
    public static final String FLUID_AMOUNT_KEY = "Amount";

    public InfinityBucketItem() {
        super(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        NbtList items = stack.getOrCreateNbt().getList(FLUIDS_NBT, NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < items.size(); i++) {
            NbtCompound compound = items.getCompound(i);
            Identifier id = new Identifier(compound.getString(FLUID_ID_KEY));
            long amount = compound.getLong(FLUID_AMOUNT_KEY);
            tooltip.add(Text.translatable(id.toTranslationKey("block")).append(Text.literal(": " + String.format("%,d", amount) + "mL")));
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (user.isSneaking()) {
            NbtList items = itemStack.getOrCreateNbt().getList(FLUIDS_NBT, NbtElement.COMPOUND_TYPE);
            items.add(items.remove(0));
            itemStack.getOrCreateNbt().put(FLUIDS_NBT, items);
            return TypedActionResult.success(itemStack, world.isClient);
        }
        BlockHitResult result = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        if (result.getType() != HitResult.Type.BLOCK) return TypedActionResult.pass(itemStack);
        BlockState target = world.getBlockState(result.getBlockPos());
        if (world.canPlayerModifyAt(user, result.getBlockPos()) && target.getBlock() instanceof FluidDrainable drainable) {
            ItemStack r = drainable.tryDrainFluid(world, result.getBlockPos(), target);
            if (!r.isEmpty() && r.getItem() instanceof BucketItem bucketItem) {
                Fluid fluid = bucketItem.fluid;
                this.insertFluid(itemStack, fluid, 1000);
            }
        } else {
            BlockPos newPos = result.getBlockPos().add(result.getSide().getVector());
            if (world.canPlayerModifyAt(user, newPos)) {
                Fluid fluid = this.getFirstAndDecrease(itemStack, 1000);
                if (fluid != Fluids.EMPTY) {
                    BlockState here = world.getBlockState(newPos);
                    if (here.getBlock() instanceof FluidFillable fillable && fluid == Fluids.WATER)
                        fillable.tryFillWithFluid(world, newPos, here, fluid.getDefaultState());
                    else {
                        if (!world.isClient && here.canBucketPlace(fluid))
                            world.breakBlock(newPos, true);
                        if (world.setBlockState(newPos, fluid.getDefaultState().getBlockState(), 11) && !here.getFluidState().isStill())
                            this.playEmptyingSound(user, world, newPos, fluid);
                    }
                }
            }
        }
        return TypedActionResult.success(itemStack, world.isClient);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        NbtList items = stack.getOrCreateNbt().getList(FLUIDS_NBT, NbtElement.COMPOUND_TYPE);
        if (entity instanceof PlayerEntity player && player.getInventory().getMainHandStack() == stack && items.size() > 0) {
            NbtCompound compound = items.getCompound(0);
            Identifier id = new Identifier(compound.getString(FLUID_ID_KEY));
            long amount = compound.getLong(FLUID_AMOUNT_KEY);
            player.sendMessage(Text.translatable("item.avaritia.infinity_bucket.message", Text.translatable(id.toTranslationKey("block")), String.format("%,d", amount)), true);
        }
    }

    public Fluid getFirstAndDecrease(ItemStack stack, long minimumAmount) {
        NbtList items = stack.getOrCreateNbt().getList(FLUIDS_NBT, NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < items.size(); i++) {
            NbtCompound compound = items.getCompound(i);
            long amount = compound.getLong(FLUID_AMOUNT_KEY);
            if (amount >= minimumAmount) {
                amount -= minimumAmount;
                Identifier id = new Identifier(compound.getString(FLUID_ID_KEY));
                if (amount == 0) items.remove(i);
                else compound.putLong(FLUID_AMOUNT_KEY, amount);
                return Registries.FLUID.get(id);
            }
        }
        return Fluids.EMPTY;
    }

    public void insertFluid(ItemStack stack, Fluid fluid, long amount) {
        Identifier fluidId = Registries.FLUID.getId(fluid);
        NbtList items = stack.getOrCreateNbt().getList(FLUIDS_NBT, NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < items.size(); i++) {
            NbtCompound compound = items.getCompound(i);
            Identifier id = new Identifier(compound.getString(FLUID_ID_KEY));
            if (fluidId.equals(id)) {
                long a = compound.getLong(FLUID_AMOUNT_KEY);
                a += amount;
                compound.putLong(FLUID_AMOUNT_KEY, a);
                return;
            }
        }
        NbtCompound compound = new NbtCompound();
        compound.putString(FLUID_ID_KEY, fluidId.toString());
        compound.putLong(FLUID_AMOUNT_KEY, amount);
        items.add(compound);
        stack.getOrCreateNbt().put(FLUIDS_NBT, items);
    }

    protected void playEmptyingSound(PlayerEntity player, WorldAccess world, BlockPos pos, Fluid fluid) {
        SoundEvent soundEvent = fluid.isIn(FluidTags.LAVA) ? SoundEvents.ITEM_BUCKET_EMPTY_LAVA : SoundEvents.ITEM_BUCKET_EMPTY;
        world.playSound(player, pos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
        world.emitGameEvent(player, GameEvent.FLUID_PLACE, pos);
    }
}
