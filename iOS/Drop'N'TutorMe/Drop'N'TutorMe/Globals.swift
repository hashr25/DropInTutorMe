//
//  Globals.swift
//  Drop'N'TutorMe
//
//  Created by Randall Hash on 2/5/16.
//  Copyright © 2016 Randall Hash. All rights reserved.
//

import Foundation

var GlobalData = GlobalDataController()

class GlobalDataController{
    ///Singleton declaration
    static let sharedInstance = GlobalDataController()
    
    var Colleges : [College] = []
    var Tutors : [Tutor] = []
    
    
    private init(){
        ///For whatever reason you need a private init for a singleton
    }
    
    func FillColleges() {
        let colleges : JSONArray = ApiController.GetAllColleges()
        
        for college in colleges.array {
            Colleges.append(College(collegeObject: college))
        }
    }
}


