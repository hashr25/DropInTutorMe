package com.cs410tutorgroup.tutorme;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cs410tutorgroup.findatutor.R;

/**
 * Created by Conor on 3/31/2015.
 */
public class TutorContainerFragment extends Fragment
{
    private int tutorIndex;

    ImageView iv;

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

        if(Globals.tutorList[tutorIndex].pictureURL != "")
        {
            //new LoadPictureTask().execute(new ApiConnector());
        }


        TextView tv = (TextView) l.getChildAt(0);
        tv.setText(Globals.tutorList[tutorIndex].firstName + " " + Globals.tutorList[tutorIndex].lastName);

        tv = (TextView) l.getChildAt(1);
        tv.setText(tv.getText() + Globals.tutorList[tutorIndex].subject);

        tv = (TextView) l.getChildAt(2);
        tv.setText(tv.getText() + Globals.tutorList[tutorIndex].emailAddress);

        //Profile Picture
        iv = (ImageView) l.getChildAt(3);
        iv.setImageDrawable(Globals.tutorList[tutorIndex].picture);

        return l;
    }

    /**
     * This Class is used to load pictures from URL to Bitmap for Tutors
     */
    private class LoadPictureTask extends AsyncTask<ApiConnector, Void, Drawable>
    {
        Drawable image;

        /**
         * @param params URL link(s) for photo. Received from inside Tutor Class
         * @return This returns the bitmap image
         */
        @Override
        protected Drawable doInBackground(ApiConnector... params)
        {
            try
            {
                return params[0].getTutorPhoto(Globals.tutorList[tutorIndex].pictureURL);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return image;
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            Globals.tutorList[tutorIndex].picture = drawable;
            iv.setImageDrawable(drawable);
        }
    }

}
