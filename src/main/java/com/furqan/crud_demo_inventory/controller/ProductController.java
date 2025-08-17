package com.furqan.crud_demo_inventory.controller;

import com.furqan.crud_demo_inventory.entity.Product;
import com.furqan.crud_demo_inventory.repository.ProductRepository;
import com.furqan.crud_demo_inventory.service.ProductService;
import com.furqan.crud_demo_inventory.service.ConfigurationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final ConfigurationService configurationService;

    // ✅ Constructor injection only
    public ProductController(ProductRepository productRepository,
                             ProductService productService,
                             ConfigurationService configurationService) {
        this.productRepository = productRepository;
        this.productService = productService;
        this.configurationService = configurationService;
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMIN')")
    @GetMapping("/list")
    public String listProducts(@RequestParam(value = "keyword", required = false) String keyword,
                               Model theModel) {

        List<Product> theProducts;

        if (keyword != null && !keyword.trim().isEmpty()) {
            // 🔒 Search only allowed for ADMIN
            boolean isAdmin = org.springframework.security.core.context.SecurityContextHolder.getContext()
                    .getAuthentication().getAuthorities()
                    .stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

            if (!isAdmin) {
                theModel.addAttribute("error", "Access Denied: Only Admin can search.");
                theProducts = productService.findAll(); // fallback
            } else {
                theProducts = productRepository.searchByItemNameOrSku(keyword);
            }
        } else {
            theProducts = productService.findAll();
        }

        // ✅ Add Currency Unit
        String currencyUnit = configurationService.getCurrencyUnit();
        theModel.addAttribute("currencyUnit", currencyUnit);

        theModel.addAttribute("products", theProducts);
        theModel.addAttribute("keyword", keyword);

        return "products/list-products";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {
        Product theProduct = new Product();
        theModel.addAttribute("product", theProduct);

        // ✅ Currency for form
        String currencyUnit = configurationService.getCurrencyUnit();
        theModel.addAttribute("currencyUnit", currencyUnit);

        return "products/products-form";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("productId") int theId, Model theModel) {
        Product theProduct = productService.findById(theId);
        theModel.addAttribute("product", theProduct);

        // ✅ Currency for update form
        String currencyUnit = configurationService.getCurrencyUnit();
        theModel.addAttribute("currencyUnit", currencyUnit);

        return "products/products-form";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping("/save")
    public String saveProducts(@ModelAttribute("product") Product theProduct) {
        productService.save(theProduct);
        return "redirect:/products/list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete")
    public String delete(@RequestParam("productId") int theId) {
        productService.deleteById(theId);
        return "redirect:/products/list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/import")
    public String importCSV(@RequestParam("file") MultipartFile file, Model model) {
        String contentType = file.getContentType();

        if (!file.getOriginalFilename().endsWith(".csv") && !"text/csv".equals(contentType)) {
            model.addAttribute("error", "Only .csv files are allowed!");
            return "redirect:/products/list";
        }

        List<Product> productList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] values = line.split(",");
                if (values.length < 8) continue; // ✅ Fix: minimum 8 fields required

                Product product = new Product();
                product.setItem_Name(values[0].trim());
                product.setSku_Number(values[1].trim());
                product.setItem_Vendor_Number(Integer.valueOf(values[2].trim()));
                product.setImage1(values[3].trim());
                product.setImage2(values[4].trim());
                product.setQuantity(Integer.parseInt(values[5].trim()));
                product.setSale_Price(Double.parseDouble(values[6].trim()));

                // ✅ If you want to keep boolean Active/Inactive
                product.setActive(Boolean.parseBoolean(values[7].trim()));

                productList.add(product);
            }

            productService.saveAll(productList);

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error processing CSV file!");
            return "redirect:/products/list";
        }

        return "redirect:/products/list";
    }

    @GetMapping("/")
    public String redirectToProductList() {
        return "redirect:/products/list";
    }
}
