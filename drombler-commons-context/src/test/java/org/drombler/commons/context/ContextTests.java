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

import java.util.Collections;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


/**
 *
 * @author puce
 */
public final class ContextTests {

    private ContextTests() {
    }

    public static void assertEqualsMyCustomFoo(Context context, MyCustomFoo myCustomFoo) {
        assertEquals(myCustomFoo, context.find(Foo.class));
        assertEquals(myCustomFoo, context.find(MyCustomFoo.class));
        assertEquals(myCustomFoo, context.find(AbstractFoo.class));
        assertEquals(myCustomFoo, context.find(Object.class));

        assertNull(context.find(Date.class));
    }

   public static void assertEqualsMyCustomFooList(Context context, List<MyCustomFoo> myCustomFooList) {
        assertEquals(myCustomFooList, context.findAll(Foo.class));
        assertEquals(myCustomFooList, context.findAll(MyCustomFoo.class));
        assertEquals(myCustomFooList, context.findAll(AbstractFoo.class));
        assertEquals(myCustomFooList, context.findAll(Object.class));

        assertEquals(Collections.emptyList(), context.findAll(Date.class));
    }
}
