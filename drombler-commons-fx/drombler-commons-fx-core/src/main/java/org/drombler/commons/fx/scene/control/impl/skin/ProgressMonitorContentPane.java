package org.drombler.commons.fx.scene.control.impl.skin;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import org.drombler.commons.fx.fxml.FXMLLoaders;

/**
 *
 * @author puce
 */
public class ProgressMonitorContentPane extends GridPane {

    private final ObjectProperty<Worker<?>> worker = new SimpleObjectProperty<>(this, "worker", null);
    private final IntegerProperty numberOfAdditionalWorkers = new SimpleIntegerProperty(this, "numberOfAdditionalWorkers", 0);

    @FXML
    private Label titleLabel;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button cancelButton;
    @FXML
    private Label moreWorkersIndicatorLabel;
    @FXML
    private ResourceBundle resources;
    private MessageFormat moreWorkersIndicatorLabelMessageFormat;

    public ProgressMonitorContentPane() {
        FXMLLoaders.loadRoot(this);

        cancelButton.setCursor(Cursor.DEFAULT);

        moreWorkersIndicatorLabel.visibleProperty().bind(Bindings.greaterThan(numberOfAdditionalWorkers, 0));
        moreWorkersIndicatorLabel.managedProperty().bind(moreWorkersIndicatorLabel.visibleProperty());
        moreWorkersIndicatorLabel.textProperty().bind(Bindings.createStringBinding(this::getMoreWorkersIndicatorLabelText, numberOfAdditionalWorkers));

        worker.addListener((observable, oldValue, newValue) -> {
            titleLabel.textProperty().unbind();
            progressBar.progressProperty().unbind();
            cancelButton.setOnAction(null);

            if (newValue != null) {
                titleLabel.textProperty().bind(newValue.titleProperty());
                progressBar.progressProperty().bind(newValue.progressProperty());
                cancelButton.setOnAction(event -> newValue.cancel());
            }
        });
    }

    @FXML
    public void initialize() {
        moreWorkersIndicatorLabelMessageFormat = new MessageFormat(resources.getString("moreWorkersIndicatorLabel.textFormat"));
    }

    public final Worker<?> getWorker() {
        return workerProperty().get();
    }

    public final void setWorker(Worker<?> worker) {
        workerProperty().set(worker);
    }

    public ObjectProperty<Worker<?>> workerProperty() {
        return worker;
    }

    public final int getNumberOfAdditionalWorkers() {
        return numberOfAdditionalWorkersProperty().get();
    }

    public final void setNumberOfAdditionalWorkers(int numberOfAdditionalWorkers) {
        numberOfAdditionalWorkersProperty().set(numberOfAdditionalWorkers);
    }

    public IntegerProperty numberOfAdditionalWorkersProperty() {
        return numberOfAdditionalWorkers;
    }

    private String getMoreWorkersIndicatorLabelText() {
        return moreWorkersIndicatorLabelMessageFormat.format(new Object[]{getNumberOfAdditionalWorkers()});
    }
}
