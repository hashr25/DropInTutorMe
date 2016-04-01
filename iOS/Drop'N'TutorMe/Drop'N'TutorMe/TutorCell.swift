//
//  TutorCell.swift
//  Drop'N'TutorMe
//
//  Created by Randall Hash on 3/16/16.
//  Copyright © 2016 Randall Hash. All rights reserved.
//

import UIKit

class TutorCell: UITableViewCell {
    @IBOutlet var txtTutorName: UILabel!
    @IBOutlet var txtTutorSubject: UILabel!
    @IBOutlet var imgTutorPicture: UIImageView!

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
