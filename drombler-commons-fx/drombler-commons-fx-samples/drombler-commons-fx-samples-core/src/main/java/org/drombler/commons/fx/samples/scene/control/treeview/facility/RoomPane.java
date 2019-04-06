package org.drombler.commons.fx.samples.scene.control.treeview.facility;

import java.text.NumberFormat;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.util.converter.NumberStringConverter;
import org.drombler.commons.fx.fxml.FXMLLoaders;

/**
 *
 * @author puce
 */
public class RoomPane extends GridPane {

    @FXML
    private TextField nameField;

    @FXML
    private TextField capacityField;

    public RoomPane(Room room) {
        FXMLLoaders.loadRoot(this);

        nameField.textProperty().bindBidirectional(room.nameProperty());

//        UnaryOperator<Change> integerFilter = change -> {
//            String newText = change.getControlNewText();
//            if (newText.matches("-?([1-9][0-9]*)?")) {
//                return change;
//            }
//            return null;
//        };
//
//        capacityField.setTextFormatter(
//                new TextFormatter<>(new IntegerStringConverter(), 0, integerFilter));

        final NumberStringConverter integerStringConverter = new NumberStringConverter(NumberFormat.getIntegerInstance());
        capacityField.setTextFormatter(new TextFormatter<>(integerStringConverter));
        capacityField.textProperty().bindBidirectional(room.capacityProperty(),integerStringConverter);
    }

}
