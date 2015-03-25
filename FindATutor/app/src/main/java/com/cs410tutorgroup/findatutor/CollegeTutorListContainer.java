package com.cs410tutorgroup.findatutor;

import android.content.Context;

import org.json.JSONObject;

/**
 * Created by hashr25 on 3/25/15.
 */
public class CollegeTutorListContainer extends TutorListContainer
{
    //Attributes
    public String collegeName;

    //Methods
    public CollegeTutorListContainer(Context context)
    {
        super(context);
    }

    public static CollegeTutorListContainer loadFromJsonObject(JSONObject jsonObj, Context context)
    {
        CollegeTutorListContainer tutor =
                (CollegeTutorListContainer )TutorListContainer.loadFromJsonObject(jsonObj, context);

        try
        {
            tutor.collegeName = jsonObj.getString("college_name");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return tutor;
    }
}
