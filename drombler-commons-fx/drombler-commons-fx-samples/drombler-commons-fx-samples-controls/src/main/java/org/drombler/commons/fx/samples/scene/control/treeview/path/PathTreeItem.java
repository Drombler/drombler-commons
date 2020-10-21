package org.drombler.commons.fx.samples.scene.control.treeview.path;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 *
 * @author puce
 */
public class PathTreeItem extends TreeItem<Path> {

    private boolean firstTimeChildren = true;
    private boolean firstTimeLeaf = true;
    private boolean leaf;

    public PathTreeItem(Path path) {
        super(path);
    }

    @Override
    public ObservableList<TreeItem<Path>> getChildren() {
        if (firstTimeChildren) {
            this.firstTimeChildren = false;
            super.getChildren().setAll(buildChildren());
        }
        return super.getChildren();
    }

    @Override
    public boolean isLeaf() {
        if (firstTimeLeaf) {
            this.firstTimeLeaf = false;
            Path path = getValue();
            this.leaf = !Files.isDirectory(path);
        }
        return leaf;
    }

    private ObservableList<TreeItem<Path>> buildChildren() {
        Path path = getValue();
        if (path != null && Files.isDirectory(path)) {
            try (Stream<Path> pathStream = Files.list(path)) {
                return pathStream
                        .map(PathTreeItem::new)
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));
            } catch (IOException ex) {
                return FXCollections.emptyObservableList();
            }
        } else {
            return FXCollections.emptyObservableList();
        }
    }
}
