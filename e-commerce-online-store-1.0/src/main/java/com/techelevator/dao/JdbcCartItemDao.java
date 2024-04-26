package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.CartItem;
import com.techelevator.model.Product;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcCartItemDao implements CartItemDao{
    private JdbcTemplate jdbcTemplate;

    public JdbcCartItemDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<CartItem> getCartItemByUserId(int userId) {

        List<CartItem> cartItems = new ArrayList<>();
        String sql = "SELECT cart_item_id, user_id, product_id, quantity\n" +
                "FROM public.cart_item\n" +
                "WHERE user_id = ?;";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);

            while(results.next()){
                CartItem cartItem = mapRowToCartItem(results);
                cartItems.add(cartItem);

            }

        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        }

        return cartItems;
    }

    @Override
    public CartItem getCartItemByCartItmId(int cartItemId) {

        CartItem cartItem = null;
        String sql = "SELECT cart_item_id, user_id, product_id, quantity\n" +
                "FROM public.cart_item\n" +
                "WHERE cart_item_id = ?;";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, cartItemId);

            if(results.next()){
               cartItem = mapRowToCartItem(results);
            }

        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        }

        return cartItem;

    }


    @Override
    public CartItem addItem(CartItem newItem) {

        CartItem item = null;
        String sql = "INSERT INTO public.cart_item(\n" +
                "\tuser_id, product_id, quantity)\n" +
                "\tVALUES (?, ?, ?) RETURNING cart_item_id;";

        try {
            int itemId = jdbcTemplate.queryForObject(sql, int.class, newItem.getUserId(), newItem.getProductId(), newItem.getQuantity());

            item = getCartItemByCartItmId(itemId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return item;

    }

    @Override
    public int deleteItemByCartItmId(int CartItemId) {

        int numberOfRows = 0;

        String sql = "DELETE FROM public.cart_item\n" +
                "WHERE cart_item_id = ?;";

        try{

            numberOfRows = jdbcTemplate.update(sql, CartItemId);

        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }

    @Override
    public int deleteAllItems() {
        int numberOfRows = 0;

        String sql = "DELETE FROM public.cart_item;";

        try{

            numberOfRows = jdbcTemplate.update(sql);

        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }



    private CartItem mapRowToCartItem(SqlRowSet result) {

        CartItem cartItem = new CartItem();

        cartItem.setCartItemId(result.getInt("cart_item_id"));
        cartItem.setUserId(result.getInt("user_id"));
        cartItem.setProductId(result.getInt("product_id"));
        cartItem.setQuantity(result.getInt("quantity"));

        return cartItem;
    }


}
