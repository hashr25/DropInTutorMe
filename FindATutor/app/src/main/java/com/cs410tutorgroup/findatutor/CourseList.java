package com.cs410tutorgroup.findatutor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Conor on 3/27/2015.
 */
public class CourseList
{
    public String subjectName;
    public ArrayList<String> courses;

    public CourseList loadFromJSOnArray(JSONArray courseList, int subjectID) throws JSONException, SubjectNotFoundException
    {
        ArrayList<String> foundCourses = new ArrayList<String>();

        if(subjectID < 0)
        {
            throw new SubjectNotFoundException();
        }

        for(int i = 0; i < courseList.length(); i++)
        {
            JSONObject j = courseList.getJSONObject(i);
            if(j.getInt("subject_id") == subjectID)
            {
                foundCourses.add(j.getString("subject_name"));
            }
        }

        return new CourseList(subjectName, foundCourses);
    }

    private CourseList(String name, ArrayList<String> courseList)
    {
        subjectName = name;
        courses = courseList;
    }

    public class SubjectNotFoundException extends Exception
    {

    }
}
