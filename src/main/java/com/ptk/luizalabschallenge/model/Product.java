package com.ptk.luizalabschallenge.model;

public class Product {
    private final String title;
    private final String description;

    public Product(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public Product clone() {
        return new Product(title, description);
    }
}
