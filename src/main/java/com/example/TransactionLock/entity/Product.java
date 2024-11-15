package com.example.TransactionLock.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product") // R2DBC uses @Table from Spring Data
public class Product {

    @Id // Use @Id from org.springframework.data.annotation for primary keys in R2DBC
    private String id;

    private String name;
    private int number;
}
