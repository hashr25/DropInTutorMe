//
//  JSONArray.swift
//  Drop'N'TutorMe
//
//  Created by Randall Hash on 2/3/16.
//  Copyright © 2016 Randall Hash. All rights reserved.
//

import Foundation

class JSONArray {
    var array : [JSONObject] = []
    
    init(param: String){
        populateFromString(param)
    }
    
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
    
    // This function cleans a json string and takes out the brackets []
    func cleanString(str: String) -> String {
        return str.stringBetween("[", backChar: "]" )
    }
    
    
    
    // This Function supposedly converts a long string into a JSONArray
    func populateFromString(param: String) {
        var workingString : String = cleanString(param)
        var wordJustMade : Bool = false
        
        var splitInObjects : [String] = []
        
        //Loop to create list of Object Strings
        while(workingString.characters.count > 0 ){
            wordJustMade = false
            var pastFirstBracket : Bool = false
            var pastSecondBracket : Bool = false
            var objectString : String = ""
            
            //Loops through each letter
            while(!pastSecondBracket){
                var letter = workingString.removeAtIndex(workingString.startIndex)
                
                if(letter == "{"){
                    pastFirstBracket = true
                } else if ( letter == "}") {
                    ///This means it is at the ending bracket
                    pastSecondBracket = true
                    wordJustMade = true
                    
                    splitInObjects.append(objectString)
                    
                    //print("\n\nObjectString: \(objectString)")
                } else if (pastFirstBracket && !pastSecondBracket ){
                    objectString.append(letter)
                }
            }
        
            for object in splitInObjects{
                var thisObject = JSONObject(param: object)
                append(thisObject)
            }
        }
    }// End of func populateFromString(param: String)

}