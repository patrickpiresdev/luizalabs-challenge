package com.ptk.luizalabschallenge.controller;

import com.ptk.luizalabschallenge.dto.ProductDto;
import com.ptk.luizalabschallenge.dto.ProductForm;
import com.ptk.luizalabschallenge.model.Client;
import com.ptk.luizalabschallenge.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {
    private final Client client;

    @Autowired
    public WishlistController(Client client) {
        this.client = client;
    }

    @GetMapping
    public Set<String> all() {
        return client.wishlist().stream().map(Product::getTitle).collect(Collectors.toSet());
    }

    @PostMapping
    public ResponseEntity<ProductDto> add(@RequestBody @Valid ProductForm productForm, UriComponentsBuilder uriBuilder) {
        Product product = productForm.convert();
        int id = client.addToWishlist(product);
        URI uri = uriBuilder.path("/wishlist/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).body(new ProductDto(product));
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
