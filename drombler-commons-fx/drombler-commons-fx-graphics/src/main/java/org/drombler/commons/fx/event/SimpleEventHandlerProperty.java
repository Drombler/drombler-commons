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
import javafx.scene.Node;

/**
 * An {@link EventHandler} property.<br>
 * <br>
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
 * @param <E> the event type
 * @see Node#setEventHandler(javafx.event.EventType, javafx.event.EventHandler)
 * @author puce
 */
public class SimpleEventHandlerProperty<E extends Event> extends ObjectPropertyBase<EventHandler<E>> {

    private final Object bean;
    private final String name;
    private final EventType<E> eventType;
    private final EventHandlerRegistrar eventHandlerRegistrar;

    /**
     * Creates a new instance of this class.
     *
     * @param bean the bean that contains this property
     * @param name the name of this property
     * @param eventType the event type to associate with the value of this property
     * @param eventHandlerRegistrar the event handler registrar. Use: {@code this::setEventHandler}
     * @see Node#setEventHandler(javafx.event.EventType, javafx.event.EventHandler)
     */
    public SimpleEventHandlerProperty(Object bean, String name, EventType<E> eventType,
            EventHandlerRegistrar eventHandlerRegistrar) {
        this.bean = bean;
        this.name = name;
        this.eventType = eventType;
        this.eventHandlerRegistrar = eventHandlerRegistrar;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void invalidated() {
        getEventHandlerRegistrar().registerEventHandler(getEventType(), get());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getBean() {
        return bean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Gets the event type to associate with the value of this property.
     *
     * @return the event type to associate with the value of this property
     */
    public EventType<E> getEventType() {
        return eventType;
    }

    /**
     * Gets the event handler registrar.<br>
     * <br>
     * Note: This method might be removed in a future version!
     *
     * @return the event handler registrar
     */
    public EventHandlerRegistrar getEventHandlerRegistrar() {
        return eventHandlerRegistrar;
    }

}
