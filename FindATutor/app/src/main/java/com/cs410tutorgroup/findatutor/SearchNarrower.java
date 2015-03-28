package com.cs410tutorgroup.findatutor;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;


public class SearchNarrower extends Activity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_narrower);

        TextView v = (TextView) findViewById(R.id.college_name_text);
        v.setText(Globals.selectedCollegeName);

        Spinner subjectSpinner = (Spinner) findViewById(R.id.subject_spinner);
        Spinner courseSpinner = (Spinner) findViewById(R.id.course_spinner);

        subjectSpinner.setEnabled(false);
        courseSpinner.setEnabled(false);

        new GetSubjectsTask().execute(new ApiConnector());
    }

    //Called when an item in one of the spinners is selected
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
    {
        if(parent.getId() == R.id.subject_spinner)
        {
            String s = parent.getItemAtPosition(pos).toString();
        }
    }

    public void onNothingSelected(AdapterView<?> parent)
    {

    }

    public void setSubjectSpinnerAdapter(JSONArray subjectsArray)
    {
        Log.d("Subject Array",subjectsArray.toString());
        Spinner subjectSpinner = (Spinner) findViewById(R.id.subject_spinner);

        String[] validSubjects = new String[subjectsArray.length()];

        try
        {
            for(int i = 0; i < subjectsArray.length(); i++)
            {
                validSubjects[i] = subjectsArray.getJSONObject(i).getString("subject_name");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, validSubjects);

        subjectSpinner.setAdapter(adapter);

        subjectSpinner.setEnabled(true);
    }

    private class GetSubjectsTask extends AsyncTask<ApiConnector,Long,JSONArray>
    {
        @Override
        protected JSONArray doInBackground(ApiConnector... params)
        {
            try
            {
                return params[0].GetSubjects();
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
            setSubjectSpinnerAdapter(jsonArray);
        }
    }

    private class GetCoursesTask extends AsyncTask<ApiConnector,Long,JSONArray>
    {
        @Override
        protected JSONArray doInBackground(ApiConnector... params)
        {
            try
            {
                return params[0].GetSubjects();
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
            setSubjectSpinnerAdapter(jsonArray);
        }
    }
}
