package com.cs410tutorgroup.tutorme;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cs410tutorgroup.findatutor.R;

/**
 * Created by hashr25 on 5/4/15.
 */
public class TutorReviewFragment extends Fragment
{
    private String reviewText;
    private float reviewRating;
    private int reviewID;
    private int reviewReports;

    private Button reportButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        RelativeLayout l = (RelativeLayout) inflater.inflate(R.layout.tutor_review_fragment, container, false);

        reviewText = getArguments().getString("review_text");
        reviewRating = Float.valueOf(String.valueOf(getArguments().getDouble("review_rating")));
        reviewID = getArguments().getInt("review_id");
        reviewReports = getArguments().getInt("num_of_reports");

        TextView tv = (TextView) l.findViewById(R.id.TextView_review_text);
        tv.setText(reviewText);

        RatingBar rb = (RatingBar) l.findViewById(R.id.RatingBar_review_rating);
        rb.setRating(reviewRating);
        rb.setIsIndicator(true);
        rb.setFocusable(false);

        reportButton = (Button) l.findViewById(R.id.report_review_button);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReportButtonPressed();
            }
        });

        return l;
    }

    public void onReportButtonPressed()
    {
        reviewReports++;
        reportButton.setEnabled(false);
        new ReportReviewTask().execute(new ApiConnector());
        showErrorDialog(R.string.review_reported);
    }

    void showErrorDialog(int messageId)
    {
        DialogFragment newFragment = Globals.ErrorDialogFragment.newInstance(messageId);
        newFragment.show(getFragmentManager(), "dialog");
    }

    private class ReportReviewTask extends AsyncTask<ApiConnector, Long, Boolean>
    {
        @Override
        protected Boolean doInBackground(ApiConnector... params)
        {
            try
            {
                return params[0].ReportReview(reviewID, reviewReports);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return false;
        }
    }
}
