package com.ptk.luizalabschallenge.model;

public class WishlistItem {
    private String clientId;
    private String productId;

    public WishlistItem(String clientId, String productId) {
        this.clientId = clientId;
        this.productId = productId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getProductId() {
        return productId;
    }
}
