package com.cs410tutorgroup.tutorme;

import org.json.JSONObject;

/**
 * Created by hashr25 on 3/24/15.
 */
public class FreelanceTutor extends Tutor
{
    //Attributes
    public double latitude, longitude;

    //Methods

    /**
     *
     * @param jsonObj This JSON Object comes from the ApiConnector from querying for tutors
     * @return  Returns the Tutor Object
     */
    public static FreelanceTutor loadFreelanceTutorFromJSONObject(JSONObject jsonObj)
    {
        FreelanceTutor tutor = null;
        tutor = (FreelanceTutor) Tutor.loadFromJsonObject(jsonObj);

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
