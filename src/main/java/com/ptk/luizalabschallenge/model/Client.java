package com.ptk.luizalabschallenge.model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Client {
    private final String name;
    private final Set<Product> wishlist;
    private final int WISHLIST_MAX = 20;

    public Client(String name) {
        this.name = name;
        this.wishlist = new HashSet<>(WISHLIST_MAX);
    }

    public void addToWishlist(Product product) {
        wishlist.add(product.clone());
    }

    public Set<Product> wishlist() {
        return wishlist.stream().map(Product::clone).collect(Collectors.toSet());
    }
}
