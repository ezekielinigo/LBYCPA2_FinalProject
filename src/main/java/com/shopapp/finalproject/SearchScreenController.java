package com.shopapp.finalproject;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.IOException;

public class SearchScreenController extends BaseController {

    String searchQuery;

    @FXML
    private GridPane productGrid;

    @FXML
    private TextField searchBar;

    public void setup(String initialSearchQuery) {
        this.searchQuery = initialSearchQuery;
        performSearch();
    }

    @FunctionalInterface
    public interface QuadConsumer<T, U, V, W> {
        void accept(T t, U u, V v, W w);
    }

    private <T> void addThumbnail(String fxmlFile, T item, Stage stage, String prevScreenType, String prevScreenIdentifier,
                                  QuadConsumer<T, Stage, String, String> detailHandler) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/shopapp/finalproject/" + fxmlFile));
            AnchorPane thumbnailView = loader.load();
            ThumbnailViewController controller = loader.getController();

            if (item instanceof Product) {
                controller.setProduct((Product) item);
            } else if (item instanceof Seller) {
                controller.setSeller((Seller) item);
            }

            Point2D nextPosition = getNextAvailablePosition(productGrid);
            if (nextPosition.getX() != -1 && nextPosition.getY() != -1) {
                productGrid.add(thumbnailView, (int) nextPosition.getX(), (int) nextPosition.getY());
                thumbnailView.setOnMouseClicked(e -> detailHandler.accept(item, stage, prevScreenType, prevScreenIdentifier));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void performSearch() {
        GlobalData g = GlobalData.getInstance();
        g.setRelevantResults(searchQuery);

        Platform.runLater(() -> {
            // Clear existing items in the grid
            if (productGrid != null) {
                productGrid.getChildren().clear();
            }

            Stage stage = (Stage) productGrid.getScene().getWindow();
            String prevScreenType = "search";
            String prevScreenIdentifier = searchQuery;

            // Add new items based on the current search query
            for (Product product : g.getRelevantProducts()) {
                addThumbnail("ThumbnailView.fxml", product, stage, prevScreenType, prevScreenIdentifier,
                        (p, s, pT, pI) -> gotoProductDetail(p, s, pT, pI));
            }

            for (Seller seller : g.getRelevantSellers()) {
                addThumbnail("ThumbnailView.fxml", seller, stage, prevScreenType, prevScreenIdentifier,
                        (s, st, pT, pI) -> gotoSellerDetail(s, st, pT, pI));
            }
        });
    }

    @FXML
    void gotoCart() throws IOException {
        super.gotoCart((Stage) productGrid.getScene().getWindow(), "search", searchQuery);
    }

    @FXML
    void gotoSearchResults(MouseEvent event) {
        // Update the searchQuery before performing a new search
        searchQuery = searchBar.getText();
        performSearch();
    }


    private Point2D getNextAvailablePosition(GridPane gridPane) {
        int numColumns = gridPane.getColumnConstraints().size();
        int numRows = gridPane.getRowConstraints().size();
        boolean isOccupied = false;

        // Check existing rows and columns for an empty space
        for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
            for (int colIndex = 0; colIndex < numColumns; colIndex++) {
                isOccupied = false;
                for (Node child : gridPane.getChildren()) {
                    // GridPane.getRowIndex and getColumnIndex might return null, so we are checking for that
                    Integer childRowIndex = GridPane.getRowIndex(child);
                    Integer childColIndex = GridPane.getColumnIndex(child);
                    // Using == for Integer comparison is safe here because it's within the range of -128 to 127 due to Integer caching
                    if (childRowIndex != null && childRowIndex == rowIndex &&
                            childColIndex != null && childColIndex == colIndex) {
                        isOccupied = true;
                        break;
                    }
                }
                if (!isOccupied) {
                    return new Point2D(colIndex, rowIndex);
                }
            }
        }

        // No empty space found, so add a new row
        gridPane.getRowConstraints().add(new RowConstraints()); // Add constraints if you have specific needs
        return new Point2D(0, numRows); // Return the first column index of the new row
    }

    @FXML
    void gotoPreviousScreen() {
        super.gotoPrevious((Stage) productGrid.getScene().getWindow());
    }

    @FXML
    void gotoHistoryScreen() {
        super.gotoHistory((Stage) productGrid.getScene().getWindow(), "home", null);
    }


}
