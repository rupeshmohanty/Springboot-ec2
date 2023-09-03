package com.deployapplication.demo.repo;

import org.springframework.stereotype.Repository;

import com.deployapplication.demo.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    
}
