//
//  AppContainers.swift
//  Drop'N'TutorMe
//
//  Created by Randall Hash on 2/5/16.
//  Copyright © 2016 Randall Hash. All rights reserved.
//

import Foundation

class College {
    var name : String = ""
    var collegeID : Int = 0
    
    init(){
        ///This is only to allow a hack. Remove if the hack within Globals is corrected
    }
    
    init(collegeObject : JSONObject){
        name = collegeObject.findValue("college_name") 
        collegeID = Int( collegeObject.findValue("college_id") )!
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
        name = subjectObject.findValue("subject_name")
        subjectID = Int(subjectObject.findValue("subject_id") )!
        collegeID = Int(subjectObject.findValue("college_id") )!
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
        displayText = courseObject.findValue("display_text")
        courseID = Int(courseObject.findValue("course_id") )!
        subjectID = Int(courseObject.findValue("subject_id") )!
        collegeID = Int(courseObject.findValue("college_id") )!
    }
}
/*
class Subject {
    var name : String = ""
    var subjectID : Int = 0
    var collegeID : Int = 0
    
    init() {
        ///This is only to allow a hack. Remove if the hack within Globals is corrected
    }
    
    init(subjectObject : JSONObject) {
        name = subjectObject.findValue("subject_name")
        subjectID = Int( subjectObject.findValue("subject_id") )!
        collegeID = Int( subjectObject.findValue("college_id") )!
    }
}

class Course {
    var displayText : String = ""
    var courseID : Int = 0
    var subjectID : Int = 0
    var collegeID : Int = 0
    
    init() {
        ///This is only to allow a hack. Remove if the hack within Globals is corrected
    }
    
    init(courseObject : JSONObject) {
        displayText = courseObject.findValue("display_text")
        courseID = Int( courseObject.findValue("course_id") )!
        subjectID = Int( courseObject.findValue("subject_id") )!
        collegeID = Int( courseObject.findValue("college_id") )!
    }
}*/

/*class Tutor {
    
}*/