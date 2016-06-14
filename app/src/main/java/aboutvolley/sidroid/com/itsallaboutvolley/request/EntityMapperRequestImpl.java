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
    private String mValue1;
    private String mValue2;
    private String mTag;
    private int mMethod;

    public EntityMapperRequestImpl(int method, String url, JSONObject requestBody, Response.ErrorListener listener, Response.Listener responseListener, String tag) {
        super(method, url, listener, responseListener);
        mRequestBody = requestBody;
        mTag = tag;
        mMethod = method;
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

    public void setAuthHeaderCredentials(String value1,String value2){
        if (TextUtils.isEmpty(value1) || TextUtils.isEmpty(value2)) {
            needAuthorisationHeader = false;
            return;
        }
        this.mValue1 = value1;
        this.mValue2 = value2;
        needAuthorisationHeader = true;
    }

    @Override
    public Map<String, String> getCustomHeaders() {
        HashMap<String,String> headers;
        /**
         * TODO:
         * uncomment below section after writing logic for getAuthorizationHeadersMap
         * so you can provide your own authentication headers
         */
//        if (needAuthorisationHeader){
//            headers = getAuthorizationHeadersMap(mValue1, mValue2);
//            needAuthorisationHeader = false;
//        }else
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
