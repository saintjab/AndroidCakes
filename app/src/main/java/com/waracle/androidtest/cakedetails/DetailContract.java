
package com.waracle.androidtest.cakedetails;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface DetailContract {

    interface View {
        void setProgressIndicator(boolean active);

        void showTitle(String title);

        void showDescription(String description);

        void loadImage(String imageURL);
    }
}
