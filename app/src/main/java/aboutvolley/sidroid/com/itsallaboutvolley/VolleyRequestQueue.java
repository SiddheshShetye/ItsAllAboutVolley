package aboutvolley.sidroid.com.itsallaboutvolley;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;

/**
 * Created by siddheshs2 on 11-01-2016.
 */
public class VolleyRequestQueue {
    private static VolleyRequestQueue sVolleyQueue;
    private RequestQueue mRequestQueue;
    private Cache mCache;
    private ImageLoader mImageLoader;

    public VolleyRequestQueue(Context context){
        if (mCache == null)
            mCache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap
        if (mRequestQueue == null) {
            /**
             * 5 is thread pool size. default value for threadpool of RequestQueue is 4
             */
            mRequestQueue = new RequestQueue(mCache, new BasicNetwork(new HurlStack()), 5);
            mRequestQueue.start();
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
    }


    public static synchronized VolleyRequestQueue getInstance(Context context){
        if (sVolleyQueue == null)
            sVolleyQueue = new VolleyRequestQueue(context);
        return sVolleyQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        mRequestQueue.add(req);
    }

    public void cancelAll(String tag){
        mRequestQueue.cancelAll(tag);
    }

    public void cancelAll(RequestQueue.RequestFilter filter){
        mRequestQueue.cancelAll(filter);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
