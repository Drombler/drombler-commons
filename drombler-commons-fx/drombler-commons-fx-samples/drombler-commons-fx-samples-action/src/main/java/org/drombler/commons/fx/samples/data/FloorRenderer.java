package org.drombler.commons.fx.samples.data;

import java.util.Arrays;

/**
 *
 * @author puce
 */
public class FloorRenderer extends AbstractFacilityRenderer<Floor> {

    public FloorRenderer() {
        super(Arrays.asList("floor"));
    }

//    @Override
//    public Tooltip getTooltip(Floor item) {
//        return new Tooltip(item.getAddress());
//    }
}
