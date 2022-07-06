package com.ptk.luizalabschallenge.dao;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoDatabase;
import com.ptk.luizalabschallenge.model.Product;
import com.ptk.luizalabschallenge.model.WishlistItem;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
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

    public List<Product> all(String clientId) {
        List<Bson> stages = Arrays.asList(
                match(eq("client_id", new ObjectId(clientId))),
                lookup("product", "product_id","_id", "product"));
        AggregateIterable<Document> wishlistIterable = database.getCollection("wishlist_item")
                .aggregate(stages);
        ArrayList<Product> wishlist = new ArrayList<>();
        for (Document wishlistItem : wishlistIterable)
            wishlist.add(productFrom(wishlistItem));
        return wishlist;
    }

    private Product productFrom(Document wishlistItem) {
        Document product = ((List<Document>) wishlistItem.get("product")).get(0);
        return Product.from(product);
    }

    public void insert(WishlistItem wishlistItem) {
        database.getCollection("wishlist_item").insertOne(documentFrom(wishlistItem));
    }

    private Document documentFrom(WishlistItem wishlistItem) {
        return new Document("client_id", new ObjectId(wishlistItem.getClientId()))
                .append("product_id", new ObjectId(wishlistItem.getProductId()));
    }

    public void remove(WishlistItem wishlistItem) {
        database.getCollection("wishlist_item").deleteOne(documentFrom(wishlistItem));
    }

    public Optional<WishlistItem> find(WishlistItem wishlistItem) {
        Document document = database.getCollection("wishlist_item")
                .find(documentFrom(wishlistItem)).first();
        if (document == null) return Optional.empty();
        return Optional.of(wishlistItemFrom(document));
    }

    private WishlistItem wishlistItemFrom(Document document) {
        return new WishlistItem(
                ((ObjectId) document.get("client_id")).toString(),
                ((ObjectId) document.get("product_id")).toString());
    }
}
