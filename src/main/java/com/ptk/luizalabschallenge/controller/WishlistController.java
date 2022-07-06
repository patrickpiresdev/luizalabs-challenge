package com.ptk.luizalabschallenge.controller;

import com.ptk.luizalabschallenge.dao.WishlistDAO;
import com.ptk.luizalabschallenge.model.Product;
import com.ptk.luizalabschallenge.model.WishlistItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {
    private final String clientId = "62c0e833a9245e4e1386ed31";
    private final WishlistDAO wishlistDao;
    private final int WISHLIST_MAX = 20;

    @Autowired
    public WishlistController(WishlistDAO wishlistDao) {
        this.wishlistDao = wishlistDao;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Object> add(@PathVariable String productId) {
        List<Product> wishlist = wishlistDao.all(clientId);
        // TODO: return response informing that the product was not added
        if (checkIfCanNotAddToWishlist(productId, wishlist))
            return ResponseEntity.ok().build();
        wishlistDao.insert(new WishlistItem(clientId, productId));
        return ResponseEntity.ok().build();
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

    @DeleteMapping("/{productId}")
    public ResponseEntity<Object> remove(@PathVariable String productId) {
        WishlistItem wishlistItem = new WishlistItem(clientId, productId);
        if (wishlistDao.find(wishlistItem).isEmpty())
            return ResponseEntity.notFound().build();
        wishlistDao.remove(wishlistItem);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<Product> all() {
        return wishlistDao.all(clientId);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Object> present(@PathVariable String productId) {
        WishlistItem wishlistItem = new WishlistItem(clientId, productId);
        if (wishlistDao.find(wishlistItem).isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().build();
    }
}
