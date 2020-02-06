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
// TODO: split up module
module org.drombler.commons.fx.graphics {
    exports org.drombler.commons.fx.application;
    exports org.drombler.commons.fx.concurrent;
    exports org.drombler.commons.fx.event;
    exports org.drombler.commons.fx.geometry;
    exports org.drombler.commons.fx.scene;
    exports org.drombler.commons.fx.scene.image;
    exports org.drombler.commons.fx.scene.layout;
    exports org.drombler.commons.fx.stage;

    requires transitive javafx.graphics;
    requires transitive org.softsmithy.lib.core;
    requires transitive org.drombler.commons.client.core;
    requires org.slf4j;
}
