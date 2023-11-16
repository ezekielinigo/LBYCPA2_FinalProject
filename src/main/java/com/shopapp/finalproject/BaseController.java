package com.shopapp.finalproject;

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
    protected void gotoProductDetail(Product product) {
        // insert code here
    }

    protected void gotoSellerDetail(Seller seller) {
        // insert code here
    }

    /**
     * this method switches to the screen that displays the previous page viewed
     */
    protected void gotoPrevious() {
        // TBA di pa tapos
        GlobalData g = GlobalData.getInstance();
        Product previousProduct = g.getHistoryStack().pop();
        gotoProductDetail(previousProduct);
    }

    /**
     * this method switches to the screen that displays the full history of visited pages
     */
    protected void gotoHistory() {

    }


}
