package com.ptk.luizalabschallenge.controller;

import com.ptk.luizalabschallenge.dao.ProductDAO;
import com.ptk.luizalabschallenge.model.Product;
import org.bson.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductDAO productDao;

    public ProductController(ProductDAO productDao) {
        this.productDao = productDao;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> detail(@PathVariable String id) {
        String filter = "{ _id: ObjectId('" + id + "') }";
        Optional<Product> product = productDao.find(Document.parse(filter));
        if (product.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(product.get());
    }
}
