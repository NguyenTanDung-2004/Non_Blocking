package com.example.TransactionLock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.TransactionLock.entity.Product;
import com.example.TransactionLock.service.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/get") // this endpoint is used to get one product data row after 1 period without
                        // request.
    public ResponseEntity<Flux<String>> get() {
        return productService.getList();
    }

    @GetMapping("/get1") // this endpoint is used to get all products data
    public Mono<ResponseEntity> get1() {
        return productService.getList1();
    }
}
