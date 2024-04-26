package com.techelevator.controller;

import com.techelevator.dao.JdbcProductDao;
import com.techelevator.dao.ProductDao;
import com.techelevator.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
@PreAuthorize("permitAll")
@CrossOrigin
public class ProductController {
    private ProductDao productDao;

    public ProductController(ProductDao productDao){

        this.productDao = productDao;

    }


    @RequestMapping(method = RequestMethod.GET)
    public List<Product> list(@RequestParam(name = "sku", required = false) String searchSku,
                                                @RequestParam(name = "name", required = false) String searchName){

        if(searchSku == null && searchName == null) {
            return productDao.getProducts();

        } else if(searchSku == null){
            return productDao.getProductsBySkuOrName("", searchName);
        } else if(searchName == null){
            return productDao.getProductsBySkuOrName(searchSku, "");
        } else {
            return productDao.getProductsBySkuOrName(searchSku, searchName);
        }

    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Product listProductById(@PathVariable int id){

        Product product = productDao.getProductsById(id);

        if(product == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found", null);
        } else {
            return product;

        }
    }

}
