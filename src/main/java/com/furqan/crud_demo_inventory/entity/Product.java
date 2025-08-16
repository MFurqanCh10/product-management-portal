package com.furqan.crud_demo_inventory.entity;

import jakarta.persistence.*;

@Entity
@Table(name="product")
public class Product {

     // fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int Id;
    @Column(name="Item_Name")
    private String Item_Name;

    @Column(name = "Sku_Number")
    private String Sku_Number;

    @Column(name="Item_Vendor_Number")
    private String Item_Vendor_Number;

    @Column(name="image1")
    private String Image1;

    @Column(name="image2")
    private String Image2;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "sale_Price")
    private double sale_Price;

    @Column(name = "active")
    private boolean active;

    // constructors

    public Product(){
    }

    public Product(String item_Name, String sku_Number, String item_Vendor_Number, String image1, String image2) {
        Item_Name = item_Name;
        Sku_Number = sku_Number;
        Item_Vendor_Number = item_Vendor_Number;
        Image1 = image1;
        Image2 = image2;
    }

    public Product(Integer quantity, double sale_Price, boolean active) {
        this.quantity = quantity;
        this.sale_Price = sale_Price;
        this.active = active;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

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

    @Override
    public String toString() {
        return "Product{" +
                "Id=" + Id +
                ", Item_Name='" + Item_Name + '\'' +
                ", Sku_Number='" + Sku_Number + '\'' +
                ", Item_Vendor_Number='" + Item_Vendor_Number + '\'' +
                ", Image1='" + Image1 + '\'' +
                ", Image2='" + Image2 + '\'' +
                ", quantity=" + quantity +
                ", sale_Price=" + sale_Price +
                ", active=" + active +
                '}';
    }
}
