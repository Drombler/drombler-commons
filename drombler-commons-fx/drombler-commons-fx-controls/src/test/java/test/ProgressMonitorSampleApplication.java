package test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import org.drombler.commons.fx.concurrent.WorkerUtils;
import org.drombler.commons.fx.scene.control.ProgressMonitor;
import org.drombler.commons.fx.scene.control.StatusBar;

public class ProgressMonitorSampleApplication extends Application {

    private final List<Worker<?>> workers = new ArrayList<>();
    private final ExecutorService executorService = Executors.newCachedThreadPool(runnable -> {
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        return thread;
    });
    private long counter = 1;

    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        StatusBar statusBar = new StatusBar();
        ProgressMonitor progressMonitor = new ProgressMonitor();
        statusBar.getRightElements().add(progressMonitor);

        addTestWorker(counter++, progressMonitor);
        Thread.sleep(2000l);
        addTestWorker(counter++, progressMonitor);

        Button createButton = new Button("Create Task");
        createButton.setOnAction(event -> addTestWorker(counter++, progressMonitor));

        GridPane root = new GridPane();
        root.add(createButton, 0, 0);
        root.add(statusBar, 0, 1);
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHgrow(Priority.ALWAYS);
        columnConstraints.setHalignment(HPos.CENTER);
        root.getColumnConstraints().add(columnConstraints);

        RowConstraints rowConstraints1 = new RowConstraints();
        rowConstraints1.setVgrow(Priority.ALWAYS);
        rowConstraints1.setFillHeight(false);
        RowConstraints rowConstraints2 = new RowConstraints();
        rowConstraints2.setVgrow(Priority.NEVER);
        rowConstraints2.setFillHeight(false);
        root.getRowConstraints().addAll(rowConstraints1, rowConstraints2);

        GridPane.setFillWidth(createButton, Boolean.FALSE);
        GridPane.setFillWidth(statusBar, Boolean.TRUE);

        Scene scene = new Scene(root, 800, 200);

        primaryStage.setTitle("ProgressMonitorSampleApplication");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addTestWorker(long id, ProgressMonitor progressMonitor) {
        final TestWorker testWorker = new TestWorker(id);
        testWorker.stateProperty().addListener((observable, oldValue, newValue) -> {
            if (WorkerUtils.getFinishedStates().contains(newValue)) {
                workers.remove(testWorker);
            }
        });
        workers.add(testWorker);
        executorService.execute(testWorker);
        progressMonitor.getWorkers().add(testWorker);
    }

    public static void main(String[] args) {
        configureLogging();
        launch(args);
    }

    private static void configureLogging() {
        Locale.setDefault(Locale.ENGLISH);
        Path loggingPropertiesPath = Paths.get("src", "test", "resources", "test", "logging.properties");
        System.out.println("Logging configuration file: " + loggingPropertiesPath + " exists: " + Files.exists(loggingPropertiesPath));
        if (Files.exists(loggingPropertiesPath)) {
            System.setProperty("java.util.logging.config.file", loggingPropertiesPath.toString());
        }
    }

    private static class TestWorker extends Task<Long> {

        private final long id;
        private long counter = 0;

        public TestWorker(long id) {
            this.id = id;
            updateTitle("Test Worker " + id);
            updateMessage("Iteration " + counter);
            updateProgress(-1, 1);
        }

        @Override
        protected Long call() throws Exception {
            while (!isCancelled()) {
                counter++;
                updateMessage("Iteration " + counter);
                Thread.sleep(1000l);
            }
            return counter;
        }

        @Override
        public String toString() {
            return getTitle();
        }

    }
}
