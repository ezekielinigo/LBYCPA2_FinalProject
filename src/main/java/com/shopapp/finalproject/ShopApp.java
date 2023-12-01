package com.shopapp.finalproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * DATASTRUCTURES:
 * GRAPH - used to store the relationships between sellers (initializeData())
 * STACK - used to store the history of visited pages
 * QUEUE - used to store the relevant tags
 * LIST - used all throughout the project for general storage use
 * **/

public class ShopApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ShopApp.class.getResource("HomeScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Chan Store");
        stage.setScene(scene);
        stage.show();
        HomeScreenController controller = fxmlLoader.getController();
        controller.setup();
    }

    public static void main(String[] args) {
        GlobalData g = GlobalData.getInstance();
        g.initializeData();
        launch();
    }

}