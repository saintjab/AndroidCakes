package com.waracle.androidtest.cakes;

import org.json.JSONArray;

/**
 * Created by Jonas Boateng on 04/07/2018.
 */

public interface DownloadCakesInterface {
    void onSuccess(JSONArray jsonArray);
    void onError(String error);
}
