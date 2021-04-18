package lk.sliit.code4.osgi.order;

import lk.sliit.code4.osgi.order.constant.Common;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class OrderServiceActivator implements BundleActivator {

    ServiceRegistration serviceRegistration;
    OrderServicePublish orderServicePublish;

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println(Common.ORDER_SERVICE_ACTIVATOR_STARTED);
        this.orderServicePublish = OrderServicePublishImpl.getInstance();
        this.orderServicePublish.setContext(context);
        this.serviceRegistration = context
                .registerService(OrderServicePublish.class.getName(), this.orderServicePublish, null);


    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println(Common.ORDER_SERVICE_ACTIVATOR_STOPPED);
        this.serviceRegistration.unregister();
    }
}
