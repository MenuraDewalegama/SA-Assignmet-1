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
 * @author : Dhanusha Perera
 * @since : 15/04/2021
 * @since : 15/04/2021
 * @since : 15/04/2021
 */
/**
 * @author : Dhanusha Perera
 * @since : 15/04/2021
 */
package lk.sliit.code4.osgi.item.entity;

import java.math.BigDecimal;

public class Item implements SuperEntity {
    private int code;
    private String name;
    private BigDecimal unitPrice;
    private int handOnQuantity;

    public Item() {
    }

    public Item(int code, String name, BigDecimal unitPrice, int handOnQuantity) {
        this.code = code;
        this.name = name;
        this.unitPrice = unitPrice;
        this.handOnQuantity = handOnQuantity;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getHandOnQuantity() {
        return handOnQuantity;
    }

    public void setHandOnQuantity(int handOnQuantity) {
        this.handOnQuantity = handOnQuantity;
    }

    @Override
    public String toString() {
        return "Item{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", unitPrice=" + unitPrice +
                ", handOnQuantity=" + handOnQuantity +
                '}';
    }
}
