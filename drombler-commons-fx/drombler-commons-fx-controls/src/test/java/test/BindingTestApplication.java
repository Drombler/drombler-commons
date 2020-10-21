package test;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

/**
 *
 * @author puce
 */
public class BindingTestApplication extends Application {

    private final ObservableList<Integer> entries = FXCollections.observableArrayList();
    private ObjectBinding<Integer> firstEntryBinding;

    @Override
    public void start(Stage primaryStage) {
        firstEntryBinding = Bindings.valueAt(entries, 0);
        firstEntryBinding.addListener((observable, oldValue, newValue) -> System.out.println(oldValue + " -> " + newValue));
        entries.add(21);
        entries.add(22);
        entries.remove(0);
        entries.remove(0);
        entries.addAll(23, 24, 25);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
