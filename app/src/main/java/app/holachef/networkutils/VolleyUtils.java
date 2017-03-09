package app.holachef.networkutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.util.Iterator;
import java.util.Map;

public class VolleyUtils {
    private static final String TAG = "VolleyUtils";
    private static VolleyUtils sInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private VolleyUtils(final Context context) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });


    }

    public static synchronized VolleyUtils getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new VolleyUtils(context);
        }
        return sInstance;
    }

    public <T> void addToRequestQueue(final Request<T> req) {
        mRequestQueue.add(req);
        mRequestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
            }
        });
    }
}
