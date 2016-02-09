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
    
    init(){
        ///This is only to allow a hack. Remove if the hack within Globals is corrected
    }
    
    init(collegeObject : JSONObject){
        name = collegeObject.findValue("college_name") 
        id = Int(collegeObject.findValue("college_id") )!
    }
}



class Tutor {
    
}