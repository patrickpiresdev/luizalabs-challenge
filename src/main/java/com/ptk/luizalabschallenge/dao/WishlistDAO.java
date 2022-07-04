package com.ptk.luizalabschallenge.dao;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.ptk.luizalabschallenge.model.Product;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WishlistDAO {
    private final MongoDatabase database;

    public WishlistDAO(MongoDatabase database) {
        this.database = database;
    }

    public List<Product> aggregate(List<Document> stages) {
        AggregateIterable<Document> wishlistIterable = database.getCollection("wishlist_item")
                .aggregate(stages);
        List<Product> wishlist = new ArrayList<>();
        for (Document wishlistItem : wishlistIterable)
            wishlist.add(productFrom(wishlistItem));
        return wishlist;
    }

    private Product productFrom(Document wishlistItem) {
        Document product = ((List<Document>) wishlistItem.get("product")).get(0);
        return Product.from(product);
    }

    public void insert(Document wishlistItem) {
        database.getCollection("wishlist_item").insertOne(wishlistItem);
    }

    public void remove(Document wishlistItem) {
        database.getCollection("wishlist_item").deleteMany(wishlistItem);
    }

    public Optional<Document> find(Document filter) {
        FindIterable<Document> wishlistItemIterable = database.getCollection("wishlist_item").find(filter);
        List<Document> wishlistItems = new ArrayList<>();
        for (Document wishlistItem : wishlistItemIterable)
            wishlistItems.add(wishlistItem);
        if (wishlistItems.size() == 0) return Optional.empty();
        return Optional.of(wishlistItems.get(0));
    }
}
