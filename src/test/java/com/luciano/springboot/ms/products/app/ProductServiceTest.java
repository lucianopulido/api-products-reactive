package com.luciano.springboot.ms.products.app;

import com.luciano.springboot.ms.products.app.exception.ProductNotFoundException;
import com.luciano.springboot.ms.products.app.models.Product;
import com.luciano.springboot.ms.products.app.repositories.ProductRepository;
import com.luciano.springboot.ms.products.app.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

public class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @Test
    public void testGetProductsByIdSuccess() {
        List<String> productIds = Arrays.asList("1", "2");
        Product product1 = new Product("1", "Producto 1", 100.0);
        Product product2 = new Product("2", "Producto 2", 200.0);

        when(productRepository.findByIdIn(productIds)).thenReturn(Flux.just(product1, product2));

        StepVerifier.create(productService.getProductsById(productIds))
                .expectNextMatches(products ->
                        products.size() == 2 &&
                                products.get(0).getProductId().equals("1") &&
                                products.get(1).getProductId().equals("2")
                )
                .verifyComplete();
    }

    @Test
    public void testGetProductsByIdPartiallyNotFound() {
        List<String> productIds = Arrays.asList("1", "2", "3");
        Product product1 = new Product("1", "Producto 1", 100.0);
        Product product2 = new Product("2", "Producto 2", 200.0);

        when(productRepository.findByIdIn(productIds)).thenReturn(Flux.just(product1, product2));

        StepVerifier.create(productService.getProductsById(productIds))
                .expectErrorMatches(throwable -> throwable instanceof ProductNotFoundException &&
                        throwable.getMessage().equals("Los siguientes productos no existen: [3]"))
                .verify();
    }

    @Test
    public void testGetProductsByIdAllNotFound() {
        List<String> productIds = Arrays.asList("1", "2", "3");

        when(productRepository.findByIdIn(productIds)).thenReturn(Flux.empty());

        StepVerifier.create(productService.getProductsById(productIds))
                .expectErrorMatches(throwable -> throwable instanceof ProductNotFoundException &&
                        throwable.getMessage().equals("Los siguientes productos no existen: [1, 2, 3]"))
                .verify();
    }
}
