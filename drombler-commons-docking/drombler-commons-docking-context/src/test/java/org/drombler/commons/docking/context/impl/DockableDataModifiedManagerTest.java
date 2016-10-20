package org.drombler.commons.docking.context.impl;

import java.util.HashSet;
import java.util.Set;
import org.drombler.commons.docking.DockableData;
import org.drombler.commons.docking.DockableEntry;
import org.drombler.commons.docking.DockableKind;
import org.drombler.commons.docking.DockablePreferences;
import org.drombler.commons.docking.context.DockingAreaContainer;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.softsmithy.lib.util.SetChangeEvent;
import org.softsmithy.lib.util.SetChangeListener;

/**
 *
 * @author puce
 */
@RunWith(MockitoJUnitRunner.class)
public class DockableDataModifiedManagerTest {

    @Mock
    private DockingAreaContainer<Object, DockableData, DockableEntry<Object, DockableData>> dockingAreaContainer;

    private final TestDockableData dockableData = new TestDockableData();
    private final TestDockable testDockable = new TestDockable();
    private final DockablePreferences dockablePreferences = new DockablePreferences("someArea", 20);
    private final DockableEntry<Object, DockableData> dockableEntry = new DockableEntry<Object, DockableData>(testDockable, DockableKind.VIEW, dockableData, dockablePreferences) {
    };

    private final Set<DockableEntry<Object, DockableData>> dockables = new HashSet<>();
    private SetChangeListener<DockableEntry<Object, DockableData>> listener;

    @Test
    public void testWithoutDockableDataModifiedManager() {
        assertFalse(dockableData.isModified());
        testDockable.modify();
        assertFalse(dockableData.isModified());
    }

    @Test
    public void testConstructor() {
        doAnswer((Answer<Void>) (InvocationOnMock invocation) -> {
            Object[] args = invocation.getArguments();
            listener = (SetChangeListener<DockableEntry<Object, DockableData>>) args[0];
            return null;
        }).when(dockingAreaContainer).addDockableSetChangeListener(any());

        try (DockableDataModifiedManager testee = new DockableDataModifiedManager(dockingAreaContainer)) {
            listener.elementAdded(new SetChangeEvent<>(dockables, dockableEntry));

            assertFalse(dockableData.isModified());
            testDockable.modify();
            assertTrue(dockableData.isModified());
            testDockable.save();
            assertFalse(dockableData.isModified());
        }
    }

    @Test
    public void testClose() {
        doAnswer((Answer<Void>) (InvocationOnMock invocation) -> {
            Object[] args = invocation.getArguments();
            listener = (SetChangeListener<DockableEntry<Object, DockableData>>) args[0];
            return null;
        }).when(dockingAreaContainer).addDockableSetChangeListener(any());

        try (DockableDataModifiedManager testee = new DockableDataModifiedManager(dockingAreaContainer)) {
            listener.elementAdded(new SetChangeEvent<>(dockables, dockableEntry));

            assertFalse(dockableData.isModified());
            testDockable.modify();
            assertTrue(dockableData.isModified());
            testDockable.save();
            assertFalse(dockableData.isModified());
        }

        verify(dockingAreaContainer).removeDockableSetChangeListener(listener);

        assertFalse(dockableData.isModified());
        testDockable.modify();
        assertFalse(dockableData.isModified());
        testDockable.save();
        assertFalse(dockableData.isModified());

    }

}