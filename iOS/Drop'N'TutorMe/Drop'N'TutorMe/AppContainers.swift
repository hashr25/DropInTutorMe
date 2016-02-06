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
    var id : Int = 42
    
    init(collegeObject : JSONObject){
        name = collegeObject.findValue("college_name") as! String
        id = Int(collegeObject.findValue("college_id") as! NSNumber)
    }
}



class Tutor {
    
}