//
//  ViewController.swift
//  TipCalculatorTutorial
//
//  Created by Randall Hash on 2/1/16.
//  Copyright © 2016 Randall Hash. All rights reserved.
//

import UIKit

class ViewController: UIViewController, UITextFieldDelegate {

    @IBOutlet weak var txtMealCost: UITextField!
    @IBOutlet weak var txtTipPercentage: UITextField!
    
    
    @IBOutlet weak var lblTip: UILabel!
    @IBOutlet weak var lblTotal: UILabel!
    
    var mealCost = ""
    var tipPercentage = ""
    
    var totalTip : Float = 0.0
    var totalMealCost : Float = 0.0
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    @IBAction func btnCalculateAction(sender: UIButton) {
        calculateTip()
        printTip()
        hideKeyboards()
    }
    
    
    @IBAction func btnClearAction(sender: UIButton) {
        clear()
        hideKeyboards()
    }
    
    override func touchesBegan(touches: Set<UITouch>, withEvent event: UIEvent?) {
        hideKeyboards()
    }

    
    func calculateTip(){
        mealCost = txtMealCost.text!
        tipPercentage = txtTipPercentage.text!
        
        let fMealCost = Float(mealCost)
        let fTipPercentage = Float(tipPercentage)
        
        totalTip = fMealCost! * (fTipPercentage! / 100)
        totalMealCost = fMealCost! + totalTip
    }
    
    func printTip(){
        let formatTip = String(format: "%0.2f", totalTip)
        let formatTotal = String(format: "%0.2f", totalMealCost)
        
        lblTip.text = "Tip: $\(formatTip)"
        lblTotal.text = "$\(formatTotal)"
    }
    
    func clear(){
        txtTipPercentage.text = ""
        txtMealCost.text = ""
        
        lblTip.text = "Tip: $0.00"
        lblTotal.text = "$0.00"
        
    }
    
    func hideKeyboards(){
        txtTipPercentage.resignFirstResponder()
        txtMealCost.resignFirstResponder()
    }
}

