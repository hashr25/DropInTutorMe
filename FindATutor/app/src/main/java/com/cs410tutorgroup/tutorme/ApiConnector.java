package com.cs410tutorgroup.tutorme;

/**
 * Created by Conor on 3/16/2015.
 */
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

    String url = "http://dropintutorme.web44.net/application_api.php";

    public JSONArray GetTutors() throws IOException
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

    public JSONArray GetNarrowedTutors(String collegeName, int subjectID, int courseID) throws IOException
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

            POSTlist.add(new BasicNameValuePair("tag", "get_tutors_narrowed"));
            POSTlist.add(new BasicNameValuePair("college_name", collegeName));
            POSTlist.add(new BasicNameValuePair("subject_id", Integer.toString(subjectID)));
            POSTlist.add(new BasicNameValuePair("course_id", Integer.toString(courseID)));

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

            Log.d("Get Narrowed Response", response.toString());

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

    public JSONArray GetNarrowedFreelanceTutors(int subjectID) throws IOException
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

            POSTlist.add(new BasicNameValuePair("tag", "get_freelance_tutors_narrowed"));
            POSTlist.add(new BasicNameValuePair("subject_id", Integer.toString(subjectID)));

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

            Log.d("Get Narrowed Response", response.toString());

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

    public Boolean AddTutor(String name, String email, String address) throws IOException
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

            latitude = Double.toString(geocodeResults.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat"));
            longitude = Double.toString(geocodeResults.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
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
            if(!latitude.equals("") && !longitude.equals(""))
            {
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestMethod("POST");

                List<NameValuePair> POSTlist = new ArrayList<>();

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

    public JSONArray GetAllColleges() throws IOException
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

    public JSONArray GetSubjects() throws IOException
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

    public JSONArray GetFreelanceSubjects() throws IOException
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

            POSTList.add(new BasicNameValuePair("tag","get_freelance_subjects"));

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

    public JSONArray GetReviews(int tutorID, int freelanceID) throws IOException
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

            POSTList.add(new BasicNameValuePair("tag","get_reviews"));
            POSTList.add(new BasicNameValuePair("tutor_id", Integer.toString(tutorID)));
            POSTList.add(new BasicNameValuePair("freelance_id", Integer.toString(freelanceID)));

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

    public JSONArray GetCourses(int subject_id) throws IOException
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

            POSTList.add(new BasicNameValuePair("subject_id",Integer.toString(subject_id)));
			
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
        finally
        {
            conn.disconnect();
        }
        
        return results;
    }
    
    public Boolean AddReview(int tutorID, int freelanceID, String reviewText, float stars) throws MalformedURLException, IOException
    {
        Boolean success = true;

        URL u = new URL(url);

        HttpURLConnection conn = (HttpURLConnection) u.openConnection();

        try
        {Log.d("AddingReview", "Attempting to add review");
            if (tutorID > 0 || freelanceID > 0)
            {Log.d("Breakpoint0", "Breakpoint 0");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestMethod("POST");

                StringBuilder sb1 = new StringBuilder();
                sb1.append(tutorID);
                String tutorIDStr = sb1.toString();

                StringBuilder sb2 = new StringBuilder();
                sb2.append(freelanceID);
                String freelanceIDStr = sb2.toString();

                StringBuilder sb3 = new StringBuilder();
                sb3.append(stars);
                String starsStr = sb3.toString();

                Log.d("Breakpoint1", "Breakpoint 1");

                List<NameValuePair> POSTlist = new ArrayList<>();

                POSTlist.add(new BasicNameValuePair("tag", "add_review"));
                POSTlist.add(new BasicNameValuePair("tutor_id", tutorIDStr));
                POSTlist.add(new BasicNameValuePair("freelance_id", freelanceIDStr));
                POSTlist.add(new BasicNameValuePair("review_text", reviewText));
                POSTlist.add(new BasicNameValuePair("stars", starsStr));

                OutputStream out = new BufferedOutputStream(conn.getOutputStream());
                writeStream(out, POSTlist);
                out.close();

                conn.connect();

                Log.d("Breakpoint2", "Breakpoint 2");

                //Retrieve data sent from the server for debugging
                InputStream in = new BufferedInputStream(conn.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder response = new StringBuilder();

                Log.d("Breakpoint3", "Breakpoint 3");
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

                Log.d("Add Review Response", response.toString());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            success = false;
        }
        finally
        {Log.d("Breakpoint4", "Breakpoint 4");
            conn.disconnect();
        }

        return success;
    }

    public Boolean ReportReview(int reviewID, int numOfReports) throws MalformedURLException, IOException
    {
        Boolean success = true;

        URL u = new URL(url);

        HttpURLConnection conn = (HttpURLConnection) u.openConnection();

        try
        {Log.d("ReportingReview", "Attempting to report review");
            if (reviewID > 0)
            {
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestMethod("POST");

                List<NameValuePair> POSTlist = new ArrayList<>();

                POSTlist.add(new BasicNameValuePair("tag", "report_review"));
                POSTlist.add(new BasicNameValuePair("review_id", Integer.toString(reviewID)));
                POSTlist.add(new BasicNameValuePair("num_of_reports", Integer.toString(numOfReports)));

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

                Log.d("Report Review Response", response.toString());
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

    public JSONArray getTutorCourses(int tutorID) throws IOException
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

            POSTList.add(new BasicNameValuePair("tag","get_tutor_courses"));
Log.d("Print tutorID", Integer.toString(tutorID));
            POSTList.add(new BasicNameValuePair("tutor_id",Integer.toString(tutorID)));

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

            Log.d("Tutor Courses Response", response.toString());

            results = new JSONArray(cleanString(response.toString()));
        }
        catch(Exception e)
        {Log.d("FailFirst", "Failing in the loading of JSON");
            e.printStackTrace();
        }
        finally
        {
            conn.disconnect();
        }

        return results;
    }

    public Drawable getTutorPhoto(String photoURL)
    {
        Drawable image = null;
        Log.d("LoadingFrom: ", photoURL);

        try
        {
            Log.d("Breakpoint1", "Passing by first attempt at loading image" );

            HttpURLConnection connection = (HttpURLConnection) new URL(photoURL).openConnection();
            connection.connect();
            InputStream input = connection.getInputStream();

            Bitmap x = BitmapFactory.decodeStream(input);

            image = new BitmapDrawable(x);

            if(image == null)
            {
                Log.d("TutorImage", "The image is null");
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return image;
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

        Log.d("url string: ", POSTstring.toString());
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
