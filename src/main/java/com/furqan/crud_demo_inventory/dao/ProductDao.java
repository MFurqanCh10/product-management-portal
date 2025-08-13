package com.furqan.crud_demo_inventory.dao;

import com.furqan.crud_demo_inventory.entity.Product;

import java.util.List;

public interface ProductDao {
    Product save (Product theProduct);

    Product findByID(Integer Id);

    List<Product> findAll();

    List<Product> findByItem_Vendor_Number(String theItem_Vendor_Number);

    void update(Product theProduct);

    void delete(Integer Id);

    int deleteAll();

    void deleteById(int theId);

    List<Product> searchProducts(String Item_Name, String Sku_Number);


}
