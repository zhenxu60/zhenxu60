package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class Product {

    @JsonProperty("product_id")
    private int id;
    @JsonProperty("product_sku")
    @NotBlank(message = "The product sku field must not be blank.")
    private String sku;
    @NotBlank(message = "The name field must not be blank.")
    private String name;
    private String description;
    @NotNull(message = "The price field must not be blank.")
    private BigDecimal price;
    @JsonProperty("image_name")
    private String imageName;

    public Product(){

    }
    public Product(int id, String sku, String name, String description, BigDecimal price, String imageName) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageName = imageName;
    }

    public Product(String sku, String name, String description, BigDecimal price, String imageName) {
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageName = imageName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public String toString() {
        return "Product{" +
                "product_id=" + id +
                ", product_sku='" + sku + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", image_Name='" + imageName + '\'' +
                '}';
    }

}
