package aboutvolley.sidroid.com.itsallaboutvolley.fragments;


import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import aboutvolley.sidroid.com.itsallaboutvolley.R;
import aboutvolley.sidroid.com.itsallaboutvolley.VolleyRequestQueue;
import aboutvolley.sidroid.com.itsallaboutvolley.model.EntityMapperResponseModel;
import aboutvolley.sidroid.com.itsallaboutvolley.request.CachingStringRequest;
import aboutvolley.sidroid.com.itsallaboutvolley.request.EntityMapperRequestImpl;
import aboutvolley.sidroid.com.itsallaboutvolley.retrypolicy.CustomRetryPolicy;

/**
 * A simple {@link Fragment} subclass.
 */
public class HitRequestFragment extends Fragment implements View.OnClickListener{

    private static final String JSON_OBJ_REQ_URL = "http://private-0ee9a-login155.apiary-mock.com/questions";
    private static final String JSON_ARR_REQ_URL = "http://private-0ee9a-login155.apiary-mock.com/questionss";
    private static final String STRING_REQ_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA";
    private static final String IMAGE_REQ_URL = "http://i.imgur.com/CqmBjo5.jpg";
    private static final int RETRY_COUNT = 2;
    private static final int TIMEOUT = 5000;
    private static final float BACKOFF_MULTIPLIER = 1f;
    private static final String REQ_JSON_OBJ = "{\n" +
            "    \"question\": \"Favourite programming language?\",\n" +
            "    \"choices\": [\n" +
            "        \"Swift\",\n" +
            "        \"Python\",\n" +
            "        \"Objective-C\",\n" +
            "        \"Ruby\"\n" +
            "    ]\n" +
            "}";
    private Button btnStringReq;
    private Button btnJsonReq;
    private Button btnJArrayReq;
    private Button btnImageReq;
    private Button btnCustomReq;
    private Button btnGoToNetworkImage;
    private TextView lblResp;
    private ProgressDialog mProgressDialog;

