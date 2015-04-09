package com.cs410tutorgroup.findatutor;

import android.app.Activity;
import android.app.Dialog;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class TutorRegistration extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_registration);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tutor_registration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void registerButtonClicked(View view)
    {
        //Check the text to make sure it's valid
        boolean nameGood = false;
        boolean emailGood = false;

        String nameString = ((EditText) findViewById(R.id.name_edit)).getText().toString();
        if(nameString.matches(getString(R.string.name_regex)))
        {
            nameGood = true;
            Log.d("NameRegEx", "It's good!");
        }

        String emailString = ((EditText) findViewById(R.id.email_edit)).getText().toString();
        if(emailString.matches(getString(R.string.email_regex)))
        {
            emailGood = true;
            Log.d("EmailRegEx", "It's good!");
        }

        if(nameGood && emailGood)
        {
            new AddTutorTask().execute(new ApiConnector());
            //Show progress dialog
        }
        else if(!emailGood && !nameGood)
        {
            showErrorDialog(R.string.name_email_error);
        }
        else if(!emailGood)
        {
            showErrorDialog(R.string.email_error);
        }
        else
        {
            showErrorDialog(R.string.name_error);
        }
    }

    void showErrorDialog(int messageId)
    {
        DialogFragment newFragment = Globals.ErrorDialogFragment.newInstance(messageId);
        newFragment.show(getFragmentManager(), "dialog");
    }

    private class AddTutorTask extends AsyncTask<ApiConnector,Long,Boolean>
    {
        @Override
        protected Boolean doInBackground(ApiConnector... params)
        {
            try
            {
                EditText nameEdit = (EditText) findViewById(R.id.name_edit);
                EditText emailEdit = (EditText) findViewById(R.id.email_edit);
                EditText addressEdit = (EditText) findViewById(R.id.address_edit);

                return params[0].AddTutor(nameEdit.getText().toString(), emailEdit.getText().toString(), addressEdit.getText().toString());
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean success)
        {
            if(success)
            {
                showErrorDialog(R.string.registration_complete);
            }
            else
            {
                showErrorDialog(R.string.address_error);
            }
        }
    }
}
