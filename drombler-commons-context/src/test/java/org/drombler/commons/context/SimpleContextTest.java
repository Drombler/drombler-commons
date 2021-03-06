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
package org.drombler.commons.context;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import static org.drombler.commons.context.ContextTests.assertEqualsMyCustomFoo;
import static org.drombler.commons.context.ContextTests.assertEqualsMyCustomFooList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author puce
 */
public class SimpleContextTest {

    private final SimpleContextContent content = new SimpleContextContent();
    private final SimpleContext context = new SimpleContext(content);

    /**
     * Test of find method, of class SimpleContext.
     */
    @Test
    public void testFind() {
        System.out.println("find");

        MyCustomFoo foo = new MyCustomFoo();
        content.add(foo);

        assertEqualsMyCustomFoo(context, foo);

        content.remove(foo);

        assertEqualsMyCustomFoo(context, null);
    }

    /**
     * Test of findAll method, of class SimpleContext.
     */
    @Test
    public void testFindAll() {
        System.out.println("findAll");

        MyCustomFoo foo1 = new MyCustomFoo();
        content.add(foo1);

        assertEqualsMyCustomFooList(context, Arrays.asList(foo1));

        MyCustomFoo foo2 = new MyCustomFoo();
        content.add(foo2);

        assertEqualsMyCustomFooList(context, Arrays.asList(foo1, foo2));

        content.remove(foo1);

        assertEqualsMyCustomFooList(context, Arrays.asList(foo2));

        content.remove(foo2);

        assertEqualsMyCustomFooList(context, Collections.<MyCustomFoo>emptyList());

    }

    @Test
    public void testAddContextListener() {
        System.out.println("addContextListener");

        TestContextListener<Foo> fooContextListener = new TestContextListener<>();
        context.addContextListener(Foo.class, fooContextListener);
        TestContextListener<MyCustomFoo> myCustomFooContextListener = new TestContextListener<>();
        context.addContextListener(MyCustomFoo.class, myCustomFooContextListener);
        TestContextListener<AbstractFoo> abstractFooContextListener = new TestContextListener<>();
        context.addContextListener(AbstractFoo.class, abstractFooContextListener);
        TestContextListener<Object> objectContextListener = new TestContextListener<>();
        context.addContextListener(Object.class, objectContextListener);

        TestContextListener<Date> dateContextListener = new TestContextListener<>();
        context.addContextListener(Date.class, dateContextListener);

        assertFalse(fooContextListener.isContextChanged());
        assertFalse(myCustomFooContextListener.isContextChanged());
        assertFalse(abstractFooContextListener.isContextChanged());
        assertFalse(objectContextListener.isContextChanged());

        assertFalse(dateContextListener.isContextChanged());

        Foo foo1 = new MyCustomFoo();
        content.add(foo1);

        assertTrue(fooContextListener.isContextChanged());
        assertTrue(myCustomFooContextListener.isContextChanged());
        assertTrue(abstractFooContextListener.isContextChanged());
        assertTrue(objectContextListener.isContextChanged());

        assertFalse(dateContextListener.isContextChanged());

        fooContextListener.reset();
        myCustomFooContextListener.reset();
        abstractFooContextListener.reset();
        objectContextListener.reset();

        assertFalse(fooContextListener.isContextChanged());
        assertFalse(myCustomFooContextListener.isContextChanged());
        assertFalse(abstractFooContextListener.isContextChanged());
        assertFalse(objectContextListener.isContextChanged());

        assertFalse(dateContextListener.isContextChanged());

        content.remove(foo1);

        assertTrue(fooContextListener.isContextChanged());
        assertTrue(myCustomFooContextListener.isContextChanged());
        assertTrue(abstractFooContextListener.isContextChanged());
        assertTrue(objectContextListener.isContextChanged());

        assertFalse(dateContextListener.isContextChanged());
    }

    @Test
    public void testRemoveContextListener() {
        System.out.println("removeContextListener");

        TestContextListener<Foo> fooContextListener = new TestContextListener<>();
        context.addContextListener(Foo.class, fooContextListener);
        TestContextListener<MyCustomFoo> myCustomFooContextListener = new TestContextListener<>();
        context.addContextListener(MyCustomFoo.class, myCustomFooContextListener);
        TestContextListener<AbstractFoo> abstractFooContextListener = new TestContextListener<>();
        context.addContextListener(AbstractFoo.class, abstractFooContextListener);
        TestContextListener<Object> objectContextListener = new TestContextListener<>();
        context.addContextListener(Object.class, objectContextListener);

        TestContextListener<Date> dateContextListener = new TestContextListener<>();
        context.addContextListener(Date.class, dateContextListener);

        context.removeContextListener(Foo.class, fooContextListener);
        context.removeContextListener(MyCustomFoo.class, myCustomFooContextListener);
        context.removeContextListener(AbstractFoo.class, abstractFooContextListener);
        context.removeContextListener(Object.class, objectContextListener);

        context.removeContextListener(Date.class, dateContextListener);

        Foo foo1 = new MyCustomFoo();
        content.add(foo1);

        assertFalse(fooContextListener.isContextChanged());
        assertFalse(myCustomFooContextListener.isContextChanged());
        assertFalse(abstractFooContextListener.isContextChanged());
        assertFalse(objectContextListener.isContextChanged());

        assertFalse(dateContextListener.isContextChanged());

        content.remove(foo1);

        assertFalse(fooContextListener.isContextChanged());
        assertFalse(myCustomFooContextListener.isContextChanged());
        assertFalse(abstractFooContextListener.isContextChanged());
        assertFalse(objectContextListener.isContextChanged());

        assertFalse(dateContextListener.isContextChanged());
    }

    @Test
    public void testAddContentToTwoContexts() {
        assertThrows(IllegalStateException.class, () -> new SimpleContext(content));
    }
}
