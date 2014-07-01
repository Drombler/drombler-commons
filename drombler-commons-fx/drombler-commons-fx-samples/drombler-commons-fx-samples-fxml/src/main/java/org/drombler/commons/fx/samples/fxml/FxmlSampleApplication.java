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
 * Copyright 2014 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.fx.samples.fxml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.drombler.commons.fx.fxml.FXMLLoaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author puce
 */
public class FxmlSampleApplication extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(FxmlSampleApplication.class);

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fXMLLoader = FXMLLoaders.createFXMLLoader(FxmlSampleApplication.class);
        Parent root = FXMLLoaders.load(fXMLLoader, FxmlSampleApplication.class);

        Scene scene = new Scene(root, 1500, 1000);

        stage.setTitle("FXML Sample Application");
        stage.setScene(scene);
        stage.show();
    }

}
