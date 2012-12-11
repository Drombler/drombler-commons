/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.commons.fx.scene.control.time.calendar.impl.skin;

/**
 *
 * @author puce
 */
public class Stylesheets {

    private Stylesheets() {
    }
    
    public static String getDefaultStylesheet(){
        return Stylesheets.class.getResource("caspian/caspian.css").toExternalForm();
    }
}
