package com.waracle.androidtest.cakes;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Jonas Boateng on 04/07/2018.
 */

public class DownloadCakes extends AsyncTask<String, Void, JSONArray> {
    DownloadCakesInterface cakeDownloadListener;

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    @Override
    protected void onPreExecute() {
        //Setup precondition to execute some task
    }

    @Override
    protected JSONArray doInBackground(String... params) {
        JSONArray array;
        try {
            InputStream is = new URL(params[0]).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            is.close();
            array = new JSONArray(jsonText);
        }
        catch (JSONException|IOException ee){
            cakeDownloadListener.onError(ee.getLocalizedMessage());
            return null;
        }
        return array;
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        cakeDownloadListener.onSuccess(jsonArray);
    }
}
