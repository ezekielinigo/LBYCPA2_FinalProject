package com.shopapp.finalproject;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Arrays;

public class Product {
    String name, description;
    Image image;
    int stock;
    float price;
    ArrayList<String> tags;
    Seller seller;

    public Product(String name, String description, float price, int stock, String tags, String seller) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;

        String[] split = tags.split(",\\s");
        this.tags = new ArrayList<>(Arrays.asList(split));

        this.seller = GlobalData.getInstance().getGlobalSeller(seller);

        try {
            this.image = new Image(Product.class.getResource("/images/products/" + name + ".png").toString());
        }catch (Exception e) {
            this.image = new Image(Product.class.getResource("/images/products/sampleProduct.png").toString());
        }
    }

    public Image getImage() {
        return this.image;
    }

    public String getName() {
        return this.name;
    }

    public float getPrice() {
        return this.price;
    }

    public Seller getSeller() {
        return this.seller;
    }
    public ArrayList<String> getTags() {
        return this.tags;
    }

    public String getDescription() {
        return this.description;
    }

    public int getStock() {
        return this.stock;
    }
}
