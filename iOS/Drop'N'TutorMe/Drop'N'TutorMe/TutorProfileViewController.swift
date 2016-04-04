//
//  TutorProfileViewController.swift
//  Drop'N'TutorMe
//
//  Created by Randall Hash on 3/29/16.
//  Copyright © 2016 Randall Hash. All rights reserved.
//

import UIKit

class TutorProfileViewController: UIViewController {
    ///UI Connections
    @IBOutlet weak var txtCollegeName: UILabel!
    @IBOutlet var svCourseScrollView: [UIScrollView]!
    
    //Tutor Profile UI Outlets
    @IBOutlet weak var txtTutorName: UILabel!
    @IBOutlet weak var txtTutorLocation: UITextView!
    @IBOutlet weak var txtMajorLabel: UILabel!
    @IBOutlet weak var txtTutorMajor: UILabel!
    @IBOutlet weak var imgTutorPhoto: UIImageView!
    
    @IBOutlet weak var txtTutorBio: UITextView!
    @IBOutlet weak var txtTutorCourses: UITextView!
    @IBOutlet weak var txtTutorSchedule: UITextView!
    
    
    ///General Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        txtCollegeName.text = GlobalData.CurrentCollege.name
        
        LoadProfile()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    ///Methods for loading profile
    func LoadProfile() {
        GlobalData.CurrentTutor.loadProfileData()
        txtTutorName.text = GlobalData.CurrentTutor.firstName + " " + GlobalData.CurrentTutor.lastName
        
        txtTutorLocation.text = GlobalData.CurrentTutor.building + " " + GlobalData.CurrentTutor.room
        imgTutorPhoto.image = GlobalData.CurrentTutor.photo
        
        if(GlobalData.CurrentTutor.major != "") {
            txtTutorMajor.text = GlobalData.CurrentTutor.major
        } else {
            txtTutorMajor.text = ""
            txtMajorLabel.text = ""
        }
        
        
        txtTutorBio.text = GlobalData.CurrentTutor.bio
        txtTutorCourses.text = GlobalData.CurrentTutor.courses
        txtTutorSchedule.text = GlobalData.CurrentTutor.schedule
        txtTutorBio.selectable = false
        txtTutorCourses.selectable = false
        txtTutorSchedule.selectable = false
    }
    
    
    
    //UI Button Actions
    @IBAction func btnBackFromTutorProfile(sender: UIButton) {
        performSegueWithIdentifier("segueBackFromTutorProfile", sender: nil)
    }

}
