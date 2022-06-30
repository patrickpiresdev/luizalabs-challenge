package com.ptk.luizalabschallenge.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Client {
    private long id;
    private String name;
    private Set<Product> wishlist;

    public Client(String name) {
        this.id = Long.parseLong(UUID.randomUUID().toString());
        this.name = name;
        this.wishlist = new HashSet<>(20);
    }
}
