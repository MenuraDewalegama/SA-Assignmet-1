package lk.sliit.code4.osgi.item;

import lk.sliit.code4.osgi.item.constant.Common;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class ItemServiceActivator implements BundleActivator {

    ServiceRegistration itemServiceRegistration;

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println(Common.ITEM_SERVICE_ACTIVATOR_STARTED);
        ItemServicePublish itemServicePublish = new ItemServicePublishImpl();
        itemServiceRegistration = context.registerService(ItemServicePublish.class.getName(), itemServicePublish, null);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        itemServiceRegistration.unregister();
        System.out.println(Common.ITEM_SERVICE_ACTIVATOR_STOPPED);
    }
}
