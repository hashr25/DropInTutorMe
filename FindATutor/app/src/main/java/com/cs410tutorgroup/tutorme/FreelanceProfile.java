package com.cs410tutorgroup.tutorme;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs410tutorgroup.findatutor.R;

public class FreelanceProfile extends Activity {
    //Attributes
    private FreelanceTutor tutorDisplayed;
    private int tutorIndex;

    //Methods
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelance_profile);

        Intent thisIntent = getIntent();

        tutorIndex = thisIntent.getExtras().getInt("index");

        tutorDisplayed = Globals.freelanceList[tutorIndex];

        try
        {
            displayTutorInfo();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void displayTutorInfo()
    {

        TextView textToChange = (TextView)findViewById(R.id.tutorNameHeader);
        textToChange.setText(tutorDisplayed.firstName + " " + tutorDisplayed.lastName);

        textToChange = (TextView)findViewById(R.id.tutorName);
        textToChange.setText(Html.fromHtml("<b>" + (String) textToChange.getText()
                + "</b>" + " " + tutorDisplayed.firstName + " " + tutorDisplayed.lastName));

        textToChange = (TextView)findViewById(R.id.tutorSubject);
        textToChange.setText(Html.fromHtml("<b>" + (String)textToChange.getText()
                + "</b>" + " " + tutorDisplayed.subject));

        textToChange = (TextView)findViewById(R.id.tutorEmail);
        textToChange.setText(Html.fromHtml("<b>" + (String)textToChange.getText()
                + "</b>" + " " + tutorDisplayed.emailAddress));

        textToChange = (TextView)findViewById(R.id.tutorBio);
        textToChange.setText(Html.fromHtml("<b>" + (String)textToChange.getText()
                + "</b>"+ " " + tutorDisplayed.bio));

        ImageView image = (ImageView) findViewById(R.id.tutorPhoto);
        image.setImageDrawable(Globals.tutorList[tutorIndex].picture);
    }

    public void onReviewButtonClicked(View view)
    {
        Intent reviewsIntent = new Intent(this, ReviewList.class);

        Log.d("showFreelanceId", Integer.toString(tutorDisplayed.tutorID));
        reviewsIntent.putExtra("freelance_id", tutorDisplayed.tutorID);
        reviewsIntent.putExtra("tutor_id", -1);

        reviewsIntent.putExtra("tutor_name", tutorDisplayed.firstName + " " + tutorDisplayed.lastName);

        startActivity(reviewsIntent);
    }

    public void onMapButtonClicked()
    {

    }
}
