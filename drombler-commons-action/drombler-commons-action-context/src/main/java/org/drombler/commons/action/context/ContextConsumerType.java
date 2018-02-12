package org.drombler.commons.action.context;

import org.drombler.commons.context.ContextEvent;

/**
 *
 * @author puce
 */


 interface ContextConsumerType<T> {

    void find(ContextEvent<T> contextEvent);

    void onAction();

    boolean isEnabled();
}
