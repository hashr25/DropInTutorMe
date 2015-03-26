package com.cs410tutorgroup.findatutor;

import org.json.JSONObject;

/**
 * Created by hashr25 on 3/24/15.
 */
public class FreelanceTutor extends Tutor
{
    //Attributes
    public double latitude, longitude;

    //Methods
    public static FreelanceTutor loadFromJSONObject(JSONObject jsonObj)
    {
        FreelanceTutor tutor = (FreelanceTutor) Tutor.loadFromJsonObject(jsonObj);

        try
        {
            tutor.latitude = jsonObj.getDouble("lat_cord");
            tutor.longitude = jsonObj.getDouble("long_cord");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return tutor;
    }
}
