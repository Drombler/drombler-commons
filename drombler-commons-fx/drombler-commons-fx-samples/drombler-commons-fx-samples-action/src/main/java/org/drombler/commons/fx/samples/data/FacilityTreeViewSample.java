package org.drombler.commons.fx.samples.data;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });

        BorderPane root = new BorderPane();
        root.setTop(btn);
//        TreeView<DataHandler<?>> treeView = new TreeView<>();
        TreeView<Facility> treeView = new TreeView<>();
//        RenderedTreeCellFactory<DataHandler<?>> renderedTreeCellFactory = new RenderedTreeCellFactory<>();
        RenderedTreeCellFactory<Facility> renderedTreeCellFactory = new RenderedTreeCellFactory<>();
        renderedTreeCellFactory.registerDataRenderer(Building.class, new BuildingRenderer());
        renderedTreeCellFactory.registerDataRenderer(Floor.class, new FloorRenderer());
        renderedTreeCellFactory.registerDataRenderer(Room.class, new RoomRenderer());
        treeView.setCellFactory(renderedTreeCellFactory);
        treeView.setRoot(new BuildingTreeItem(createBuilding()));
        root.setCenter(treeView);
        Scene scene = new Scene(root, 300, 250);

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

    private static Building createBuilding() {
        Building building = new Building();
        building.setName("Building 1");
        building.setAddress("Some Stree 1, 3000 Bern");
        building.getFloors().addAll(createFloor(1), createFloor(2));
        return building;
    }

    private static Floor createFloor(int level) {
        Floor floor = new Floor();
        floor.setName("Floor " + level);
        floor.getRooms().addAll(createRoom("A1", 8), createRoom("C3", 20));
        return floor;
    }

    private static Room createRoom(String name, int numPersons) {
        Room room = new Room();
        room.setName(name);
        room.setNumPersons(numPersons);
        return room;
    }

//    private static class SimpleDirectoryHandler extends AbstractDirectoryHandler{
//        
//    }
}
