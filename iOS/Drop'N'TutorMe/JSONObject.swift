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
    
    /**
     * 
     */
    init(param: String){
        self.objectData = Dictionary<String, String>()
        populateFromString(param)
    }
    
    /**
     *
     */
    func printObject(){
        for item in objectData{
            print("\(item.0):\(item.1)")
        }
    }
    
    /**
     *
     */
    func append(key: String, value: String){
        objectData[ key ] = value;
    }
    
    /**
     *
     */
    func remove(key: String) {
        self.objectData[key] = nil
    }
    
    /**
     *
     */
    func getString(key: String) -> String {
        return self.findValue(key)
    }
    
    /**
     *
     */
    func getInt(key: String) -> Int {
        return Int( self.findValue(key) )!
    }
    
    /**
     *
     */
    func getFloat(key: String) -> Float {
        return Float( self.findValue(key) )!
    }
    
    /**
     *
     */
    private func findValue(key: String) -> String {
        return self.objectData[key]!
    }
    
    /**
     *
     */
    func populateFromString(param: String){
        let keyValuePairStrings = param.componentsSeparatedByString("\",\"")
        
        /*
        print("\n\n\nThis is the raw data.")
        for pair in keyValuePairStrings {
            print(pair)
        }
        print("Printing raw data complete.\n\n\n")
        */
 
        for keyValuePair in keyValuePairStrings {
            appendFromString(keyValuePair)
        }
    }
    
    /**
     *
     */
    func appendFromString(keyValuePairString: String){
        var keyAndValue = keyValuePairString.componentsSeparatedByString("\":\"")
        
        let key = cleanQuotes (keyAndValue[0].substringFromIndex(0))
        //var value : String
        
        let value = cleanQuotes(keyAndValue[1].substringToIndex(keyAndValue[1].characters.count))
        
        
        //print("Key: \(key)")
        //print("Value: \(value)")
        
        //if(valueNum != nil) {
        //    print("Found it as a number")
        //    append(key, value: valueNum!)
        //} else {
            append(key, value: value)
        //}
    }
    
    /**
    * Removes quotes from beginning and/or end of string
    * Called from func appendFromString(keyValuePairString: String)
    */
    func cleanQuotes(str : String) -> String {
        var workingString = str
        //print("Trying to clean this string: \(str)")
        
        if(workingString.characters.count != 0) {
            ///Check first character
            if(workingString.characters[workingString.startIndex] == "\"") {
                workingString = workingString.substringFromIndex(1)
            }
            
            ///Check last character
            if(workingString.characters.last == "\"") {
                workingString = workingString.substringToIndex(workingString.characters.count-1)
            }
        }
        
        
        //print("Cleaned the string down to: \(workingString)")
        
        return workingString
    }
        
}