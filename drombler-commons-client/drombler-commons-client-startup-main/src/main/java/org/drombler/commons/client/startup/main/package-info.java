/**
 * The Drombler Client Starter Framework.<br>
 * <br>
 * This framework allows to re-use start-up code such as:
 * <ul>
 * <li>distinction between user directory and installation directory</li>
 * <li>{@link org.drombler.commons.client.startup.main.cli simple command line arguments / switches support}</li>
 * <li>extendable {@link org.drombler.commons.client.startup.main.BootServiceStarter} framework</li>
 * <li>{@link org.drombler.commons.client.startup.main.ApplicationConfiguration#isSingleInstanceApplication "single instance"} support, which makes sure the application runs only once per user
 * directory and passes the command line arguments to the running single instance</li>
 * </ul>
 */
package org.drombler.commons.client.startup.main;
