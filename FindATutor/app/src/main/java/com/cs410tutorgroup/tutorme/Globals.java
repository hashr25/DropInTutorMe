package com.cs410tutorgroup.tutorme;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import com.cs410tutorgroup.findatutor.R;

/**
 * Created by Conor on 3/21/2015.
 */
public class Globals
{
    public static final String API_KEY = "AIzaSyDbcrROWyAIVoYqXhaFW7lxi_KYxmoqWYY";

    public static Drawable tempPic = null;

    //Information on the currently selected college
    public static String selectedCollegeName = "";

    public static CollegeTutor[] tutorList = null;

    public static class ErrorDialogFragment extends DialogFragment
    {
        public static ErrorDialogFragment newInstance(int message)
        {
            ErrorDialogFragment frag = new ErrorDialogFragment();
            Bundle args = new Bundle();
            args.putInt("message", message);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            int message = getArguments().getInt("message");

            return new AlertDialog.Builder(getActivity())
                    .setMessage(message)
                    .setPositiveButton(R.string.alert_dialog_ok,
                            new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int whichButton){}
                            }
                    )
                    .create();
        }
    }

    public static class InvalidTimeException extends Exception {}

    //Utils provides functions used application-wide
    public static class Utils
    {
        public static int timeToInt(String day, String time) throws InvalidTimeException
        {
            int dayNum = 0;

            switch(day)
            {
                case "Monday":
                    dayNum = 10000;
                    break;

                case "Tuesday":
                    dayNum = 20000;
                    break;

                case "Wednesday":
                    dayNum = 30000;
                    break;

                case "Thursday":
                    dayNum = 40000;
                    break;

                case "Friday":
                    dayNum = 50000;
                    break;

                default:
                    throw(new InvalidTimeException());
            }

            String[] timePieces = time.split(":");

            //Extract the hour information(first 2 characters) from the time string
            int hourNum = Integer.valueOf(timePieces[0]);

            //Hour must be between 0 and 23 to be part of the 24 hour clock
            if(hourNum < 0 || hourNum > 23)
            {
                throw(new InvalidTimeException());
            }

            hourNum = 100 * hourNum;

            //Extract minute information
            int minuteNum = Integer.valueOf(timePieces[1]);

            //Return the combined time integer
            return dayNum + hourNum + minuteNum;
        }
    }
}
