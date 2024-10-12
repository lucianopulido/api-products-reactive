package com.luciano.springboot.ms.products.app.controllers;

import com.luciano.springboot.ms.products.app.models.ProductDto;
import com.luciano.springboot.ms.products.app.services.IProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Mono<ResponseEntity<List<ProductDto>>> getProductById(@RequestParam List<String> products) {
        return productService.getProductsById(products)
                .map(productDtoList -> {
                    if (products.isEmpty()) {
                        return ResponseEntity.notFound().build();
                    } else {
                        return ResponseEntity.ok(productDtoList);
                    }
                });
    }
}
