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

import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import org.drombler.commons.action.ActionListener;
import org.drombler.commons.action.ToggleActionListener;
import org.drombler.commons.action.fx.ActionEventHandlerAdapter;
import org.drombler.commons.action.fx.ActionListenerAdapter;
import org.drombler.commons.action.fx.ButtonUtils;
import org.drombler.commons.action.fx.FXAction;
import org.drombler.commons.action.fx.FXToggleAction;
import org.drombler.commons.action.fx.MenuItemUtils;
import org.drombler.commons.action.fx.ToggleActionListenerAdapter;
import org.drombler.commons.context.ContextInjector;
import org.drombler.commons.context.ContextManager;
import org.drombler.commons.context.SimpleContext;
import org.drombler.commons.context.SimpleContextContent;
import org.drombler.commons.fx.scene.control.XToggleButton;
import org.drombler.commons.fx.scene.image.IconFactory;
import org.softsmithy.lib.util.ResourceLoader;

/**
 *
 * @author puce
 */
public class ActionSampleApplication extends Application {

    private static final int MENU_ICON_SIZE = 16;
    private static final int TOOL_BAR_BUTTON_SIZE = 24;

    @Override
    public void start(Stage stage) throws Exception {
        GridPane root = new GridPane();

        MenuBar menuBar = new MenuBar();
        root.add(menuBar, 0, 0);
        GridPane.setHgrow(menuBar, Priority.ALWAYS);

        ToolBar toolBar = new ToolBar();
        root.add(toolBar, 0, 1);
        GridPane.setHgrow(toolBar, Priority.ALWAYS);

        BorderPane contentPane = new BorderPane();
        root.add(contentPane, 0, 2);
        GridPane.setHgrow(contentPane, Priority.ALWAYS);
        GridPane.setVgrow(contentPane, Priority.ALWAYS);

        ResourceLoader resourceLoader = new ResourceLoader(ActionSampleApplication.class);

        List<FXAction> fXActions = createFXActions(resourceLoader);
        Menu fileMenu = new Menu("_File");
        menuBar.getMenus().add(fileMenu);
        addMenuItems(fileMenu, fXActions);
        addToolBarButtons(toolBar, fXActions);
        addSeparator(toolBar);

        List<FXToggleAction> fxToggleActionsForGrouped = createFXToggleActionsForGrouped(resourceLoader);
        ToggleGroup menuToggleGroup = new ToggleGroup();
        Menu groupedMenu = new Menu("_Grouped");
        fileMenu.getItems().add(groupedMenu);
        addRadioMenuItems(groupedMenu, fxToggleActionsForGrouped, menuToggleGroup);
        ToggleGroup toolBarToggleGroup = new ToggleGroup();
        addGroupedToggleToolBarButtons(toolBar, fxToggleActionsForGrouped, toolBarToggleGroup);
        fxToggleActionsForGrouped.get(0).setSelected(true);
        addSeparator(toolBar);

        List<FXToggleAction> fxToggleActionsForUngrouped = createFXToggleActionsForUngrouped(resourceLoader);
        Menu ungroupedMenu = new Menu("_Ungrouped");
        fileMenu.getItems().add(ungroupedMenu);
        addCheckMenuItems(ungroupedMenu, fxToggleActionsForUngrouped);
        addUngroupedToggleToolBarButtons(toolBar, fxToggleActionsForUngrouped);
        addSeparator(toolBar);

        ContextManager contextManager = new ContextManager();
        ContextInjector contextInjector = new ContextInjector(contextManager);
        List<FXAction> contextSensitiveFXActions = createContextSensitiveFXActions(resourceLoader, contextInjector);
        Menu contextSensitiveMenu = new Menu("_Context Sensitive");
        fileMenu.getItems().add(contextSensitiveMenu);
        addMenuItems(contextSensitiveMenu, contextSensitiveFXActions);
        addToolBarButtons(toolBar, contextSensitiveFXActions);

        addContent(contentPane, fXActions, fxToggleActionsForGrouped, fxToggleActionsForUngrouped,
                contextSensitiveFXActions, contextManager);

        Scene scene = new Scene(root, 1500, 1000);
        stage.setTitle("Action Sample Application");
        stage.setScene(scene);
        stage.show();
    }

