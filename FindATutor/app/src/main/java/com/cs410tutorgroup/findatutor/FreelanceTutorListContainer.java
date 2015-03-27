package com.cs410tutorgroup.findatutor;

import android.content.Context;

import org.json.JSONObject;

/**
 * Created by hashr25 on 3/25/15.
 */
public class FreelanceTutorListContainer extends TutorListContainer
{
    //Attributes
    public float distance;

    //Method
    public FreelanceTutorListContainer(Context context) {
        super(context);
    }

    public static FreelanceTutorListContainer loadFromJsonObject(JSONObject jsonObj, Context context)
    {
        FreelanceTutorListContainer tutor =
                (FreelanceTutorListContainer)TutorListContainer.loadFromJsonObject(jsonObj, context);

        try
        {
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return tutor;
    }
}
