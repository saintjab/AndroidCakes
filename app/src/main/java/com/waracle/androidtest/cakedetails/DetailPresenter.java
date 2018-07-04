
package com.waracle.androidtest.cakedetails;

import android.support.annotation.NonNull;

import static com.google.api.client.repackaged.com.google.common.base.Preconditions.checkNotNull;

public class DetailPresenter implements DetailContract{

    private final DetailContract.View mCakesDetailView;

    public DetailPresenter(@NonNull DetailContract.View cakeDetailView) {
        mCakesDetailView = checkNotNull(cakeDetailView, "cakeDetailView cannot be null!");
    }

    public void showCakeDetails(String title, String description) {
        if (title != null && title.isEmpty()) {
            mCakesDetailView.showTitle("No title");;
        } else {
            mCakesDetailView.showTitle(title);
        }

        if (description != null && description.isEmpty()) {
            mCakesDetailView.showDescription("No description");
        } else {
            mCakesDetailView.showDescription(description);
        }
    }
}
