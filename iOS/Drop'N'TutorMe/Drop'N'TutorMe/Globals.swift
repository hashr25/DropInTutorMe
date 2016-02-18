//
//  Globals.swift
//  Drop'N'TutorMe
//
//  Created by Randall Hash on 2/5/16.
//  Copyright © 2016 Randall Hash. All rights reserved.
//

import Foundation

var GlobalData = GlobalDataController()

class GlobalDataController {
    ///Singleton declaration
    static let sharedInstance = GlobalDataController()
    
    var Colleges : [College] = []
    var CurrentCollege : College = College()
    
    var Subjects : [Subject] = []
    var CurrentSubject : Subject = Subject()
    
    var Courses : [Course] = []
    var CurrentCourse : Course = Course()
    
    //var Tutors : [Tutor] = []
    
    
    private init(){
        ///For whatever reason you need a private init for a singleton
        FillColleges()
    }
    
    
    ///////////////////////////
    /// Filling Functions
    ///////////////////////////
    func FillColleges() {
        Colleges.removeAll()
        let colleges : JSONArray = ApiController.GetAllColleges()
        
        for college in colleges.array {
            Colleges.append(College(collegeObject: college))
        }
    }
    
    func FillSubjects() {
        Subjects.removeAll()
        let subjects : JSONArray =  ApiController.GetSubjects(CurrentCollege.name)
        
        subjects.printArray()
        
        for subject in subjects.array {
            Subjects.append(Subject(subjectObject: subject))
        }
    }
    
    func FillCourses() {
        Courses.removeAll()
        let courses : JSONArray = ApiController.GetCourses(CurrentCollege.name, subjectID: CurrentSubject.subjectID)
        
        for course in courses.array {
            Courses.append(Course(courseObject: course))
        }
    }
    
    //////////////////////////
    /// Setting functions
    //////////////////////////
    func SetCollege(collegeName : String) {
        for college in Colleges {
            if(college.name == collegeName) {
                CurrentCollege = college
            }
        }
        
        FillSubjects()
    }
    
    func SetSubject(subjectName : String) {
        if(subjectName == "Any") {
            CurrentSubject.name = ""
            CurrentSubject.collegeID = 0
            CurrentSubject.subjectID = -1
        } else {
            for subject in Subjects {
                if(subject.name == subjectName) {
                    CurrentSubject.name = subject.name
                    CurrentSubject.collegeID = subject.collegeID
                    CurrentSubject.subjectID = subject.subjectID
                }
            }
        }
        
        
        FillCourses()
    }
    
    func SetCourse(courseName : String) {
        for course in Courses {
            if(course.displayText == courseName) {
                CurrentCourse = course
            }
        }
    }
}


