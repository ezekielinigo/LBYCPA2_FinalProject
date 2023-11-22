package com.shopapp.finalproject;

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
    protected void gotoProductDetail(Product product, Stage stage) {
        try {
            // add previous scene to history
            GlobalData.getInstance().addToHistory(stage.getScene());

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

    protected void gotoSellerDetail(Seller seller, Stage stage) {
        // insert code here
    }

    /**
     * this method switches to the screen that displays the previous page viewed
     */
    protected void gotoPrevious(Stage stage) {
        Scene newScene = GlobalData.getInstance().getPreviousScene();
        stage.setScene(newScene);

    }

    /**
     * this method switches to the screen that displays the full history of visited pages
     */
    protected void gotoHistory() {

    }


}
