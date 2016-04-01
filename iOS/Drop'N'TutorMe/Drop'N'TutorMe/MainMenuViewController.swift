//
//  MainMenuViewController.swift
//  Drop'N'TutorMe
//
//  Created by Randall Hash on 2/8/16.
//  Copyright © 2016 Randall Hash. All rights reserved.
//

import Foundation
import UIKit

class MainMenuViewController : UIViewController {
    /*
    *  Basic Information
    */
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        //ApiController.QueryTestSuite()
        print(GlobalData.CurrentCollege.name)
        
        //Loads colleges if it hasn't been done yet
        if(GlobalData.Colleges.count == 0){
            GlobalData.FillColleges()
            
        }
        GlobalData.CurrentCollege = College()
        
        ///Only does it on the first screen
        if(txtEnterCollege != nil){
            var collegeNames : [String] = []
            
            for college in GlobalData.Colleges{
                collegeNames.append(college.name)
            }
            
            txtEnterCollege.autoCompleteStrings = collegeNames
        }
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    
    
    // ////////////////////////////////////////////////////////////////////////////
    /// Main Menu
    // ////////////////////////////////////////////////////////////////////////////
    @IBOutlet var txtEnterCollege: AutoCompleteTextField!
    
    
    @IBAction func btnCollegeSearch(sender: UIButton) {
        hideKeyboards()
        
        ///Just a place holder until autocomplete and college search has been implemented
        let CollegeNameString = txtEnterCollege.text
        
        GlobalData.SetCollege(CollegeNameString!)
        
        
        if(GlobalData.CurrentCollege.name != ""){
            performSegueWithIdentifier("segueToCollegeSelected", sender: nil)
            
            ///Clear the current college
            //GlobalData.CurrentCollege = College()
        } else {
            let alert = UIAlertController(
                title: "Invalid College",
                message: "That is not a valid college. Please select from autocomplete options.",
                preferredStyle: UIAlertControllerStyle.Alert)
            
            alert.addAction(UIAlertAction(title: "Okay", style: UIAlertActionStyle.Default, handler: nil))
            
            self.presentViewController(alert, animated: true, completion: nil)
        }
        
        
    }
    
    @IBAction func btnOnlineResources(sender: UIButton) {
        performSegueWithIdentifier("segueToOnlineResources", sender: nil)
    }
    
    func hideKeyboards(){
        txtEnterCollege.resignFirstResponder()
    }
    
    override func touchesBegan(touches: Set<UITouch>, withEvent event: UIEvent?) {
        hideKeyboards()
    }

    
}
