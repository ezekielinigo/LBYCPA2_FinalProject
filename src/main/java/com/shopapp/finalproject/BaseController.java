package com.shopapp.finalproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BaseController {

    /**
     * gotoProductDetail: used to go to the product detail page where you view images, info, add to cart, etc.
     * gotoPrevious: used to go to the previous page viewed
     *               uses a STACK to store the previous pages
     *
     * gotoHistory: displays ALL previously visited pages in a similar grid format to the homescreen but without the banner
     */


    /**
     * this method switches to a screen that displays the product's details, images, etc. as well as the add to cart and seller buttons
     * @param product: the product that will be used to fill the product detail page
     */
    protected void gotoProductDetail(Product product, Stage stage, String prevScreenType, String prevScreenIdentifier) {
        try {
            // add previous scene to history
            GlobalData.getInstance().addToHistory(prevScreenType, prevScreenIdentifier);

            // update relevant tags based on opened product
            for (String tag : product.getTags())
                GlobalData.getInstance().addRelevantTag(tag);

            // switch scenes
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductDetail.fxml"));
            Scene productDetailScreen = new Scene(loader.load());
            stage.setScene(productDetailScreen);

            // setup product information
            ProductDetailController controller = loader.getController();
            controller.setup(product);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void gotoProductDetail(Product product, Stage stage) {
        // this is the same as the above method but without the previous screen type and identifier
        // which is used for going back to the previous screen
        try {
            // switch scenes
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductDetail.fxml"));
            Scene productDetailScreen = new Scene(loader.load());
            stage.setScene(productDetailScreen);

            // setup product information
            ProductDetailController controller = loader.getController();
            controller.setup(product);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void gotoSellerDetail(Seller seller, Stage stage, String prevScreenType, String prevScreenIdentifier) {
        try {

            // add previous scene to history
            GlobalData.getInstance().addToHistory(prevScreenType, prevScreenIdentifier);

            // update relevant tags based on opened seller
            for (String tag : seller.getTags())
                GlobalData.getInstance().addRelevantTag(tag);

            // switch scenes
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SellerDetail.fxml"));
            Scene sellerDetailScreen = new Scene(loader.load());
            stage.setScene(sellerDetailScreen);

            // setup seller information
            SellerDetailController controller = loader.getController();
            controller.setup(seller);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void gotoSellerDetail(Seller seller, Stage stage) {
        // this is the same as the above method but without the previous screen type and identifier
        // which is used for going back to the previous screen
        try {
            // switch scenes
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SellerDetail.fxml"));
            Scene sellerDetailScreen = new Scene(loader.load());
            stage.setScene(sellerDetailScreen);

            // setup seller information
            SellerDetailController controller = loader.getController();
            controller.setup(seller);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method switches to the screen that displays the previous page viewed
     * input: stage of the current scene
     *        screen type of previous scene ("product", "seller", "home", "history", "cart")
     *        identifier (name of product or seller)
     */
    protected void gotoPrevious(Stage stage) {
        try {

            String[] prevScreen = GlobalData.getInstance().popHistory();
            String screenType = prevScreen[0];
            String identifier = prevScreen[1];

            if (screenType.equals("product")) {
                Product product = GlobalData.getInstance().getGlobalProduct(identifier);
                gotoProductDetail(product, stage);
            } else if (screenType.equals("seller")) {
                Seller seller = GlobalData.getInstance().getGlobalSeller(identifier);
                gotoSellerDetail(seller, stage);
            } else if (screenType.equals("history")) {
                gotoHistory(stage);
            } else if (screenType.equals("home")) {
                gotoHome(stage);
            } else if (screenType.equals("cart")) {
                gotoCart(stage);
            }

        }catch(Exception e){
            gotoHome(stage); // handle if history is empty
        }
    }

    /**
     * this method switches to the screen that displays the full history of visited pages
     */
    protected void gotoHome(Stage stage) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ShopApp.class.getResource("HomeScreen.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);

            HomeScreenController controller = fxmlLoader.getController();
            controller.setup();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void gotoHistory(Stage stage, String prevScreenType, String prevScreenIdentifier) {

        try {
            GlobalData.getInstance().addToHistory(prevScreenType, prevScreenIdentifier);

            FXMLLoader fxmlLoader = new FXMLLoader(ShopApp.class.getResource("History.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);

            HistoryController controller = fxmlLoader.getController();
            controller.setup();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void gotoHistory(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ShopApp.class.getResource("History.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);

            HistoryController controller = fxmlLoader.getController();
            controller.setup();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void gotoCart(Stage stage, String prevScreenType, String prevScreenIdentifier) {
        try {
            GlobalData.getInstance().addToHistory(prevScreenType, prevScreenIdentifier);

            FXMLLoader fxmlLoader = new FXMLLoader(ShopApp.class.getResource("Cart.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);

            CartController controller = fxmlLoader.getController();
            controller.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void gotoCart(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ShopApp.class.getResource("Cart.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);

            CartController controller = fxmlLoader.getController();
            controller.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
