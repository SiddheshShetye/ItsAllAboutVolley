package aboutvolley.sidroid.com.itsallaboutvolley;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
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

import aboutvolley.sidroid.com.itsallaboutvolley.fragments.HitRequestFragment;
import aboutvolley.sidroid.com.itsallaboutvolley.model.EntityMapperResponseModel;
import aboutvolley.sidroid.com.itsallaboutvolley.request.CachingStringRequest;
import aboutvolley.sidroid.com.itsallaboutvolley.request.EntityMapperRequestImpl;
import aboutvolley.sidroid.com.itsallaboutvolley.retrypolicy.CustomRetryPolicy;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        VolleyRequestQueue.init(getApplicationContext());
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFrame, HitRequestFragment.newInstance()).commit();
    }
}