    private void addSeparator(ToolBar toolBar) {
        toolBar.getItems().add(new Separator(Orientation.VERTICAL));
    }

    private void addMenuItems(Menu menu, List<FXAction> fXActions) {
        fXActions.forEach(fxAction -> {
            MenuItem menuItem = new MenuItem();
            MenuItemUtils.configureMenuItem(menuItem, fxAction, MENU_ICON_SIZE);
            menu.getItems().add(menuItem);
        });
    }

    private void addRadioMenuItems(Menu menu, List<FXToggleAction> fxToggleActions, ToggleGroup toggleGroup) {
        fxToggleActions.forEach(fxToggleAction -> {
            RadioMenuItem menuItem = new RadioMenuItem();
            MenuItemUtils.configureRadioMenuItem(menuItem, fxToggleAction, MENU_ICON_SIZE);
            menu.getItems().add(menuItem);
            toggleGroup.getToggles().add(menuItem);
        });
    }

    private void addCheckMenuItems(Menu menu, List<FXToggleAction> fxToggleActions) {
        fxToggleActions.forEach(fxToggleAction -> {
            CheckMenuItem menuItem = new CheckMenuItem();
            MenuItemUtils.configureCheckMenuItem(menuItem, fxToggleAction, MENU_ICON_SIZE);
            menu.getItems().add(menuItem);
        });
    }

    private void addToolBarButtons(ToolBar toolBar, List<FXAction> fXActions) {
        fXActions.forEach(fxAction -> {
            Button button = new Button();
            ButtonUtils.configureToolbarButton(button, fxAction, TOOL_BAR_BUTTON_SIZE);
            toolBar.getItems().add(button);
        });
    }

    private void addGroupedToggleToolBarButtons(ToolBar toolBar, List<FXToggleAction> fxToggleActions,
            ToggleGroup toggleGroup) {
        fxToggleActions.forEach(fxToggleAction -> {
            XToggleButton toggleButton = new XToggleButton();
            ButtonUtils.configureToolbarToggleButton(toggleButton, fxToggleAction, TOOL_BAR_BUTTON_SIZE);
            toolBar.getItems().add(toggleButton);
            toggleGroup.getToggles().add(toggleButton);
        });
    }

    private void addUngroupedToggleToolBarButtons(ToolBar toolBar, List<FXToggleAction> fxToggleActions) {
        fxToggleActions.forEach(fxToggleAction -> {
            XToggleButton toggleButton = new XToggleButton();
            ButtonUtils.configureToolbarToggleButton(toggleButton, fxToggleAction, TOOL_BAR_BUTTON_SIZE);
            toolBar.getItems().add(toggleButton);
        });
    }

    private List<FXAction> createFXActions(ResourceLoader resourceLoader) {
        final ActionListener<Object> test1ActionListener = new ActionListenerTestAction();
        final FXAction test1Action = new ActionListenerAdapter(test1ActionListener);
        test1Action.setDisplayName("Test _1");
        test1Action.setAccelerator(KeyCombination.keyCombination("Shortcut+1"));
        test1Action.setGraphicFactory(new IconFactory("one.png", resourceLoader, false));

        final EventHandler<ActionEvent> test2ActionEventHandler = new EventHandlerTestAction();
        final FXAction test2Action = new ActionEventHandlerAdapter(test2ActionEventHandler);
        test2Action.setDisplayName("Test _2");
        test2Action.setAccelerator(KeyCombination.keyCombination("Shortcut+2"));
        test2Action.setGraphicFactory(new IconFactory("two.png", resourceLoader, false));

        final FXAction test3Action = new FXActionTestAction("Test _3", "Shortcut+3", "three.png");

        return Arrays.asList(test1Action, test2Action, test3Action);
    }

