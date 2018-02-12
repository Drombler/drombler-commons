/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (GitHub user: puce77).
 * Copyright 2017 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.fx.samples.action;

import org.drombler.commons.action.fx.context.AbstractActiveContextSensitiveFXToggleAction;
import org.drombler.commons.context.ContextEvent;

/**
 *
 * @author puce
 */
public class TestActiveContextSensitiveToggleAction extends AbstractActiveContextSensitiveFXToggleAction<MyConfiguration> {

    private MyConfiguration myConfiguration;

    public TestActiveContextSensitiveToggleAction() {
        super(MyConfiguration.class);
        setEnabled(false);
    }

    @Override
    protected void contextChanged(ContextEvent<MyConfiguration> event) {
        MyConfiguration oldMyConfiguration = myConfiguration;
        myConfiguration = getActiveContext().find(event.getType());
        onMyConfigurationChanged(oldMyConfiguration, myConfiguration);

        setEnabled(myConfiguration != null);
    }

    private void onMyConfigurationChanged(MyConfiguration oldMyConfiguration, MyConfiguration newMyConfiguration) {
        if (oldMyConfiguration != null) {
            selectedProperty().unbindBidirectional(oldMyConfiguration.testProperty());
        }
        if (newMyConfiguration != null) {
            selectedProperty().bindBidirectional(newMyConfiguration.testProperty());
        } else {
            setSelected(false);
        }

    }

}
