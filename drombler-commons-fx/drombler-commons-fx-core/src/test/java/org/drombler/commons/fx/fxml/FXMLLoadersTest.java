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
package org.drombler.commons.fx.fxml;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author puce
 */
public class FXMLLoadersTest {

    @Test
    @Ignore
    public void testLoadRootWithFXMLControllerAnnotation() throws IOException {
        TestRootControllerPane testRootController = new TestRootControllerPane();
//        FXMLLoaders.loadRoot(testRootController, testRootController.getClass().getAnnotation(FXMLController.class));
        assertEquals(3, testRootController.getChildren().size());
        assertTrue(testRootController.getChildren().get(0) instanceof Label);
        assertEquals("Test Label", ((Label) testRootController.getChildren().get(0)).getText());
        assertTrue(testRootController.getChildren().get(1) instanceof Button);
        assertEquals("Test Button", ((Button) testRootController.getChildren().get(1)).getText());
        assertTrue(testRootController.getChildren().get(0) instanceof NestedTestRootControllerPane);
    }

    @Test
    @Ignore
    public void testLoadFxmlWithBuilderFactory() throws IOException {
        TestRootControllerPane controllerPane = new TestRootControllerPane();

        Class<TestRootControllerPane> type = TestRootControllerPane.class;
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new TestBuilderFactory(loader.getBuilderFactory()));
        loader.setClassLoader(type.getClassLoader());
        loader.setResources(ResourceBundle.getBundle(type.getPackage().getName()+".Bundle", Locale.getDefault(), type.getClassLoader()));
        loader.setRoot(controllerPane);
        loader.setController(controllerPane);

        String fxmlFileName = "TestRootControllerPane.fxml";
        try (InputStream is = type.getResourceAsStream(fxmlFileName)) {
            if (is == null) {
                throw new FileNotFoundException(fxmlFileName);
            }
            loader.load(is);
        }
    }
}
