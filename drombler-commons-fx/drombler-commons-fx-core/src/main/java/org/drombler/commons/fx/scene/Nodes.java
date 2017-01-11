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
package org.drombler.commons.fx.scene;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Window;

/**
 * A utility class for {@link Node}s.
 * @author puce
 */
public class Nodes {

    private Nodes() {
    }

    /**
     * Gets the location of a {@link Node} in screen coordinates.
     * 
     * @param node a {@link Node}
     * @return the location of the node in screen coordinates
     */
    public static Point2D getScreenLocation(Node node) {
        Scene scene = node.getScene();
        Window window = scene.getWindow();
        double x = window.getX() + scene.getX();
        double y = window.getY() + scene.getY();

        for (Node currentNode = node; currentNode != null;
                currentNode = currentNode.getParent()) {
            Bounds boundsInParent = currentNode.getBoundsInParent(); // TODO: correct???
            x += boundsInParent.getMinX();
            y += boundsInParent.getMinY();
        }
//        double x = window.getX() + scene.getX() + boundsInLocal.getMinX() + 300;
//        double y = window.getY() + scene.getY() + boundsInLocal.getMinY() + 250;
        return new Point2D(x, y);
    }

    /**
     * Checks if the provided {@link Parent] is either a direct or indirect parent of the provided {@link Node}.
     *
     * @param parent the parent
     * @param node the node
     * @return true if the provided {@link Parent] is either a direct or indirect parent of the provided {@link Node}, else false
     */
    public static boolean isParent(Parent parent, Node node) {
        boolean found = false;
        Parent currentParent = node.getParent();
        while (!found && currentParent != null) {
            if (currentParent != parent) {
                found = true;
            } else {
                currentParent = currentParent.getParent();
            }
        }
        return found;
    }
}
