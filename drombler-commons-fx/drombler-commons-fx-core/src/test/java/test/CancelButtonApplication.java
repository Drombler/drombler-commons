package test;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import test.skin.Stylesheets;

/**
 *
 * @author puce
 */
public class CancelButtonApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
//        btn.setText("Say 'Hello World'");
//btn.setText("x");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        btn.getStyleClass().add("icon-button");
        btn.setPickOnBounds(true);

//        Region icon = new Region();
        SVGPath icon = new SVGPath();
        icon.getStyleClass().add("icon");
//        icon.setContent("M0, 0 L100, 100 M100, 0 L0, 100z");
        icon.setContent("M0, 0 L5, 5 M5, 0 L0, 5z");
        btn.setGraphic(icon);
//        btn.setContentDisplay(ContentDisplay.TEXT_ONLY);

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);
        scene.getStylesheets().add(Stylesheets.getDefaultStylesheet());

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
