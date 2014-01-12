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

import org.drombler.commons.context.ProxyContext;
import org.drombler.commons.context.Context;
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
public class ProxyContextTest {

    private ProxyContext context;

    public ProxyContextTest() {
    }

    @Before
    public void setUp() {
        context = new ProxyContext();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testFindNoContext() {
        System.out.println("testFindNoContext");

        assertEqualsMyCustomFoo(context, null);
    }

    @Test
    public void testFindSingleContext() {
        System.out.println("testFindSingleContext");

        SimpleContext context1 = new SimpleContext();

        context.addContext(context1);
        MyCustomFoo foo = new MyCustomFoo();
        context1.add(foo);

        assertEqualsMyCustomFoo(context, foo);

        context1.remove(foo);

        assertEqualsMyCustomFoo(context, null);

        context1.add(foo);

        assertEqualsMyCustomFoo(context, foo);

        context.removeContext(context1);

        assertEqualsMyCustomFoo(context, null);
    }

    @Test
    public void testFindTwoContexts() {
        System.out.println("testFindTwoContexts");

        SimpleContext context1 = new SimpleContext();
        context.addContext(context1);

        SimpleContext context2 = new SimpleContext();
        context.addContext(context2);

        MyCustomFoo foo = new MyCustomFoo();
        context1.add(foo);

        assertEqualsMyCustomFoo(context, foo);

        context1.remove(foo);

        assertEqualsMyCustomFoo(context, null);

        context2.add(foo);

        assertEqualsMyCustomFoo(context, foo);

        context.removeContext(context1);

        assertEqualsMyCustomFoo(context, foo);

        context.removeContext(context2);

        assertEqualsMyCustomFoo(context, null);
    }

    @Test
    public void testFindAll() {
        System.out.println("findAll");

        SimpleContext context1 = new SimpleContext();
        context.addContext(context1);

        MyCustomFoo foo1 = new MyCustomFoo();
        context1.add(foo1);

        assertEqualsMyCustomFooList(context, Arrays.asList(foo1));

        SimpleContext context2 = new SimpleContext();
        context.addContext(context2);

        MyCustomFoo foo2 = new MyCustomFoo();
        context2.add(foo2);

        assertEqualsMyCustomFooList(context, Arrays.asList(foo1, foo2));


        context.removeContext(context1);

        assertEqualsMyCustomFooList(context, Arrays.asList(foo2));

        context.removeContext(context2);

        assertEqualsMyCustomFooList(context, Collections.<MyCustomFoo>emptyList());


    }

    @Test
    public void testSetContextsList() {
        System.out.println("setContextsList");

        SimpleContext context1 = new SimpleContext();
        MyCustomFoo foo1 = new MyCustomFoo();
        context1.add(foo1);

        TestContextListener fooContextListener = new TestContextListener();
        context.addContextListener(Foo.class, fooContextListener);

        assertFalse(fooContextListener.isContextChanged());

        context.setContexts(Arrays.asList(context1));

        assertTrue(fooContextListener.isContextChanged());

        fooContextListener.reset();

        assertFalse(fooContextListener.isContextChanged());

        context.setContexts(Arrays.asList(context1));

        assertFalse(fooContextListener.isContextChanged());

        fooContextListener.reset();

        assertFalse(fooContextListener.isContextChanged());

        context.setContexts(Collections.<Context>emptyList());

        assertTrue(fooContextListener.isContextChanged());

    }
    
     @Test
    public void testSetContextsVarArgs() {
        System.out.println("setContextsVarArgs");

        SimpleContext context1 = new SimpleContext();
        MyCustomFoo foo1 = new MyCustomFoo();
        context1.add(foo1);

        TestContextListener fooContextListener = new TestContextListener();
        context.addContextListener(Foo.class, fooContextListener);

        assertFalse(fooContextListener.isContextChanged());

        context.setContexts(context1);

        assertTrue(fooContextListener.isContextChanged());

        fooContextListener.reset();

        assertFalse(fooContextListener.isContextChanged());

        context.setContexts(context1);

        assertFalse(fooContextListener.isContextChanged());

        fooContextListener.reset();

        assertFalse(fooContextListener.isContextChanged());

        context.setContexts();

        assertTrue(fooContextListener.isContextChanged());

    }

    @Test
    public void testAddContextListener() {
        System.out.println("addContextListener");

        TestContextListener fooContextListener = new TestContextListener();
        context.addContextListener(Foo.class, fooContextListener);
        TestContextListener myCustomFooContextListener = new TestContextListener();
        TestContextListener abstractFooContextListener = new TestContextListener();
        TestContextListener objectContextListener = new TestContextListener();

        TestContextListener dateContextListener = new TestContextListener();

        SimpleContext context1 = new SimpleContext();
        context.addContext(context1);

        context.addContextListener(MyCustomFoo.class, myCustomFooContextListener);
        context.addContextListener(AbstractFoo.class, abstractFooContextListener);
        context.addContextListener(Object.class, objectContextListener);
        context.addContextListener(Date.class, dateContextListener);

        assertFalse(fooContextListener.isContextChanged());
        assertFalse(myCustomFooContextListener.isContextChanged());
        assertFalse(abstractFooContextListener.isContextChanged());
        assertFalse(objectContextListener.isContextChanged());

        assertFalse(dateContextListener.isContextChanged());

        Foo foo1 = new MyCustomFoo();
        context1.add(foo1);

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

        context.removeContext(context1);

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

        context.addContext(context1);

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

        context1.remove(foo1);

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

        SimpleContext context1 = new SimpleContext();
        context.addContext(context1);

        Foo foo1 = new MyCustomFoo();
        context1.add(foo1);

        assertFalse(fooContextListener.isContextChanged());
        assertFalse(myCustomFooContextListener.isContextChanged());
        assertFalse(abstractFooContextListener.isContextChanged());
        assertFalse(objectContextListener.isContextChanged());

        assertFalse(dateContextListener.isContextChanged());

        context.removeContext(context1);

        assertFalse(fooContextListener.isContextChanged());
        assertFalse(myCustomFooContextListener.isContextChanged());
        assertFalse(abstractFooContextListener.isContextChanged());
        assertFalse(objectContextListener.isContextChanged());

        assertFalse(dateContextListener.isContextChanged());

        context1.remove(foo1);

        assertFalse(fooContextListener.isContextChanged());
        assertFalse(myCustomFooContextListener.isContextChanged());
        assertFalse(abstractFooContextListener.isContextChanged());
        assertFalse(objectContextListener.isContextChanged());

        assertFalse(dateContextListener.isContextChanged());
    }
}
