package com.techelevator.model;

import java.math.BigDecimal;
import java.util.List;

public class Cart {
    private List<CartItem> items;
    private BigDecimal tax;
    private BigDecimal itemSubtotal;
    private BigDecimal total;

    public Cart(List<CartItem> items) {
        this.items = items;
        this.tax = new BigDecimal("0.0");
        this.itemSubtotal = new BigDecimal("0.0");
        this.total = new BigDecimal("0.0");
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getItemSubtotal() {
        return itemSubtotal;
    }

    public void setItemSubtotal(BigDecimal itemSubtotal) {
        this.itemSubtotal = itemSubtotal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
