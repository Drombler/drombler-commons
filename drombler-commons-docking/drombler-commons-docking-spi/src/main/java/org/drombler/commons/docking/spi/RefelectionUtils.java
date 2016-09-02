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
 * Copyright 2015 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.docking.spi;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import org.drombler.commons.docking.DockableEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softsmithy.lib.util.PositionableAdapter;

/**
 *
 * @author puce77
 */
class RefelectionUtils {

    private static final Logger LOG = LoggerFactory.getLogger(RefelectionUtils.class);

    private RefelectionUtils() {
    }

    public static void invokeAnnotatedDockableMethod(
            PositionableAdapter<? extends DockableEntry<?, ?>> positionableDockableEntry,
            Class<? extends Annotation> annotationType) {
        if (positionableDockableEntry != null && positionableDockableEntry.getAdapted() != null && positionableDockableEntry.
                getAdapted().getDockable() != null) {
            Object dockable = positionableDockableEntry.getAdapted().getDockable();
            try {
                invokeAnnotatedDeclaredMethod(dockable, annotationType);
            } catch (IllegalAccessException | IllegalArgumentException |
                    InvocationTargetException ex) {
                LOG.error(ex.getMessage(), ex);
            }
        }
    }

    private static void invokeAnnotatedDeclaredMethod(Object obj, Class<? extends Annotation> annotationType)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        Optional<Method> annotatedMethod = Arrays.stream(obj.getClass().getDeclaredMethods())
                .filter(method -> method.getParameterCount() == 0)
                .filter(method -> method.isAnnotationPresent(annotationType))
                .findFirst();

        if (annotatedMethod.isPresent()) {
            annotatedMethod.get().invoke(obj);
        }
    }
}
