package com.cs410tutorgroup.findatutor;

/**
 * Created by Conor on 3/16/2015.
 */
import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ApiConnector {

    public JSONArray GetTutors() throws MalformedURLException, IOException, JSONException
    {
        // URL for getting tutors
        String url = "http://tutorapp.net76.net/get_tutors.php";

        JSONArray results = null;

        URL u = new URL(url);

        HttpURLConnection conn = (HttpURLConnection) u.openConnection();

        try
        {
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            //conn.setChunkedStreamingMode(0);

            List<NameValuePair> POSTlist = new ArrayList<NameValuePair>();

            POSTlist.add(new BasicNameValuePair("tag","POOP"));

            OutputStream out = new BufferedOutputStream(conn.getOutputStream());
            writeStream(out, POSTlist);
            out.close();

            conn.connect();

            InputStream in = new BufferedInputStream(conn.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder response = new StringBuilder();
            while(true)
            {
                String s = reader.readLine();
                if(s==null)
                {
                    break;
                }
                else
                {
                    response.append(s);
                }
            }

            Log.d("Response", response.toString());

            results = new JSONArray(cleanString(response.toString()));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            conn.disconnect();
        }

        return results;
    }

    private void writeStream(OutputStream out, List<NameValuePair> l) throws UnsupportedEncodingException, IOException
    {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));

        StringBuilder POSTstring = new StringBuilder();
        boolean onFirstPair = true;

        for(NameValuePair p : l)
        {
            if(onFirstPair)
            {
                onFirstPair = false;
            }
            else
            {
                POSTstring.append("&");
            }

            POSTstring.append(URLEncoder.encode(p.getName(), "UTF-8"));
            POSTstring.append("=");
            POSTstring.append(URLEncoder.encode(p.getValue(), "UTF-8"));
        }

        writer.write(POSTstring.toString());
        writer.flush();
        writer.close();
    }

    private String cleanString(String s)
    {
        int firstIndex = -1, secondIndex = -1;

        firstIndex = s.indexOf("[");
        secondIndex = s.lastIndexOf("]");

        if(firstIndex >= 0 && secondIndex >= firstIndex)
        {
            return s.substring(firstIndex,secondIndex+1);
        }
        else
        {
            return "";
        }
    }
}
