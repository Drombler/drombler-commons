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
module org.drombler.commons.fx.base {
    exports org.drombler.commons.fx.beans.binding;
    exports org.drombler.commons.fx.beans.property;

    requires transitive javafx.base;
    requires org.softsmithy.lib.core;

}
