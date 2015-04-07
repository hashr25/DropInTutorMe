package com.cs410tutorgroup.findatutor;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Conor on 3/31/2015.
 */
public class TutorContainerFragment extends Fragment
{
    private int tutorIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        RelativeLayout l = (RelativeLayout) inflater.inflate(R.layout.tutor_container_fragment, container, false);

        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent profileIntent = new Intent (getActivity(), TutorProfile.class);
                profileIntent.putExtra("index", tutorIndex);
                startActivity(profileIntent);
            }
        });

        tutorIndex = getArguments().getInt("index");

        TextView tv = (TextView) l.getChildAt(0);
        tv.setText(getArguments().getString("tutor_name"));

        tv = (TextView) l.getChildAt(1);
        tv.setText(getArguments().getString("tutor_subject"));

        tv = (TextView) l.getChildAt(2);
        tv.setText(getArguments().getString("tutor_email"));

        return l;
    }

}
