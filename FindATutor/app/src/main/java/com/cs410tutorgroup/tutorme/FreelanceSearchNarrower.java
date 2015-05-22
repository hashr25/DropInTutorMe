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

import com.cs410tutorgroup.findatutor.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class FreelanceSearchNarrower extends Activity
                                     implements AdapterView.OnItemSelectedListener
{
    //Is a task currently running to fetch tutors from the database?
    private boolean gettingTutors = false;

    //Used to keep track of currently selected information
    private int currentSubjectID = -1;

    //Holds the lists of subject names/ids returned from the database
    private int[] subjectIDs;
    private String[] subjectNames;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelance_search_narrower);

        Spinner subjectSpinner = (Spinner) findViewById(R.id.subject_spinner);
        subjectSpinner.setOnItemSelectedListener(this);
        subjectSpinner.setEnabled(false);

        new GetFreelanceSubjectsTask().execute(new ApiConnector());
    }

    //OnItemSelectedListener methods

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
    {
        //An item was selected in the subject spinner
        if (parent.getId() == R.id.subject_spinner)
        {
            Log.d("Hey", "Listen");
            //Get the name of the selected subject
            String s = parent.getItemAtPosition(pos).toString();

            //Search the list of subject names from the database to match the name to an ID
            for(int i = 0; i < subjectNames.length; i++)
            {
                //If a match is found, pull the courses for that subject from the database
                if(subjectNames[i].equals(s))
                {
                    currentSubjectID = subjectIDs[i];

                    break;
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
            new GetNarrowedFreelanceTutorsTask().execute(new ApiConnector());
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

    public void startSearch()
    {
        if(Globals.freelanceList != null && Globals.freelanceList.length > 0)
        {
            Intent tutorSearchIntent = new Intent(this, FreelanceTutorSearch.class);
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

    //Task to get subjects from database
    private class GetFreelanceSubjectsTask extends AsyncTask<ApiConnector,Long,JSONArray>
    {
        @Override
        protected JSONArray doInBackground(ApiConnector... params)
        {
            try
            {
                return params[0].GetFreelanceSubjects();
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

    private class GetNarrowedFreelanceTutorsTask extends AsyncTask<ApiConnector,Long,JSONArray>
    {
        @Override
        protected JSONArray doInBackground(ApiConnector... params)
        {
            try
            {
                return params[0].GetNarrowedFreelanceTutors(currentSubjectID);
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
                ArrayList<FreelanceTutor> tutors = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    FreelanceTutor newTutor = (FreelanceTutor)(FreelanceTutor.loadFreelanceTutorFromJSONObject(jsonArray.getJSONObject(i)));
                    newTutor.subject = findSubjectName(jsonArray.getJSONObject(i).getInt("subject_id"));

                    tutors.add(newTutor);
                }

                Globals.freelanceList = new FreelanceTutor[tutors.size()];

                for(int i = 0; i < tutors.size(); i++)
                {
                    Globals.freelanceList[i] = tutors.get(i);
                }
            }
            catch(Exception e)
            {
                Globals.freelanceList = null;
                e.printStackTrace();
            }

            //If assigning the list was successful, start a new tutor search activity
            startSearch();
        }
    }
}
