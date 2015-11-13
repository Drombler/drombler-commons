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
package org.drombler.commons.docking.spi;

/**
 * An abstract Docking Area.
 *
 * Note: The interface might change in future versions.
 *
 * @param <A> the type of the Docking Area
 * @author puce
 */
public interface DockingArea<A extends DockingArea<A>> {

    void setParentManager(DockingAreaManager<A> parentDockingAreaManager);

    int getPosition();

    boolean isVisual();
}
