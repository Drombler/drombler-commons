package org.drombler.commons.docking.context.impl;

import org.drombler.commons.docking.context.DockingAreaContainer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author puce
 * @param <DATA>
 * @param <E>
 */
@RunWith(MockitoJUnitRunner.class)
public class DockableDataModifiedManagerTest {

    @InjectMocks
    private DockableDataModifiedManager testee;
    @Mock
    private DockingAreaContainer<TestDockable, ?, ?> dockingAreaContainer;
    private final TestDockable testDockable = new TestDockable();


    @Test
    public void testWithoutDockableDataModifiedManager() {
//        assertFalse(dockableData.isModified());
        testDockable.modify();
//        assertFalse(dockableData.isModified());
    }
//
//    @Test
//    public void testConstructor() {
//        try (DockableDataModifiedManager instance = new DockableDataModifiedManager(dockingPane)) {
//            dockingPane.getDockables().add(dockableEntry);
//            assertFalse(dockableData.isModified());
//            testDockable.modify();
//            assertTrue(dockableData.isModified());
//            testDockable.save();
//            assertFalse(dockableData.isModified());
//        }
//    }
//
//    @Test
//    public void testClose() {
//        try (DockableDataModifiedManager instance = new DockableDataModifiedManager(dockingAreaContainer)) {
//            dockingAreaContainer.getDockables().add(dockableEntry);
//            assertFalse(dockableData.isModified());
//            testDockable.modify();
//            assertTrue(dockableData.isModified());
//            testDockable.save();
//            assertFalse(dockableData.isModified());
//        }
//    }

}
