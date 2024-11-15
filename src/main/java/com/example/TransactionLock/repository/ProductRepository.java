package com.example.TransactionLock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.TransactionLock.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
    // @Override
    // @Query(nativeQuery = true, value = "CALL get_products_with_delay()")
    // public List<Product> findAll();
}
