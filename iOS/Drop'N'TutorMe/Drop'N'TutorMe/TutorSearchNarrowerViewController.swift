//
//  TutorSearchNarrowerViewController.swift
//  Drop'N'TutorMe
//
//  Created by Randall Hash on 2/8/16.
//  Copyright © 2016 Randall Hash. All rights reserved.
//

import Foundation
import UIKit

class TutorSearchNarrowerViewController : UIViewController {
    /*
    *  Basic Information
    */
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        txtCollegeName.text = GlobalData.CurrentCollege.name
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
    
    // ////////////////////////////////////////////////////////////////////////////
    /// College Selected
    // ////////////////////////////////////////////////////////////////////////////
    @IBOutlet var txtCollegeName: UILabel!
    
    
    @IBAction func btnBackFromCollegeSelected(sender: UIButton) {
        performSegueWithIdentifier("segueBackFromCollegeSelected", sender: nil)
    }
}