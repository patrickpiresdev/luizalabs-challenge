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
}
