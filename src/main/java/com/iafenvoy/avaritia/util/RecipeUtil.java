package com.iafenvoy.avaritia.util;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.JsonHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecipeUtil {
    public static Map<String, Ingredient> readSymbols(JsonObject json) {
        Map<String, Ingredient> map = Maps.newHashMap();
        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
            if ((entry.getKey()).length() != 1)
                throw new JsonSyntaxException("Invalid key entry: '" + entry.getKey() + "' is an invalid symbol (must be 1 character only).");
            if (" ".equals(entry.getKey()))
                throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
            map.put(entry.getKey(), Ingredient.fromJson(entry.getValue(), false));
        }
        map.put(" ", Ingredient.EMPTY);
        return map;
    }

    public static String[] getPattern(JsonArray json, int maxRows, int maxColumn) {
        String[] strings = new String[json.size()];
        if (strings.length > maxRows)
            throw new JsonSyntaxException("Invalid pattern: too many rows, maximum=" + maxRows);
        else if (strings.length == 0)
            throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
        for (int i = 0; i < strings.length; ++i) {
            String string = JsonHelper.asString(json.get(i), "pattern[" + i + "]");
            if (string.length() > maxColumn)
                throw new JsonSyntaxException("Invalid pattern: too many columns, maximum=" + maxColumn);
            if (i > 0 && strings[0].length() != string.length())
                throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
            strings[i] = string;
        }
        return strings;
    }

    public static Ingredient[][] replacePattern(String[] pattern, Map<String, Ingredient> symbols) {
        Ingredient[][] ingredients = new Ingredient[pattern.length][pattern[0].length()];
        for (int i = 0; i < pattern.length; i++)
            for (int j = 0; j < pattern[i].length(); j++) {
                if (symbols.containsKey(pattern[i].substring(j, j + 1)))
                    ingredients[i][j] = symbols.get(pattern[i].substring(j, j + 1));
                else
                    throw new IllegalArgumentException("Invalid Key: " + pattern[i].charAt(j));
            }
        return ingredients;
    }

    public static <T> List<List<T>> toTable(T[][] array) {
        List<List<T>> table = new ArrayList<>();
        for (T[] ts : array) table.add(List.of(ts));
        return table;
    }

    public static <T> List<List<T>> toTable(List<T> array, int width, int height) {
        List<List<T>> table = new ArrayList<>();
        int flag = 0;
        while (table.size() < height) {
            if (array.size() - flag < width) {
                table.add(array.subList(flag, array.size()));
                break;
            }
            table.add(array.subList(flag, flag + width));
            flag += width;
        }
        return table;
    }
}
