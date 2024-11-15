package com.example.TransactionLock.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.TransactionLock.entity.Product;
import com.example.TransactionLock.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public ResponseEntity<Flux<String>> getList() {
        // get data from database as flux
        Flux<Product> products = this.productRepository.findAll();
        // convert each product to String
        Flux<String> productString = products
                .index()
                .delayElements(Duration.ofMillis(500))
                .map(i -> {
                    long index = i.getT1(); // get index
                    Product product = i.getT2(); // get value at this index.
                    try {
                        return this.objectMapper.writeValueAsString(product);
                    } catch (JsonProcessingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    return "";
                });
        // return
        return ResponseEntity
                .ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(productString);

    }

    public Mono<ResponseEntity> getList1() {

        // get data from database as flux
        Flux<Product> flux = this.productRepository.findAll();

        // convert flux to mono
        Mono<List<Product>> resultMono = flux.index()
                .map(tuple -> {
                    long index = tuple.getT1(); // get index
                    Product product = tuple.getT2(); // get the value at this index
                    return product;
                })
                .delaySubscription(Duration.ofMillis(5000)) // delayElements is used to delay for each row,
                                                            // delaySubscription is used to delay for all
                .collectList(); // collect products return type to list

        // Bọc kết quả trong ResponseEntity
        return resultMono.map(ResponseEntity::ok);

    }

}
