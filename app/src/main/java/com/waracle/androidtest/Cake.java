package com.waracle.androidtest;

/**
 * Created by Jonas Boateng on 02/07/2018.
 */

public class Cake {
    private final String title;
    private final String details;
    private final String imageURL;

    public Cake(String title, String details, String imageURL){
        this.title = title;
        this.imageURL = imageURL;
        this.details = details;
    }

    public String getDetails() {
        return details;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getTitle() {
        return title;
    }
}
