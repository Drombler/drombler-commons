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

import java.io.IOException;
import java.io.InputStream;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import org.softsmithy.lib.util.ResourceFileNotFoundException;

public class DateTimeEditorPane2 extends BorderPane {

    private static final String FXML_EXTENSION = ".fxml";

    public DateTimeEditorPane2() throws IOException {
        loadFXML();

    }

    private void loadFXML() throws IOException {
        Class<DateTimeEditorPane2> type = DateTimeEditorPane2.class;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setClassLoader(type.getClassLoader());

        try (InputStream is = type.getResourceAsStream(type.getSimpleName() + FXML_EXTENSION)) {
            if (is == null) {
                // avoid NullPointerException
                throw new ResourceFileNotFoundException("/" + type.getName().replace(".", "/") + FXML_EXTENSION);
            }
            fxmlLoader.load(is);
        }
    }
}
