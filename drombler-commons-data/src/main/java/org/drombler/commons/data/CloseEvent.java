package org.drombler.commons.data;

import java.util.EventObject;

/**
 *
 * @author puce
 */
// TODO: move to SoftSmithy
public class CloseEvent extends EventObject {

    private static final long serialVersionUID = 4350767329150470528L;

    public CloseEvent(Object source) {
        super(source);
    }

}
