/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.scene.control.skin;

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
