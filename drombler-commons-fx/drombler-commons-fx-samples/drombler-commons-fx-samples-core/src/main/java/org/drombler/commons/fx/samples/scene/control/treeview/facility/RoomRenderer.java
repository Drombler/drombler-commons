package org.drombler.commons.fx.samples.scene.control.treeview.facility;

import java.util.Arrays;

/**
 *
 * @author puce
 */
public class RoomRenderer extends AbstractFacilityRenderer<Room> {

    public RoomRenderer() {
        super(Arrays.asList("room"));
    }

//    @Override
//    public Tooltip getTooltip(Room item) {
//        return new Tooltip(item.getAddress());
//    }
}