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
    
    ///This is a stand-alone test suite for all query functions
    ///This is intended to be used just to test query outputs
    ///Only run this is a non-production product. Again, only 
    ///for testing purposes.
    static func QueryTestSuite(){
        //print("Get Tutors Query: \n")
        //let query1 = GetTutors()
        //query1.printArray()
        
        //print("\n\nGet Narrowed Tutors Query: \n")
        //let query2 = GetNarrowedTutors("Concord University", subjectID: 0, courseID: 0)
        //query2.printArray()
        
        //print("\n\nGet Colleges Query: \n")
        let query3 = GetAllColleges()
        query3.printArray()
        
        //print("\n\nGet Subjects Query: \n")
        //let query4 = GetSubjects("Concord University")
        //query4.printArray()
        
        //print("\n\nGet Courses Query: \n")
        //let query5 = GetCourses("Concord University", subjectID: 8)
        //query5.printArray()
        
        //print("\n\nGet Reviews Query: \n")
        //let query6 = GetReviews(36)
        //query6.printArray()
        
        //print("\n\nGet Tutor Courses Query: \n")
        //let query7 = GetTutorCourses(36)
        //query7.printArray()
    }
    
/////////////////////////////////////////////
///
/// PostGet Methods
/// These methods return a JSONArray
///
/////////////////////////////////////////////
    static func GetTutors() -> JSONArray {
        let postRules : [String : String] = [
            "tag":"get_tutors"
        ]
        
        return postGet(postRules)
    }
    
    
    
    static func GetNarrowedTutors(collegeName : String, subjectID: Int, courseID: Int) -> JSONArray {
        let postRules : [String : String] = [
            "tag":"get_tutors_narrowed",
            "college_name":collegeName,
            "subject_id":String(subjectID),
            "course_id":String(courseID)
        ]
        
        return postGet(postRules)
    }
    
    
    
    static func GetAllColleges() -> JSONArray  {
        let postRules : [String : String] = [
            "tag":"get_colleges"
        ]
        
        return postGet(postRules)
    }
    
    
    
    static func GetSubjects(collegeName: String) -> JSONArray {
        let postRules : [String : String] = [
            "tag":"get_subjects",
            "college_name":collegeName
        ]
        
        return postGet(postRules)
    }
    
    
    
    static func GetCourses(collegeName: String, subjectID: Int) -> JSONArray {
        let postRules : [String : String] = [
            "tag":"get_courses",
            "subject_id":String(subjectID)
        ]
        
        return postGet(postRules)
    }
    
    
    
    static func GetReviews(tutorID: Int) -> JSONArray {
        let postRules : [String : String] = [
            "tag":"get_reviews",
            "tutor_id":String(tutorID)
        ]
        
        return postGet(postRules)
    }
    
    
    
    static func GetTutorCourses(tutorID: Int) -> JSONArray {
        let postRules : [String : String] = [
            "tag":"get_tutor_courses",
            "tutor_id":String(tutorID)
        ]
        
        return postGet(postRules)
    }
    
    
    
    static func GetTutorPhoto(tutorID: Int) /* -> data type for photo */ {
        ///Figure something about queuing a link to a picture
    }
    
/////////////////////////////////////////////
///
/// PostPush Methods
/// These methods return a Boolean
///
/////////////////////////////////////////////
    static func AddReview(tutorID: Int, reviewText: String, stars: Float) -> Bool {
        var success : Bool = true;
        
        success = false;
        
        return success;
    }
    
    
    
    static func ReportReview(reviewID: Int, numOfReports: Int) -> Bool {
        var success : Bool = true;
        
        success = false;
        
        return success;
    }
    
    
    
    
    
/////////////////////////////////////////////
///
/// Helper Methods
/// These methods are used in the request methods
///
/////////////////////////////////////////////
    class func WriteQueryString(data: [String:String] ) -> String  {
        var urlEnding : String = ""
        
        for item in data{
            for character in item.0.characters {
                if(character == " ") {
                    urlEnding.appendContentsOf( "+" )
                } else {
                    urlEnding.append(character)
                }
            }
            urlEnding.appendContentsOf("=")
            
            for character in item.1.characters {
                if(character == " ") {
                    urlEnding.appendContentsOf("+")
                } else {
                    urlEnding.append(character)
                }
            }
            urlEnding.appendContentsOf("&")
        }
        
        
        
        urlEnding = urlEnding.substringToIndex(urlEnding.characters.count-1)
        //print("URL Ending: \(urlEnding)")
        
        return urlEnding
    }
    
    
    // This function cleans a json string and takes out the brackets []
    class func CleanString(str: String) -> String {
        return str.stringBetween("[", backChar: "]" )
    }
    
    
    
    // This Function supposedly converts a long string into a JSONArray
    class func parseToJSONArray(text: String) -> JSONArray {
        let array = JSONArray(param: text)
        //array.printArray()
        
        return array
    }
       
    
    
    // Taken from a blog tutorial
    // http://www.brianjcoleman.com/tutorial-post-to-web-server-api-in-swift-using-nsurlconnection/
    // Added the ability to specify parameters to enter as the dataString
    class func postGet(params : Dictionary<String, String>) -> JSONArray {
        let url = NSURL(string:apiURL)
        let cachePolicy = NSURLRequestCachePolicy.ReloadIgnoringLocalCacheData
        let request = NSMutableURLRequest(URL: url!, cachePolicy: cachePolicy, timeoutInterval: 2.0)
        request.HTTPMethod = "POST"
        
        // set Content-Type in HTTP header
        let boundaryConstant = "----------V2ymHFg03esomerandomstuffhbqgZCaKO6jy";
        let contentType = "multipart/form-data; boundary=" + boundaryConstant
        NSURLProtocol.setProperty(contentType, forKey: "Content-Type", inRequest: request)
        
        // set data
        // var dataString = "tag=get_tutors"
        let dataString = WriteQueryString(params)
        
        let requestBodyData = (dataString as NSString).dataUsingEncoding(NSUTF8StringEncoding)
        request.HTTPBody = requestBodyData
        
        // set content length
        //NSURLProtocol.setProperty(requestBodyData.length, forKey: "Content-Length", inRequest: request)
        
        var response: NSURLResponse? = nil
        let reply = try! NSURLConnection.sendSynchronousRequest(request, returningResponse:&response)
        
        let results = NSString(data:reply, encoding:NSUTF8StringEncoding)
        
        let endResult : JSONArray = JSONArray(param: String(results))
        
        //print("Query: \(params["tag"])")
        for param in params {
            //print(param)
        }
        
        //endResult.printArray()
        //print(results)
        
        return endResult
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
    
    public func split(separator: Character) -> [String] {
        var strings : [String] = []
        var startString : Int = 0
        var endString : Int = 0
        
        for char in self.characters {
            if (char == separator) {
                strings.append(self.substringWithRange(startString, end: endString))
                endString += 1
                startString = endString
            }
            else {
                endString += 1
            }
        }
        
        strings.append(self.substringWithRange(startString, end: endString))
        
        return strings
    }
    
    public func stringBetween(frontChar: Character, backChar: Character) -> String {
        var endProduct : String = ""
        //var pastFirst : Bool = false
        //var pastLast : Bool = false
        
        for letter in self.characters{
            if(letter == frontChar){
                ///This just needs to skip it
                //pastFirst = true
            } else if(letter == backChar) {
                //pastLast = true
                return endProduct
            } else {
                endProduct.append(letter)
            }
        }
        
        return endProduct
    }
}