package org.drombler.commons.docking.fx;

import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import org.drombler.commons.fx.scene.renderer.AbstractDataRenderer;
import org.drombler.commons.fx.scene.renderer.DataRenderer;

/**
 * A {@link DataRenderer} for {@link FXDockableEntry}.
 *
 * @author puce
 */
public class FXDockableEntryRenderer extends AbstractDataRenderer<FXDockableEntry> {

    private final int graphicSize;

    /**
     * Creates a new instance of this class.
     *
     * @param graphicSize the size of the graphic
     */
    public FXDockableEntryRenderer(int graphicSize) {
        this.graphicSize = graphicSize;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getText(FXDockableEntry item) {
        return item.getDockableData().getTitle();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Node getGraphic(FXDockableEntry item) {
        return item.getDockableData().getGraphicFactory().createGraphic(graphicSize); // TODO: avoid creating new graphic nodes (dockableData.getGraphic() cannot be used since it already has a parent (Tab))
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Tooltip getTooltip(FXDockableEntry item) {
        return item.getDockableData().getTooltip();
    }

}
