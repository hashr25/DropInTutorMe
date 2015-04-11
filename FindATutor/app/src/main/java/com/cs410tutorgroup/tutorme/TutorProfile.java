package com.cs410tutorgroup.tutorme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs410tutorgroup.findatutor.R;

import org.json.JSONArray;

import java.io.IOException;
import java.net.MalformedURLException;


public class TutorProfile extends Activity
{
    //Attributes
    private CollegeTutor tutorDisplayed;
    private int tutorIndex;

    //Methods
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_profile);

        Intent thisIntent = getIntent();

        tutorIndex = thisIntent.getExtras().getInt("index");

        tutorDisplayed = Globals.tutorList[tutorIndex];

        //createTestTutor();
        try{ displayTutorInfo(); }
        catch(Exception e) {e.printStackTrace();}
    }

    private void displayTutorInfo()
    {

        TextView textToChange = (TextView)findViewById(R.id.tutorNameHeader);
        textToChange.setText(tutorDisplayed.firstName + " " + tutorDisplayed.lastName);

        textToChange = (TextView)findViewById(R.id.tutorName);
        textToChange.setText(Html.fromHtml("<b>" + (String)textToChange.getText()
                            + "</b>" + " " + tutorDisplayed.firstName + " " + tutorDisplayed.lastName));

        textToChange = (TextView)findViewById(R.id.tutorCollege);
        textToChange.setText(Html.fromHtml("<b>" + (String)textToChange.getText()
                            + "</b>" + " " + tutorDisplayed.college));

        textToChange = (TextView)findViewById(R.id.tutorLocation);
        textToChange.setText(Html.fromHtml("<b>" + (String)textToChange.getText()
                            + "</b>" + " " + tutorDisplayed.building + " " + tutorDisplayed.room));

        textToChange = (TextView)findViewById(R.id.tutorSubject);
        textToChange.setText(Html.fromHtml("<b>" + (String)textToChange.getText()
                            + "</b>" + " " + tutorDisplayed.subject));

        textToChange = (TextView)findViewById(R.id.tutorBio);
        textToChange.setText(Html.fromHtml("<b>" + (String)textToChange.getText()
                            + "</b>"+ " " + tutorDisplayed.bio));

        textToChange = (TextView) findViewById(R.id.tutorCourses);
        textToChange.setText(Html.fromHtml("<b>" + (String)textToChange.getText()
                            + "</b>" + " " + getCourses(tutorDisplayed.tutorID)));

        ImageView image = (ImageView) findViewById(R.id.tutorPhoto);
        image.setImageDrawable(Globals.tutorList[tutorIndex].picture);
    }

    private String getCourses(int tutorID)
    {
        String result = "";

        try
        {
            JSONArray rawTutorCourses = new ApiConnector().getTutorCourses(tutorID);

            for( int i = 0; i < rawTutorCourses.length(); i++ )
            {
                result = result + ", " + rawTutorCourses.getString(i);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return result;
    }

    public void onReviewButtonClicked(View view)
    {
        Intent reviewsIntent = new Intent(this, TutorReview.class);

        Log.d("showTutorId", Integer.toString(tutorDisplayed.tutorID));
        reviewsIntent.putExtra("tutor_id", tutorDisplayed.tutorID);
        reviewsIntent.putExtra("freelance_id", 0);

        reviewsIntent.putExtra("tutor_name", tutorDisplayed.firstName + " " + tutorDisplayed.lastName);


        startActivity(reviewsIntent);
    }

    public void onMapButtonClicked()
    {

    }

    public void createTestTutor()
    {
        tutorDisplayed = new CollegeTutor();
        tutorDisplayed.firstName = "Alex";
        tutorDisplayed.lastName = "Hudgins";
        tutorDisplayed.tutorID = 4;
        tutorDisplayed.emailAddress = "none@none.com";
        tutorDisplayed.bio = "What am I doing on here? I am just in the second grade...";
        tutorDisplayed.subject = "Math";
    }

}
