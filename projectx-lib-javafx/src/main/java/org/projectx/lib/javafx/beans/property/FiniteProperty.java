/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.beans.property;

import javafx.beans.property.Property;

/**
 *
 * @author puce
 */
public interface FiniteProperty<T> extends Property<T> {

    Property<T> minProperty();

    T getMin();

    void setMin(T min);

    Property<T> maxProperty();

    T getMax();

    void setMax(T max);
}
