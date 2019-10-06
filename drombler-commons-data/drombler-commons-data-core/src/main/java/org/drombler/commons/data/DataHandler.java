/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (GitHub user: puce77).
 * Copyright 2016 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.data;

import org.drombler.commons.context.LocalContextProvider;
import org.softsmithy.lib.beans.Bean;
import org.softsmithy.lib.util.CloseEventListener;
import org.softsmithy.lib.util.UniqueKeyProvider;

/**
 * A data handler. A data handler usually knows how to read, save etc. a particular kind of data.
 *
 * To fully integrate with the framework it should observe registered {@link DataCapabilityProvider}s and add the found data capabilities to it's local context.
 *
 *
 * @param <K> the type of the unique key of this data handler
 * @author puce
 */
public interface DataHandler<K> extends LocalContextProvider, UniqueKeyProvider<K>, Bean, AutoCloseable {

    /**
     * The name of the 'uniqueKey' property.
     *
     * @see #getUniqueKey()
     */
    String UNIQUE_KEY_PROPERTY_NAME = "uniqueKey";

    /**
     * The name of the 'title' property.
     *
     * @see #getTitle()
     */
    String TITLE_PROPERTY_NAME = "title";

    /**
     * The name of the 'tooltipText' property.
     *
     * @see #getTooltipText()
     */
    String TOOLTIP_TEXT_PROPERTY_NAME = "tooltipText";

    /**
     * The name of the 'dirty' property.
     *
     * @see #isDirty()
     */
    String DIRTY_PROPERTY_NAME = "dirty";

    /**
     * The name of the 'initialized' property.
     *
     * @see #isInitialized()
     */
    String INITIALIZED_PROPERTY_NAME = "initialized";

    /**
     * Gets the title of this data handler. It's intended to be used e.g. in tabs and cells.<br>
     * <br>
     * This is a bound property.
     *
     * @return the title of this data handler
     * @see #TITLE_PROPERTY_NAME
     */
    String getTitle();

    /**
     * Gets the tooltip text. This text is intended to be used in tooltips.<br>
     * <br>
     * This is a bound property.
     *
     * @return the tooltip text
     * @see #TOOLTIP_TEXT_PROPERTY_NAME
     */
    String getTooltipText();

    /**
     * Marks the content of this data handler as dirty.<br>
     * <br>
     * If the content has been modified but the user discarded the changes, the data handler should be marked as dirty to indicate that the content needs to be reloaded from its source when it's
     * accessed the next time.
     *
     * @see #isDirty()
     */
    void markDirty();

    /**
     * A flag if the content of this data handler is dirty.<br>
     * <br>
     * If the content is marked as dirty it should be reloaded from its source when it's accessed the next time.<br>
     * <br>
     * This is a bound property.
     *
     * @return true, if the content is marked as dirty, else false
     * @see #DIRTY_PROPERTY_NAME
     */
    boolean isDirty();

    /**
     * A flag if this data handler is initialized.<br>
     * <br>
     * This is a bound property.
     *
     * @return true, if the data handler is initialized, else false
     * @see #INITIALIZED_PROPERTY_NAME
     */
    boolean isInitialized();

    /**
     * Registers a listener, which gets notified when this DataHandler gets closed.
     *
     * @param listener the listener to add
     */
    void addCloseEventListener(CloseEventListener listener);

    /**
     * Unregisters a listener, which gets notified when this DataHandler gets closed.
     *
     * @param listener the listener to remove
     */
    void removeCloseEventListener(CloseEventListener listener);

}
