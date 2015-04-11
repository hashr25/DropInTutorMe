package com.cs410tutorgroup.tutorme;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.cs410tutorgroup.findatutor.R;


public class CollegeTutorSearch extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_tutor_search);

        TextView v = (TextView) findViewById(R.id.college_name_text);
        v.setText(Globals.selectedCollegeName);

        //Add each tutor fragment to the tutor container
        for(int i = 0; i < Globals.tutorList.length; i++)
        {
            Bundle args = new Bundle();
            args.putString("tutor_name",Globals.tutorList[i].firstName + " " + Globals.tutorList[i].lastName);
            args.putString("tutor_subject",Globals.tutorList[i].subject);
            args.putString("tutor_email",Globals.tutorList[i].emailAddress);
            args.putInt("index", i);
            TutorContainerFragment newTutorFragment = new TutorContainerFragment();
            newTutorFragment.setArguments(args);
            getFragmentManager().beginTransaction().add(R.id.tutor_list, newTutorFragment).commit();
        }


    }
}



