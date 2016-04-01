//
//  AppContainers.swift
//  Drop'N'TutorMe
//
//  Created by Randall Hash on 2/5/16.
//  Copyright © 2016 Randall Hash. All rights reserved.
//

import Foundation
import UIKit

class College {
    var name : String = ""
    var collegeID : Int = 0
    
    init(){
        ///This is only to allow a hack. Remove if the hack within Globals is corrected
    }
    
    init(collegeObject : JSONObject){
        name = collegeObject.getString("college_name")
        collegeID = collegeObject.getInt("college_id")
    }
}
class Subject {
    var name : String
    var subjectID : Int
    var collegeID : Int
    
    init() {
        name = ""
        subjectID = -1
        collegeID = -1
    }
    
    init(subjectObject : JSONObject) {
        name = subjectObject.getString("subject_name")
        subjectID = subjectObject.getInt("subject_id")
        collegeID = subjectObject.getInt("college_id")
    }
}

class Course {
    var displayText : String
    var courseID : Int
    var subjectID : Int
    var collegeID : Int
    
    init() {
        displayText = ""
        courseID = -1
        subjectID = -1
        collegeID = -1
    }
    
    init(courseObject: JSONObject) {
        displayText = courseObject.getString("display_text")
        courseID =  courseObject.getInt("course_id")
        subjectID = courseObject.getInt("subject_id")
        collegeID = courseObject.getInt("college_id")
    }
}

class Tutor {
    var tutorID : Int
    var firstName : String
    var lastName : String
    var emailAdress : String
    var major : String
    var bio : String
    var building : String
    var room : String
    var courses : String
    var schedule : String
    var pictureURL : String
    var startTimes : [Int]
    var endTimes : [Int]
    var photo : UIImage
    
    init() {
        tutorID = -1
        firstName = ""
        lastName = ""
        emailAdress = ""
        major = ""
        bio = ""
        building = ""
        room = ""
        courses = ""
        schedule = ""
        pictureURL = ""
        photo = UIImage(named: "NoPhoto")!
        startTimes = []
        endTimes = []
    }
    
    init( tutorObject : JSONObject ) {
        tutorID = tutorObject.getInt("tutor_id")
        firstName = tutorObject.getString("first_name")
        lastName = tutorObject.getString("last_name")
        emailAdress = tutorObject.getString("email")
        bio = tutorObject.getString("bio")
        building = tutorObject.getString("building")
        room = tutorObject.getString("room")
        courses = ""
        schedule = ""
        pictureURL = tutorObject.getString("picture").stringByReplacingOccurrencesOfString("\\", withString: "")
        photo = UIImage(named: "NoPhoto")!
        major = ""
        startTimes = []
        endTimes = []
        
        
        ///Get Major
        let subID = tutorObject.getInt("subject_id")
        for subject in GlobalData.Subjects {
            if subject.subjectID == subID {
                major = subject.name
            }
        }
        
        ///Get Course String
        let courseJSON = ApiController.GetTutorCourses(tutorID).array
        for course in courseJSON {
            courses += course.getString("display_text")
            courses += ", "
        }
        courses = courses.substringToIndex(courses.characters.count-2)

        ///Convert all day/times
        var days : [String] = tutorObject.getString("GROUP_CONCAT(day_times.day)").split(",")
        var startStrings : [String] = tutorObject.getString("GROUP_CONCAT(day_times.start_time)").split(",")
        var endStrings : [String] = tutorObject.getString("GROUP_CONCAT(day_times.end_time)").split(",")
        
        
        for index in 0..<days.count {
            startTimes.append(convertTime(days[index], time: startStrings[index]))
            endTimes.append(convertTime(days[index], time: endStrings[index]))
        }
        
        
        ///If there is a picture URL, download the image.
        if(pictureURL.characters.count > 3) {
            self.loadPicture()
        }
        
        makeSchedule()
    }
    
    
    //load picture
    func loadPicture() {
        //print("Attempting to load photo from", pictureURL)
        if let url = NSURL(string: pictureURL) {
            //print("Loaded url")
            if let imgData = NSData(contentsOfURL: url) {
                //print("Loaded data from url")
                photo = UIImage(data: imgData)!
            }
        }
    }
    
    //This method as well as the loadPhoto() method was taken from stackoverflow.com but changed to fit this class
    func getDataFromUrl(url:NSURL, completion: ((data: NSData?, response: NSURLResponse?, error: NSError? ) -> Void)) {
        NSURLSession.sharedSession().dataTaskWithURL(url) { (data, response, error) in
            completion(data: data, response: response, error: error)
            }.resume()
    }
    
    
    private func convertTime(day : String, time : String) -> Int {
        var result = 0
        
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
        
        ///Days
        if(day == "Monday"){
            result += 10000
        } else if (day == "Tuesday") {
            result += 20000
        } else if (day == "Wednesday") {
            result += 30000
        } else if (day == "Thursday") {
            result += 40000
        } else if (day == "Friday") {
            result += 50000
        } else if (day == "Saturday") {
            result += 60000
        } else {
            ///I don't know what else this would come
        }
        
        let timeSplit : [String] = time.split(":")
        let hour = Int(timeSplit[0])
        let minute = Int(timeSplit[1])
        
        result += 100 * hour!
        result += minute!
        
        return result
    }
    
    
    private func makeSchedule() {
        let days : [String] = ["blah", "Monday","Tuesday","Wednesday","Thursday","Friday"]
        
        var tutorSchedule : String = "";
        
        for index in 0..<startTimes.count {
            var timeSlot : String = ""
            
            timeSlot = timeSlot + days[startTimes[index]/10000];
            
            var startTime : Int = startTimes[index]%10000;
            var endTime : Int = endTimes[index]%10000;
            
            var startAmOrPm : String = "AM";
            var endAmOrPm : String = "AM";
            
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
            
            let startHourString : String  = (startTime/100).description
            let endHourString : String = (endTime/100).description
            var startMinuteString : String = (startTime%100).description
            var endMinuteString : String = (endTime%100).description
            
            if(startMinuteString == "0")
            {
                startMinuteString = "00";
            }
            if(endMinuteString == "0")
            {
                endMinuteString = "00";
            }
            
            var startTimeStr : String = startHourString + ":" + startMinuteString
            var endTimeStr : String = endHourString  + ":" + endMinuteString
            
            startTimeStr = startTimeStr + " " + startAmOrPm
            endTimeStr = endTimeStr + " " + endAmOrPm
            
            timeSlot = timeSlot + ": " + startTimeStr + " - " + endTimeStr + ", "
            
            tutorSchedule = tutorSchedule + timeSlot
        } // End of for loop.
        
        tutorSchedule = tutorSchedule.substringToIndex(tutorSchedule.characters.count-2)
        
        schedule = tutorSchedule
    }
}