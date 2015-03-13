<!--
This is the script that is run to register a college into the database
-->
<?php
	include '/home/a5055916/public_html/core/init.php';
	include '/home/a5055916/public_html/core/functions/widgets/nav_bar_widget.php';
	include '/home/a5055916/public_html/core/functions/widgets/reg_college_widget.php';
	
	$error = '';

	//check if user is logged in
	if (isLoggedIn())
	{
		if($_SERVER["REQUEST_METHOD"] == "POST")
		{
			//first, check if any field was not filled out, if so, log error
			if (empty($_POST['college_name']) || empty($_POST['college_zip']))
			{
				$error = "A field was not entered.";
				print($error);
				exit();
			}
			else 
			{
				//sanitize the data
				$name = mysql_real_escape_string($_POST['college_name']);
				$zipcode = mysql_real_escape_string($_POST['college_zip']);

				//first, we need to make sure that the user does not already have a college on their account
				$login_id = getCurrentUser();
				$query = mysql_query("SELECT * FROM `college` WHERE `login_id` = '$login_id'");
				$rows = mysql_num_rows($query);

				//if the user already has a college registered, log the error
				if ($rows != 0)
				{
					$error = "You already have a college associated with this account, remove it to register a new one.";
					print($error);
					exit();
				}
				else //we insert the college into the database and link the user to the college and vice-versa
				{
					//create college
					$college_insert = "INSERT INTO college (`college_id`,`college_name`,`zipcode`,`login_id`) VALUES (NULL,'$name','$zipcode','$login_id')";
					$result = mysql_query($college_insert);
					
					//link college to the user
					$college_id = mysql_result(mysql_query("SELECT `college_id` FROM `college` WHERE `college_name` = '$name' AND `zipcode` = '$zipcode' AND `login_id` = '$login_id'"), 0, 'college_id');
					$login_update = "UPDATE `login` SET `college_id` = '$college_id' WHERE `login_id` = '$login_id'"; 
					$result = mysql_query($login_update);
					
					header("location: index.php"); //go back to the main page
					exit();
				}
			}
		}
	}
	else //return to main page
	{
		header("location: index.php"); //go back to the main page
		exit();
	}
?>