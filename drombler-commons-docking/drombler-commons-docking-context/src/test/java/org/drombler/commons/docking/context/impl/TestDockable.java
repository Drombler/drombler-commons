package org.drombler.commons.docking.context.impl;

import java.util.Locale;
import org.drombler.commons.action.command.Savable;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.LocalContextProvider;
import org.drombler.commons.context.SimpleContext;
import org.drombler.commons.context.SimpleContextContent;

class TestDockable implements LocalContextProvider {

    private final SimpleContextContent content = new SimpleContextContent();
    private final SimpleContext context = new SimpleContext(content);
    private final Savable savable = new TestSavable();

    @Override
    public Context getLocalContext() {
        return context;
    }

    public void modify() {
        content.add(savable);
    }

    public void save() {
        content.remove(savable);
    }

    private static class TestSavable implements Savable {

        @Override
        public void save() {
            System.out.println("Save");
        }

        @Override
        public String getDisplayString(Locale inLocale) {
            return "Test Pane";
        }
    }
}
