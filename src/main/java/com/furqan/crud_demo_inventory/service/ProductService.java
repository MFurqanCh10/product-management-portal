package com.furqan.crud_demo_inventory.service;

import com.furqan.crud_demo_inventory.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(int theId);

    Product save(Product theProduct);

    void deleteById(int theId);

    List<Product> searchProducts(String Item_Name, String Sku_Number);

    void saveAll(List<Product> products);




}
