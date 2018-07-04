package com.waracle.androidtest.cache;

import android.content.Context;

import java.io.File;

/**
 * Created by Jonas Boateng on 04/07/2018.
 */

public class LocalDiskCache {
    private File cacheDir;

    public LocalDiskCache(Context context){

        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
        {
            cacheDir = new File(
                    android.os.Environment.getExternalStorageDirectory(),"LazyList");
        }
        else
        {
            cacheDir = context.getCacheDir();
        }

        if(!cacheDir.exists()){
            cacheDir.mkdirs();
        }
    }

    public File getFile(String url){
        String filename = String.valueOf(url.hashCode());
        File f = new File(cacheDir, filename);
        return f;
    }

    public void clear(){
        File[] files = cacheDir.listFiles();
        if(files == null)
            return;
        for (File f : files)
            f.delete();
    }
}
