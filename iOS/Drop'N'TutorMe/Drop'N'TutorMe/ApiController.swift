//
//  ApiController.swift
//  Drop'N'TutorMe
//
//  Created by Randall Hash on 2/3/16.
//  Copyright © 2016 Randall Hash. All rights reserved.
//

import Foundation

let apiURL : String = "http://dropintutorme.web44.net/application_api.php"

class ApiController {
    
    static func GetTutors() /* ->  Data Type for Tutors */ {
        
    }
    
    static func GetNarrowedTutors(collegeName : String, subjectID: Int, courseID: Int) /* ->  Data Type for Tutors */ {
        
    }
    
    static func GetAllColleges() /*-> [String]*/  {
        //var endResult = [String]()
        //NSLog(apiURL.stringBetween("[", backChar: "]"))
        NSLog("\nit is hitting this line of code\n")
        let postRules : Dictionary<String, String> = ["tag":"get_colleges"]
        
        NSLog("\n\n\nBefore post()\n\n\n")
        post()
        
        //return nil
    }
    
    static func GetSubjects(collegeName: String) /* -> data type for query */ {
        
    }
    
    static func GetCourses(collegeName: String, subjectID: Int) /* -> data type for query */ {
        
    }
    
    static func GetReviews(tutorID: Int) /* -> data type for query */ {
        
    }
    
    static func AddReview(tutorID: Int, reviewText: String, stars: Float) -> Bool {
        var success : Bool = true;
        
        success = false;
        
        return success;
    }
    
    func ReportReview(reviewID: Int, numOfReports: Int) -> Bool {
        var success : Bool = true;
        
        success = false;
        
        return success;
    }
    
    func GetTutorCourses(tutorID: Int) /* -> data type for query */ {
        
    }
    
    func GetTutorPhoto(tutorID: Int) /* -> data type for photo */ {
        
    }
    
    func WriteQueryString(data: [String:String] ) -> String  {
        var urlEnding : String = ""
        
        for item in data{
            urlEnding.appendContentsOf(item.0)
            urlEnding.appendContentsOf("=")
            urlEnding.appendContentsOf(item.1)
            urlEnding.appendContentsOf("&")
        }
        
        urlEnding.removeAtIndex(urlEnding.endIndex)
        
        return urlEnding
    }
    
    // This function cleans the JSON string from all characters before and after the set brackets []
    class func CleanString(str: String) -> String {
        return str.stringBetween("[", backChar: "]" )n "";
    }
    
    // This Function supposedly converts a long string into a dictionary
    class func convertStringToDictionary(text: String) -> [String:AnyObject]? {
        var workingString : String = text
        
        while workingString.characters.count > 0{
            for letter in workingString.characters{
                var first
            }
        }
        return nil
    }
    
    // Taken from a blog tutorial
    // http://www.brianjcoleman.com/tutorial-post-to-web-server-api-in-swift-using-nsurlconnection/
    // Added the ability to specify parameters to enter as the dataString
    class func post(/*params : Dictionary<String, String>*/) /*-> NSString*/ {
        let url = NSURL(string:apiURL)
        let cachePolicy = NSURLRequestCachePolicy.ReloadIgnoringLocalCacheData
        var request = NSMutableURLRequest(URL: url!, cachePolicy: cachePolicy, timeoutInterval: 2.0)
        request.HTTPMethod = "POST"
        
        // set Content-Type in HTTP header
        let boundaryConstant = "----------V2ymHFg03esomerandomstuffhbqgZCaKO6jy";
        let contentType = "multipart/form-data; boundary=" + boundaryConstant
        NSURLProtocol.setProperty(contentType, forKey: "Content-Type", inRequest: request)
        
        // set data
        var dataString = "tag=get_tutors&"
        /// var dataString = WriteQueryString(params)
        
        let requestBodyData = (dataString as NSString).dataUsingEncoding(NSUTF8StringEncoding)
        request.HTTPBody = requestBodyData
        
        // set content length
        //NSURLProtocol.setProperty(requestBodyData.length, forKey: "Content-Length", inRequest: request)
        
        var response: NSURLResponse? = nil
        var error: NSError? = nil
        let reply = try! NSURLConnection.sendSynchronousRequest(request, returningResponse:&response)
        
        let results = NSString(data:reply, encoding:NSUTF8StringEncoding)
        

        print("API Response: \(results)")
        
        let cleanedString : String = CleanString(results as! String)
        
        print("Cleaned String: \(cleanedString)")
        
        let dict = convertStringToDictionary(cleanedString)
        
        print("Dictionary Response: \(dict)" )
        
        
        //return results!
    }
    
    
}

///////////////////////////////////
/// String stuff
///////////////////////////////////
extension String
{
    func substringFromIndex(index: Int) -> String
    {
        if (index < 0 || index > self.characters.count)
        {
            print("index \(index) out of bounds")
            return ""
        }
        return self.substringFromIndex(self.startIndex.advancedBy(index))
    }
    
    func substringToIndex(index: Int) -> String
    {
        if (index < 0 || index > self.characters.count)
        {
            print("index \(index) out of bounds")
            return ""
        }
        return self.substringToIndex(self.startIndex.advancedBy(index))
    }
    
    func substringWithRange(start: Int, end: Int) -> String
    {
        if (start < 0 || start > self.characters.count)
        {
            print("start index \(start) out of bounds")
            return ""
        }
        else if end < 0 || end > self.characters.count
        {
            print("end index \(end) out of bounds")
            return ""
        }
        let range = Range(start: self.startIndex.advancedBy(start), end: self.startIndex.advancedBy(end))
        return self.substringWithRange(range)
    }
    
    func substringWithRange(start: Int, location: Int) -> String
    {
        if (start < 0 || start > self.characters.count)
        {
            print("start index \(start) out of bounds")
            return ""
        }
        else if location < 0 || start + location > self.characters.count
        {
            print("end index \(start + location) out of bounds")
            return ""
        }
        let range = Range(start: self.startIndex.advancedBy(start), end: self.startIndex.advancedBy(start + location))
        return self.substringWithRange(range)
    }
    
    public func split(frontSeparator: String, backSeparator: String) -> [String] {
        if frontSeparator.isEmpty || backSeparator.isEmpty {
            return [self]
        }
        if var pre = self.rangeOfString(frontSeparator) {
            var parts = [self.substringToIndex(pre.startIndex)]
            while let rng = self.rangeOfString(backSeparator, range: pre.endIndex..<endIndex) {
                parts.append(self.substringWithRange(pre.endIndex..<rng.startIndex))
                pre = rng
            }
            parts.append(self.substringWithRange(pre.endIndex..<endIndex))
            return parts
        } else {
            return [self]
        }
    }
    
    public func stringBetween(frontChar: Character, backChar: Character) -> String {
        var endProduct : String = ""
        var pastFirst : Bool = false
        var pastLast : Bool = false
        
        for letter in self.characters{
            if(letter == frontChar){
                pastFirst = true
            } else if(letter == backChar) {
                pastLast = true
                return endProduct
            } else if(pastFirst && !pastLast) {
                endProduct.append(letter)
            }
        }
        
        return endProduct
    }
}