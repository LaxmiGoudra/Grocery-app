package com.ls.grocery_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ls.grocery_app.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
