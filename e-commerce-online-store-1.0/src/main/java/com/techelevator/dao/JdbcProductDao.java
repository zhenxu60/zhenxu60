package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Product;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcProductDao implements ProductDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcProductDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> getProducts() {

        List<Product> products = new ArrayList<>();
        String sql = "SELECT product_id, product_sku, name, description, price, image_name\n" +
                "\tFROM public.product;";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

            while(results.next()){
                Product product = mapRowToProduct(results);
                products.add(product);

            }

        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        }

        return products;
    }

    @Override
    public List<Product> getProductsBySkuOrName(String sku, String name) {

        List<Product> products = new ArrayList<>();
        String sql = "SELECT product_id, product_sku, name, description, price, image_name\n" +
                "FROM public.product\n" +
                "WHERE product_sku = ? OR name = ?;";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, sku, name);

            while(results.next()){
                Product product = mapRowToProduct(results);
                products.add(product);

            }

        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        }

        return products;
    }

    @Override
    public Product getProductsById(int id) {

        Product product = null;
        String sql = "SELECT product_id, product_sku, name, description, price, image_name\n" +
                "FROM public.product\n" +
                "WHERE product_id = ?;";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);

            if(results.next()){
                product = mapRowToProduct(results);
            }

        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        }

        return product;
    }

    @Override
    public List<Product> getProductsByUserId(int userId) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT cart_item_id, user_id, product.product_id, quantity, product_sku, name, description, price, image_name\n" +
                "FROM public.cart_item\n" +
                "JOIN product\n" +
                "ON product.product_id = cart_item.product_id;";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

            while(results.next()){
                Product product = mapRowToProduct(results);
                products.add(product);

            }

        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        }

        return products;
    }

    private Product mapRowToProduct(SqlRowSet result){

        Product product = new Product();

        product.setId(result.getInt("product_id"));
        product.setSku(result.getString("product_sku"));
        product.setName(result.getString("name"));
        product.setDescription((result.getString("description")));
        product.setPrice(result.getBigDecimal("price"));
        product.setImageName(result.getString("image_name"));

        return product;

    }

}
