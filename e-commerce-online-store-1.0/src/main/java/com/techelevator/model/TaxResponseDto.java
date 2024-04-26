package com.techelevator.model;

import java.math.BigDecimal;

public class TaxResponseDto {
    private BigDecimal salesTax;
    private String lastUpdated;

    public TaxResponseDto() {

    }
    public TaxResponseDto(BigDecimal saleTax, String lastUpdated) {
        this.salesTax = saleTax;
        this.lastUpdated = lastUpdated;
    }

    public BigDecimal getSaleTax() {
        return salesTax;
    }

    public void setSalesTax(BigDecimal salesTax) {
        this.salesTax = salesTax;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
