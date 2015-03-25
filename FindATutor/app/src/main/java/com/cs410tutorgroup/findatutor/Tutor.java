package com.cs410tutorgroup.findatutor;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by hashr25 on 3/24/15.
 */
public class Tutor
{
    //Attributes
    public int tutorID;
    public String firstName;
    public String lastName;
    public String emailAddress;
    public String subject;
    public String bio;
    public String building;
    public String room;
    public int scheduleID;
    public String pictureLink;

    //Methods
    public void loadFromJsonObject(JSONObject jsonObj)
    {
        try
        {
            this.tutorID = jsonObj.getInt("tutor_id");
            this.firstName = jsonObj.getString("first_name");
            this.lastName = jsonObj.getString("last_name");
            this.emailAddress = jsonObj.getString("email");
            this.subject = jsonObj.getString("subject");
            this.bio = jsonObj.getString("bio");
            this.building = jsonObj.getString("building");
            this.room = jsonObj.getString("room");
            this.scheduleID = jsonObj.getInt("schedule_id");
            //tutorToLoad.pictureLink = jsonObj.getString("picture");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
