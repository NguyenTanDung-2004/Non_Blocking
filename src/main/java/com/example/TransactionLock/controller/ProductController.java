package com.example.TransactionLock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.TransactionLock.entity.Product;
import com.example.TransactionLock.repository.ProductRepository;
import com.example.TransactionLock.service.ProductService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/get1")
    public ResponseEntity get1() {
        return productService.getList();
    }
}
