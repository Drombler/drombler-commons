/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.control;

/**
 *
 * @author puce
 */
public class ObjectCellRenderer extends AbstractCellRenderer<Object> {

    @Override
    public String getText(Object item) {
        if (item != null){
            return item.toString();
        } else {
            return null;
        }
    }
    
}
