package com.ptk.luizalabschallenge.model;

import org.bson.Document;
import org.bson.types.ObjectId;

public class Product {
    private String id;
    private final String title;
    private final String description;

    public Product(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public static Product from(Document document) {
        return new Product(
                ((ObjectId) document.get("_id")).toString(),
                (String) document.get("title"),
                (String) document.get("description"));
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Product)) return false;
        Product other = (Product) obj;
        if (this == other) return true;
        return id.equals(other.id) &&
                title.equals(other.title) &&
                description.equals(other.description);
    }
}
