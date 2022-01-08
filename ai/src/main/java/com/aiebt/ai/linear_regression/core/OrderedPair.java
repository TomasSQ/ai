package com.aiebt.ai.linear_regression.core;

import java.util.Objects;

public class OrderedPair<T> {

    public final T a1;
    public final T a2;

    private OrderedPair(T a1, T a2) {
        this.a1 = a1;
        this.a2 = a2;
    }

    public static <T> OrderedPair<T> of(T a1, T a2) {
        return new OrderedPair<>(a1, a2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderedPair<?> that = (OrderedPair<?>) o;
        return a1.equals(that.a1) && a2.equals(that.a2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a1, a2);
    }
}