    public static HitRequestFragment newInstance() {
        return new HitRequestFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_hit_request, container, false);
        initComponents(layout);
        setListener(layout);
        return layout;
    }

    private void initComponents(View layout) {
        btnStringReq = (Button) layout.findViewById(R.id.btnStringReq);
        btnJsonReq = (Button) layout.findViewById(R.id.btnJsonReq);
        btnJArrayReq = (Button) layout.findViewById(R.id.btnJArratReq);
        btnImageReq = (Button) layout.findViewById(R.id.btnImageReq);
        btnCustomReq = (Button) layout.findViewById(R.id.btnCustomReq);
        btnGoToNetworkImage = (Button) layout.findViewById(R.id.btnNetworkImageView);
        lblResp = (TextView) layout.findViewById(R.id.lblResp);
        lblResp.setMovementMethod(new ScrollingMovementMethod());
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Loading...");
    }

    private void setListener(View layout) {
        btnStringReq.setOnClickListener(this);
        btnJsonReq.setOnClickListener(this);
        btnJArrayReq.setOnClickListener(this);
        btnImageReq.setOnClickListener(this);
        btnCustomReq.setOnClickListener(this);
        btnGoToNetworkImage.setOnClickListener(this);
    }

    private void hitStringReq() {
        CachingStringRequest stringReq = new CachingStringRequest(Request.Method.GET, STRING_REQ_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                lblResp.setText(response);
                mProgressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                lblResp.setText(error.getMessage());
                mProgressDialog.dismiss();
            }
        });
        stringReq.setRetryPolicy(new CustomRetryPolicy(TIMEOUT, RETRY_COUNT, BACKOFF_MULTIPLIER));
        stringReq.setShouldCache(true); //enable disable caching, default is true
        stringReq.setTtl(60 * 1000);//1 min
        stringReq.setSoftTtl(30 * 1000);//30sec
        VolleyRequestQueue.getInstance(getActivity()).addToRequestQueue(stringReq);
        mProgressDialog.show();
    }

    private void hitJsonObjReq() {
        JSONObject reqBody = null;
        try {
            reqBody = new JSONObject(REQ_JSON_OBJ);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(JSON_OBJ_REQ_URL, reqBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                lblResp.setText(response.toString());
                mProgressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                lblResp.setText(error.getMessage());
                mProgressDialog.dismiss();
            }
        }) {
            /**
             * Default priority for request is Priority.NORMAL you can override
             * getPriority and specify your own request priority
             */
            @Override
            public Priority getPriority() {
                super.getPriority();
                return Priority.IMMEDIATE;
            }
        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT, RETRY_COUNT, BACKOFF_MULTIPLIER));
        VolleyRequestQueue.getInstance(getActivity()).addToRequestQueue(jsonObjReq);
        mProgressDialog.show();
    }

    private void hitJsonArrReq() {
        JSONArray reqBody = null;
        try {
            reqBody = new JSONArray("[" + REQ_JSON_OBJ + "]");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.POST, JSON_ARR_REQ_URL, reqBody, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                lblResp.setText(response.toString());
                mProgressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                lblResp.setText(error.getMessage());
                mProgressDialog.dismiss();
            }
        });
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT, RETRY_COUNT, BACKOFF_MULTIPLIER));
        VolleyRequestQueue.getInstance(getActivity()).addToRequestQueue(jsonObjReq);
        mProgressDialog.show();
    }

    private void hitImageReq() {
        ImageRequest stringReq = new ImageRequest(IMAGE_REQ_URL, new Response.Listener<Bitmap>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Bitmap response) {
//                lblResp.setText(response);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                    lblResp.setBackground(new BitmapDrawable(response));
                else
                    lblResp.setBackground(new BitmapDrawable(getResources(), response));
                mProgressDialog.dismiss();
            }
        }, 200, 200, ImageView.ScaleType.FIT_CENTER, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                lblResp.setText(error.getMessage());
                mProgressDialog.dismiss();
            }
        });
        stringReq.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT, RETRY_COUNT, BACKOFF_MULTIPLIER));
        VolleyRequestQueue.getInstance(getActivity()).addToRequestQueue(stringReq);
        mProgressDialog.show();
    }

    private void hitCustomReq() {
        JSONObject reqBody = null;
        try {
            reqBody = new JSONObject(REQ_JSON_OBJ);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        EntityMapperRequestImpl customReq = new EntityMapperRequestImpl(Request.Method.POST, JSON_OBJ_REQ_URL, reqBody, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                lblResp.setText(error.getMessage());
                mProgressDialog.dismiss();
            }
        }, new Response.Listener<EntityMapperResponseModel>() {
            @Override
            public void onResponse(EntityMapperResponseModel response) {
                if (response != null)
                    lblResp.setText(response.getQuestion());
                mProgressDialog.dismiss();
            }
        }, "CustomReqTag") {
            @Override
            public Class<?> getMapperClass() {
                return EntityMapperResponseModel.class;
            }
        };
        customReq.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT, RETRY_COUNT, BACKOFF_MULTIPLIER));
        VolleyRequestQueue.getInstance(getActivity()).addToRequestQueue(customReq);
        mProgressDialog.show();
    }

    @Override
    public void onClick(View v) {
        lblResp.setBackgroundColor(Color.WHITE);
        lblResp.setText("");
        switch (v.getId()) {
            case R.id.btnStringReq:
                hitStringReq();
                break;
            case R.id.btnJsonReq:
                hitJsonObjReq();
                break;
            case R.id.btnJArratReq:
                hitJsonArrReq();
                break;
            case R.id.btnImageReq:
                hitImageReq();
                break;
            case R.id.btnCustomReq:
                hitCustomReq();
                break;
            case R.id.btnNetworkImageView:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentFrame,NIVListFragment.newInstance())
                        .addToBackStack("NIVList")
                        .commit();
                break;
        }
    }

}
