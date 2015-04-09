package com.cs410tutorgroup.findatutor;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;


public class TutorReview extends Activity
{
    //Data
    private int tutorID;
    private int freelanceID;
    private String tutorName;
    private String reviewText;
    private float stars;

    private TextView pageHeader;
    private TextView charCounter;
    private TextView tutorNameField;
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
        tutorName = intent.getStringExtra("tutor_name");

        this.charCounter = (TextView) this.findViewById(R.id.charCount);
        this.reviewTextField = (EditText) this.findViewById(R.id.reviewText);
        this.tutorNameField = (TextView) this.findViewById(R.id.tutorName);
        this.pageHeader = (TextView) this.findViewById(R.id.tutorNameHeader);
        this.ratingStars = (RatingBar) this.findViewById(R.id.ratingStars);

        tutorNameField.setText(tutorNameField.getText() + " " + tutorName);
        pageHeader.setText(tutorName);

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
            }
            else
            {
                showErrorDialog(R.string.review_fail);
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
