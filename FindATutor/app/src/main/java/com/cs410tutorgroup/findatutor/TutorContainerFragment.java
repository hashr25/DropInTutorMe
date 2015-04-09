package com.cs410tutorgroup.findatutor;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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

        l.setOnClickListener(new View.OnClickListener()
        {
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
        tv.setText(Globals.tutorList[tutorIndex].firstName + " " + Globals.tutorList[tutorIndex].lastName);

        tv = (TextView) l.getChildAt(1);
        tv.setText(tv.getText() + Globals.tutorList[tutorIndex].subject);

        tv = (TextView) l.getChildAt(2);
        tv.setText(tv.getText() + Globals.tutorList[tutorIndex].emailAddress);

        //Profile Picture
        ImageView iv = (ImageView) l.getChildAt(3);
        iv.setImageDrawable(Globals.tutorList[tutorIndex].picture);

        return l;
    }

}
