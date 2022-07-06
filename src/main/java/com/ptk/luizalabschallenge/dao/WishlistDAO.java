package com.ptk.luizalabschallenge.dao;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoDatabase;
import com.ptk.luizalabschallenge.model.Wishlist;
import com.ptk.luizalabschallenge.model.WishlistItem;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Aggregates.lookup;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Filters.eq;

public class WishlistDAO {
    private final MongoDatabase database;

    public WishlistDAO(MongoDatabase database) {
        this.database = database;
    }

    public void insert(WishlistItem wishlistItem) {
        database.getCollection("wishlist_item").insertOne(wishlistItem.toDocument());
    }

    public void remove(WishlistItem wishlistItem) {
        database.getCollection("wishlist_item").deleteOne(wishlistItem.toDocument());
    }

    public Wishlist all(String clientId) {
        List<Bson> stages = Arrays.asList(
                match(eq("client_id", new ObjectId(clientId))),
                lookup("product", "product_id","_id", "product"));
        AggregateIterable<Document> wishlistIterable = database.getCollection("wishlist_item")
                .aggregate(stages);
        return Wishlist.from(wishlistIterable);
    }

    public Optional<WishlistItem> find(WishlistItem wishlistItem) {
        Document document = database.getCollection("wishlist_item")
                .find(wishlistItem.toDocument()).first();
        if (document == null) return Optional.empty();
        return Optional.of(WishlistItem.from(document));
    }
}
