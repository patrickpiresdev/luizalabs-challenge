package com.ptk.luizalabschallenge.model;

import com.mongodb.client.AggregateIterable;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Wishlist {
    private final int WISHLIST_MAX = 20;
    private final List<WishlistItem> items;

    public Wishlist() {
        items = new ArrayList<>();
    }

    public static Wishlist from(AggregateIterable<Document> wishlistIterable) {
        Wishlist wishlist = new Wishlist();
        for (Document document : wishlistIterable)
            wishlist.add(WishlistItem.from(document));
        return wishlist;
    }

    private void add(WishlistItem item) {
        items.add(item);
    }

    public boolean isFull() {
        return items.size() == WISHLIST_MAX;
    }

    public boolean contains(String productId) {
        return items.stream().map(WishlistItem::getProductId).anyMatch(id -> id.equals(productId));
    }

    public List<Product> toProductsList() {
        return items.stream().map(WishlistItem::getProduct).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
