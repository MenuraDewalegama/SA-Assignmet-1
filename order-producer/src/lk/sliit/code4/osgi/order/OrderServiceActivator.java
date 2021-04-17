/**
 * MIT License
 * <p>
 * Copyright (c) 2021 Dhanusha Perera
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * @author : Dhanusha Perera
 * @author : Dhanusha Perera
 * @since : 16/04/2021
 * @since : 16/04/2021
 */
/**
 * @author : Dhanusha Perera
 * @since : 16/04/2021
 */
package lk.sliit.code4.osgi.order;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class OrderServiceActivator implements BundleActivator {

    //    ServiceReference orderDetailServiceReference;
    //    OrderDetailPublish orderDetailPublish;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        System.out.println("OrderServiceActivator is started...!");
//        ServiceReference serviceReferenceForOrderDetail = context.getServiceReference(OrderDetailPublish.class.getName());
//        orderDetailPublish = (OrderDetailPublish) context.getService(serviceReferenceForOrderDetail);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("OrderServiceActivator is stopped...!");
    }
}
