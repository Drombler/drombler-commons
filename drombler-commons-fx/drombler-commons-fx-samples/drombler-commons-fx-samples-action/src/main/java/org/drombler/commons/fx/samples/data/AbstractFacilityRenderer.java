package org.drombler.commons.fx.samples.data;

import java.util.List;
import org.drombler.commons.fx.scene.renderer.AbstractDataRenderer;

/**
 *
 * @author puce
 * @param <E>
 */
public  abstract class AbstractFacilityRenderer<E extends Facility> extends AbstractDataRenderer<E> {

    private final List<String> styleClass;

    public AbstractFacilityRenderer(List<String> styleClass){
        this.styleClass = styleClass;
    }
    
    @Override
    public String getText(E item) {
        return item.getName();
    }


    @Override
    public List<String> getStyleClass(E item) {
        return styleClass;
    }

    @Override
    public List<String> getStyleClass() {
        return styleClass;
    }
    
}
