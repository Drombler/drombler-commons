package org.drombler.commons.docking.fx;

import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import org.drombler.commons.fx.scene.renderer.AbstractDataRenderer;

/**
 *
 * @author puce
 */
public class FXDockableEntryRenderer extends AbstractDataRenderer<FXDockableEntry> {

    private final int graphicSize;

    public FXDockableEntryRenderer(int graphicSize) {
        this.graphicSize = graphicSize;
    }

    @Override
    public String getText(FXDockableEntry item) {
        return item.getDockableData().getTitle();
    }

    @Override
    public Node getGraphic(FXDockableEntry item) {
        return item.getDockableData().getGraphicFactory().createGraphic(graphicSize); // TODO: avoid creating new graphic nodes (dockableData.getGraphic() cannot be used since it already has a parent (Tab))
    }

    @Override
    public Tooltip getTooltip(FXDockableEntry item) {
        return item.getDockableData().getTooltip();
    }

}
