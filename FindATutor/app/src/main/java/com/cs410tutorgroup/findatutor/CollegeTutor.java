package com.cs410tutorgroup.findatutor;

import org.json.JSONObject;

/**
 * Created by hashr25 on 3/24/15.
 */
public class CollegeTutor extends Tutor
{
    //Attributes
    public int collegeID;

    //Methods
    public void loadFromJsonObject(JSONObject jsonObj)
    {
        super.loadFromJsonObject(jsonObj);

        try
        {
            this.collegeID = jsonObj.getInt("college_id");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
