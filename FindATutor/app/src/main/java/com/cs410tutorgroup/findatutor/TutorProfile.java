package com.cs410tutorgroup.findatutor;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;


public class TutorProfile extends Activity
{
    //Attributes
    private Tutor tutorDisplayed;

    //Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_profile);

        createTestTutor();
        displayTutorInfo();
    }

    private void displayTutorInfo()
    {
        TextView textToChange = (TextView)findViewById(R.id.tutorName);
        textToChange.setText(textToChange.getText() + tutorDisplayed.firstName + " " + tutorDisplayed.lastName);

        textToChange = (TextView)findViewById(R.id.tutorBio);
        textToChange.setText(textToChange.getText() + " " + tutorDisplayed.bio);

        textToChange = (TextView)findViewById(R.id.tutorSubject);
        textToChange.setText(textToChange.getText() + tutorDisplayed.subject);

        textToChange = (TextView)findViewById(R.id.tutorNameHeader);
        textToChange.setText(tutorDisplayed.firstName + " " + tutorDisplayed.lastName);

        if(tutorDisplayed.getClass() == CollegeTutor.class)
        {
            textToChange = (TextView)findViewById(R.id.tutorCollege);
            textToChange.setText(textToChange.getText() + " Concord University"); //THIS NEEDS TO BE FIXED

            //textToChange = (TextView)findViewById(R.id.tutorLocation);
            //textToChange.setText(textToChange.getText() + tutorDisplayed.building + " " + tutorDisplayed.room);

            textToChange = (TextView)findViewById(R.id.tutorCourses);
            textToChange.setText(textToChange.getText() + "\nMath623, Math302, Math325");
        }
        else if(tutorDisplayed.getClass() == FreelanceTutor.class)
        {

        }
    }

    public void onReviewButtonClicked(View view)
    {
        Intent reviewsIntent = new Intent(this, TutorReview.class);

        if(tutorDisplayed.getClass() == FreelanceTutor.class)
        {
            reviewsIntent.putExtra("freelance_id", tutorDisplayed.tutorID);
        }
        else if(tutorDisplayed.getClass() == CollegeTutor.class)
        {
            reviewsIntent.putExtra("tutor_id", tutorDisplayed.tutorID);
        }


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
        tutorDisplayed.emailAddress = "none@none.com";
        tutorDisplayed.bio = "What am I doing on here? I am just in the second grade...";
        tutorDisplayed.subject = "Math";
    }

}
