package com.cs410tutorgroup.findatutor;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Pair;

import java.util.ArrayList;

/**
 * Created by Conor on 3/21/2015.
 */
public class Globals
{
    public static final String API_KEY = "AIzaSyDbcrROWyAIVoYqXhaFW7lxi_KYxmoqWYY";

    //Information on the currently selected college
    public static String selectedCollegeName = "";

    public static Tutor[] tutorList = null;

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
}
