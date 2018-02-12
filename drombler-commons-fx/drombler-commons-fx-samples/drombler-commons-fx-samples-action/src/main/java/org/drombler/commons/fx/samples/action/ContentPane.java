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

import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.LocalContextProvider;
import org.drombler.commons.context.SimpleContext;
import org.drombler.commons.context.SimpleContextContent;

/**
 * A pane with a local {@link Context}. 
 * Depending on the state of the selected property of a {@link CheckBox} an 
 * instance of {@link MyCommand} / {@link MyConfiguration} gets added 
 * to/ removed from the local context.
 *
 * @author puce
 */
public class ContentPane extends GridPane implements LocalContextProvider {

    private final SimpleContextContent contextContent = new SimpleContextContent();
    private final SimpleContext localContext = new SimpleContext(contextContent);

    public ContentPane() {
        setHgap(5.0);
        setVgap(5.0);
        ColumnConstraints columnConstraints0 = new ColumnConstraints();
        columnConstraints0.setHgrow(Priority.ALWAYS);
        columnConstraints0.setHalignment(HPos.CENTER);
        ColumnConstraints columnConstraints1 = new ColumnConstraints();
        columnConstraints1.setHgrow(Priority.ALWAYS);
        columnConstraints1.setHalignment(HPos.CENTER);
        getColumnConstraints().addAll(columnConstraints0, columnConstraints1);

        RowConstraints rowConstraints0 = new RowConstraints();
        rowConstraints0.setVgrow(Priority.ALWAYS);
        getRowConstraints().add(rowConstraints0);

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
        add(myCommandCheckBox, 0, 0);

        VBox vBox = new VBox(10.0);
        vBox.setAlignment(Pos.CENTER_LEFT);
        add(vBox, 1, 0);
        
        MyConfiguration myConfiguration = new MyConfiguration();
        CheckBox myConfigurationCheckBox = new CheckBox("MyConfiguration active and in local context");
        myConfigurationCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                contextContent.add(myConfiguration);
            } else {
                contextContent.remove(myConfiguration);
            }
        });
        vBox.getChildren().add(myConfigurationCheckBox);

        CheckBox testConfigCheckBox = new CheckBox("Test configuration");
        testConfigCheckBox.selectedProperty().bindBidirectional(myConfiguration.testProperty());
        testConfigCheckBox.disableProperty().bind(Bindings.not(myConfigurationCheckBox.selectedProperty()));
        vBox.getChildren().add(testConfigCheckBox);

    }

    @Override
    public Context getLocalContext() {
        return localContext;
    }

}
