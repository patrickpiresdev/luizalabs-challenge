package com.ptk.luizalabschallenge.controller;

import com.ptk.luizalabschallenge.model.Client;
import com.ptk.luizalabschallenge.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {
    @Autowired
    private Client client;

    @GetMapping
    public Set<String> all() {
        return client.wishlist().stream().map(Product::getTitle).collect(Collectors.toSet());
    }
}
