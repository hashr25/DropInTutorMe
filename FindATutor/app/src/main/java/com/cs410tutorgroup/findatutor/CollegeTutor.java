package com.cs410tutorgroup.findatutor;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by hashr25 on 3/24/15.
 */
public class CollegeTutor extends Tutor
{
    //Attributes
    public int collegeID;
    public String building;
    public String room;
    public int scheduleID;

    //Methods

    /**
     *
     * @param jsonObj This JSON Object comes from the ApiConnector from querying for tutors
     * @return  Returns the Tutor Object
     */
    public CollegeTutor loadCollegeTutorFromJsonObject(JSONObject jsonObj)
    {
        CollegeTutor tutor = null;
        tutor = (CollegeTutor) Tutor.loadFromJsonObject(jsonObj);

        try
        {
            tutor.collegeID = jsonObj.getInt("college_id");
            tutor.building = jsonObj.getString("building");
            tutor.room = jsonObj.getString("room");
            tutor.scheduleID = jsonObj.getInt("schedule_id");
        }
        catch(Exception e)
        {
            Log.d("CollegeTutorFail", "Failed loading at college tutor level");
            e.printStackTrace();
        }

        return tutor;
    }
}
