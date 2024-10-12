package com.luciano.springboot.ms.products.app.repositories;

import com.luciano.springboot.ms.products.app.models.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
    Flux<Product> findByIdIn(Iterable<String> ids);
}
