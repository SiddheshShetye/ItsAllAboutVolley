package aboutvolley.sidroid.com.itsallaboutvolley.request;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by siddheshs2 on 12-01-2016.
 */
public class CachingStringRequest extends StringRequest {

    /**
     * This class is created just give you better idea and keep code clean
     * you can override parseNetworkResponse while creating StringRequest
     * object and do the following alteration there itself.
     */

    private int ttl = 2 * 60 * 1000; //2 min
    private int softTtl = 60 * 1000;//1 min

    public CachingStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public CachingStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this(Method.GET, url, listener, errorListener);
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public void setSoftTtl(int softTtl) {
        this.softTtl = softTtl;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        Response<String> resp = super.parseNetworkResponse(response);
        resp.cacheEntry.ttl = System.currentTimeMillis() + ttl;
        return resp;
    }
}
