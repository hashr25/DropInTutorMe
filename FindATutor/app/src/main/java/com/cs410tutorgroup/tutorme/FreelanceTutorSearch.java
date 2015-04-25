package com.cs410tutorgroup.tutorme;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.cs410tutorgroup.findatutor.R;


public class FreelanceTutorSearch extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelance_tutor_search);

        //Add each tutor fragment to the tutor container
        for(int i = 0; i < Globals.freelanceList.length; i++)
        {
            Bundle args = new Bundle();
            args.putString("tutor_name",Globals.freelanceList[i].firstName + " " + Globals.freelanceList[i].lastName);
            args.putString("tutor_subject",Globals.freelanceList[i].subject);
            args.putString("tutor_email",Globals.freelanceList[i].emailAddress);
            args.putInt("index", i);
            FreelanceContainerFragment newTutorFragment = new FreelanceContainerFragment();
            newTutorFragment.setArguments(args);
            getFragmentManager().beginTransaction().add(R.id.tutor_list, newTutorFragment).commit();
        }
    }
}

