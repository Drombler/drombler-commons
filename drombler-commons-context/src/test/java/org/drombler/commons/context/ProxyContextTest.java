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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author puce
 */
public class ProxyContextTest {

    private final ProxyContext context = new ProxyContext();

    @Test
    public void testFindNoContext() {
        System.out.println("testFindNoContext");

        assertEqualsMyCustomFoo(context, null);
    }

    @Test
    public void testFindSingleContext() {
        System.out.println("testFindSingleContext");

        SimpleContextContent content1 = new SimpleContextContent();
        SimpleContext context1 = new SimpleContext(content1);

        context.addContext(context1);
        MyCustomFoo foo = new MyCustomFoo();
        content1.add(foo);

        assertEqualsMyCustomFoo(context, foo);

        content1.remove(foo);

        assertEqualsMyCustomFoo(context, null);

        content1.add(foo);

        assertEqualsMyCustomFoo(context, foo);

        context.removeContext(context1);

        assertEqualsMyCustomFoo(context, null);
    }

    @Test
    public void testFindTwoContexts() {
        System.out.println("testFindTwoContexts");

        SimpleContextContent content1 = new SimpleContextContent();
        SimpleContext context1 = new SimpleContext(content1);
        context.addContext(context1);

        SimpleContextContent content2 = new SimpleContextContent();
        SimpleContext context2 = new SimpleContext(content2);
        context.addContext(context2);

        MyCustomFoo foo = new MyCustomFoo();
        content1.add(foo);

        assertEqualsMyCustomFoo(context, foo);

        content1.remove(foo);

        assertEqualsMyCustomFoo(context, null);

        content2.add(foo);

        assertEqualsMyCustomFoo(context, foo);

        context.removeContext(context1);

        assertEqualsMyCustomFoo(context, foo);

        context.removeContext(context2);

        assertEqualsMyCustomFoo(context, null);
    }

    @Test
    public void testFindAll() {
        System.out.println("findAll");

        SimpleContextContent content1 = new SimpleContextContent();
        SimpleContext context1 = new SimpleContext(content1);
        context.addContext(context1);

        MyCustomFoo foo1 = new MyCustomFoo();
        content1.add(foo1);

        assertEqualsMyCustomFooList(context, Arrays.asList(foo1));

        SimpleContextContent content2 = new SimpleContextContent();
        SimpleContext context2 = new SimpleContext(content2);
        context.addContext(context2);

        MyCustomFoo foo2 = new MyCustomFoo();
        content2.add(foo2);

        assertEqualsMyCustomFooList(context, Arrays.asList(foo1, foo2));

        context.removeContext(context1);

        assertEqualsMyCustomFooList(context, Arrays.asList(foo2));

        context.removeContext(context2);

        assertEqualsMyCustomFooList(context, Collections.<MyCustomFoo>emptyList());

    }

    @Test
    public void testSetContextsList() {
        System.out.println("setContextsList");

        SimpleContextContent content1 = new SimpleContextContent();
        SimpleContext context1 = new SimpleContext(content1);
        MyCustomFoo foo1 = new MyCustomFoo();
        content1.add(foo1);

        TestContextListener<Foo> fooContextListener = new TestContextListener<>();
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

        SimpleContextContent content1 = new SimpleContextContent();
        SimpleContext context1 = new SimpleContext(content1);
        MyCustomFoo foo1 = new MyCustomFoo();
        content1.add(foo1);

        TestContextListener<Foo> fooContextListener = new TestContextListener<>();
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

        TestContextListener<Foo> fooContextListener = new TestContextListener<>();
        context.addContextListener(Foo.class, fooContextListener);
        TestContextListener<MyCustomFoo> myCustomFooContextListener = new TestContextListener<>();
        TestContextListener<AbstractFoo> abstractFooContextListener = new TestContextListener<>();
        TestContextListener<Object> objectContextListener = new TestContextListener<>();

        TestContextListener<Date> dateContextListener = new TestContextListener<>();

        SimpleContextContent content1 = new SimpleContextContent();
        SimpleContext context1 = new SimpleContext(content1);
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
        content1.add(foo1);

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

        content1.remove(foo1);

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

        SimpleContextContent content1 = new SimpleContextContent();
        SimpleContext context1 = new SimpleContext(content1);
        context.addContext(context1);

        Foo foo1 = new MyCustomFoo();
        content1.add(foo1);

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

        content1.remove(foo1);

        assertFalse(fooContextListener.isContextChanged());
        assertFalse(myCustomFooContextListener.isContextChanged());
        assertFalse(abstractFooContextListener.isContextChanged());
        assertFalse(objectContextListener.isContextChanged());

        assertFalse(dateContextListener.isContextChanged());
    }
}
