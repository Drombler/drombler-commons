package org.drombler.commons.data;

import java.util.EventListener;

/**
 *
 * @author puce
 */
// TODO: move to SoftSmithy
public interface CloseEventListener extends EventListener {

    void onClose(CloseEvent evt);
}
