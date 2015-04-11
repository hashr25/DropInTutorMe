package com.cs410tutorgroup.tutorme;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.cs410tutorgroup.findatutor.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class CollegeSearchNarrower extends Activity implements AdapterView.OnItemSelectedListener
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

        //Setup default adapters for the spinners until data is retrieved from the database
        subjectNames = new String[1];
        subjectIDs = new int[1];

        subjectNames[0] = "All";
        subjectIDs[0] = -1;

        courseNames = new String[1];
        courseIDs = new int[1];

        courseNames[0] = "All";
        courseIDs[0] = -1;

        courseSpinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, courseNames));
        subjectSpinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, subjectNames));

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

        subjectNames = new String[subjectsArray.length()+1];
        subjectIDs = new int[subjectsArray.length()+1];

        //All subjects setup
        subjectNames[0] = "All";
        subjectIDs[0] = -1;

        try
        {
            for(int i = 0; i < subjectsArray.length(); i++)
            {
                JSONObject jObj = subjectsArray.getJSONObject(i);
                subjectNames[i+1] = jObj.getString("subject_name");
                subjectIDs[i+1] = jObj.getInt("subject_id");
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

        //All courses setup
        courseNames[0] = "All";
        courseIDs[0] = -1;

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

    public void startSearch()
    {
        if(Globals.tutorList != null)
        {
            Intent tutorSearchIntent = new Intent(this, CollegeTutorSearch.class);
            startActivity(tutorSearchIntent);
        }
        else
        {
            showErrorDialog(R.string.college_search_error);
        }
    }

    void showErrorDialog(int messageId)
    {
        DialogFragment newFragment = Globals.ErrorDialogFragment.newInstance(messageId);
        newFragment.show(getFragmentManager(), "dialog");
    }

    public String findSubjectName(int id)
    {
        for(int i = 0; i < subjectIDs.length; i++)
        {
            if(subjectIDs[i] == id)
            {
                return subjectNames[i];
            }
        }

        return "";
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
            //Done getting tutors
            gettingTutors = false;

            //Assign the list of tutors in the Global class
            try
            {
                Globals.tutorList = new CollegeTutor[jsonArray.length()];
                for (int i = 0; i < Globals.tutorList.length; i++)
                {
                    Globals.tutorList[i] = Globals.tutorList[i].loadFromJsonObject(jsonArray.getJSONObject(i));
                    Globals.tutorList[i].subject = findSubjectName(jsonArray.getJSONObject(i).getInt("subject_id"));
                }
            }
            catch(Exception e)
            {
                Globals.tutorList = null;
                e.printStackTrace();
            }

            //If assigning the list was successful, start a new tutor search activity
            startSearch();
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
