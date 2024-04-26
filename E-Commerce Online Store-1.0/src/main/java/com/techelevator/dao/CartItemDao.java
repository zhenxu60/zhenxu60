package com.techelevator.dao;

import com.techelevator.model.CartItem;

import java.util.List;

public interface CartItemDao {

    List<CartItem> getCartItemByUserId(int userId);

    CartItem getCartItemByCartItmId(int cartItemId);

    CartItem addItem(CartItem newItem);

    int deleteItemByCartItmId(int cartItemId);

    int deleteAllItems();



}
