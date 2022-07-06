package com.ptk.luizalabschallenge.dao;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
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
    private final MongoCollection<Document> wishlistItemCollection;

    public WishlistDAO(MongoDatabase database) {
        wishlistItemCollection = database.getCollection("wishlist_item");
    }

    public void insert(WishlistItem wishlistItem) {
        wishlistItemCollection.insertOne(wishlistItem.toDocument());
    }

    public void remove(WishlistItem wishlistItem) {
        wishlistItemCollection.deleteOne(wishlistItem.toDocument());
    }

    public Wishlist all(String clientId) {
        List<Bson> stages = Arrays.asList(
                match(eq("client_id", new ObjectId(clientId))),
                lookup("product", "product_id","_id", "product"));
        return Wishlist.from(wishlistItemCollection.aggregate(stages));
    }

    public Optional<WishlistItem> find(WishlistItem wishlistItem) {
        Document document = wishlistItemCollection
                .find(wishlistItem.toDocument()).first();
        if (document == null) return Optional.empty();
        return Optional.of(WishlistItem.from(document));
    }
}
