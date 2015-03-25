package com.cs410tutorgroup.findatutor;

import android.content.Context;
import android.view.View;

import org.json.JSONObject;

/**
 * Created by hashr25 on 3/25/15.
 */
public class TutorListContainer extends View
{
    //Attributes
    public String firstName;
    public String lastName;
    public String subject;
    public float rating;

    //Methods
    public TutorListContainer(Context context)
    {
        super(context);
    }

    public static TutorListContainer loadFromJsonObject(JSONObject jsonObj, Context context)
    {
        TutorListContainer container = new TutorListContainer(context);

        try
        {
            container.firstName = jsonObj.getString("first_name");
            container.lastName = jsonObj.getString("last_name");
            container.subject = jsonObj.getString("subject");
            //tutorToLoad.pictureLink = jsonObj.getString("picture");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return container;
    }
}
