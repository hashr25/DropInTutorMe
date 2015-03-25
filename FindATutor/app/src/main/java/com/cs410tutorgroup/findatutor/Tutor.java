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
    public String pictureLink;

    //Methods
    public static Tutor loadFromJsonObject(JSONObject jsonObj)
    {
        Tutor tutor = new Tutor();

        try
        {
            tutor.tutorID = jsonObj.getInt("tutor_id");
            tutor.firstName = jsonObj.getString("first_name");
            tutor.lastName = jsonObj.getString("last_name");
            tutor.emailAddress = jsonObj.getString("email");
            tutor.subject = jsonObj.getString("subject");
            tutor.bio = jsonObj.getString("bio");
            //tutorToLoad.pictureLink = jsonObj.getString("picture");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return tutor;
    }
}
