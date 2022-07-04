package com.ptk.luizalabschallenge.controller;

import com.ptk.luizalabschallenge.dao.WishlistDAO;
import com.ptk.luizalabschallenge.dto.ProductDto;
import com.ptk.luizalabschallenge.model.Client;
import com.ptk.luizalabschallenge.model.Product;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {
    private final String clientId = "62c0e833a9245e4e1386ed31";
    private final Client client;
    private final WishlistDAO wishlistDao;

    @Autowired
    public WishlistController(Client client, WishlistDAO wishlistDao) {
        this.client = client;
        this.wishlistDao = wishlistDao;
    }

    @GetMapping
    public List<String> all() {
        String matchAggregationStage = "{ $match: { client_id: ObjectId('" + clientId + "') } }";
        String lookupAggregationStage = "{ $lookup: { from: 'product', localField: 'product_id', foreignField: '_id', as: 'product' } }";
        List<Document> aggregationStages = Arrays.asList(
                Document.parse(matchAggregationStage),
                Document.parse(lookupAggregationStage));
        return wishlistDao.aggregate(aggregationStages).stream().map(Product::getTitle).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody String productId) {
        String wishlistItem = "{ client_id: ObjectId('" + clientId + "'), product_id: ObjectId('" + productId + "') }";
        wishlistDao.insert(Document.parse(wishlistItem));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> detail(@PathVariable int id) {
        Optional<Product> product = client.getFromWishlist(id);
        if (product.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new ProductDto(product.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable int id) {
        Optional<Product> product = client.removeFromWishlist(id);
        if (product.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().build();
    }
}
