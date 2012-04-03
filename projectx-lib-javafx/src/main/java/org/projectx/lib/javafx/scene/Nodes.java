/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.scene;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Window;

/**
 *
 * @author puce
 */
public class Nodes {

    private Nodes() {
    }

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
}
