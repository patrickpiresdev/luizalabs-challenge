package com.ptk.luizalabschallenge.model;

public class Product {
    private int id;
    private final String title;
    private final String description;

    public Product(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product clone() {
        return new Product(title, description);
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
