package com.iafenvoy.avaritia.data.singularity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.HashMap;

@Environment(EnvType.CLIENT)
public record SingularityColor(String id, Color color) {
    public static final SingularityColor EMPTY = new SingularityColor("", new Color(0, 0, 0, 0));
    public static final HashMap<String, SingularityColor> COLOR_MAP = new HashMap<>();

    public record Color(int a, int r, int g, int b) {
        public int get() {
            return (this.a & 255) << 24 | (this.r & 255) << 16 | (this.g & 255) << 8 | (this.b & 255);
        }
    }
}
