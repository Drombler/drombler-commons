package org.drombler.commons.context;

import java.util.Collection;

/**
 * A proxy for local contexts to handle implicit and explicit local contexts.
 *
 * The explicit local context is provided by a {@link LocalContextProvider}.
 *
 * Implicit local contexts may be provided by frameworks to associate additional content with an object.
 *
 * @see #createLocalProxyContext(java.lang.Object)
 * @author puce
 */
public class LocalProxyContext implements Context {

    private final ProxyContext proxyContext = new ProxyContext();
    private final ProxyContext implicitContext = new ProxyContext();
    private final Context explicitContext;

    private LocalProxyContext() {
        this(null);
    }

    private LocalProxyContext(Context explicitContext) {
        this.explicitContext = explicitContext;
        if (explicitContext != null) {
            this.proxyContext.addContext(explicitContext);
        }

        this.proxyContext.addContext(implicitContext);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public <T> T find(Class<T> type) {
        return proxyContext.find(type);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public <T> Collection<? extends T> findAll(Class<T> type) {
        return proxyContext.findAll(type);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public <T> void addContextListener(Class<T> type, ContextListener<T> listener) {
        proxyContext.addContextListener(type, listener);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public <T> void removeContextListener(Class<T> type, ContextListener<T> listener) {
        proxyContext.removeContextListener(type, listener);
    }

    /**
     * Gets the implicit proxy context.
     *
     * @return the implicit proxy context
     */
    public ProxyContext getImplicitContext() {
        return implicitContext;
    }

    /**
     * Gets the explicit context (provided by a {@link LocalContextProvider}.
     *
     * @return the explicit context
     */
    public Context getExplicitContext() {
        return explicitContext;
    }

    /**
     * Indicates if this local context has some implicit contexts.
     *
     * @return true, if there are implicit contexts, else false.
     */
    public boolean hasImplicitContext() {
        return !implicitContext.isEmpty();
    }

    /**
     * Indicates if this local context has an explicit context (provided by a {@link LocalContextProvider}.
     *
     * @return true, if this local context has an explicit context, else false.
     */
    public boolean hasExplicitContext() {
        return explicitContext != null;
    }

    /**
     * Creates a new instance of this class for a given object.
     *
     * If the object is an instance of {@link LocalContextProvider}, the provided local context will be set as explicit context.
     *
     * Else an instance with no explicit context gets created.
     *
     * @param obj the given object.
     * @return a LocalProxyContext for the given object
     */
    public static LocalProxyContext createLocalProxyContext(Object obj) {
        if (obj instanceof LocalContextProvider) {
            return new LocalProxyContext(Contexts.getLocalContext(obj));
        } else {
            return new LocalProxyContext();
        }
    }
}
