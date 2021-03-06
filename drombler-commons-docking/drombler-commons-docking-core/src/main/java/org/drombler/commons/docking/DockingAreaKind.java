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
package org.drombler.commons.docking;

/**
 * The Docking Area kind.
 *
 * @author puce
 */
//@XmlType(name = "DockingAreaKindType")//namespace="http://www.drombler.org/schema/acp/dockingAreas"
//@XmlEnum
public enum DockingAreaKind {
    /**
     * A Docking Area of kind 'view' can hold Dockables of kind 'view'.
     *
     * @see DockableKind#VIEW
     */
    VIEW,
    /**
     * A Docking Area of kind 'editor' can hold Dockables of kind 'editor'.
     *
     * @see DockableKind#EDITOR
     */
    EDITOR;
}
