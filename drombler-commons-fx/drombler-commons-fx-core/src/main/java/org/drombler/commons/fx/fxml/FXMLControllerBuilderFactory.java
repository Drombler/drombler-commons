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
package org.drombler.commons.fx.fxml;

import javafx.util.Builder;
import javafx.util.BuilderFactory;

/**
 *
 * @author puce
 */
class FXMLControllerBuilderFactory implements BuilderFactory {

    private final BuilderFactory builderFactory;

    public FXMLControllerBuilderFactory(BuilderFactory builderFactory) {
        this.builderFactory = builderFactory;
    }

    @Override
    public Builder<?> getBuilder(Class<?> type) {
        Builder<?> builder = builderFactory.getBuilder(type);
        if (builder != null) {
            return new FXMLControllerBuilder<>(builder);
        } else {
            return null;
        }
    }

    private static class FXMLControllerBuilder<T> implements Builder<T> {

        private final Builder<T> builder;

        public FXMLControllerBuilder(Builder<T> builder) {
            this.builder = builder;
        }

        @Override
        public T build() {
            T t = builder.build();
//            FXMLController fxmlControllerAnnotation = t.getClass().getAnnotation(FXMLController.class);
//            if (fxmlControllerAnnotation != null) {
//                try {
//                    FXMLLoaders.loadRoot(t, fxmlControllerAnnotation);
//                } catch (IOException ex) {
//                    throw new FXMLLoadingException(ex);
//                }
//            }
            return t;
        }
    }
}
