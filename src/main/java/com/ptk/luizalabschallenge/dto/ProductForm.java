package com.ptk.luizalabschallenge.dto;

import com.ptk.luizalabschallenge.model.Product;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ProductForm {
    @NotNull @NotEmpty
    public String title;
    @NotNull @NotEmpty
    public String description;

    public Product convert() {
        return new Product(title, description);
    }
}
