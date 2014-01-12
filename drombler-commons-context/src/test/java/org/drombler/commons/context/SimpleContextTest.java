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

import org.drombler.commons.context.SimpleContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.drombler.commons.context.ContextTests.*;

/**
 *
 * @author puce
 */
public class SimpleContextTest {

    private SimpleContext context;


    @Before
    public void setUp() {
        context = new SimpleContext();;
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of find method, of class SimpleContext.
     */
    @Test
    public void testFind() {
        System.out.println("find");

        MyCustomFoo foo = new MyCustomFoo();
        context.add(foo);

        assertEqualsMyCustomFoo(context, foo);

        context.remove(foo);

        assertEqualsMyCustomFoo(context, null);
    }



    /**
     * Test of findAll method, of class SimpleContext.
     */
    @Test
    public void testFindAll() {
        System.out.println("findAll");

        MyCustomFoo foo1 = new MyCustomFoo();
        context.add(foo1);

        assertEqualsMyCustomFooList(context, Arrays.asList(foo1));

        MyCustomFoo foo2 = new MyCustomFoo();
        context.add(foo2);

        assertEqualsMyCustomFooList(context, Arrays.asList(foo1, foo2));


        context.remove(foo1);

        assertEqualsMyCustomFooList(context, Arrays.asList(foo2));

        context.remove(foo2);

        assertEqualsMyCustomFooList(context, Collections.<MyCustomFoo>emptyList());


    }



    @Test
    public void testAddContextListener() {
        System.out.println("addContextListener");

        TestContextListener fooContextListener = new TestContextListener();
        context.addContextListener(Foo.class, fooContextListener);
        TestContextListener myCustomFooContextListener = new TestContextListener();
        context.addContextListener(MyCustomFoo.class, myCustomFooContextListener);
        TestContextListener abstractFooContextListener = new TestContextListener();
        context.addContextListener(AbstractFoo.class, abstractFooContextListener);
        TestContextListener objectContextListener = new TestContextListener();
        context.addContextListener(Object.class, objectContextListener);

        TestContextListener dateContextListener = new TestContextListener();
        context.addContextListener(Date.class, dateContextListener);

        assertFalse(fooContextListener.isContextChanged());
        assertFalse(myCustomFooContextListener.isContextChanged());
        assertFalse(abstractFooContextListener.isContextChanged());
        assertFalse(objectContextListener.isContextChanged());

        assertFalse(dateContextListener.isContextChanged());

        Foo foo1 = new MyCustomFoo();
        context.add(foo1);

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

        context.remove(foo1);

        assertTrue(fooContextListener.isContextChanged());
        assertTrue(myCustomFooContextListener.isContextChanged());
        assertTrue(abstractFooContextListener.isContextChanged());
        assertTrue(objectContextListener.isContextChanged());

        assertFalse(dateContextListener.isContextChanged());
    }

    @Test
    public void testRemoveContextListener() {
        System.out.println("removeContextListener");

        TestContextListener fooContextListener = new TestContextListener();
        context.addContextListener(Foo.class, fooContextListener);
        TestContextListener myCustomFooContextListener = new TestContextListener();
        context.addContextListener(MyCustomFoo.class, myCustomFooContextListener);
        TestContextListener abstractFooContextListener = new TestContextListener();
        context.addContextListener(AbstractFoo.class, abstractFooContextListener);
        TestContextListener objectContextListener = new TestContextListener();
        context.addContextListener(Object.class, objectContextListener);

        TestContextListener dateContextListener = new TestContextListener();
        context.addContextListener(Date.class, dateContextListener);

        context.removeContextListener(Foo.class, fooContextListener);
        context.removeContextListener(MyCustomFoo.class, myCustomFooContextListener);
        context.removeContextListener(AbstractFoo.class, abstractFooContextListener);
        context.removeContextListener(Object.class, objectContextListener);

        context.removeContextListener(Date.class, dateContextListener);

        Foo foo1 = new MyCustomFoo();
        context.add(foo1);

        assertFalse(fooContextListener.isContextChanged());
        assertFalse(myCustomFooContextListener.isContextChanged());
        assertFalse(abstractFooContextListener.isContextChanged());
        assertFalse(objectContextListener.isContextChanged());

        assertFalse(dateContextListener.isContextChanged());

        context.remove(foo1);

        assertFalse(fooContextListener.isContextChanged());
        assertFalse(myCustomFooContextListener.isContextChanged());
        assertFalse(abstractFooContextListener.isContextChanged());
        assertFalse(objectContextListener.isContextChanged());

        assertFalse(dateContextListener.isContextChanged());
    }
}
