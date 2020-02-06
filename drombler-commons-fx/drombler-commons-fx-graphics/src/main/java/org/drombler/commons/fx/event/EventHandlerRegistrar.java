/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (Sourceforge.net user: puce).
 * Copyright 2014 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.fx.event;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;

/**
 * An event handler registrar can register an event handler. This functional interface matches the signature of {@link Node#setEventHandler(javafx.event.EventType, javafx.event.EventHandler) }.<br>
 * <br>
 * Note: This interface might be removed in a future version!
 *
 * @author puce
 */
@FunctionalInterface
public interface EventHandlerRegistrar {

    /**
     * Registers an event handler.
     *
     * @param <T> the event type
     * @param eventType the event type to associate with the event handler
     * @param eventHandler the event handler to register, or null to unregister
     */
    <T extends Event> void registerEventHandler(EventType<T> eventType, EventHandler<? super T> eventHandler);
}
