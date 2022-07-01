package com.ptk.luizalabschallenge.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Client {
    private final String name;
    private final List<Product> wishlist;
    private final int WISHLIST_MAX = 20;

    public Client(String name) {
        this.name = name;
        this.wishlist = new ArrayList<>(WISHLIST_MAX);
    }

    public int addToWishlist(Product product) {
        Product clone = product.clone();
        clone.setId(wishlist.size()+1);
        wishlist.add(clone);
        return clone.getId();
    }

    public List<Product> wishlist() {
        return wishlist.stream().map(Product::clone).collect(Collectors.toList());
    }

    public Optional<Product> getProductFromWishList(int id) {
        if (id >= wishlist.size()) return Optional.empty();
        return Optional.of(wishlist.get(id));
    }
}
