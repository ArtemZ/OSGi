package ru.artemz.test.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Created with IntelliJ IDEA.
 * User: artemz
 * Date: 10/19/14
 * Time: 11:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class Activator implements BundleActivator {
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        System.out.println("Starting with bundle context: " + bundleContext.toString());
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("Stopping with bundle context: " + bundleContext.toString());
    }
}
