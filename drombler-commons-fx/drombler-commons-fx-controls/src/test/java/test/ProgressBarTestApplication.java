package test;

import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;


public class ProgressBarTestApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        ColumnConstraints columnConstraints0 = new ColumnConstraints();
        columnConstraints0.setHgrow(Priority.SOMETIMES);
        ColumnConstraints columnConstraints1 = new ColumnConstraints();
        columnConstraints1.setHgrow(Priority.NEVER);
        gridPane.getColumnConstraints().addAll(columnConstraints0, columnConstraints1);

        RowConstraints rowConstraints0 = new RowConstraints();
        rowConstraints0.setVgrow(Priority.SOMETIMES);
        gridPane.getRowConstraints().add(rowConstraints0);

        Label someLabel = new Label("Some Label:");
        ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefWidth(250.0d);
        someLabel.setLabelFor(progressBar);
        gridPane.add(someLabel, 0, 0);
        gridPane.add(progressBar, 1, 0);
        gridPane.setCursor(Cursor.HAND);
        gridPane.setHgap(5.0d);
        gridPane.setOnMouseClicked(event -> System.out.println("Clicked!"));

        Scene scene = new Scene(gridPane, 350, 150);

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
