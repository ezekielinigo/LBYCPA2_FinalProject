package com.shopapp.finalproject;

import com.shopapp.finalproject.DataStructs.Queue;
import com.shopapp.finalproject.DataStructs.Stack;
import javafx.scene.Scene;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

        try (InputStream is = GlobalData.class.getResourceAsStream("/GlobalData.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].replace("\"", ""); // Remove quotes
                }

                if (parts.length == 4) {
                    addGlobalSeller(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3]);
                } else if (parts.length == 6) {
                    addGlobalProduct(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), parts[4], parts[5]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



        // insert more sellers and products here vv






        // finally, you add the products to the seller's list of products (so that the seller page can show all their products)
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

    /** getter and setters */
    public ArrayList<Product> getGlobalProducts() {
        return globalProducts;
    }
    public void addGlobalProduct(String name, String description, float price, int stock, String tags, String seller) {
        this.globalProducts.add(new Product(name, description, price, stock, tags, seller));
    }

    public ArrayList<Seller> getGlobalSellers() {
        return globalSellers;
    }

    public void addGlobalSeller(String name, String description, int rating, String tags) {
        this.globalSellers.add(new Seller(name, description, rating, tags));
    }

    public ArrayList<Product> getCart() {
        return cart;
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

    public Seller getGlobalSeller(String name) {
        for (Seller seller : globalSellers) {
            if (seller.getName().equals(name)) {
                return seller;
            }
        }
        return null;
    }

    /** relevant product/seller handling **/

    /**
     * the global arrays will not be used to display the products in the home page / search page
     * instead, separate "relevant" arrays will be used
     * this means that when searching for "flowers", only products/sellers with the tag "flowers" or has "flowers" in its name will be added to the relevant arrays
     *
     * HOW DOES IT WORK?
     *
     * on startup, the relevantProducts will contain randomly arranged products from the globalProducts
     *             the relevantSellers will do the same
     *             the elements of these arrays should sum up to 20 only (arbitrary number of products/sellers present at a time)
     *             this means that on startup, the homescreen should contain an assortment of 20 thumbnails of products and sellers
     *
     * let's say the user opens a product page for "Sampaguita" which has tags "flowers, plants, decoration"
     *             these tags will be pushed to the relevantTags queue
     *             queue is first in first out, this means that when the relevant tags is full, the earliest tag will be removed
     *             this keeps the relevant products and sellers to be relevant to the user's latest searches
     *
     * what happens when relevantProducts and relevantSellers cannot be populated fully (20 total) with products and sellers according to the relevant tag queue?
     *             the relevantProducts and relevantSellers will be populated with random products and sellers from the globalProducts and globalSellers
     *             while still keeping track of already added products and sellers to avoid duplicates
     *
     */
    private ArrayList<Product> relevantProducts = new ArrayList<>();
    private ArrayList<Seller> relevantSellers = new ArrayList<>();

    private Queue<String> relevantTags = new Queue<>(10); // only used to determine which tags are still relevant
    private ArrayList<String> relevantTagsList = new ArrayList<>(10); // used to check and manipulate the tags themselves

    public ArrayList<Product> getRelevantProducts() {
        return relevantProducts;
    }
    public ArrayList<Seller> getRelevantSellers() {
        return relevantSellers;
    }

    public void addRelevantTag(String tag) {
        relevantTagsList.remove(tag); // avoid duplicates
        relevantTagsList.add(tag);
        if (relevantTags.isFull()) { // remove the earliest tag if the queue is full
            String x = relevantTags.pop();
            relevantTagsList.remove(x);
        }else {
            relevantTags.push(tag);
        }
    }

    public void setRelevantResults() {
        // the bulk that does the logic itself
        // tasked with populating the relevantProducts and relevantSellers arrays

        relevantProducts.clear();
        relevantSellers.clear();

        // first, populate the relevantProducts and relevantSellers with products and sellers that have the relevant tags
        for (String tag : relevantTagsList) {
            for (Product product : globalProducts) {
                if (product.getTags().contains(tag.toLowerCase()) || product.getName().toLowerCase().contains(tag.toLowerCase()) || product.getSeller().getName().toLowerCase().contains(tag.toLowerCase())) {
                    relevantProducts.add(product);
                }
            }
            for (Seller seller : globalSellers) {
                if (seller.getTags().contains(tag.toLowerCase()) || seller.getName().toLowerCase().contains(tag.toLowerCase()) || seller.getDescription().toLowerCase().contains(tag.toLowerCase())) {
                    relevantSellers.add(seller);
                }
            }
        }

        if (relevantProducts.size() < 5) {
            // if the relevantProducts array is not yet full, populate it with random products from the globalProducts array
            // avoid duplicates
            while (relevantProducts.size() < 5) {
                Product randomProduct = globalProducts.get((int) (Math.random() * globalProducts.size()));
                if (!relevantProducts.contains(randomProduct)) {
                    relevantProducts.add(randomProduct);
                }
            }
        }


    }

    /**
     * history and previously visited handling
     * while historyStack is used for the "back button" func, historyList will be used to display the history page
     */

    public void addToHistory(Product product) {
        productHistoryList.add(product);
    }

    public void addToHistory(Seller seller) {
        sellerHistoryList.add(seller);
    }

    public void addToHistory(Scene scene) { sceneHistoryStack.push(scene); }


    private Stack<Scene> sceneHistoryStack = new Stack<>();
    private ArrayList<Product> productHistoryList = new ArrayList<>();
    private ArrayList<Seller> sellerHistoryList = new ArrayList<>();

    public Scene getPreviousScene() {
        return sceneHistoryStack.pop();
    }


}
