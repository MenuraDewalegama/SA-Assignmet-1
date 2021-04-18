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
