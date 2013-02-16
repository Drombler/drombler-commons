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

import javafx.beans.property.Property;

/**
 * A {@link Property} with an optional minimum value and an optional maximum
 * value.
 *
 * @author puce
 */
public interface LimitedProperty<T> extends Property<T> {

    /**
     * The minimum value.
     */
    Property<T> minProperty();

    T getMin();

    void setMin(T min);

    /**
     * The maximum value.
     */
    Property<T> maxProperty();

    T getMax();

    void setMax(T max);
}
