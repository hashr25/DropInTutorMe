package com.cs410tutorgroup.findatutor;

/**
 * Created by Conor on 3/16/2015.
 */
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

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

    String url = "http://tutorapp.net76.net/application_api.php";

    public JSONArray GetTutors() throws MalformedURLException, IOException
    {

        JSONArray results = null;

        URL u = new URL(url);

        HttpURLConnection conn = (HttpURLConnection) u.openConnection();

        try
        {
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");

            List<NameValuePair> POSTlist = new ArrayList<NameValuePair>();

            POSTlist.add(new BasicNameValuePair("tag","get_tutors"));

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

            Log.d("Get Tutors Response", response.toString());

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

    public Boolean AddTutor(String name, String email, String address) throws MalformedURLException, IOException
    {
        Boolean success = true;

        //Connection for geocoding the entered address

        URL geocodeApi = new URL(parseAddressURL(address));

        HttpURLConnection geocodeConn = (HttpURLConnection) geocodeApi.openConnection();

        String latitude = "", longitude = "";

        try
        {
            geocodeConn.setDoOutput(true);
            geocodeConn.setDoInput(true);
            geocodeConn.setRequestMethod("POST");

            geocodeConn.connect();

            InputStream in = new BufferedInputStream(geocodeConn.getInputStream());

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

            Log.d("Geocoding response", response.toString());

            JSONArray geocodeResults = new JSONArray(cleanString(response.toString()));

            latitude = new Double(geocodeResults.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat")).toString();
            longitude = new Double(geocodeResults.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng")).toString();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            success = false;
        }
        finally
        {
            geocodeConn.disconnect();
        }

        //Connection for sending data to the database

        URL u = new URL(url);

        HttpURLConnection conn = (HttpURLConnection) u.openConnection();

        try
        {
            if(latitude != "" && longitude != "")
            {
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestMethod("POST");

                List<NameValuePair> POSTlist = new ArrayList<NameValuePair>();

                POSTlist.add(new BasicNameValuePair("tag", "add_tutor"));
                POSTlist.add(new BasicNameValuePair("first_name", name.substring(0, name.indexOf(" "))));
                POSTlist.add(new BasicNameValuePair("last_name", name.substring(name.indexOf(" "), name.length())));
                POSTlist.add(new BasicNameValuePair("email", email));
                POSTlist.add(new BasicNameValuePair("latitude", latitude));
                POSTlist.add(new BasicNameValuePair("longitude", longitude));

                OutputStream out = new BufferedOutputStream(conn.getOutputStream());
                writeStream(out, POSTlist);
                out.close();

                conn.connect();

                //Retrieve data sent from the server for debugging
                InputStream in = new BufferedInputStream(conn.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder response = new StringBuilder();
                while (true)
                {
                    String s = reader.readLine();
                    if (s == null)
                    {
                        break;
                    } else
                    {
                        response.append(s);
                    }
                }

                Log.d("Add Tutor Response", response.toString());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            success = false;
        }
        finally
        {
            conn.disconnect();
        }

        return success;
    }

    public JSONArray GetAllColleges() throws MalformedURLException, IOException
    {
        JSONArray results = null;

        URL u = new URL(url);

        HttpURLConnection conn = (HttpURLConnection) u.openConnection();

        try
        {
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");

            List<NameValuePair> POSTlist = new ArrayList<>();

            POSTlist.add(new BasicNameValuePair("tag","get_colleges"));

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

            Log.d("Colleges Response", response.toString());

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

    public JSONArray GetSubjects() throws MalformedURLException, IOException
    {
        JSONArray results = null;

        URL u = new URL(url);

        HttpURLConnection conn = (HttpURLConnection) u.openConnection();

        try
        {
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");

            List<NameValuePair> POSTList = new ArrayList<>();

            POSTList.add(new BasicNameValuePair("tag","get_subjects"));
            Log.d("Current college",Globals.selectedCollegeName);
            POSTList.add(new BasicNameValuePair("college_name",Globals.selectedCollegeName));

            OutputStream out = new BufferedOutputStream(conn.getOutputStream());
            writeStream(out,POSTList);
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

            Log.d("Subjects Response", response.toString());

            results = new JSONArray(cleanString(response.toString()));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return results;
    }

    public JSONArray GetCourses(int subject_id) throws MalformedURLException, IOException
    {
        JSONArray results = null;

        URL u = new URL(url);

        HttpURLConnection conn = (HttpURLConnection) u.openConnection();

        try
        {
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");

            List<NameValuePair> POSTList = new ArrayList<>();

            POSTList.add(new BasicNameValuePair("tag","get_courses"));
            Log.d("Current college",Globals.selectedCollegeName);
            POSTList.add(new BasicNameValuePair("subject_id",new Integer(subject_id).toString()));

            OutputStream out = new BufferedOutputStream(conn.getOutputStream());
            writeStream(out,POSTList);
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

            Log.d("Courses Response", response.toString());

            results = new JSONArray(cleanString(response.toString()));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return results;
    }

    //Writes a request URL to the server connection's output stream
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

    //Cleans a JSON response from the server so it can be used as a JSONArray
    private String cleanString(String s)
    {
        int firstIndex = -1, secondIndex = -1;

        /*int firstBracketIndex = s.indexOf("[");
        int secondBracketIndex = s.lastIndexOf("]");

        int firstBraceIndex = s.indexOf("{");
        int secondBraceIndex = s.lastIndexOf("}");

        if(firstBraceIndex < firstBracketIndex)
        {
            firstIndex = firstBraceIndex;
            secondIndex = secondBraceIndex;
        }
        else
        {
            firstIndex = firstBracketIndex;
            secondIndex = secondBracketIndex;
        }*/

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

    //Turns an address into a url request for google's geocoding API
    private String parseAddressURL(String address)
    {
        String baseURL = "https://maps.googleapis.com/maps/api/geocode/json?address=";

        String[] addressPieces = address.split(" ");

        for(int i = 0; i < addressPieces.length; i++)
        {
            String piece = addressPieces[i];
            baseURL += piece;

            if(i != addressPieces.length-1)
            {
                baseURL += "+";
            }
        }

        baseURL += "&";
        baseURL += Globals.API_KEY;

        Log.d("Compiled URL", baseURL);

        return baseURL;
    }
}
