package com.cs410tutorgroup.findatutor;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;


public class testDatabase extends Activity {

    private TextView responseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_database);


        this.responseTextView = (TextView) this.findViewById(R.id.responseTextView);
        new GetTutorsTask().execute(new ApiConnector());
    }

    @Override
    protected void onStart() {
        super.onStart();
        new GetTutorsTask().execute(new ApiConnector());

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

