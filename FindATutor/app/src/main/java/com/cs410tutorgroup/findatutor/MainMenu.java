package com.cs410tutorgroup.findatutor;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainMenu extends Activity
{

    private TextView responseTextView;

    private SearchView collegeSearch;
    private Button onlineResourcesButton;
    private Button freelanceSearchButton;
    private Button freelanceSignUpButton;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        this.collegeSearch = (SearchView) this.findViewById(R.id.collegeSearch);
        this.onlineResourcesButton = (Button) this.findViewById(R.id.onlineResourcesButton);
        this.freelanceSearchButton = (Button) this.findViewById(R.id.freelanceTutorSearchButton);
        this.freelanceSignUpButton = (Button) this.findViewById(R.id.freelanceSignupButton);

        this.responseTextView = (TextView) this.findViewById(R.id.responseTextView);
        new GetTutorsTask().execute(new ApiConnector());
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
                    s = s +
                            "Name : " + json.getString("first_name") + " " + json.getString("last_name") + "\n" +
                            "Email : " + json.getString("email") + "\n" +
                            "Subject : " + json.getString("subject") + "\n\n";
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        this.responseTextView.setText(s);
    }

    public void tutorRegButtonClicked(View view)
    {
        Intent regIntent = new Intent(this, TutorRegistration.class);

        startActivity(regIntent);
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
}
