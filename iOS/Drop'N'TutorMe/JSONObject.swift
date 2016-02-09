//
//  JSONObject.swift
//  Drop'N'TutorMe
//
//  Created by Randall Hash on 2/3/16.
//  Copyright © 2016 Randall Hash. All rights reserved.
//

import Foundation

class JSONObject{
    var objectData : Dictionary<String, String>
    
    init(param: String){
        self.objectData = Dictionary<String, String>()
        populateFromString(param)
    }
    
    func printObject(){
        for item in objectData{
            print("\(item.0):\(item.1)")
        }
    }
    
    func append(key: String, value: String){
        objectData[ key ] = value;
    }
    
    func remove(key: String) {
        self.objectData[key] = nil
    }
    
    func findValue(key: String) -> String {
        return self.objectData[key]!
    }
    
    func populateFromString(param: String){
        let keyValuePairStrings = param.componentsSeparatedByString(",")
        
        for keyValuePair in keyValuePairStrings {
            appendFromString(keyValuePair)
        }
    }
    
    func appendFromString(keyValuePairString: String){
        var keyAndValue = keyValuePairString.componentsSeparatedByString("\":\"")
        let key = keyAndValue[0].substringFromIndex(1)
        //var value : String
        
        //if(
        let value = keyAndValue[1].substringToIndex(keyAndValue[1].characters.count-1)
        
        
        //print("Key: \(key)")
        //print("Value: \(value)")
        
        //if(valueNum != nil) {
        //    print("Found it as a number")
        //    append(key, value: valueNum!)
        //} else {
            append(key, value: value)
        //}
    }
        
}