package com.iafenvoy.avaritia.entity;

import com.iafenvoy.avaritia.registry.ModDamageType;
import com.iafenvoy.avaritia.registry.ModEntities;
import com.iafenvoy.avaritia.registry.ModGameRules;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class InfinityArrowEntity extends PersistentProjectileEntity {
    public InfinityArrowEntity(World world) {
        super(ModEntities.INFINITY_ARROW, world);
        this.pickupType = PickupPermission.CREATIVE_ONLY;
        this.setCritical(true);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (entity instanceof PlayerEntity player && player.isCreative() && !entity.getWorld().getGameRules().getBoolean(ModGameRules.INFINITY_KILL_CREATIVE))
            return;
        if (entity instanceof LivingEntity livingEntity) {
            Registry<DamageType> registry = livingEntity.getDamageSources().registry;
            DamageSource source = new DamageSource(registry.getEntry(registry.get(ModDamageType.INFINITY)), this, this);
            livingEntity.setInvulnerable(false);
            livingEntity.damage(source, livingEntity.getHealth());
            livingEntity.setHealth(0);
            livingEntity.onDeath(source);
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if (this.isCritical())
            switch (this.getWorld().getGameRules().get(ModGameRules.INFINITY_BOW_BEHAVIOUR).get()) {
                case Explode ->
                        this.getWorld().createExplosion(this, blockHitResult.getBlockPos().getX(), blockHitResult.getBlockPos().getY(), blockHitResult.getBlockPos().getZ(), 100, false, World.ExplosionSourceType.NONE);
                case ArrowRain -> {
                    Random random = this.getWorld().getRandom();
                    for (int i = 0; i < 10; i++) {
                        double angle = random.nextDouble() * 2 * Math.PI;
                        double dist = random.nextGaussian() * 0.5;
                        double x = Math.sin(angle) * dist + this.getX();
                        double z = Math.cos(angle) * dist + this.getZ();
                        double y = this.getY() + 25.0;
                        double dangle = random.nextDouble() * 2 * Math.PI;
                        double ddist = random.nextDouble() * 0.35;
                        double dx = Math.sin(dangle) * ddist;
                        double dz = Math.cos(dangle) * ddist;
                        InfinityArrowEntity arrow = new InfinityArrowEntity(this.getWorld());
                        arrow.setPosition(x, y, z);
                        arrow.setOwner(this.getOwner());
                        arrow.addVelocity(dx, -(random.nextDouble() * 1.85 + 0.15), dz);
                        arrow.setDamage(this.getDamage());
                        arrow.setCritical(false);
                        this.getWorld().spawnEntity(arrow);
                    }
                }
            }
        super.onBlockHit(blockHitResult);
        this.discard();
    }

    @Override
    protected ItemStack asItemStack() {
        return ItemStack.EMPTY;
    }

    public enum HitBlockBehaviour {
        None, Explode, ArrowRain
    }
}
