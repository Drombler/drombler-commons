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
 * Copyright 2012 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.context;

import java.util.EventObject;

/**
 * An event which indicates the content of a {@link Context} changed.
 *
 * @author puce
 * @param <T> the type of objects which have been added or removed from the source context.
 */
public class ContextEvent<T> extends EventObject {

    private static final long serialVersionUID = 5546522706282340729L;

    private final Context sourceContext;
    private final Class<T> type;

    /**
     * Creates a new instance of this class.
     *
     * @param sourceContext
     * @param type
     */
    public ContextEvent(Context sourceContext, Class<T> type) {
        super(sourceContext);
        this.sourceContext = sourceContext;
        this.type = type;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Context getSource() {
        return sourceContext;
    }

    /**
     * Gets the type of objects which have been added or removed from the source context.
     * 
     * @return the type of objects which have been added or removed from the source context
     */
    public Class<T> getType() {
        return type;
    }

    /*
     * Finds an instance of the type associated with this event in the source context. If the source context has more than one instance of the specified type, the first one found will be returned.
     *
     * @return the first instance found in the source context with the specified type, or {@code null} if no instance was found.
     *
     * TODO: return Optional?
     */
//    public T find() {
//        return sourceContext.find(type);
//    }

    /*
     * Finds all instances of the type associated with this event in the source context.
     *
     * @return a collection with all instances of the type associated with this event found in the source context, or an empty collection if no instance was found
     *
     * TODO: return {@code List} instead of {@code Collection}? <br>
     * TODO: return {@code <T>} instead of {@code <? extends T>}?
     */
//    public Collection<? extends T> findAll() {
//        return sourceContext.findAll(type);
//    }
}
