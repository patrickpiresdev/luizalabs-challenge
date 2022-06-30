package com.ptk.luizalabschallenge.model;

import java.util.UUID;

public class Product {
    private long id;
    private String title;
    private String description;

    public Product(String title, String description) {
        this.id = Long.parseLong(UUID.randomUUID().toString());
        this.title = title;
        this.description = description;
    }
}
