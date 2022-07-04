package com.ptk.luizalabschallenge.dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.ptk.luizalabschallenge.model.Product;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDAO {
    private final MongoDatabase database;

    public ProductDAO(MongoDatabase database) {
        this.database = database;
    }

    public Optional<Product> find(Document filter) {
        FindIterable<Document> productIterable = database.getCollection("product").find(filter);
        List<Product> products = new ArrayList<>();

        for (Document product : productIterable)
            products.add(Product.from(product));

        if (products.size() == 0) return Optional.empty();
        return Optional.of(products.get(0));
    }
}
