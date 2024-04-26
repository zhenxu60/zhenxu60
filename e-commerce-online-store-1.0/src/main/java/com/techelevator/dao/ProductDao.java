package com.techelevator.dao;

import com.techelevator.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProducts();
    List<Product> getProductsBySkuOrName(String sku, String name);
    Product getProductsById(int id);
    List<Product> getProductsByUserId(int userId);

}
