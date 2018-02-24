package org.drombler.commons.settings.fx;

import java.util.Optional;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.drombler.commons.settings.fx.impl.skin.DefaultSettingsCategoryPane;

/**
 *
 * @author puce
 */
public class SettingsDialogTest extends Application {

    @Override
    public void start(Stage primaryStage) {
        SettingsPane settingsPane = new SettingsPane();
        Dialog<FooSettings> dialog = new Dialog<>();
        dialog.getDialogPane().setContent(settingsPane);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.APPLY, ButtonType.CANCEL);
        final Node applyNode = dialog.getDialogPane().lookupButton(ButtonType.APPLY);
        if (applyNode instanceof Button) {
            final Button applyButton = (Button) applyNode;
            applyButton.addEventFilter(ActionEvent.ACTION, event -> {
                store();
                event.consume();
            });
        }
        SettingsCategory aCategory = new SettingsCategory();
        aCategory.setId("a");
        aCategory.setDisplayName("Aaa");
        aCategory.setDisplayDescription("Something Aaa.");
        aCategory.setContentPaneType(DefaultSettingsCategoryPane.class);
        SettingsCategory fooCategory = new SettingsCategory();
        fooCategory.setId("foo");
        fooCategory.setDisplayName("Foo");
        fooCategory.setContentPaneType(FooSettingsPane.class);
        aCategory.getSubCategories().add(fooCategory);
        SettingsCategory cCategory = new SettingsCategory();
        cCategory.setId("c");
        cCategory.setDisplayName("Ccc");
        cCategory.setDisplayDescription("Something Ccc.");
        cCategory.setContentPaneType(DefaultSettingsCategoryPane.class);
//        Settings<FooSettings> foo = new Settings<>(bCategory, FooSettings.class, FooSettingsPane.class);

        settingsPane.getTopCategories().addAll(aCategory, cCategory);

        SettingsCategory barCategory = new SettingsCategory();
        barCategory.setId("bar");
        barCategory.setDisplayName("Bar");
        barCategory.setContentPaneType(BarSettingsPane.class);
        aCategory.getSubCategories().add(0, barCategory);

        Button btn = new Button();
        btn.setText("Settings...'");
        btn.setOnAction((ActionEvent event) -> {
            Optional<FooSettings> fooSettingsOptional = dialog.showAndWait();
            if (fooSettingsOptional.isPresent()) {
//                FooSettings fooSettings = fooSettingsOptional.get();
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void store() {
        System.out.println("Store...");
    }

}
