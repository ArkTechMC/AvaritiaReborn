package com.iafenvoy.avaritia.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import net.fabricmc.loader.api.FabricLoader;

import java.util.List;

public class DependencyHolder {
    private final List<String> ids;

    public DependencyHolder(List<String> ids) {
        this.ids = ids;
    }

    public DependencyHolder(String... idsByString) {
        this(List.of(idsByString));
    }

    public DependencyHolder(JsonArray array) {
        this(array.asList().stream().map(JsonElement::getAsString).toList());
    }

    public boolean anyLoaded() {
        return this.ids.isEmpty() || this.ids.stream().anyMatch(FabricLoader.getInstance()::isModLoaded);
    }
}
