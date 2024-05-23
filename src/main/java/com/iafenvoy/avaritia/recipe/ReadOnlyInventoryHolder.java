package com.iafenvoy.avaritia.recipe;

import com.iafenvoy.avaritia.util.ISizeable;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.List;

public class ReadOnlyInventoryHolder<T> implements ISizeable {
    private final List<T> objects;
    private final int width, height;

    public ReadOnlyInventoryHolder(T[][] objects) {
        this(toSingleList(objects), objects.length, objects[0].length);
    }

    public ReadOnlyInventoryHolder(List<T> objects) {
        this(objects, objects.size(), 1);
    }

    public ReadOnlyInventoryHolder(List<T> objects, int width, int height) {
        if (width * height > objects.size())
            throw new IllegalArgumentException("width * height must <= to objects.size()");
        this.objects = objects;
        this.width = width;
        this.height = height;
    }

    public int size() {
        return this.objects.size();
    }

    public T get(int index) {
        return this.objects.get(index);
    }

    public <M> boolean sameSize(ReadOnlyInventoryHolder<M> another) {
        return this.width == another.width && this.height == another.height;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    private static <T> List<T> toSingleList(T[][] array) {
        List<T> result = new ArrayList<>();
        for (T[] sub : array)
            result.addAll(List.of(sub));
        return result;
    }

    public List<T> getObjects() {
        return this.objects;
    }
}
