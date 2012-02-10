/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.beans.property;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.softsmithy.lib.util.Comparables;

/**
 *
 * @author puce
 */
public class FiniteComparableProperty<T extends Comparable<? super T>> extends SimpleObjectProperty<T> implements FiniteProperty<T> {

    private final ObjectProperty<T> min = new SimpleObjectProperty<T>(this, "min") {

        @Override
        public void set(T min) {
            if (min != null && getMax() != null) {
                min = Comparables.min(min, getMax());
            }
            super.set(min);
            if (min != null) {
                FiniteComparableProperty.this.set(FiniteComparableProperty.this.get());
            }
        }
    };
    private final ObjectProperty<T> max = new SimpleObjectProperty<T>(this, "max") {

        @Override
        public void set(T max) {
            if (max != null && getMin() != null) {
                max = Comparables.max(max, getMin());
            }
            super.set(max);
            if (max != null) {
                FiniteComparableProperty.this.set(FiniteComparableProperty.this.get());
            }
        }
    };

    public FiniteComparableProperty() {
    }

    public FiniteComparableProperty(T initialValue) {
        super(initialValue);
    }

    public FiniteComparableProperty(Object bean, String name) {
        super(bean, name);
    }

    public FiniteComparableProperty(Object bean, String name, T initialValue) {
        super(bean, name, initialValue);
    }

    public FiniteComparableProperty(T min, T max) {
        setMin(min);
        setMax(max);
    }

    public FiniteComparableProperty(T initialValue, T min, T max) {
        super(initialValue);
        setMin(min);
        setMax(max);
    }

    public FiniteComparableProperty(Object bean, String name, T min, T max) {
        super(bean, name);
        setMin(min);
        setMax(max);
    }

    public FiniteComparableProperty(Object bean, String name, T initialValue, T min, T max) {
        super(bean, name, initialValue);
        setMin(min);
        setMax(max);
    }

    @Override
    public ObjectProperty<T> minProperty() {
        return min;
    }

    @Override
    public final T getMin() {
        return min.get();
    }

    @Override
    public final void setMin(T min) {
        this.min.set(min);
    }

    @Override
    public ObjectProperty<T> maxProperty() {
        return max;
    }

    @Override
    public final T getMax() {
        return max.get();
    }

    @Override
    public final void setMax(T max) {
        this.max.set(max);
    }

    @Override
    public void set(T t) {
        if (t != null) {
            t = Comparables.toRange(t, getMin(), getMax());
        }
        super.set(t);
    }
}
