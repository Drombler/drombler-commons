/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.scene.control;

/**
 *
 * @author puce
 */
public class ObjectRenderer extends AbstractDataRenderer<Object> {

    @Override
    public String getText(Object item) {
        if (item != null){
            return item.toString();
        } else {
            return null;
        }
    }
    
}
