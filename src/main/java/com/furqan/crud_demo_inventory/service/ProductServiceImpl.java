package com.furqan.crud_demo_inventory.service;

import com.furqan.crud_demo_inventory.dao.ProductDao;
import com.furqan.crud_demo_inventory.entity.Product;
import com.furqan.crud_demo_inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductDao theProductDao, ProductRepository productRepository) {
        this.productDao = theProductDao;
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public Product findById(int theId) {
        return productDao.findByID(theId);
    }

    @Transactional
    @Override
    public Product save(Product theProduct) {
        return productDao.save(theProduct);
    }

    @Transactional
    @Override
    public void deleteById(int theId) {
        productDao.delete(theId);
    }

    @Override
    public List<Product> searchProducts(String Item_Name, String Sku_Number) {
        return productDao.searchProducts(Item_Name, Sku_Number);
    }

    @Override
    public void saveAll(List<Product> products) {
        productRepository.saveAll(products);
    }
}
