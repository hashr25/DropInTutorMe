package com.cs410tutorgroup.tutorme;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import com.cs410tutorgroup.findatutor.R;
import com.google.android.gms.common.api.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
