package com.cs410tutorgroup.tutorme;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cs410tutorgroup.findatutor.R;


public class AddReview extends Activity
{
    //Data
    private int tutorID;
    private int freelanceID;
    private String reviewText;
    private float stars;

    private TextView charCounter;
    private EditText reviewTextField;
    private RatingBar ratingStars;



    //Methods
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_review);

        Intent intent = getIntent();
        tutorID = intent.getExtras().getInt("tutor_id");
        freelanceID = intent.getExtras().getInt("freelance_id");
        String tutorName = intent.getStringExtra("tutor_name");

        this.charCounter = (TextView) this.findViewById(R.id.charCount);
        this.reviewTextField = (EditText) this.findViewById(R.id.reviewText);
        TextView tutorNameField = (TextView) this.findViewById(R.id.tutorName);
        TextView pageHeader = (TextView) this.findViewById(R.id.tutorNameHeader);
        this.ratingStars = (RatingBar) this.findViewById(R.id.ratingStars);

        tutorNameField.setText(tutorNameField.getText() + " " + tutorName);
        pageHeader.setText(tutorName);

        //Allows tracking of characters
        final TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                reviewText = reviewTextField.toString();
                charCounter.setText(String.valueOf(255 - s.length()) + " character(s) left");
            }
        };

        reviewTextField.addTextChangedListener(textWatcher);
    }

    private class AddTutorReviewTask extends AsyncTask<ApiConnector,Long,Boolean>
    {
        @Override
        protected Boolean doInBackground(ApiConnector... params)
        {
            try
            {
                return params[0].AddReview(tutorID, freelanceID, reviewText, stars);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean success)
        {
            if(success)
            {
                showErrorDialog(R.string.review_submitted);
                finish();
                finish();
            }
            else
            {
                showErrorDialog(R.string.review_fail);
                finish();
                finish();
            }
        }
    }

    public void onSubmitButtonClicked(View view)
    {
        if(reviewTextField.getText().toString().length() > 0)
        {
            reviewText = reviewTextField.getText().toString();
            stars = ratingStars.getRating();

            new AddTutorReviewTask().execute(new ApiConnector());
        }
        else
        {
            showErrorDialog(R.string.enter_text);
        }
    }

    void showErrorDialog(int messageId)
    {
        DialogFragment newFragment = Globals.ErrorDialogFragment.newInstance(messageId);
        newFragment.show(getFragmentManager(), "dialog");
    }

}
