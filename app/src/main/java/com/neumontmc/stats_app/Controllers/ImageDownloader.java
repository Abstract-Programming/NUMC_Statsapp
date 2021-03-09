package com.neumontmc.stats_app.Controllers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.InputStream;

@Deprecated
class ImageDownloader extends AsyncTask<String, Void, Object> {
    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    final int cacheSize = maxMemory / 4;
    private LruCache<String, Bitmap> memoryCache = new LruCache<String, Bitmap>(cacheSize) {
        @Override
        protected int sizeOf(String key, Bitmap bitmap) {
            return bitmap.getByteCount() / 1024;
        }
    };
    /*
     * Example use:
     * ImageDownloader aname = new ImageDownloader(targetImageView);
     * aname.execute("https://api.neumontmc.com/images/bust/" + apic.get(0).getUsername() + ".png");
     * */

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
       if (getBitmapFromMemCache(key) == null)
            memoryCache.put(key, bitmap);
        //System.out.println(getBitmapFromMemCache(key));
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return memoryCache.get(key);
    }

    ImageView bmImage;
    String username;
    String url;

    /**
     * Constructor for a ImageDownloader task.
     *
     * @param bmImage Target ImageView to set the image to.
     */
    public ImageDownloader(ImageView bmImage, String user) {
        this.bmImage = bmImage;
        this.username = user;
        this.url = "https://api.neumontmc.com/images/bust/" + user + ".png";
    }

    /**
     * Check if an image is in the image cache, if its not:
     * Fetch a remote image, convert it to a BitMap, then set the target ImageView to that image.
     *
     * @param urls URL to fetch
     * @return image bitmap
     */
    protected Object doInBackground(String... urls) {
        if (getBitmapFromMemCache(username) == null) {
            //Log.i("Fetching image to cache:", username);
            final String urldisplay = url;
            try {
                final InputStream in = new java.net.URL(urldisplay).openStream();
                final Bitmap mIcon11 = BitmapFactory.decodeStream(in);
                in.close();
                addBitmapToMemoryCache(username, mIcon11);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
        }
        return username;
    }

    /**
     * After downloading a image, set the target ImageView to that image.
     *
     * @param result The bitmap image returned by doInBackground()
     */
    protected void onPostExecute(Object result) {
        bmImage.setImageBitmap(getBitmapFromMemCache((String) result));
    }


}
