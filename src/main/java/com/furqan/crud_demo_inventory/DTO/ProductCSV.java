package com.furqan.crud_demo_inventory.DTO;

import com.opencsv.bean.CsvBindByName;

public class ProductCSV {
    @CsvBindByName(column = "Item_Name")
    private String Item_Name;

    @CsvBindByName(column = "Sku_Number")
    private String Sku_Number;

    @CsvBindByName(column = "Item_Vendor_Number")
    private String Item_Vendor_Number;

    @CsvBindByName(column = "image1")
    private String Image1;

    @CsvBindByName(column = "image2")
    private String Image2;

    @CsvBindByName(column = "quantity")
    private Integer quantity;

    @CsvBindByName(column = "sale_price")
    private double sale_Price;

    @CsvBindByName(column = "active")
    private  boolean active;

    // Getters and Setters
    public String getItem_Name() {
        return Item_Name;
    }

    public void setItem_Name(String item_Name) {
        Item_Name = item_Name;
    }

    public String getSku_Number() {
        return Sku_Number;
    }

    public void setSku_Number(String sku_Number) {
        Sku_Number = sku_Number;
    }

    public String getItem_Vendor_Number() {
        return Item_Vendor_Number;
    }

    public void setItem_Vendor_Number(String item_Vendor_Number) {
        Item_Vendor_Number = item_Vendor_Number;
    }

    public String getImage1() {
        return Image1;
    }

    public void setImage1(String image1) {
        Image1 = image1;
    }

    public String getImage2() {
        return Image2;
    }

    public void setImage2(String image2) {
        Image2 = image2;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getSale_Price() {
        return sale_Price;
    }

    public void setSale_Price(double sale_Price) {
        this.sale_Price = sale_Price;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
