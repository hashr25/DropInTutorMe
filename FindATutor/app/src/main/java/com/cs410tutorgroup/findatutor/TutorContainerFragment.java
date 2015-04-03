package com.cs410tutorgroup.findatutor;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Conor on 3/31/2015.
 */
public class TutorContainerFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        RelativeLayout l = (RelativeLayout) inflater.inflate(R.layout.tutor_list_container, container, false);

        TextView tv = (TextView) l.getChildAt(0);
        tv.setText(getArguments().getString("tutor_name"));

        tv = (TextView) l.getChildAt(1);
        tv.setText(getArguments().getString("tutor_subject"));

        tv = (TextView) l.getChildAt(2);
        tv.setText(getArguments().getString("tutor_email"));

        return l;
    }
}
