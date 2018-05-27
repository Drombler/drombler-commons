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
// in the event package as it has a dependency to javafx.event
package org.drombler.commons.fx.event;

import javafx.beans.property.ObjectPropertyBase;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;

/**
 * Usage:
 * <pre>
 * {@code
 * public class SomePane extends SomeNode {
 *
 *    private final ObjectProperty<EventHandler<SomeEvent>> onMyCustomEvent = new SimpleEventHandlerProperty<>(this,
 *            "onMyCustomEvent", SomeEvent.SOME_EVENT_TYPE, this::setEventHandler);
 *    ...
 * }
 * }
 * </pre>
 *
 * @author puce
 * @param <E> the event type
 */
public class SimpleEventHandlerProperty<E extends Event> extends ObjectPropertyBase<EventHandler<E>> {

    private final Object bean;
    private final String name;
    private final EventType<E> eventType;
    private final EventHandlerRegistrar eventHandlerRegistrar;

    public SimpleEventHandlerProperty(Object bean, String name, EventType<E> eventType,
            EventHandlerRegistrar eventHandlerRegistrar) {
        this.bean = bean;
        this.name = name;
        this.eventType = eventType;
        this.eventHandlerRegistrar = eventHandlerRegistrar;
    }

    @Override
    protected void invalidated() {
        getEventHandlerRegistrar().registerEventHandler(getEventType(), get());
    }

    @Override
    public Object getBean() {
        return bean;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * @return the eventType
     */
    public EventType<E> getEventType() {
        return eventType;
    }

    /**
     * Note: might be removed in a future version!
     *
     * @return the eventHandlerRegistrar
     */
    public EventHandlerRegistrar getEventHandlerRegistrar() {
        return eventHandlerRegistrar;
    }

}
