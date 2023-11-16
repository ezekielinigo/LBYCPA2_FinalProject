package com.shopapp.finalproject;

import com.shopapp.finalproject.DataStructs.Queue;
import com.shopapp.finalproject.DataStructs.Stack;

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

        // first you initialize the seller
        // then you initialize the products wherein each will be assigned to a seller (so that the product page can show the seller)
//        addGlobalSeller("St. Peter Life & Cremation", "We sell flowers :DD", 5, "flowers, plants, gardening");
//        addGlobalProduct("Sampaguita", "mabango sya fr", 145, 34, "flowers, plants, decoration", "St. Peter Life & Cremation");
//        addGlobalProduct("Roses", "w/ free fertilizer", 459, 21, "flowers, plants, decoration", "St. Peter Life & Cremation");
//        addGlobalProduct("Silver Steel Casket With Crepe Interior", "w/ free flowers of your choice", 111795, 2, "tools, equipment, decoration", "St. Peter Life & Cremation");
//        addGlobalProduct("Steel Black Casket Handcrafted", "free shipping", 91770, 2, "tools, equipment, decoration", "St. Peter Life & Cremation");
//
//        addGlobalSeller("Strickland Propane", "We sell propane and propane accessories", 4, "tools, cooking");
//        addGlobalProduct("Petron Gasul 11kg LPG", "Snap on valve available nationwide!", 2799, 12, "tools, equipment, cooking", "Strickland Propane");
//        addGlobalProduct("Petron Gasul 2.7kg LPG burner", "Snap on valve available nationwide!", 999, 12, "tools, equipment, cooking", "Strickland Propane");
//
//        addGlobalSeller("Samsung Electronics", "We sell electronics", 5, "electronics, gadgets, appliances, cooking");
//        addGlobalProduct("Samsung Microwave Smart Oven 32L", "Reheat your favorite meal with Samsung Microwave Smart Oven 32L. 6-in-1 Kitchen Solution. Enjoy healthier fried food. Even Grilling.", 13994, 5, "electronics, appliances, gadgets, cooking", "Samsung Electronics");
//        addGlobalProduct("Samsung Smart Oven 35L", "All-in-One Cooking: Convection, Air Fry, Grill, Steam, Microwave and Ferment , HotBlast™ Technology, More Space for Bigger Plates: 380 mm Turntable", 26295, 5, "electronics, appliances, gadgets, cooking", "Samsung Electronics");
//        addGlobalProduct("Samsung 55\" QLED 4K Smart TV", "QLED 4K Smart TV. Quantum Processor 4K. Quantum HDR 4X. Ambient Mode. 100% Color Volume with Quantum Dot. Real Game Enhancer. 4K UHD. 100% Color Volume with Quantum Dot. Real Game Enhancer. 4K UHD.", 59999, 5, "electronics, appliances, gadgets", "Samsung Electronics");
//        addGlobalProduct("Samsung 65\" QLED 4K Smart TV", "QLED 4K Smart TV. Quantum Processor 4K. Quantum HDR 4X. Ambient Mode. 100% Color Volume with Quantum Dot. Real Game Enhancer. 4K UHD. 100% Color Volume with Quantum Dot. Real Game Enhancer. 4K UHD.", 89999, 5, "electronics, appliances, gadgets", "Samsung Electronics");
//        addGlobalProduct("Samsung Refrigerator with Family Hub and SpaceMax Technology", "Our 1st Smart Refrigerator with a built-in screen, Control smart appliances and devices with SmartThings App, Share and enjoy family moments", 159000, 1, "electronics, appliances, gadgets, cooking", "Samsung Electronics");
//        addGlobalProduct("Samsung Front Load Washing Machine 10.5kg", "AddWash, Hygiene steam: Anti-Bacteria, Intensive Wash, EcoBubble Technology", 54995, 2, "electronics, appliances, gadgets, cleaning", "Samsung Electronics");
//        addGlobalProduct("Samsung Twin Tub Washing Machine 16kg", "Power Storm, Magic Mixer, Two-way Lint Filter, Rust-proof Body", 19000, 2, "electronics, appliances, gadgets, cleaning", "Samsung Electronics");

        //String filePath = GlobalData.class.getResourceAsStream("/GlobalData.txt").toString(); // cannot find the file specified
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

    /**
     * while historyStack is used for the "back button" func, historyList will be used to display the history page
     */
    private Stack<Product> historyStack = new Stack<>();
    private ArrayList<Product> historyList = new ArrayList<>();

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

    public Seller getGlobalSeller(String name) {
        for (Seller seller : globalSellers) {
            if (seller.getName().equals(name)) {
                return seller;
            }
        }
        return null;
    }

    /** utility methods **/

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



}
