package com.cs410tutorgroup.findatutor;

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
    public static CollegeTutor loadFromJsonObject(JSONObject jsonObj)
    {
        CollegeTutor tutor = (CollegeTutor) Tutor.loadFromJsonObject(jsonObj);

        try
        {
            tutor.collegeID = jsonObj.getInt("college_id");
            tutor.building = jsonObj.getString("building");
            tutor.room = jsonObj.getString("room");
            tutor.scheduleID = jsonObj.getInt("schedule_id");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return tutor;
    }
}
