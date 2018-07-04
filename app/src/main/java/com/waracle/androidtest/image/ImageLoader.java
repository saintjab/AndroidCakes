package com.waracle.androidtest.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

import com.waracle.androidtest.util.TheInputStream;
import com.waracle.androidtest.cache.LocalDiskCache;
import com.waracle.androidtest.cache.MemoryCache;
import com.waracle.androidtest.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Jonas Boateng on 04/07/2018.
 */

public class ImageLoader {

    private MemoryCache memoryCache = new MemoryCache();
    private LocalDiskCache fileCache;
    private final int stub_id = R.mipmap.loading;
    private ExecutorService executorService;
    private Handler handler = new Handler();

    private Map<ImageView, String> imageViews = Collections.synchronizedMap(
            new WeakHashMap<ImageView, String>());

    public ImageLoader(Context context){
        fileCache = new LocalDiskCache(context);
        executorService = Executors.newFixedThreadPool(5);
    }

    public void DisplayImage(String url, ImageView imageView)
    {
        imageViews.put(imageView, url);

        Bitmap bitmap = memoryCache.get(url);

        if(bitmap != null){
            imageView.setImageBitmap(bitmap);
        }
        else
        {
            queuePhoto(url, imageView);
            imageView.setImageResource(stub_id);
        }
    }

    private void queuePhoto(String url, ImageView imageView)
    {
        PhotoToLoad p = new PhotoToLoad(url, imageView);

        // pass PhotoToLoad object to PhotosLoader runnable class
        // and submit PhotosLoader runnable to executers to run runnable
        // Submits a PhotosLoader runnable task for execution

        executorService.submit(new PhotosLoader(p));
    }

    private class PhotoToLoad
    {
        public String url;
        public ImageView imageView;

        private PhotoToLoad(String u, ImageView i){
            url = u;
            imageView = i;
        }
    }

    class PhotosLoader implements Runnable {
        PhotoToLoad photoToLoad;
        PhotosLoader(PhotoToLoad photoToLoad){
            this.photoToLoad = photoToLoad;
        }

        @Override
        public void run() {
            try{
                //Check if image already downloaded
                if(imageViewReused(photoToLoad))
                    return;

                Bitmap bmp = getBitmap(photoToLoad.url);
                memoryCache.put(photoToLoad.url, bmp);

                if(imageViewReused(photoToLoad))
                    return;

                DisplayBitmap bd = new DisplayBitmap(bmp, photoToLoad);

                handler.post(bd);

            }catch(Throwable th){
                th.printStackTrace();
            }
        }
    }

    private Bitmap getBitmap(String url)
    {
        File f = fileCache.getFile(url);
        Bitmap b = decodeFile(f);
        if(b != null)
            return b;

        // Now download image file from web
        try {
            Bitmap bitmap = null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(new TheInputStream(is));

            conn.disconnect();
            return bitmap;

        } catch (Throwable ex){
            ex.printStackTrace();
            if(ex instanceof OutOfMemoryError)
                memoryCache.clear();
            return null;
        }
    }

    //Decodes image and scales it to reduce memory consumption
    private Bitmap decodeFile(File f){
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream stream1 = new FileInputStream(f);
            BitmapFactory.decodeStream(stream1,null,o);
            stream1.close();

            final int REQUIRED_SIZE = 100;

            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;

            while(true){
                if(width_tmp/2 < REQUIRED_SIZE || height_tmp/2 < REQUIRED_SIZE)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale*=2;
            }

            //decode with current scale values
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            FileInputStream stream2 = new FileInputStream(f);
            Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, o2);
            stream2.close();
            return bitmap;

        } catch (FileNotFoundException e) {
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    boolean imageViewReused(PhotoToLoad photoToLoad){
        String tag = imageViews.get(photoToLoad.imageView);
        //Check url is already exist in imageViews MAP

        if(tag == null || !tag.equals(photoToLoad.url))
            return true;
        return false;
    }

    //Used to display bitmap in the UI thread
    class DisplayBitmap implements Runnable
    {
        Bitmap bitmap;
        PhotoToLoad photoToLoad;

        public DisplayBitmap(Bitmap b, PhotoToLoad p){
            bitmap = b;
            photoToLoad = p;
        }

        public void run()
        {
            if(imageViewReused(photoToLoad))
                return;

            if(bitmap != null) {
                photoToLoad.imageView.setImageBitmap(bitmap);
            }
            else {
                photoToLoad.imageView.setImageResource(stub_id);
            }
        }
    }
}
