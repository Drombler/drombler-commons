package test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class CancelButtonTestApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();

        Label someLabel = new Label("Some Label:");
        Button cancelButton = new Button();
        cancelButton.setStyle("-fx-padding: 0.083333em 0.083333em 0.083333em 0.083333em; -fx-shape: M10,16 10,0 0,8z; -fx-content-display: graphic-only; -size: 16; -icon-paint: black;"
                + " -fx-min-height: -size;" +
"    -fx-min-width: -size;" +
"    -fx-max-height: -size;" +
"    -fx-max-width: -size;"); /* 1 1 1 1 */
        cancelButton.setMaxHeight(Region.USE_PREF_SIZE);
        
        someLabel.setLabelFor(cancelButton);
        gridPane.add(someLabel, 0, 0);
        gridPane.add(cancelButton, 1, 0);
        gridPane.setHgap(5.0d);
        GridPane.setVgrow(cancelButton, Priority.NEVER);
        GridPane.setHgrow(cancelButton, Priority.NEVER);

        Scene scene = new Scene(gridPane, 200, 50);

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

}
