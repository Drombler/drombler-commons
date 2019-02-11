package org.drombler.commons.fx.samples.scene.control.treeview.facility;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.drombler.commons.fx.scene.control.RenderedTreeCellFactory;

/**
 *
 * @author puce
 */
public class FacilityTreeViewSample extends Application {

    @Override
    public void start(Stage primaryStage) {
        Building building = createBuilding();

        BorderPane root = new BorderPane();

        TreeView<Facility> facilityTreeView = createFacilityTreeView(building);
        facilityTreeView.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends TreeItem<Facility>> observable, TreeItem<Facility> oldValue, TreeItem<Facility> newValue) -> {
                    if (newValue != null) {
                        root.setCenter(((AbstractFacilityTreeItem<?>) newValue).createFacilityEditor());
                    } else {
                        root.setCenter(null);
                    }
                });

        BorderPane detailsParentPane = new BorderPane();
        root.setLeft(facilityTreeView);
        root.setCenter(detailsParentPane);
        Scene scene = new Scene(root, 600, 250);
        scene.getStylesheets().add(FacilityTreeViewSample.class.getResource("FacilityTreeViewSample.css").toExternalForm());

        primaryStage.setTitle("Facility TreeView Sample");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TreeView<Facility> createFacilityTreeView(Building building) {
        // A TreeView of the common base type Facility
        TreeView<Facility> treeView = new TreeView<>();

        // A RenderedTreeCellFactory of the common base type Facility
        // It allows to register DataRenderers for subclasses
        RenderedTreeCellFactory<Facility> renderedTreeCellFactory = new RenderedTreeCellFactory<>();
        renderedTreeCellFactory.registerDataRenderer(Building.class, new BuildingRenderer());
        renderedTreeCellFactory.registerDataRenderer(Floor.class, new FloorRenderer());
        renderedTreeCellFactory.registerDataRenderer(Room.class, new RoomRenderer());

        treeView.setCellFactory(renderedTreeCellFactory);
        treeView.setRoot(new BuildingTreeItem(building));

        treeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        return treeView;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private static Building createBuilding() {
        Building building = new Building();
        building.setName("Building 1");
        building.setAddress("Some Street 1, 3000 Bern");
        building.getFloors().addAll(createFloor(1), createFloor(2));
        return building;
    }

    private static Floor createFloor(int level) {
        Floor floor = new Floor();
        floor.setName("Floor " + level);
        floor.getRooms().addAll(createRoom("A" + level, 8), createRoom("C" + level, 20));
        return floor;
    }

    private static Room createRoom(String name, int numPersons) {
        Room room = new Room();
        room.setName(name);
        room.setCapacity(numPersons);
        return room;
    }

}
