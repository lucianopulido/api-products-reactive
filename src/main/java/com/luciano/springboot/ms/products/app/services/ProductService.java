package com.luciano.springboot.ms.products.app.services;

import com.luciano.springboot.ms.products.app.exception.ProductNotFoundException;
import com.luciano.springboot.ms.products.app.models.ProductDto;
import com.luciano.springboot.ms.products.app.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ProductService implements IProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Mono<List<ProductDto>> getProductsById(List<String> productIds) {
        return productRepository.findByIdIn(productIds)
                .map(product -> new ProductDto(
                        product.getId(),
                        product.getName(),
                        product.getPrice()
                ))
                .collectList()
                .flatMap(products -> {
                    if (products.size() != productIds.size()) {
                        List<String> missingIds = productIds.stream()
                                .filter(id -> products.stream().noneMatch(product -> product.getProductId().equals(id)))
                                .toList();
                        return Mono.error(new ProductNotFoundException("Los siguientes productos no existen: " + missingIds));
                    }
                    return Mono.just(products);
                });
    }
}
