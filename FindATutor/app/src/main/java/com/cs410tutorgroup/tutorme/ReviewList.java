package com.cs410tutorgroup.tutorme;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cs410tutorgroup.findatutor.R;

import org.json.JSONArray;
import org.json.JSONException;

public class ReviewList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        new GetReviewsTask().execute(new ApiConnector());
    }

    private void fillReviewList(JSONArray reviews) throws JSONException
    {
        LinearLayout l = (LinearLayout)findViewById(R.id.review_layout);

        for(int i = 0; i < reviews.length(); i++)
        {
            TextView tv = new TextView(getBaseContext());
            tv.setText(reviews.getJSONObject(i).getString("review_text"));

            l.addView(tv);
        }
    }

    private class GetReviewsTask extends AsyncTask<ApiConnector, Long, JSONArray>
    {
        protected JSONArray doInBackground(ApiConnector... params)
        {
            try
            {
                return params[0].GetReviews();
            }
            catch(Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(JSONArray results)
        {
            try
            {
                fillReviewList(results);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
