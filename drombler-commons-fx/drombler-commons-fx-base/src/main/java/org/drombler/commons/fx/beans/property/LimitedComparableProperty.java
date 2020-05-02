/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (Sourceforge.net user: puce).
 * Copyright 2012 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.fx.beans.property;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.softsmithy.lib.util.Comparables;

/**
 * An implementation of {@link LimitedProperty} for a {@link Comparable}.
 *
 * @param <T> the type of the wrapped object
 */
public class LimitedComparableProperty<T extends Comparable<? super T>> extends SimpleObjectProperty<T> implements LimitedProperty<T> {

    private final ObjectProperty<T> min = new SimpleObjectProperty<T>(this, "min") {
        @Override
        public void set(T min) {
            if (min != null && getMax() != null) {
                min = Comparables.min(min, getMax());
            }
            super.set(min);
            if (min != null) {
                LimitedComparableProperty.this.set(LimitedComparableProperty.this.get());
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
                LimitedComparableProperty.this.set(LimitedComparableProperty.this.get());
            }
        }
    };

    /**
     * Creates a new instance of this class.
     */
    public LimitedComparableProperty() {
    }

    /**
     * Creates a new instance of this class.
     *
     * @param initialValue the initial value of this property
     */
    public LimitedComparableProperty(T initialValue) {
        super(initialValue);
    }

    /**
     * Creates a new instance of this class
     *
     * @param bean the bean of this property
     * @param name the name of this property
     */
    public LimitedComparableProperty(Object bean, String name) {
        super(bean, name);
    }

    /**
     * Creates a new instance of this class.
     *
     * @param bean the bean of this property
     * @param name the name of this property
     * @param initialValue the initial value of this property
     */
    public LimitedComparableProperty(Object bean, String name, T initialValue) {
        super(bean, name, initialValue);
    }

    /**
     * Creates a new instance of this class.
     *
     * @param min the min value allowed for this property
     * @param max the max value allowed for this property
     */
    public LimitedComparableProperty(T min, T max) {
        setMin(min);
        setMax(max);
    }

    /**
     * Creates a new instance of this class.
     *
     * @param initialValue the initial value of this property
     * @param min the min value allowed for this property
     * @param max the max value allowed for this property
     */
    public LimitedComparableProperty(T initialValue, T min, T max) {
        super(initialValue);
        setMin(min);
        setMax(max);
    }

    /**
     * Creates a new instance of this class.
     *
     * @param bean the bean of this property
     * @param name the name of this property
     * @param min the min value allowed for this property
     * @param max the max value allowed for this property
     */
    public LimitedComparableProperty(Object bean, String name, T min, T max) {
        super(bean, name);
        setMin(min);
        setMax(max);
    }

    /**
     * Creates a new instance of this class.
     *
     * @param bean the bean of this property
     * @param name the name of this property
     * @param initialValue the initial value of this property
     * @param min the min value allowed for this property
     * @param max the max value allowed for this property
     */
    public LimitedComparableProperty(Object bean, String name, T initialValue, T min, T max) {
        super(bean, name, initialValue);
        setMin(min);
        setMax(max);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectProperty<T> minProperty() {
        return min;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final T getMin() {
        return min.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setMin(T min) {
        this.min.set(min);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectProperty<T> maxProperty() {
        return max;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final T getMax() {
        return max.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setMax(T max) {
        this.max.set(max);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(T t) {
        if (t != null) {
            t = Comparables.toRange(t, getMin(), getMax());
        }
        super.set(t);
    }
}
