package com.ptk.luizalabschallenge.dto;

import com.ptk.luizalabschallenge.model.Product;

public class ProductDto {
    public String title;
    public String description;

    public ProductDto(Product product) {
        title = product.getTitle();
        description = product.getDescription();
    }
}
