/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.commons.fx.scene.renderer;

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
