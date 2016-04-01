//
//  TutorSearchNarrowerViewController.swift
//  Drop'N'TutorMe
//
//  Created by Randall Hash on 2/8/16.
//  Copyright © 2016 Randall Hash. All rights reserved.
//

import Foundation
import UIKit

class TutorSearchNarrowerViewController : UIViewController, UIPickerViewDelegate, UIPickerViewDataSource {
    /*
    * UI Objects
    */
    @IBOutlet var txtCollegeName: UILabel!
    
    @IBOutlet var SubjectPicker: UIPickerView!
    @IBOutlet var CoursePicker: UIPickerView!
    @IBOutlet var DayPicker: UIPickerView!
    @IBOutlet var TimePicker: UIPickerView!
    
    
    /*
    *  Basic Information
    */
    var subjects : [String] = ["Any"]
    var courses : [String] = ["Any"]
    let days : [String] = [
                "Any",
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday"
    ]
    var times : [String] = [
        "Any", "10:00 AM",
        "11:00 AM", "12:00 PM",
        "1:00 PM", "2:00 PM",
        "3:00 PM", "4:00 PM",
        "5:00 PM", "6:00 PM"
    ]
    
    /*
    * Standard Functions
    */
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        txtCollegeName.text = GlobalData.CurrentCollege.name
        
        SubjectPicker.delegate = self;
        CoursePicker.delegate = self;
        DayPicker.delegate = self;
        TimePicker.delegate = self;
        
        SubjectPicker.dataSource = self;
        CoursePicker.dataSource = self;
        DayPicker.dataSource = self;
        TimePicker.dataSource = self;
        
        SubjectPicker.tag = 1
        CoursePicker.tag = 2
        DayPicker.tag = 3
        TimePicker.tag = 4
        
        fillSubjectData()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func hideKeyboards(){
        //Text Fields?
    }
    
    override func touchesBegan(touches: Set<UITouch>, withEvent event: UIEvent?) {
        hideKeyboards()
    }
    
    ///Picker View Stuff
    func numberOfComponentsInPickerView(pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        if (pickerView.tag == 1) {
            return subjects.count;
        } else if (pickerView.tag == 2 ) {
            return courses.count;
        } else if (pickerView.tag == 3 ) {
            return days.count;
        } else {
            return times.count;
        }
    }
    
    func pickerView(pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        if (pickerView.tag == 1) {
            return subjects[row]
        } else if (pickerView.tag == 2) {
            return courses[row]
        } else if (pickerView.tag == 3) {
            return days[row]
        } else {
            return times[row]
        }
    }
    
    func pickerView(pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        if (pickerView.tag == 1) {
            if(subjects[row] != "Any") {
                GlobalData.SetSubject(subjects[row])
                fillCourseData()
            } else {
                
            }
            
            CoursePicker.selectRow(0, inComponent: 0, animated: true)
        } else if (pickerView.tag == 2) {
            if(courses[row] != "Any") {
                GlobalData.SetCourse(courses[row])
            }
        } else if (pickerView.tag == 3) {
            //Do Something
        } else {
            //Do Something
        }
    }
    
    // ////////////////////////////////////////////////////////////////////////////
    /// Changing Picker Data
    // ////////////////////////////////////////////////////////////////////////////
    func fillSubjectData() {
        subjects.removeAll()
        subjects.append("Any")
        
        for subject in GlobalData.Subjects {
            subjects.append(subject.name)
        }
    }
    
    func fillCourseData() {
        courses.removeAll()
        courses.append("Any")
        
        for course in GlobalData.Courses {
            courses.append(course.displayText)
        }
        
        CoursePicker.reloadAllComponents()
    }
    
    // ////////////////////////////////////////////////////////////////////////////
    /// College Selected
    // ////////////////////////////////////////////////////////////////////////////
    @IBAction func btnBackFromCollegeSelected(sender: UIButton) {
        performSegueWithIdentifier("segueBackFromCollegeSelected", sender: nil)
    }

    @IBAction func btnTutorSearchNarrower(sender: UIButton) {
        GlobalData.FillTutors()
        
        print("There are \(GlobalData.Tutors.count) tutors")
        
        performSegueWithIdentifier("SegueToTutorList", sender: nil)
    }
    
}