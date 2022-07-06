package com.ptk.luizalabschallenge.service;

import com.ptk.luizalabschallenge.dao.WishlistDAO;
import com.ptk.luizalabschallenge.model.Product;
import com.ptk.luizalabschallenge.model.WishlistItem;

import java.util.List;

public class WishlistService {
    private final int WISHLIST_MAX = 20;
    private final WishlistDAO wishlistDao;
    private final String defaultClientId;

    public WishlistService(WishlistDAO wishlistDAO, String defaultClientId) {
        this.wishlistDao = wishlistDAO;
        this.defaultClientId = defaultClientId;
    }

    public void add(String productId) {
        List<Product> wishlist = wishlistDao.all(defaultClientId);
        if (checkIfCanNotAddToWishlist(productId, wishlist))
            throw new IllegalStateException("Wishlist is already full!");
        wishlistDao.insert(new WishlistItem(defaultClientId, productId));
    }

    private boolean checkIfCanNotAddToWishlist(String productId, List<Product> wishlist) {
        return wishlistIsFull(wishlist) || productAlreadyInWishlist(productId, wishlist);
    }

    private boolean wishlistIsFull(List<Product> wishlist) {
        return wishlist.size() == WISHLIST_MAX;
    }

    private boolean productAlreadyInWishlist(String productId, List<Product> products) {
        return products.stream().map(Product::getId).anyMatch(id -> id.equals(productId));
    }

    public void remove(String productId) {
        WishlistItem wishlistItem = new WishlistItem(defaultClientId, productId);
        if (wishlistDao.find(wishlistItem).isEmpty())
            throw new IllegalStateException("The product is not on wishlist to be removed!");
        wishlistDao.remove(wishlistItem);
    }
}
