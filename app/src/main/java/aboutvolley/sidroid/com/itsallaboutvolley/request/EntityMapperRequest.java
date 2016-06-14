package aboutvolley.sidroid.com.itsallaboutvolley.request;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by siddheshs2 on 30-11-2015.
 */
public abstract class EntityMapperRequest extends Request {

    private static final String PROTOCOL_CHARSET = "utf-8";
    private final Response.Listener mResponseListener;
    private String mContentType = "application/json";
    private int mMethod;
    private int ttl = 2 * 60 * 1000;//2 min
    private int softTtl = 60 * 1000;//1 min

    public EntityMapperRequest(int method, String url, Response.ErrorListener listener, Response.Listener responseListener) {
        super(method, url ,listener);
        mResponseListener = responseListener;
        mMethod = method;
        setShouldCache(false);
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        if (getMapperClass() == null)
            throw new IllegalStateException("MapperClass can not be null; set mapper class in getMapperClass method.");
        String resp = new String(response.data);
        GsonBuilder builder = new GsonBuilder();
        if (registerCustomDeserializer(builder) != null)
            builder = registerCustomDeserializer(builder);
        Gson gson = builder.create();
        Object mappedResponse = gson.fromJson(resp, getMapperClass());
        Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);
        entry.ttl = System.currentTimeMillis() + ttl;
        entry.softTtl = System.currentTimeMillis() + softTtl;
        return Response.success(mappedResponse, entry);
    }

    @Override
    protected void deliverResponse(Object response) {
        mResponseListener.onResponse(response);
    }

    @Override
    public int compareTo(Object another) {
        return 0;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (getCustomHeaders() != null) {
            return getCustomHeaders();
        }
        return super.getHeaders();
    }

    @Override
    public String getBodyContentType() {
        return mContentType;
    }

    @Override
    public Priority getPriority() {
        return Priority.IMMEDIATE;
    }

    @Override
    public byte[] getBody() {
        String requestBody = null;
        if (getRequestBody() != null)
            requestBody = getRequestBody().toString();
        try {
            return requestBody == null ? null : requestBody.getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                    requestBody, PROTOCOL_CHARSET);
            return null;
        }
    }

    public GsonBuilder registerCustomDeserializer(GsonBuilder builder){
        return null;
    }

    public void setRetriesAndTimeOut(int noOfRetries, int requestTimeOutMillis){
        RetryPolicy policy = new DefaultRetryPolicy(requestTimeOutMillis, noOfRetries, 1f);
        setRetryPolicy(policy);
    }

    public void enableResponseCaching(boolean enableResponseCache){
        setShouldCache(enableResponseCache);
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public void setSoftTtl(int softTtl) {
        this.softTtl = softTtl;
    }

    public Map<String, String> getCustomHeaders(){
        return new HashMap<String,String>();
    }

    public void setContentType(String mContentType) {
        this.mContentType = mContentType;
    }

    public abstract Class<?> getMapperClass();

    public abstract JSONObject getRequestBody();

    public abstract String getTag();
}
