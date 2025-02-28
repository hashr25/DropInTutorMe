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
        
        var splitInObjects : [String] = []
        
        //Loop to create list of Object Strings
        while(workingString.characters.count > 0 ){
            var pastFirstBracket : Bool = false
            var pastSecondBracket : Bool = false
            var objectString : String = ""
            var stringNotEmpty : Bool = workingString.characters.count > 0
            
            //Loops through each letter
            while(!pastSecondBracket && stringNotEmpty){
                let letter = workingString.removeAtIndex(workingString.startIndex)
                
                if(letter == "{"){
                    pastFirstBracket = true
                } else if ( letter == "}") {
                    ///This means it is at the ending bracket
                    pastSecondBracket = true
                    
                    splitInObjects.append(objectString)
                    
                    //print("\n\nObjectString: \(objectString)")
                } else if (pastFirstBracket && !pastSecondBracket ){
                    objectString.append(letter)
                }
                
                stringNotEmpty = workingString.characters.count > 0
            }
        }
        
        for object in splitInObjects{
            let thisObject = JSONObject(param: object)
            append(thisObject)
        }
        
    }// End of func populateFromString(param: String)

}