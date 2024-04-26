package com.techelevator.service;

import com.techelevator.dao.CartItemDao;
import com.techelevator.dao.ProductDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Cart;
import com.techelevator.model.CartItem;
import com.techelevator.model.Product;
import com.techelevator.model.User;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
@Component
public class CartService {
private CartItemDao cartItemDao;
private ProductDao productDao;
private UserDao userDao;

private TaxService taxService;

    public CartService(CartItemDao cartItemDao, ProductDao productDao, UserDao userDao, TaxService taxService) {
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
        this.userDao = userDao;
        this.taxService = taxService;
    }

    public Cart getUserCart(Principal principal){

        BigDecimal itemSubtotal = new BigDecimal("0.0");

        // get userId
        String userName = principal.getName();
        User user = userDao.getUserByUsername(userName);
        int userId = user.getId();

        List<CartItem> items = cartItemDao.getCartItemByUserId(userId);

        // get all products of the user
        List<Product>  productsOfUser = productDao.getProductsByUserId(userId);

        for(CartItem item : items){
            int productId = item.getProductId();
            Product product = findProductInCart(productId, productsOfUser); // get user's product in cart
            item.setProduct(product);
            itemSubtotal = itemSubtotal.add(product.getPrice());
        }

        Cart cart = new Cart(items);
        BigDecimal taxAmount = taxService.getTaxRate(user.getStateCode());
        cart.setTax(taxAmount);

        cart.setItemSubtotal(itemSubtotal);

        BigDecimal total = itemSubtotal.multiply(taxAmount.add(new BigDecimal("1")));
        cart.setTotal(total);

        return cart;

    }

    public Cart addItem(Principal principal, CartItem newItem){

        Cart cart = getUserCart(principal);
        List<CartItem> cartItems = cart.getItems();

        for(CartItem item : cartItems){

            if(newItem.getProductId() == item.getProductId()){

                item.setQuantity(item.getQuantity() + newItem.getQuantity());
                cart.setItems(cartItems);
                return cart;
            }

        }

        //newItem.setProduct(productDao.getProductsById(newItem.getProductId()));
        cartItems.add(cartItemDao.addItem(newItem));
        cart.setItems(cartItems);

        return cart;

    }



    private Product findProductInCart(int productId, List<Product> productsOfUser) {

        for(Product product : productsOfUser){
            if(product.getId() == productId){
                return product;
            }
        }

        return null;
    }

}
