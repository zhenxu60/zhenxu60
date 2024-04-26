package com.techelevator.controller;

import com.techelevator.dao.CartItemDao;
import com.techelevator.dao.UserDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Cart;
import com.techelevator.model.CartItem;
import com.techelevator.model.User;
import com.techelevator.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/cart")
@PreAuthorize("isAuthenticated()")
@CrossOrigin
public class CartController {
    private CartService cartService;
    private UserDao userDao;
    private CartItemDao cartItemDao;


    public CartController(CartService cartService, UserDao userDao, CartItemDao cartItemDao) {
        this.cartService = cartService;
        this.userDao = userDao;
        this.cartItemDao = cartItemDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Cart getCart(Principal principal){

        return cartService.getUserCart(principal);
    }

    @RequestMapping(path = "/items", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Cart addItem(@Valid @RequestBody CartItem newItem, Principal principal){

        User user = userDao.getUserByUsername(principal.getName());
        newItem.setUserId(user.getId());

        return cartService.addItem(principal, newItem);

    }

    @RequestMapping(path = "/items/{itemId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int itemId){
        try{
            cartItemDao.deleteItemByCartItmId(itemId);

        } catch(DaoException e){

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
        }
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllItems() {
        cartItemDao.deleteAllItems();

    }

}
