package com.iafenvoy.avaritia.registry;

import com.iafenvoy.avaritia.AvaritiaReborn;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class ModGameRules {
    public static final GameRules.Key<GameRules.IntRule> INFINITY_AXE_RANGE = register("tool.axe.range", GameRules.Category.PLAYER, GameRuleFactory.createIntRule(16, 0));
    public static final GameRules.Key<GameRules.IntRule> INFINITY_HOE_RANGE = register("tool.hoe.range", GameRules.Category.PLAYER, GameRuleFactory.createIntRule(8, 0));
    public static final GameRules.Key<GameRules.IntRule> INFINITY_PICKAXE_RANGE = register("tool.pickaxe.range", GameRules.Category.PLAYER, GameRuleFactory.createIntRule(8, 0));
    public static final GameRules.Key<GameRules.IntRule> INFINITY_SHOVEL_RANGE = register("tool.shovel.range", GameRules.Category.PLAYER, GameRuleFactory.createIntRule(8, 0));

    public static void init() {
    }

    private static <T extends GameRules.Rule<T>> GameRules.Key<T> register(String name, GameRules.Category category, GameRules.Type<T> rule) {
        return GameRuleRegistry.register(AvaritiaReborn.MOD_ID + ":" + name, category, rule);
    }
}