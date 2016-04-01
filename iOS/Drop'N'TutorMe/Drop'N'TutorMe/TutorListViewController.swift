//
//  TutorListViewController.swift
//  Drop'N'TutorMe
//
//  Created by Randall Hash on 3/16/16.
//  Copyright © 2016 Randall Hash. All rights reserved.
//

import UIKit

class TutorListViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    ///View Objects
    @IBOutlet weak var txtCollegeName: UILabel!
    @IBOutlet weak var tableView: UITableView!
    

    var tableData : [Tutor] = []
    
    /// General Methods
    // 1
    override func viewDidLoad() {
        super.viewDidLoad()
        txtCollegeName.text = GlobalData.CurrentCollege.name
        tableData = GlobalData.Tutors
        
        // Register custom cell
        var nib = UINib(nibName: "TutorCell", bundle: nil)
        tableView.registerNib(nib, forCellReuseIdentifier: "tutorCell")
        
        print("There are", tableView.visibleCells.count, "cells visible")
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // 2
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.tableData.count
    }
    
    
    // 3
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell 	{
        
        var cell : TutorCell = self.tableView.dequeueReusableCellWithIdentifier("tutorCell") as! TutorCell
        
        cell.txtTutorName.text = tableData[indexPath.row].firstName + " " + tableData[indexPath.row].lastName
        cell.txtTutorSubject.text = "Major: " + tableData[indexPath.row].major
        cell.imgTutorPicture.image = tableData[indexPath.row].photo
        
        return cell
        
    }
    
    // 4
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        print("Row \(indexPath.row) selected")
        GlobalData.CurrentTutor = tableData[indexPath.row]
        performSegueWithIdentifier("segueToTutorProfile", sender: nil)
    }
    
    // 5
    func tableView(tableView: UITableView, heightForRowAtIndexPath indexPath: NSIndexPath) -> CGFloat {
        return 70
    }


    
    ///Specific Methods for UI
    @IBAction func btnBackFromTutorList(sender: UIButton) {
        performSegueWithIdentifier("SegueBackFromTutorList", sender: nil)
    }
}