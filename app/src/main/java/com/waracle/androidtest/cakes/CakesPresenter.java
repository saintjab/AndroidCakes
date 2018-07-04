
package com.waracle.androidtest.cakes;

import android.support.annotation.NonNull;
import com.waracle.androidtest.Cake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.google.api.client.repackaged.com.google.common.base.Preconditions.checkNotNull;

public class CakesPresenter implements CakesContract.UserActionsListener, DownloadCakesInterface {

    private final CakesContract.View mCakesView;
    private DownloadCakes downloadCakes;
    private List<Cake> cakeList = new ArrayList<>();

    private final String jsonURL = "https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/" +
            "raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json";

    public CakesPresenter(@NonNull CakesContract.View cakeView) {
        mCakesView = checkNotNull(cakeView, "notesView cannot be null!");

        loadCakes(jsonURL);
    }

    public void loadCakes(String jsonURL){
        mCakesView.setProgressIndicator(true);
        downloadCakes = new DownloadCakes();

        downloadCakes.cakeDownloadListener = this;
        downloadCakes.execute(jsonURL);
    }

    @Override
    public void onSuccess(JSONArray jsonArray) {
        for (int x = 0; x <= jsonArray.length(); x++) {
            try {
                JSONObject object = jsonArray.getJSONObject(x);
                String title = object.getString("title");
                String details = object.getString("desc");
                String image = object.getString("image");

                Cake cake = new Cake(title, details, image);
                cakeList.add(cake);
            } catch (JSONException cc) {

            }
        }
        mCakesView.showCakes(cakeList);
    }

    @Override
    public void onError(String error) {
        mCakesView.setProgressIndicator(false);
        mCakesView.showMessage("Error downloading Cake List");
    }

    @Override
    public void reloadCakes(boolean forceUpdate) {
        loadCakes(jsonURL);
    }

    @Override
    public void showCakeDetails(@NonNull Cake selectedCake) {
        checkNotNull(selectedCake, "selectedCake cannot be null!");
        mCakesView.showCakeDetailUi(selectedCake);
    }
}
