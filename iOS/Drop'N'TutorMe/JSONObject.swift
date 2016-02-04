//
//  JSONObject.swift
//  Drop'N'TutorMe
//
//  Created by Randall Hash on 2/3/16.
//  Copyright © 2016 Randall Hash. All rights reserved.
//

import Foundation

class JSONObject{
    internal var data : [String : AnyObject] = Dictionary<String, AnyObject>()
    
    func printObject(){
        for item in data{
            print("\(item.0):\(item.1)")
        }
    }
    
    func append(key: String, value: AnyObject){
        data[key] = value;
    }
    
    func remove(key: String, value: AnyObject) {
        data[key] = nil
    }
}