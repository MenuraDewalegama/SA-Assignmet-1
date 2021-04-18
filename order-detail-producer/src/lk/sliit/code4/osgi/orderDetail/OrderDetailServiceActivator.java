package lk.sliit.code4.osgi.orderDetail;

import lk.sliit.code4.osgi.orderDetail.constant.Common;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class OrderDetailServiceActivator implements BundleActivator {

    ServiceRegistration serviceRegistration;
    OrderDetailServicePublish orderDetailServicePublish;

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println(Common.ORDER_DETAIL_SERVICE_ACTIVATOR_STARTED);
        this.orderDetailServicePublish = OrderDetailServicePublishImpl.getInstance();
        this.serviceRegistration = context
                .registerService(OrderDetailServicePublish.class.getName(),
                        this.orderDetailServicePublish,
                        null);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        this.serviceRegistration.unregister();
        System.out.println(Common.ORDER_DETAIL_SERVICE_ACTIVATOR_STOPPED);
    }
}
