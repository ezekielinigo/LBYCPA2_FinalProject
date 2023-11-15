package com.shopapp.finalproject;

import com.shopapp.finalproject.DataStructs.Stack;

import java.lang.reflect.Array;
import java.util.ArrayList;



public class GlobalData {
    /**
     * this singleton class is used to store and initialize all product, seller, and misc. data such as the cart and history
     * example usage of its methods outside the class:
     *
     *      // this should find the list of products with the tag "flowers"
     *      // possibly useful for the search bar
     *      GlobalData g = GlobalData.getInstance();
     *      g.getGlobalProducts().stream().filter(product -> product.tags.contains("flowers")).collect(Collectors.toList());
     *
     *      // this should find the list of products from the seller "Flower Shop" in increasing price
     *      // possibly useful for sorting the seller page
     *      GlobalData g = GlobalData.getInstance();
     *      g.getSeller("Flower Shop").getProducts().stream().sorted(Comparator.comparing(Product::getPrice)).collect(Collectors.toList());
     *
     *      // this should find the list of products that was previously visited
     *      // useful for the history page
     *      GlobalData g = GlobalData.getInstance();
     *      g.historyList;
     *
     */
    private static final GlobalData instance = new GlobalData();
    public static GlobalData getInstance() {
        return instance;
    }
    private GlobalData() {

    }
    public void initializeData() {
        // this constructor contains the initialization of all products, sellers, etc.
        // di pwede to sa GlobalData kasi nagkaka circular dependency error
        // this class is only used at the very start of the program (ShopApp.java)

        // first you initialize the seller
        // then you initialize the products wherein each will be assigned to a seller (so that the product page can show the seller)
        // finally, you add the products to the seller's list of products (so that the seller page can show all their products)
        this.globalSellers.add(new Seller("Flower Shop", "We sell flowers :DD", 5, "flowers, plants, gardening"));
        this.globalProducts.add(new Product("Sampaguita", "mabango sya fr", 10, 100, "flowers, plants, decoration", "Flower Shop"));
        this.globalProducts.add(new Product("Bugambilia", "matinik sya fr", 10, 100, "flowers, plants, decoration", "Flower Shop"));
        for (Product product : globalProducts) {
            product.getSeller().addProduct(product);
        }
    }

    /**
     * self-explanatory, these are the global variables that can be used everywhere in the program
     * as long as you import GlobalData using "GlobalData g = GlobalData.getInstance();"
     */
    private ArrayList<Seller> globalSellers = new ArrayList<>();
    private ArrayList<Product> globalProducts = new ArrayList<>();
    private ArrayList<Product> cart = new ArrayList<>();

    /**
     * while historyStack is used for the "back button" func, historyList will be used to display the history page
     */
    private Stack<Product> historyStack = new Stack<>();
    private ArrayList<Product> historyList = new ArrayList<>();

    /** getter and setters */
    public ArrayList<Product> getGlobalProducts() {
        return globalProducts;
    }

    public ArrayList<Seller> getGlobalSellers() {
        return globalSellers;
    }

    public ArrayList<Product> getCart() {
        return cart;
    }

    public Stack<Product> getHistoryStack() {
        return historyStack;
    }

    public ArrayList<Product> getHistoryList() {
        return historyList;
    }

    public void addToCart(Product product) {
        cart.add(product);
    }

    public Product getGlobalProduct(String name) {
        for (Product product : globalProducts) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }

    public Seller getSeller(String name) {
        for (Seller seller : globalSellers) {
            if (seller.getName().equals(name)) {
                return seller;
            }
        }
        return null;
    }




}
