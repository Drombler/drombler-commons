/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is provided by Drombler GmbH. The Initial Developer of the
 * Original Code is Florian Brunner (GitHub user: puce77).
 * Copyright 2020 Drombler GmbH. All Rights Reserved.
 *
 * Contributor(s): .
 */

module org.drombler.commons.fx.samples.controls {
    exports org.drombler.commons.fx.samples.scene.control.treeview.facility to javafx.graphics;
    opens org.drombler.commons.fx.samples.scene.control.treeview.facility.editor to org.drombler.commons.fx.fxml, javafx.fxml;

    requires org.drombler.commons.fx.controls;
    requires org.drombler.commons.fx.fxml;
    requires org.drombler.commons.action.fx;

}
