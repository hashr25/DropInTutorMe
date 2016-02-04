//
//  JSONArray.swift
//  Drop'N'TutorMe
//
//  Created by Randall Hash on 2/3/16.
//  Copyright © 2016 Randall Hash. All rights reserved.
//

import Foundation

class JSONArray {
    public var array : [JSONObject] = []
    
    func printArray(){
        for item in array{
            item.printObject()
            print("\n")
        }
    }
    
    func append(newObject : JSONObject){
        array.append(newObject)
    }
    
    func remove(objectToRemove : JSONObject){
        //Will figure out removing
    }
}