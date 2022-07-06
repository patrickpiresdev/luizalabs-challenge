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
    private final String defaultClientId;
    private final WishlistDAO wishlistDao;
    private final int WISHLIST_MAX = 20;

    @Autowired
    public WishlistController(WishlistDAO wishlistDao, String defaultClientId) {
        this.wishlistDao = wishlistDao;
        this.defaultClientId = defaultClientId;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Object> add(@PathVariable String productId) {
        List<Product> wishlist = wishlistDao.all(defaultClientId);
        // TODO: return response informing that the product was not added
        if (checkIfCanNotAddToWishlist(productId, wishlist))
            return ResponseEntity.ok().build();
        wishlistDao.insert(new WishlistItem(defaultClientId, productId));
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
        WishlistItem wishlistItem = new WishlistItem(defaultClientId, productId);
        if (wishlistDao.find(wishlistItem).isEmpty())
            return ResponseEntity.notFound().build();
        wishlistDao.remove(wishlistItem);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<Product> all() {
        return wishlistDao.all(defaultClientId);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Object> present(@PathVariable String productId) {
        WishlistItem wishlistItem = new WishlistItem(defaultClientId, productId);
        if (wishlistDao.find(wishlistItem).isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().build();
    }
}
