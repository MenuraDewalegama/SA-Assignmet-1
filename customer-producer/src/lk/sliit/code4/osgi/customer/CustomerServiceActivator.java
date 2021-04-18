package lk.sliit.code4.osgi.customer;

import lk.sliit.code4.osgi.customer.constant.Common;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class CustomerServiceActivator implements BundleActivator {

    ServiceRegistration serviceRegistration;

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println(Common.CUSTOMER_SERVICE_ACTIVATOR_STARTED);
        CustomerServicePublish customerServicePublish = new CustomerServicePublishImpl();
        serviceRegistration = context.registerService(CustomerServicePublish.class.getName(), customerServicePublish, null);

    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println(Common.CUSTOMER_SERVICE_ACTIVATOR_STOPPED);
        serviceRegistration.unregister();
    }
}
