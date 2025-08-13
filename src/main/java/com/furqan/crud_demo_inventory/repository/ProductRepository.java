package com.furqan.crud_demo_inventory.repository;

import com.furqan.crud_demo_inventory.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.Item_Name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.Sku_Number) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchByItemNameOrSku(@Param("keyword") String keyword);



}
