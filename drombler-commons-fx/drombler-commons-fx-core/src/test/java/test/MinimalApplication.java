package test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MinimalApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        MinimalControl minimalControl = new MinimalControl();
        minimalControl.setText("test");
        BorderPane root = new BorderPane(minimalControl);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
