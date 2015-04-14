package com.cs410tutorgroup.tutorme;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hashr25 on 3/24/15.
 */
public class CollegeTutor extends Tutor
{
    //Attributes
    public String college;
    public String building;
    public String room;
    public String tutorCourses;
    public String tutorSchedule;

    //Schedule info
    private int MONDAY = 10000;
    private int TUESDAY = 20000;
    private int WEDNESDAY = 30000;
    private int THURSDAY = 40000;
    private int FRIDAY = 50000;

    private int[] startTimes;
    private int[] endTimes;

    //Methods

    /**
     *
     * @param jsonObj This JSON Object comes from the ApiConnector from querying for tutors
     * @return  Returns the Tutor Object
     */
    public static CollegeTutor loadFromJsonObject(JSONObject jsonObj)
    {
        CollegeTutor tutor;
        Tutor baseTutor = Tutor.loadFromJsonObject(jsonObj);

        tutor = copyTutor( baseTutor );

        try
        {
            //ApiTutorPair pair = new ApiTutorPair(tutor);
            //tutor.tutorCourses = "";
            //new getCoursesTask().execute(pair);

            tutor.college = Globals.selectedCollegeName;
            tutor.building = jsonObj.getString("building");
            tutor.room = jsonObj.getString("room");


            //Extract and format the schedule information
            //Day/time format:
            //Time stored as 5 digit integer,
            //Day denoted by adding:
            //Monday: 10000
            //Tuesday: 20000
            //Wednesday: 30000
            //Thursday: 40000
            //Friday: 50000
            //i.e Tuesday at 2:30 p.m. = 21430

            String[] dayStrings = jsonObj.getString("GROUP_CONCAT(day_times.day)").split(",");
            String[] startStrings = jsonObj.getString("GROUP_CONCAT(day_times.start_time)").split(",");
            String[] endStrings = jsonObj.getString("GROUP_CONCAT(day_times.end_time)").split(",");

            //build the lists of start and end times
            tutor.startTimes = new int[dayStrings.length];
            tutor.endTimes = new int[dayStrings.length];
            for(int i = 0; i < dayStrings.length; i++)
            {
                tutor.startTimes[i] = Globals.Utils.timeToInt(dayStrings[i], startStrings[i]);
                tutor.endTimes[i] = Globals.Utils.timeToInt(dayStrings[i], endStrings[i]);
            }
            tutor.startTimes = sortSchedule(tutor.startTimes);
            tutor.endTimes = sortSchedule(tutor.endTimes);

            tutor.tutorSchedule = makeSchedule(tutor);
        }
        catch(Exception e)
        {
            Log.d("CollegeTutorFail", "Failed loading at college tutor level");
            e.printStackTrace();
        }

        return tutor;
    }

    //Checks to see if a time is in this tutor's schedule
    public boolean isTimeInSchedule(int time)
    {
        for(int i = 0; i < startTimes.length; i++)
        {
            if(time >= startTimes[i] && time <= endTimes[i])
            {
                return true;
            }
        }

        return false;
    }

    private static String makeSchedule(CollegeTutor tutor)
    {
        String days[] = {"", "Monday","Tuesday","Wednesday","Thursday","Friday"};

        String tutorSchedule = "";

        for(int i = 0; i < tutor.startTimes.length; i++)
        {
            String timeSlot = "<br>";

            timeSlot = timeSlot + days[tutor.startTimes[i]/10000];

            int startTime = tutor.startTimes[i]%10000;
            int endTime = tutor.endTimes[i]%10000;

            String startAmOrPm = "AM";
            String endAmOrPm = "AM";

            if(startTime > 1200)
            {
                startAmOrPm = "PM";
                if(startTime >= 1300)
                {
                    startTime = startTime - 1200;
                }
            }
            if(endTime >= 1200)
            {
                endAmOrPm = "PM";
                if(endTime >= 1300)
                {
                    endTime = endTime - 1200;
                }
            }

            String startHourString = Integer.toString(startTime/100);
            String endHourString = Integer.toString(endTime/100);
            String startMinuteString = Integer.toString(startTime%100);
            String endMinuteString = Integer.toString(endTime%100);

            if(startMinuteString.equals("0"))
            {
                startMinuteString = "00";
            }
            if(endMinuteString.equals("0"))
            {
                endMinuteString = "00";
            }

            String startTimeStr = startHourString + ":" + startMinuteString;
            String endTimeStr = endHourString  + ":" + endMinuteString;

            startTimeStr = startTimeStr + " " + startAmOrPm;
            endTimeStr = endTimeStr + " " + endAmOrPm;

            timeSlot = timeSlot + ": " + startTimeStr + " - " + endTimeStr;

            tutorSchedule = tutorSchedule + timeSlot;
        }

        Log.d("TutorSchedule", tutorSchedule);

        return tutorSchedule;
    }

    private static int[] sortSchedule(int[] scheduleTimes)
    {//Yes, I seriously did a bubblesort. Its all I could remember off the top of my head.
        int[] sortedArray = scheduleTimes;
        boolean sorted = false;

        while(!sorted)
        {
            sorted = true;
            for(int i = 0; i < sortedArray.length-1; i++)
            {
                if(sortedArray[i] > sortedArray[i+1])
                {
                    int temp = sortedArray[i];
                    sortedArray[i] = sortedArray[i+1];
                    sortedArray[i+1] = temp;
                    sorted = false;
                }
            }
        }

        return sortedArray;
    }

    private static CollegeTutor copyTutor(Tutor tutor)
    {
        CollegeTutor copiedTutor = new CollegeTutor();

        copiedTutor.tutorID = tutor.tutorID;
        copiedTutor.pictureURL = tutor.pictureURL;
        copiedTutor.picture = tutor.picture;
        copiedTutor.firstName = tutor.firstName;
        copiedTutor.lastName = tutor.lastName;
        copiedTutor.bio = tutor.bio;
        copiedTutor.emailAddress = tutor.emailAddress;

        return copiedTutor;
    }

    private static String getTutorCourses(JSONArray jsonArray) throws JSONException
    {
        String tutorCourses = "";

        for(int i = 0; i < jsonArray.length(); i++)
        {
            tutorCourses = tutorCourses + ", " + jsonArray.getString(i);
        }

        return tutorCourses;
    }

    public static class ApiTutorPair
    {
        ApiConnector api;
        CollegeTutor tutor;

        ApiTutorPair(CollegeTutor tutor)
        {
            api = new ApiConnector();
            this.tutor = tutor;
        }
    }

    private static class getCoursesTask extends AsyncTask<ApiTutorPair, Long, String>
    {
        @Override
        protected String doInBackground(ApiTutorPair... params)
        {
            {
                try
                {
                    JSONArray array = params[0].api.getTutorCourses(params[0].tutor.tutorID);
                    String tutorCourses = params[0].api.getCourses(array);
                    params[0].tutor.tutorCourses = tutorCourses;
                    return tutorCourses;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        }
    }

}
