package org.drombler.commons.fx.samples.data;

import java.util.Arrays;
import javafx.scene.control.Tooltip;

/**
 *
 * @author puce
 */
public class BuildingRenderer extends AbstractFacilityRenderer<Building> {

    public BuildingRenderer() {
        super(Arrays.asList("building"));
    }

    @Override
    public Tooltip getTooltip(Building item) {
        return new Tooltip(item.getAddress());
    }



}
