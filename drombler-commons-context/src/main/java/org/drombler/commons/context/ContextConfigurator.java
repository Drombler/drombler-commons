package org.drombler.commons.context;


/**
 *
 * @author puce
 */
public class ContextConfigurator {
    private final ContextManager contextManager = new ContextManager();
    private final ContextInjector contextInjector = new ContextInjector(contextManager);

    public void configureInstance(Object obj, LocalProxyContext context) {
        contextManager.putLocalContext(obj, context);
        contextInjector.inject(obj);
    }
}
