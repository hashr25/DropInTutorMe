package com.cs410tutorgroup.findatutor;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONObject;

public class SearchNarrower extends Activity implements AdapterView.OnItemSelectedListener
{
    //Is a task currently running to fetch tutors from the database?
    private boolean gettingTutors = false;

    //Used to keep track of currently selected information
    private int currentCourseID = -1;
    private int currentSubjectID = -1;

    //Holds the lists of subject names/ids returned from the database
    private int[] subjectIDs;
    private String[] subjectNames;

    //Same as above, but for courses
    private int[] courseIDs;
    private String[] courseNames;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_narrower);

        TextView v = (TextView) findViewById(R.id.college_name_text);
        v.setText(Globals.selectedCollegeName);

        Spinner subjectSpinner = (Spinner) findViewById(R.id.subject_spinner);
        Spinner courseSpinner = (Spinner) findViewById(R.id.course_spinner);

        subjectSpinner.setOnItemSelectedListener(this);
        courseSpinner.setOnItemSelectedListener(this);

        subjectSpinner.setEnabled(false);
        courseSpinner.setEnabled(false);

        new GetSubjectsTask().execute(new ApiConnector());
    }

    //Called when an item in one of the spinners is selected
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
    {
        //An item was selected in the subject spinner
        if(parent.getId() == R.id.subject_spinner)
        {
            //Get the name of the selected subject
            String s = parent.getItemAtPosition(pos).toString();

            //Search the list of subject names from the database to match the name to an ID
            for(int i = 0; i < subjectNames.length; i++)
            {
                //If a match is found, pull the courses for that subject from the database
                if(subjectNames[i].equals(s))
                {
                    currentSubjectID = subjectIDs[i];

                    //Disable the cvourse spinner while the new courses are being obtained
                    Spinner courseSpinner = (Spinner) findViewById(R.id.course_spinner);
                    courseSpinner.setEnabled(false);

                    //Start a thread to pull course information from the database
                    new GetCoursesTask(subjectIDs[i]).execute(new ApiConnector());
                }
            }
        }
        else if(parent.getId() == R.id.course_spinner)
        {
            //Get the name of the selected course
            String s = parent.getItemAtPosition(pos).toString();

            //Search the list of course names for a match
            for(int i = 0; i < courseNames.length; i++)
            {
                //If found, mark that course as selected
                if(courseNames[i].equals(s))
                {
                    currentCourseID = courseIDs[i];
                }
            }
        }
    }

    public void onNothingSelected(AdapterView<?> parent)
    {

    }

    public void onSearchButtonClicked(View view)
    {
        if(!gettingTutors)
        {
            gettingTutors = true;
            new GetNarrowedTutorsTask().execute(new ApiConnector());
        }
    }

    //Builds and attaches an adapter to the subject spinner
    public void setSubjectSpinnerAdapter(JSONArray subjectsArray)
    {
        Spinner subjectSpinner = (Spinner) findViewById(R.id.subject_spinner);

        subjectNames = new String[subjectsArray.length()];
        subjectIDs = new int[subjectsArray.length()];

        try
        {
            for(int i = 0; i < subjectsArray.length(); i++)
            {
                JSONObject jObj = subjectsArray.getJSONObject(i);
                subjectNames[i] = jObj.getString("subject_name");
                subjectIDs[i] = jObj.getInt("subject_id");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, subjectNames);

        subjectSpinner.setAdapter(adapter);

        subjectSpinner.setEnabled(true);
    }

    //Builds and attaches an adapter to the course spinner
    public void setCourseSpinnerAdapter(JSONArray subjectsArray)
    {
        Spinner courseSpinner = (Spinner) findViewById(R.id.course_spinner);

        courseNames = new String[subjectsArray.length()];
        courseIDs = new int[subjectsArray.length()];

        try
        {
            for(int i = 0; i < subjectsArray.length(); i++)
            {
                JSONObject jObj = subjectsArray.getJSONObject(i);
                courseNames[i] = jObj.getString("display_text");
                courseIDs[i] = jObj.getInt("course_id");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, courseNames);

        courseSpinner.setAdapter(adapter);

        courseSpinner.setEnabled(true);
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
            try
            {
                setSubjectSpinnerAdapter(jsonArray);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private class GetNarrowedTutorsTask extends AsyncTask<ApiConnector,Long,JSONArray>
    {
        @Override
        protected JSONArray doInBackground(ApiConnector... params)
        {
            try
            {
                return params[0].GetNarrowedTutors(Globals.selectedCollegeName, currentSubjectID, currentCourseID);
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
            gettingTutors = false;
        }
    }

    private class GetCoursesTask extends AsyncTask<ApiConnector,Long,JSONArray>
    {
        public int subjectID;

        public GetCoursesTask(int id)
        {
            super();
            subjectID = id;
        }

        @Override
        protected JSONArray doInBackground(ApiConnector... params)
        {
            try
            {
                return params[0].GetCourses(subjectID);
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
            try
            {
                setCourseSpinnerAdapter(jsonArray);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
