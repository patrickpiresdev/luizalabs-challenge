package com.ptk.luizalabschallenge.model;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;

public class WishlistItem {
    public static final String CLIENT_ID_FIELD = "client_id";
    public static final String PRODUCT_ID_FIELD = "product_id";
    private final Product product;
    private String clientId;
    private String productId;

    public WishlistItem(String clientId, String productId) {
        this(clientId, productId, null);
    }

    public WishlistItem(String clientId, String productId, Product product) {
        this.clientId = clientId;
        this.productId = productId;
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public String getProductId() {
        return productId;
    }

    public Document toDocument() {
        return new Document(CLIENT_ID_FIELD, new ObjectId(clientId))
                .append(PRODUCT_ID_FIELD, new ObjectId(productId));
    }

    public static WishlistItem from(Document document) {
        return new WishlistItem(
                ((ObjectId) document.get(CLIENT_ID_FIELD)).toString(),
                ((ObjectId) document.get(PRODUCT_ID_FIELD)).toString(),
                productFrom(document));
    }

    private static Product productFrom(Document wishlistItem) {
        List<Document> documents = (List<Document>) wishlistItem.get("product");
        if (documents == null) return null;
        return Product.from(documents.get(0));
    }
}
