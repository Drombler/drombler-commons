package org.drombler.commons.fx.concurrent;

import java.util.function.Consumer;
import org.drombler.commons.fx.application.PlatformUtils;

/**
 * Executes a {@link Consumer} on the JavaFX Application Thread.
 *
 * @param <T> the input type
 * @author puce
 */
public class FXConsumer<T> implements Consumer<T> {

    private final Consumer<T> consumer;

    /**
     * Creates a new instance of this class.
     *
     * @param consumer the consumer to call
     */
    public FXConsumer(Consumer<T> consumer) {
        this.consumer = consumer;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void accept(T t) {
        PlatformUtils.runOnFxApplicationThread(() -> consumer.accept(t));
    }

}
