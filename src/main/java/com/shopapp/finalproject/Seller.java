package com.shopapp.finalproject;

import javafx.scene.image.Image;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Seller {
    private String name, description;
    private int rating;
    private Image image;
    private ArrayList<String> tags = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();

    /**
     * @param name: name of the seller
     * @param description: description of the seller (pwede short bio or smth)
     * @param rating: rating of the seller out of 5 (will be used to display stars sa seller page)
     * @param tags: seller tags (format: "tech, computers, computer parts") to be used for searching and graph correlation between sellers
     *              tags are automatically split into an arraylist
     */
    public Seller(String name, String description, int rating, String tags) {
        this.name = name;
        this.description = description;
        this.rating = rating;

        String[] split = tags.split(",\\s");
        this.tags = new ArrayList<>(Arrays.asList(split));

        try {
            this.image = new Image(Seller.class.getResource("/images/sellers/" + name + ".png").toString());
        }catch (Exception e) {
            this.image = new Image(Seller.class.getResource("/images/sellers/sampleSeller.png").toString());
        }
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public ArrayList<Product> getProducts() {
        return this.products;
    }

    public String getName() {
        return this.name;
    }

    public Image getImage() {
        return this.image;
    }

    public String getDescription() {
        return this.description;
    }

    public ArrayList<String> getTags() {
        return this.tags;
    }

    public int getRating() {
        return this.rating;
    }
}