    private List<FXToggleAction> createFXToggleActionsForGrouped(ResourceLoader resourceLoader) {
        final ToggleActionListener<Object> test4ToggleActionListener = new ToggleActionListenerTestAction();
        final FXToggleAction test4ToggleAction = new ToggleActionListenerAdapter(test4ToggleActionListener);
        test4ToggleAction.setDisplayName("Test _4");
        test4ToggleAction.setAccelerator(KeyCombination.keyCombination("Shortcut+4"));
        test4ToggleAction.setGraphicFactory(new IconFactory("four.png", resourceLoader, false));

        final FXToggleAction test5ToggleAction = new FXToggleActionTestAction("Test _5", "Shortcut+5", "five.png");

        return Arrays.asList(test4ToggleAction, test5ToggleAction);
    }

    private List<FXToggleAction> createFXToggleActionsForUngrouped(ResourceLoader resourceLoader) {
        final ToggleActionListener<Object> test6ToggleActionListener = new ToggleActionListenerTestAction();
        final FXToggleAction test6ToggleAction = new ToggleActionListenerAdapter(test6ToggleActionListener);
        test6ToggleAction.setDisplayName("Test _6");
        test6ToggleAction.setAccelerator(KeyCombination.keyCombination("Shortcut+6"));
        test6ToggleAction.setGraphicFactory(new IconFactory("six.png", resourceLoader, false));

        final FXToggleAction test7ToggleAction = new FXToggleActionTestAction("Test _7", "Shortcut+7", "seven.png");

        return Arrays.asList(test6ToggleAction, test7ToggleAction);
    }

    private List<FXAction> createContextSensitiveFXActions(ResourceLoader resourceLoader,
            ContextInjector contextInjector) {
        final ActiveContextSensitiveAction test8Action = new ActiveContextSensitiveAction();
        contextInjector.inject(test8Action);
        final FXAction test8ActionListenerAdapter = new ActionListenerAdapter(test8Action);
        test8ActionListenerAdapter.setDisplayName("Test _8");
        test8ActionListenerAdapter.setAccelerator(KeyCombination.keyCombination("Shortcut+8"));
        test8ActionListenerAdapter.setGraphicFactory(new IconFactory("eight.png", resourceLoader, false));

        final ApplicationContextSensitiveAction test9Action = new ApplicationContextSensitiveAction();
        contextInjector.inject(test9Action);
        final FXAction test9ActionListenerAdapter = new ActionListenerAdapter(test9Action);
        test9ActionListenerAdapter.setDisplayName("Test _9");
        test9ActionListenerAdapter.setAccelerator(KeyCombination.keyCombination("Shortcut+9"));
        test9ActionListenerAdapter.setGraphicFactory(new IconFactory("nine.png", resourceLoader, false));

        return Arrays.asList(test8ActionListenerAdapter, test9ActionListenerAdapter);
    }

    private void addContent(BorderPane contentPane, List<FXAction> fXActions,
            List<FXToggleAction> fxToggleActionsForGrouped, List<FXToggleAction> fxToggleActionsForUngrouped,
            List<FXAction> contextSensitiveFXActions, ContextManager contextManager) {
        GridPane checkBoxPane = new GridPane();
        int row = 0;
        int column = 0;
//        for (FXAction fxAction : fXActions) {
//            addEnabledCheckedBox(checkBoxPane, fxAction, row, column++);
//        }
        column++;
        addMyCommandCheckBox(checkBoxPane, contextManager, row, column);
        contentPane.setCenter(checkBoxPane);
    }

//    private void addEnabledCheckedBox(GridPane checkBoxPane, FXAction fxAction, int row, int column) {
//        CheckBox enabledCheckBox = new CheckBox(fxAction.getDisplayName() + " enabled:");
//        enabledCheckBox.selectedProperty().addListener(event -> fxAction.setEnabled());
//    }
    private void addMyCommandCheckBox(GridPane checkBoxPane, ContextManager contextManager, int row, int column) {
        SimpleContextContent contextContent = new SimpleContextContent();
        SimpleContext localContext = new SimpleContext(contextContent);
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
        contextManager.putLocalContext(this, localContext);
        contextManager.setLocalContextActive(this);
        checkBoxPane.add(myCommandCheckBox, column, row);
    }

}
