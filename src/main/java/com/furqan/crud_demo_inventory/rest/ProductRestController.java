package com.furqan.crud_demo_inventory.rest;

import com.furqan.crud_demo_inventory.DTO.ProductCSV;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.furqan.crud_demo_inventory.entity.Product;
import com.furqan.crud_demo_inventory.service.ProductService;
import com.furqan.crud_demo_inventory.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.apache.tomcat.jni.SSLConf.apply;

@RestController
@RequestMapping("/api")
public class ProductRestController {

    private ProductService productService;

    private ObjectMapper objectMapper;

    //inject product dao using constructors injection

    @Autowired
    public ProductRestController(ProductService theProductService, ObjectMapper theObjectMapper){

        productService = theProductService;
        objectMapper = theObjectMapper;

    }
    // expose "/products" and return a list of product
    @GetMapping("/products")
    public List<Product> findAll(){
        return productService.findAll();
    }
    // Add mapping for get /products/{productsId}
    @GetMapping("/product/{productId}")

    public Product getProduct(@PathVariable int productId){
        Product theProduct = productService.findById(productId);

        if (theProduct == null){
            throw new RuntimeException("Product id not found - " + productId);
        }

        return theProduct;
    }
    //ADD MAPPING FOR POST PRODUCT (ADD NEW PRODUCT)
    @PostMapping("/products")
    public  Product addProduct(@RequestBody Product theProduct){

        //also just in case they pass an id in jason ... set id to 0
        //this is to force a save of new item .. instead of update

        theProduct.setId(0);

        Product dbProduct = productService.save(theProduct);
        return dbProduct;
    }

    // add mapping for delete product with product id
    @DeleteMapping("/products/{productId}")

    public  String deleteProduct(@PathVariable int productId){
        Product tempProduct = productService.findById(productId);

        //throw exception if null
        if(tempProduct == null){
            throw new RuntimeException("Product id not found " + productId);

        }
        productService.deleteById(productId);

        return "Deleted product id " + productId;
    }
    //add mapping for patch /product/{productId} --partial update
    @Transactional
    @PatchMapping("/products/{productId}")
    public Product patchProduct(@PathVariable int productId, @RequestBody Map<String, Object> patchPayload) {
        // Fetch the managed entity
        Product tempProduct = productService.findById(productId);

        if (tempProduct == null) {
            throw new RuntimeException("Product Id not found: " + productId);
        }

        if (patchPayload.containsKey("id")) {
            throw new RuntimeException("Product ID modification not allowed");
        }

        // Apply changes to the managed entity
        Product updatedProduct = apply(patchPayload, tempProduct);

        // Return the managed entity (no need for explicit save)
        return updatedProduct;
    }

    private Product apply(Map<String, Object> patchPayload, Product tempProduct) {
        try {
            // Convert product to JSON
            ObjectNode productNode = objectMapper.convertValue(tempProduct, ObjectNode.class);

            // Convert patch payload to JSON
            ObjectNode patchNode = objectMapper.convertValue(patchPayload, ObjectNode.class);

            // Merge changes
            productNode.setAll(patchNode);

            // Update the managed entity with merged values
            objectMapper.readerForUpdating(tempProduct).readValue(productNode);

            return tempProduct; // Return the managed entity
        } catch (Exception e) {
            throw new RuntimeException("Failed to apply patch", e);
        }
    }

        @GetMapping("/products/search")
        public List<Product> searchProducts(
                @RequestParam(required = false) String Item_Name,
                @RequestParam(required = false) String Sku_Number) {

            return productService.searchProducts(Item_Name, Sku_Number);
        }

    @PostMapping("/products/upload-csv")
    public ResponseEntity<String> uploadCSVFile(@RequestParam("file") MultipartFile file) {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            // Parse CSV to Product objects
            CsvToBean<ProductCSV> csvToBean = new CsvToBeanBuilder<ProductCSV>(reader)
                    .withType(ProductCSV.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<ProductCSV> csvProducts = csvToBean.parse();

            // Convert to Product entities and save
            List<Product> products = csvProducts.stream()
                    .map(this::convertToEntity)
                    .collect(Collectors.toList());

            products.forEach(productService::save);

            return ResponseEntity.ok("Successfully uploaded and saved " + products.size() + " products");
        } catch (Exception ex) {
            return ResponseEntity.status(500)
                    .body("Failed to process CSV file: " + ex.getMessage());
        }
    }

    private Product convertToEntity(ProductCSV csv) {
        Product product = new Product();
        product.setItem_Name(csv.getItem_Name());
        product.setSku_Number(csv.getSku_Number());
        product.setItem_Vendor_Number(csv.getItem_Vendor_Number());
        product.setImage1(csv.getImage1());
        product.setImage2(csv.getImage2());

        // ✅ New fields
        product.setQuantity(csv.getQuantity());        // Integer
        product.setSale_Price(csv.getSale_Price());      // Double
        product.setActive(csv.isActive());

        return product;
    }



    }











