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
package org.drombler.commons.fx.samples.action;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.LocalContextProvider;
import org.drombler.commons.context.SimpleContext;
import org.drombler.commons.context.SimpleContextContent;

/**
 *
 * @author puce
 */
public class ContentPane extends BorderPane implements LocalContextProvider {

    private final SimpleContextContent contextContent = new SimpleContextContent();
    private final SimpleContext localContext = new SimpleContext(contextContent);

    public ContentPane() {
        CheckBox myCommandCheckBox = new CheckBox("MyCommand in local context");
        MyCommand myCommand = () -> {
            System.out.println("MyCommand doSomething!");
            myCommandCheckBox.setSelected(false);
        };

        myCommandCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                contextContent.add(myCommand);
            } else {
                contextContent.remove(myCommand);
            }
        });
        setCenter(myCommandCheckBox);
    }

    @Override
    public Context getLocalContext() {
        return localContext;
    }

}
