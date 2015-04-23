package com.cs410tutorgroup.tutorme;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
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
        setContentView(R.layout.activity_tutor_profile);

        Intent thisIntent = getIntent();

        tutorIndex = thisIntent.getExtras().getInt("index");

        tutorDisplayed = Globals.freelanceList[tutorIndex];

        //new getCoursesTask().execute(new ApiConnector());

        createTestTutor();
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

        textToChange = (TextView)findViewById(R.id.tutorLocation);
        textToChange.setText(Html.fromHtml("<b>" + (String)textToChange.getText()
                + "</b>" + " " /* Add the location here */ ));

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

        /*Log.d("showTutorId", Integer.toString(tutorDisplayed.tutorID));
        reviewsIntent.putExtra("tutor_id", tutorDisplayed.tutorID);
        reviewsIntent.putExtra("freelance_id", 0);

        reviewsIntent.putExtra("tutor_name", tutorDisplayed.firstName + " " + tutorDisplayed.lastName);*/

        startActivity(reviewsIntent);
    }

    public void onMapButtonClicked()
    {

    }

    public void createTestTutor()
    {
        tutorDisplayed = new FreelanceTutor();
        tutorDisplayed.firstName = "Alex";
        tutorDisplayed.lastName = "Hudgins";
        tutorDisplayed.tutorID = 4;
        tutorDisplayed.emailAddress = "none@none.com";
        tutorDisplayed.bio = "What am I doing on here? I am just in the second grade...";
        tutorDisplayed.subject = "Math";
    }



}
