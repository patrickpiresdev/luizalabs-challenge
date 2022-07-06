package com.ptk.luizalabschallenge.controller;

import com.ptk.luizalabschallenge.dao.WishlistDAO;
import com.ptk.luizalabschallenge.model.Product;
import com.ptk.luizalabschallenge.model.WishlistItem;
import com.ptk.luizalabschallenge.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {
    private final String defaultClientId;
    private final WishlistDAO wishlistDao;
    private final WishlistService wishlistService;

    @Autowired
    public WishlistController(WishlistDAO wishlistDao, String defaultClientId, WishlistService wishlistService) {
        this.wishlistDao = wishlistDao;
        this.defaultClientId = defaultClientId;
        this.wishlistService = wishlistService;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Object> add(@PathVariable String productId) {
        try {
            wishlistService.add(productId);
        } catch (IllegalStateException ex) {
            // TODO: return response informing that the product was not added
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
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
