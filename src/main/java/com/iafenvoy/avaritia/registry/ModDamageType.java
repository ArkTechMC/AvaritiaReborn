package com.iafenvoy.avaritia.registry;

import com.iafenvoy.avaritia.AvaritiaReborn;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModDamageType {
    public static final RegistryKey<DamageType> INFINITY = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(AvaritiaReborn.MOD_ID,"infinity"));
}
