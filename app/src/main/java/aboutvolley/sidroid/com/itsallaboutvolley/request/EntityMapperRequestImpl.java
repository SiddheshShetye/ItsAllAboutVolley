package aboutvolley.sidroid.com.itsallaboutvolley.request;

import android.text.TextUtils;

import com.android.volley.Response;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import aboutvolley.sidroid.com.itsallaboutvolley.model.CommonResponseModel;

/**
 * Created by siddheshs2 on 01-12-2015.
 */
public class EntityMapperRequestImpl extends EntityMapperRequest {

    private JSONObject mRequestBody;
    private boolean needAuthorisationHeader;
    private String mTag;
    private int mMethod;
    private Map<String,String> mAuthHeader;

    public EntityMapperRequestImpl(int method, String url, JSONObject requestBody, Response.ErrorListener listener, Response.Listener responseListener, String tag) {
        super(method, url, listener, responseListener);
        mRequestBody = requestBody;
        mTag = tag;
        mMethod = method;
        mAuthHeader = new HashMap<>();
    }


    public EntityMapperRequestImpl(int method, String url, Object requestModel, Response.ErrorListener listener, Response.Listener responseListener, String tag) {
        super(method, url, listener, responseListener);
        try {
            if (requestModel != null)
                mRequestBody = new JSONObject(new Gson().toJson(requestModel));
            mTag = tag;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mMethod = method;
    }

    public void setAuthHeaderCredentials(Map<String,String> authHeaders){
        if (authHeaders == null) {
            needAuthorisationHeader = false;
            return;
        }
        mAuthHeader = authHeaders;
        needAuthorisationHeader = true;
    }

    @Override
    public Map<String, String> getCustomHeaders() {
        HashMap<String,String> headers;

        if (needAuthorisationHeader){
            headers = (HashMap<String, String>) mAuthHeader;
            needAuthorisationHeader = false;
        }else
            headers = (HashMap<String, String>) super.getCustomHeaders();
        if (mMethod == Method.GET)
            headers.put("Content-Type", getBodyContentType());
        return headers;
    }



    @Override
    public Class<?> getMapperClass() {
        return CommonResponseModel.class;
    }

    @Override
    public JSONObject getRequestBody() {
        return mRequestBody;
    }

    @Override
    public String getTag() {
        return mTag;
    }
}
