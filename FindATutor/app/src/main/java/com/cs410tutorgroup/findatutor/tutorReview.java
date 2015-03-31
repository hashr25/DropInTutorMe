package com.cs410tutorgroup.findatutor;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class TutorReview extends Activity
{
    //Data
    public int tutorID;
    public int freelanceID;
    public String reviewText;
    public float stars;

    //Methods
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_review);

        Intent intent = getIntent();
        tutorID = intent.getIntExtra("tutorID", 0);
        freelanceID = intent.getIntExtra("freelanceID", 0);
    }




}
