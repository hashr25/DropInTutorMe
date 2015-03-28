package com.cs410tutorgroup.findatutor;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainMenu extends Activity
{

    private TextView responseTextView;

    private AutoCompleteTextView collegeSearch;
    private Button onlineResourcesButton;
    private Button freelanceSearchButton;
    private Button freelanceSignUpButton;

    //Used to hold the list of valid college names returned from the database
    private String[] validColleges = null;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        this.collegeSearch = (AutoCompleteTextView) this.findViewById(R.id.collegeSearch);

        collegeSearch.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                    checkQuery((AutoCompleteTextView) v);
                    handled = true;
                }
                return handled;
            }
        });

        this.onlineResourcesButton = (Button) this.findViewById(R.id.onlineResourcesButton);
        this.freelanceSearchButton = (Button) this.findViewById(R.id.freelanceTutorSearchButton);
        this.freelanceSignUpButton = (Button) this.findViewById(R.id.freelanceSignupButton);

        this.responseTextView = (TextView) this.findViewById(R.id.responseTextView);
        new GetCollegesTask().execute(new ApiConnector());
    }

    public void onlineResourcesButtonClicked(View view)
    {
        Intent onlineResourcesIntent = new Intent(this, OnlineResources.class);
        startActivity(onlineResourcesIntent);
    }

    public void freelanceSearchButtonClicked(View view)
    {
        //This needs to be changed to correct activity. This was used for testing.
        Intent freelanceSearchIntent = new Intent(this, TutorProfile.class);
        startActivity(freelanceSearchIntent);
    }

    public void freelanceSignupButtonClicked(View view)
    {
        Intent freelanceSignupIntent = new Intent(this, TutorRegistration.class);
        startActivity(freelanceSignupIntent);
    }


    public void setTextToTextView(JSONArray jsonArray)
    {
        String s  = "";

        if(jsonArray != null)
        {
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject json = null;
                try
                {
                    json = jsonArray.getJSONObject(i);
                    s = s + "Name : " + json.getString("college_name") + "\n\n";
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        this.responseTextView.setText(s);
    }

    public void setAutoCompleteList(JSONArray listArray)
    {

        AutoCompleteTextView v = (AutoCompleteTextView) findViewById(R.id.collegeSearch);

        //initialize the size of the array of valid colleges to the number of colleges returned from the database
        validColleges = new String[listArray.length()];

        try
        {
            //Transfer the college names from the JSON to the String array
            for (int i = 0; i < listArray.length(); i++)
            {
                JSONObject jObj = listArray.getJSONObject(i);

                validColleges[i] = jObj.getString("college_name");
            }

            //Set the list of auto-complete options
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, validColleges);

            v.setAdapter(adapter);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    //Checks the currently entered text in v to ensure it is a valid college name
    public void checkQuery(AutoCompleteTextView v)
    {
        boolean queryOk = false;

        if(validColleges != null)
        {
            for (int i = 0; i < validColleges.length; i++)
            {
                if (v.getText().toString().equals(validColleges[i]))
                {
                    queryOk = true;
                    break;
                }
            }
        }

        if(queryOk)
        {
            //Query was good! We have a college to search in
            Globals.selectedCollegeName = v.getText().toString();
            Intent searchNarrowerIntent = new Intent(this, SearchNarrower.class);
            startActivity(searchNarrowerIntent);
        }
        else
        {
            //Query was bad; shame the user with an error dialog
            showErrorDialog(R.string.college_error);
        }
    }

    void showErrorDialog(int messageId)
    {
        DialogFragment newFragment = Globals.ErrorDialogFragment.newInstance(messageId);
        newFragment.show(getFragmentManager(), "dialog");
    }

    private class GetTutorsTask extends AsyncTask<ApiConnector,Long,JSONArray>
    {
        @Override
        protected JSONArray doInBackground(ApiConnector... params)
        {
            try
            {
                return params[0].GetTutors();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray)
        {
            setTextToTextView(jsonArray);
        }
    }

    private class GetCollegesTask extends AsyncTask<ApiConnector,Long,JSONArray>
    {
        @Override
        protected JSONArray doInBackground(ApiConnector... params)
        {
            try
            {
                return params[0].GetAllColleges();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray)
        {
            setAutoCompleteList(jsonArray);
        }
    }
}
