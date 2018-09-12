package org.drombler.commons.data.fx;

import java.util.Collections;
import java.util.List;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import org.apache.commons.lang3.StringUtils;
import org.drombler.commons.data.AbstractDataHandlerDescriptor;
import org.drombler.commons.data.DataHandler;
import org.drombler.commons.data.DataHandlerDescriptorRegistry;
import org.drombler.commons.fx.scene.image.IconFactory;
import org.drombler.commons.fx.scene.renderer.DataRenderer;

/**
 * A {@link DataRenderer} for {@link DataHandler}s.
 */
public class DataHandlerRenderer implements DataRenderer<DataHandler<?>> {

    private final DataHandlerDescriptorRegistry dataHandlerDescriptorRegistry;
    private final int graphicSize;

    /**
     * Creates a new instance of this class.
     *
     * @param dataHandlerDescriptorRegistry the DataHandlerDescriptorRegistry to get some meta data of the DataHandler
     * @param graphicSize the graphicSize used in the given context
     */
    public DataHandlerRenderer(DataHandlerDescriptorRegistry dataHandlerDescriptorRegistry, int graphicSize) {
        this.dataHandlerDescriptorRegistry = dataHandlerDescriptorRegistry;
        this.graphicSize = graphicSize;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getText(DataHandler<?> item) {
        return item.getTitle();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Node getGraphic(DataHandler<?> item) {
        AbstractDataHandlerDescriptor<?> dataHandlerDescriptor = dataHandlerDescriptorRegistry.getDataHandlerDescriptor(item);
        String icon = dataHandlerDescriptor.getIcon();
        if (StringUtils.isNotBlank(icon)) {
            IconFactory iconFactory = new IconFactory(icon, dataHandlerDescriptor.getResourceLoader(), true);
            return iconFactory.createGraphic(graphicSize);
        } else {
            return null;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Tooltip getTooltip(DataHandler<?> item) {
        return new Tooltip(item.getTooltipText()); // create a new instance every time???
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<String> getStyleClass(DataHandler<?> item) {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<String> getStyleClass() {
        return Collections.emptyList();
    }

    //https://stackoverflow.com/questions/35009982/javafx-treeview-of-multiple-object-types-and-more
    private PseudoClass asPseudoClass(Class<?> clz) {
        return PseudoClass.getPseudoClass(clz.getSimpleName().toLowerCase());
    }

}
