package com.cs410tutorgroup.tutorme;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hashr25 on 3/24/15.
 */
public class CollegeTutor extends Tutor
{
    //Attributes
    public String college;
    public String building;
    public String room;
    public String tutorCourses;

    //Methods

    /**
     *
     * @param jsonObj This JSON Object comes from the ApiConnector from querying for tutors
     * @return  Returns the Tutor Object
     */
    public static CollegeTutor loadFromJsonObject(JSONObject jsonObj)
    {
        CollegeTutor tutor = new CollegeTutor();
        Tutor baseTutor = Tutor.loadFromJsonObject(jsonObj);

        tutor = copyTutor( baseTutor );

        try
        {
            tutor.college = Globals.selectedCollegeName;
            tutor.building = jsonObj.getString("building");
            tutor.room = jsonObj.getString("room");
            tutor.tutorCourses = getTutorCourses(new ApiConnector().getTutorCourses(tutor.tutorID));

            while(tutor.tutorCourses == "")
            {

            }
        }
        catch(Exception e)
        {
            Log.d("CollegeTutorFail", "Failed loading at college tutor level");
            e.printStackTrace();
        }

        return tutor;
    }

    private static CollegeTutor copyTutor(Tutor tutor)
    {
        CollegeTutor copiedTutor = new CollegeTutor();

        copiedTutor.tutorID = tutor.tutorID;
        copiedTutor.pictureURL = tutor.pictureURL;
        copiedTutor.picture = tutor.picture;
        copiedTutor.firstName = tutor.firstName;
        copiedTutor.lastName = tutor.lastName;
        copiedTutor.bio = tutor.bio;
        copiedTutor.emailAddress = tutor.emailAddress;

        return copiedTutor;
    }

    private static String getTutorCourses(JSONArray jsonArray) throws JSONException
    {
        String tutorCourses = "";

        for(int i = 0; i < jsonArray.length(); i++)
        {
            tutorCourses = tutorCourses + ", " + jsonArray.getString(i);
        }

        return tutorCourses;
    }


}
