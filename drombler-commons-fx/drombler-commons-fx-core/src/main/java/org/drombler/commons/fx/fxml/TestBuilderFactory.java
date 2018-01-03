package org.drombler.commons.fx.fxml;

import javafx.util.Builder;
import javafx.util.BuilderFactory;

/**
 *
 * @author puce
 */
class TestBuilderFactory implements BuilderFactory {

    private final BuilderFactory builderFactory;

    public TestBuilderFactory(BuilderFactory builderFactory) {
        this.builderFactory = builderFactory;
    }

    @Override
    public Builder<?> getBuilder(Class<?> type) {
        return createBuilderWrapper(type);
    }
    
    private <T> Builder<T> createBuilderWrapper(Class<T> type){
        @SuppressWarnings("unchecked")
        Builder<T> builder = (Builder<T>) builderFactory.getBuilder(type);
        return new BuilderWrapper<>(builder, type);
    }

    private static class BuilderWrapper<T> implements Builder<T> {

        private final Builder<T> builder;
        private final Class<T> type;

        public BuilderWrapper(Builder<T> builder, Class<T> type) {
            this.builder = builder;
            this.type = type;
        }

        @Override
        public T build() {
            T t = doBuild();
            doSomething(t);
            return t;
        }

        private T doBuild() throws FXMLLoadingException {
            if (builder != null) {
                return builder.build();
            } else {
                try {
                    return type.newInstance();
                } catch (InstantiationException | IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        private void doSomething(T t) {
            // do something
        }
    }
}
