//
//  ViewController.swift
//  Drop'N'TutorMe
//
//  Created by Randall Hash on 2/2/16.
//  Copyright © 2016 Randall Hash. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    
    /*
     *  Basic Information
     */

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        ApiController.GetAllColleges()
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    
    
    // ////////////////////////////////////////////////////////////////////////////
    /// Main Menu
    // ////////////////////////////////////////////////////////////////////////////
    @IBAction func btnCollegeSearch(sender: UIButton) {
        ///Just a place holder until autocomplete and college search has been implemented
        performSegueWithIdentifier("segueToCollegeSelected", sender: nil)
    }
    
    @IBAction func btnOnlineResources(sender: UIButton) {
        performSegueWithIdentifier("segueToOnlineResources", sender: nil)
    }
    
    
    
    // ////////////////////////////////////////////////////////////////////////////
    /// Online Resources
    // ////////////////////////////////////////////////////////////////////////////
    @IBAction func btnBackFromOnlineResources(sender: UIButton) {
        performSegueWithIdentifier("segueBackFromOnlineResources", sender: nil)
    }
    
    
    @IBAction func btnSmarthinking(sender: UIButton) {
        if let url = NSURL(string: "http://www.smarthinking.com") {
            UIApplication.sharedApplication().openURL(url)
        }
    }
    
    @IBAction func btnKhanAcademy(sender: UIButton) {
        //let url = NSURL(string: "www.khanacademy.com")!
        //UIApplication.sharedApplication().openURL(url)
        //UIApplication.sharedApplication().openURL(NSURL(string: "www.khanacademy.org")!)
        if let url = NSURL(string: "http://www.khanacademy.org") {
            UIApplication.sharedApplication().openURL(url)
        }
        
    }
    
    @IBAction func btnMITOpenCourseWare(sender: UIButton) {
        //let url = NSURL(string: "ocw.mit.edu")!
        //UIApplication.sharedApplication().openURL(url)
        //UIApplication.sharedApplication().openURL(NSURL(string: "ocw.mit.edu")!)
        if let url = NSURL(string: "http://ocw.mit.edu") {
            UIApplication.sharedApplication().openURL(url)
        }
    }

    // ////////////////////////////////////////////////////////////////////////////
    /// College Selected
    // ////////////////////////////////////////////////////////////////////////////
    @IBAction func btnBackFromCollegeSelected(sender: UIButton) {
        performSegueWithIdentifier("segueBackFromCollegeSelected", sender: nil)
    }
    

}

