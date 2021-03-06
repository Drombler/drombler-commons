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
package org.drombler.commons.fx.samples.time;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.drombler.commons.fx.fxml.FXMLLoaders;

/**
 * @author puce
 */


public class DateTimeSampleApplication extends Application {

    private final FXMLLoader loader = FXMLLoaders.createFXMLLoader(DateTimeSampleApplication.class.getClassLoader());

    @Override
    public void start(Stage stage) {
        BorderPane root = FXMLLoaders.load(loader, getClass());
        FXMLLoaders.resetFXMLLoader(loader);

        Scene scene = new Scene(root, 1500, 1000);

        stage.setTitle("Date & Time JavaFX 8 Sample Apllication");
        stage.setScene(scene);
        stage.show();
    }

}
