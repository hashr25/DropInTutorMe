package com.cs410tutorgroup.tutorme;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cs410tutorgroup.findatutor.R;

import org.json.JSONArray;
import org.json.JSONException;

public class ReviewList extends Activity
{
    private int tutorID;
    private int freelanceID;
    private String tutorName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        Intent intent = getIntent();

        tutorID = intent.getExtras().getInt("tutor_id");
        freelanceID = intent.getExtras().getInt("freelance_id");
        tutorName = intent.getExtras().getString("tutor_name");

        Log.v("BeforeReviewTask", "Right before the 'getReviewTask' begins");

        new GetReviewsTask().execute(new ApiConnector());

        TextView tv = (TextView) findViewById(R.id.review_list_header);
        tv.setText(tutorName);

        //Just to pull the blank message down.
        try{ fillReviewList(new JSONArray()); }
        catch(Exception e) { e.printStackTrace(); }
    }

    public void onAddReviewButtonClicked(View view)
    {
        Intent addReviewIntent = new Intent(this, AddReview.class);

        addReviewIntent.putExtra("tutor_id", tutorID);
        addReviewIntent.putExtra("freelance_id", freelanceID);
        addReviewIntent.putExtra("tutor_name", tutorName);

        startActivity(addReviewIntent);
    }

    private void fillReviewList(JSONArray reviews) throws JSONException
    {
        Log.d("FillingList", "Attempting to fill list with JSONArray");
        TextView tv = (TextView) findViewById(R.id.no_review_text);

        if( reviews.length() == 0 )
        {//No Reviews
            tv.setVisibility(View.VISIBLE);
            tv.setText("Sorry, there are no reviews for this tutor. If you have received help from this tutor, leave a review!");
            LinearLayout l = (LinearLayout) findViewById(R.id.review_list);
            l.addView(tv);
        }
        else
        {
            tv.setVisibility(View.GONE);
            for(int i = 0; i < reviews.length(); i++)
            {
                Bundle args = new Bundle();
                args.putString("review_text",reviews.getJSONObject(i).getString("review_text"));
                args.putDouble("review_rating", reviews.getJSONObject(i).getDouble("stars"));
                args.putInt("review_id", reviews.getJSONObject(i).getInt("review_id"));
                args.putInt("num_of_reports", reviews.getJSONObject(i).getInt("num_of_reports"));

                TutorReviewFragment review = new TutorReviewFragment();
                review.setArguments(args);

                getFragmentManager().beginTransaction().add(R.id.review_list, review).commit();
            }
        }
    }

    private class GetReviewsTask extends AsyncTask<ApiConnector, Long, JSONArray>
    {
        protected JSONArray doInBackground(ApiConnector... params)
        {
            Log.d("GetReviewTask", "Starting 'GetReviewTask' ");
            try
            {
                return params[0].GetReviews(tutorID, freelanceID);
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
