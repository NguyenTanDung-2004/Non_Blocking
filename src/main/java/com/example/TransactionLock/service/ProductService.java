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

import reactor.core.publisher.Flux;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<Flux<String>> getList() { // get list trả dữ liệu theo từng row
        // trả dữ liệu theo từng row.
        String s = "nguyentanDung";

        List<Product> list = this.productRepository.findAll();

        // Lấy dữ liệu từ ProductRepository dưới dạng Flux
        Flux<Product> flux = Flux.fromIterable(list);
        List<String> list1 = new ArrayList<>();
        // khi fromIterable ở mỗi dòng thì chúng ta sẽ xử lí cái đóng này thông qua
        // subscriber
        flux.index().subscribe(i -> { // đánh index() để có thể lấy ra index
            Long index = i.getT1();
            Product product = i.getT2();
            list1.add(product.getName());
        },
                error -> System.err.println("Error: " + error), // Xử lý lỗi nếu có
                () -> { // khi mà hoàn tất duyeetj qua hết các dòng.
                    System.out.println(list1);
                });

        // Xử lý dữ liệu trong Flux
        Flux<String> resultFlux = flux.index()
                .delayElements(Duration.ofMillis(500)) // Mô phỏng trả dần dần, có thể không cần thiết
                .map(tuple -> {
                    long index = tuple.getT1(); // Lấy index
                    Product product = tuple.getT2(); // Lấy phần tử
                    // System.out.println(index);
                    return s + index; // Trả về chuỗi
                });

        for (int i = 0; i < 10; i++) {
            System.out.println("nguyentandung");
        }

        // Trả về ResponseEntity với kiểu nội dung là Server-Sent Events (SSE)
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM) // Định dạng SSE
                .body(resultFlux);

    }
}
