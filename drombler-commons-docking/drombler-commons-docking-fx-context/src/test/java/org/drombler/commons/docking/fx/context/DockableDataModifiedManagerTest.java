package org.drombler.commons.docking.fx.context;

import java.util.Locale;
import javafx.scene.layout.BorderPane;
import org.drombler.commons.action.command.Savable;
import org.drombler.commons.docking.DockablePreferences;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.LocalContextProvider;
import org.drombler.commons.context.SimpleContext;
import org.drombler.commons.context.SimpleContextContent;
import org.drombler.commons.docking.fx.DockingPane;
import org.drombler.commons.docking.fx.FXDockableData;
import org.drombler.commons.docking.fx.FXDockableEntry;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 *
 * @author puce
 */
@Category(GUITests.class)
public class DockableDataModifiedManagerTest {

    static {
        SimpleControlLauncher.main(new String[]{});
    }

    private final DockingPane dockingPane = new DockingPane();

    private final TestPane testPane = new TestPane();
    private final FXDockableData dockableData = new FXDockableData();
    private final DockablePreferences dockablePreferences = new DockablePreferences();
    private final FXDockableEntry dockableEntry = new FXDockableEntry(testPane, dockableData, dockablePreferences);

    @Test
    public void testWithoutDockableDataModifiedManager() {
        assertFalse(dockableData.isModified());
        testPane.modify();
        assertFalse(dockableData.isModified());
    }

    @Test
    public void testConstructor() {
        try (DockableDataModifiedManager instance = new DockableDataModifiedManager(dockingPane)) {
            dockingPane.getDockables().add(dockableEntry);
            assertFalse(dockableData.isModified());
            testPane.modify();
            assertTrue(dockableData.isModified());
            testPane.save();
            assertFalse(dockableData.isModified());
        }
    }

    @Test
    public void testClose() {
        try (DockableDataModifiedManager instance = new DockableDataModifiedManager(dockingPane)) {
            dockingPane.getDockables().add(dockableEntry);
            assertFalse(dockableData.isModified());
            testPane.modify();
            assertTrue(dockableData.isModified());
            testPane.save();
            assertFalse(dockableData.isModified());
        }
    }

    private static class TestPane extends BorderPane implements LocalContextProvider {

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
}
