package sample.number;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author puce
 */
public class NumberFieldsSampleApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        NumberFieldsSamplePane root = new NumberFieldsSamplePane();
        Scene scene = new Scene(root, 600, 400);

        stage.setTitle("Number Fields Sample Apllication");
        stage.setScene(scene);
        stage.show();
    }

}
