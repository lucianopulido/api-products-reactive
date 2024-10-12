package com.luciano.springboot.ms.products.app.services;

import com.luciano.springboot.ms.products.app.models.ProductDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IProductService {
    Mono<List<ProductDto>> getProductsById(List<String> products);
}
