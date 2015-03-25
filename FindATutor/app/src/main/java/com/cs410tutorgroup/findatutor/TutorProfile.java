package com.cs410tutorgroup.findatutor;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;


public class TutorProfile extends Activity
{
    //Attributes
    private CollegeTutor tutorDisplayed;

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

        textToChange = (TextView)findViewById(R.id.tutorCourses);
        textToChange.setText(textToChange.getText() + "\nMath623, Math302, Math32");

        textToChange = (TextView)findViewById(R.id.tutorBio);
        textToChange.setText(textToChange.getText() + " " + tutorDisplayed.bio);

        textToChange = (TextView)findViewById(R.id.tutorCollege);
        textToChange.setText(textToChange.getText() + " Concord University"); //THIS NEEDS TO BE FIXED

        textToChange = (TextView)findViewById(R.id.tutorLocation);
        textToChange.setText(textToChange.getText() + tutorDisplayed.building + " " + tutorDisplayed.room);

        textToChange = (TextView)findViewById(R.id.tutorSubject);
        textToChange.setText(textToChange.getText() + tutorDisplayed.subject);

        textToChange = (TextView)findViewById(R.id.tutorNameHeader);
        textToChange.setText(tutorDisplayed.firstName + " " + tutorDisplayed.lastName);
    }

    public void onReviewButtonClicked()
    {
        Intent reviewsIntent = new Intent(this, TutorReview.class);
    }

    public void onMapButtonClicked()
    {

    }

    public void createTestTutor()
    {
        tutorDisplayed = new CollegeTutor();
        tutorDisplayed.firstName = "Alex";
        tutorDisplayed.lastName = "Hudgins";
        tutorDisplayed.emailAddress = "none@none.com";
        tutorDisplayed.building = "Marsh Hall";
        tutorDisplayed.room = "101";
        tutorDisplayed.bio = "What am I doing on here? I am just in the second grade...";
        tutorDisplayed.subject = "Math";
    }

}
