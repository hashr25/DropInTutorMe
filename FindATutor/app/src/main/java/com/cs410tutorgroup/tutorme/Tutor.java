package com.cs410tutorgroup.tutorme;

import android.graphics.drawable.Drawable;
import android.util.Log;

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
    public String pictureURL;
    public Drawable picture;

    //Methods

    /**
     *
     * @param jsonObj This JSON Object comes from the ApiConnector from querying for tutors
     * @return  Returns the Tutor Object
     */
    public static Tutor loadFromJsonObject(JSONObject jsonObj)
    {
        Tutor tutor = new Tutor();

        try
        {
            tutor.tutorID = jsonObj.getInt("tutor_id");
            tutor.firstName = jsonObj.getString("first_name");
            tutor.lastName = jsonObj.getString("last_name");
            tutor.emailAddress = jsonObj.getString("email");
            tutor.bio = jsonObj.getString("bio");
            tutor.pictureURL = jsonObj.getString("picture");
            tutor.picture = Globals.noPhoto;
        }
        catch (Exception e)
        {
            Log.d("TutorFail", "Failed loading at the Tutor level");
            e.printStackTrace();
        }

        return tutor;

    }

}
