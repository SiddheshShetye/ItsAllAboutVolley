package aboutvolley.sidroid.com.itsallaboutvolley.retrypolicy;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;

/**
 * Created by siddheshs2 on 11-01-2016.
 */
public class CustomRetryPolicy implements RetryPolicy {

    private int mMaxRetries = 0;
    private float mBackOffMultiplier = 1f;
    private int mTimeOut = 5000;
    private int mCurrentRetryCount;

    public CustomRetryPolicy(int timeOutMillies,int numOfRetries,float backOffMultiplier){
        mMaxRetries = numOfRetries;
        mBackOffMultiplier = backOffMultiplier;
        mTimeOut = timeOutMillies;
    }

    @Override
    public int getCurrentTimeout() {
        return mTimeOut;
    }

    @Override
    public int getCurrentRetryCount() {
        return mCurrentRetryCount;
    }

    @Override
    public void retry(VolleyError error) throws VolleyError {
        mTimeOut += (mTimeOut * mBackOffMultiplier);
        if (!hasAttemptRemaining()) {
            throw error;
        }
        mCurrentRetryCount++;
    }

    protected boolean hasAttemptRemaining() {
        return mCurrentRetryCount < mMaxRetries;
    }
}
