package com.ptk.luizalabschallenge.service;

import com.ptk.luizalabschallenge.dao.WishlistDAO;
import com.ptk.luizalabschallenge.model.Product;
import com.ptk.luizalabschallenge.model.Wishlist;
import com.ptk.luizalabschallenge.model.WishlistItem;

import java.util.List;

public class WishlistService {
    private final WishlistDAO wishlistDao;
    private final String defaultClientId;

    public WishlistService(WishlistDAO wishlistDAO, String defaultClientId) {
        this.wishlistDao = wishlistDAO;
        this.defaultClientId = defaultClientId;
    }

    public void add(String productId) {
        Wishlist wishlist = wishlistDao.all(defaultClientId);
        if (!canAddToWishlist(productId, wishlist))
            throw new IllegalStateException("Wishlist already has the product or is full!");
        wishlistDao.insert(new WishlistItem(defaultClientId, productId));
    }

    private boolean canAddToWishlist(String productId, Wishlist wishlist) {
        return !wishlist.isFull() && !wishlist.contains(productId);
    }

    public void remove(String productId) {
        WishlistItem wishlistItem = new WishlistItem(defaultClientId, productId);
        if (!isPresent(wishlistItem))
            throw new IllegalStateException("The product is not on wishlist to be removed!");
        wishlistDao.remove(wishlistItem);
    }

    public List<Product> all(String defaultClientId) {
        return wishlistDao.all(defaultClientId).toProductsList();
    }

    public boolean isPresent(WishlistItem wishlistItem) {
        return wishlistDao.find(wishlistItem).isPresent();
    }
}
