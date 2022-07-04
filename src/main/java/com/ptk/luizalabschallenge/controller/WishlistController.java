package com.ptk.luizalabschallenge.controller;

import com.ptk.luizalabschallenge.dao.WishlistDAO;
import com.ptk.luizalabschallenge.model.Product;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {
    private final String clientId = "62c0e833a9245e4e1386ed31";
    private final WishlistDAO wishlistDao;

    @Autowired
    public WishlistController(WishlistDAO wishlistDao) {
        this.wishlistDao = wishlistDao;
    }

    @GetMapping
    public List<Product> all() {
        String matchAggregationStage = "{ $match: { client_id: ObjectId('" + clientId + "') } }";
        String lookupAggregationStage = "{ $lookup: { from: 'product', localField: 'product_id', foreignField: '_id', as: 'product' } }";
        List<Document> aggregationStages = Arrays.asList(
                Document.parse(matchAggregationStage),
                Document.parse(lookupAggregationStage));
        return wishlistDao.aggregate(aggregationStages);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody String productId) {
        String wishlistItem = "{ client_id: ObjectId('" + clientId + "'), product_id: ObjectId('" + productId + "') }";
        Document document = Document.parse(wishlistItem);
        Optional<Document> wishlistItemDoc = wishlistDao.find(document);
        if (wishlistItemDoc.isEmpty())
            wishlistDao.insert(document);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> remove(@PathVariable String productId) {
        String wishlistItem = "{ client_id: ObjectId('" + clientId + "'), product_id: ObjectId('" + productId + "') }";
        Document document = Document.parse(wishlistItem);
        Optional<Document> wishlistItemDoc = wishlistDao.find(document);
        if (wishlistItemDoc.isEmpty()) return ResponseEntity.notFound().build();
        wishlistDao.remove(document);
        return ResponseEntity.ok().build();
    }
}
