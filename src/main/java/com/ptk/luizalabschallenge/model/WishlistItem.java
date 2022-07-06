package com.ptk.luizalabschallenge.model;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;

public class WishlistItem {
    private final Product product;
    private String clientId;
    private String productId;

    public WishlistItem(String clientId, String productId) {
        this.clientId = clientId;
        this.productId = productId;
        product = null;
    }

    public WishlistItem(String clientId, String productId, Product product) {
        this.clientId = clientId;
        this.productId = productId;
        this.product = product;
    }

    public static WishlistItem from(Document document) {
        return new WishlistItem(
                ((ObjectId) document.get("client_id")).toString(),
                ((ObjectId) document.get("product_id")).toString(),
                productFrom(document));
    }

    private static Product productFrom(Document wishlistItem) {
        List<Document> documents = (List<Document>) wishlistItem.get("product");
        if (documents == null) return null;
        return Product.from(documents.get(0));
    }

    public String getProductId() {
        return productId;
    }

    public Document toDocument() {
        return new Document("client_id", new ObjectId(clientId))
                .append("product_id", new ObjectId(productId));
    }

    public Product getProduct() {
        return product;
    }
}
