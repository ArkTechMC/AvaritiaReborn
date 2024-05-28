package com.iafenvoy.avaritia.util;

@FunctionalInterface
public interface Consumer2<T1, T2> {
    void accept(T1 p1, T2 p2);
}
