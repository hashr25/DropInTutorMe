package com.cs410tutorgroup.findatutor;

/**
 * Created by Conor on 3/16/2015.
 */
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

public class ApiConnector {


    public JSONArray GetTutors()
    {
        // URL for getting tutors
        String url = "http://tutorapp.net76.net/get_tutors.php";

        HttpEntity httpEntity = null;

        try
        {
            DefaultHttpClient httpClient = new DefaultHttpClient();  // Default HttpClient
            HttpGet httpGet = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpGet);

            httpEntity = httpResponse.getEntity();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Convert HttpEntity into JSON Array
        JSONArray jsonArray = null;

        if (httpEntity != null)
        {
            try
            {
                String entityResponse = EntityUtils.toString(httpEntity);

                Log.e("Entity Response  : ", entityResponse);

                jsonArray = new JSONArray(entityResponse);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return jsonArray;
    }
}
