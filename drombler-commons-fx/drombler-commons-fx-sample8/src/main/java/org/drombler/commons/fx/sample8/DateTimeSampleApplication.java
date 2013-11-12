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
package org.drombler.commons.fx.sample8;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author puce
 */


public class DateTimeSampleApplication extends Application{

     public static void main(String... args) {
        launch(args);
    }
     
    @Override
    public void start(Stage stage) throws Exception {
        Class<DateTimeSampleApplication> type = DateTimeSampleApplication.class;
        Parent root = FXMLLoader.load(type.getResource(type.getSimpleName()+".fxml"));
    
        Scene scene = new Scene(root, 1500, 1000);
    
        stage.setTitle("Date & Time JavaFX 8 Sample Apllication");
        stage.setScene(scene);
        stage.show();
    }
    
}
