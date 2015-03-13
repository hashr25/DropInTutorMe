<!--
This script will allow a user to register for the website
-->
<?php
	include '/home/a5055916/public_html/core/init.php';
	include '/home/a5055916/public_html/core/functions/widgets/nav_bar_widget.php';
	include '/home/a5055916/public_html/core/functions/widgets/registration_widget.php';
		
	$error = '';

	if($_SERVER["REQUEST_METHOD"] == "POST")
	{
		//first, check if any field was not filled out, if so, log error
		if (empty($_POST['username']) || empty($_POST['password']) || empty($_POST['password_ver']) || empty($_POST['email']))
		{
			$error = "A field was not entered.";
			print($error);
			exit();
		}
		else 
		{
			//sanitize the data and encrypt the password
			$username = mysql_real_escape_string($_POST['username']);
			$password = mysql_real_escape_string($_POST['password']);
			$password = md5($password); //encrypt the password using md5
			$password_ver = mysql_real_escape_string($_POST['password_ver']);
			$password_ver = md5($password_ver); //encrypt the password_ver using md5
			$email = mysql_real_escape_string($_POST['email']);
				
			//first, we need to make sure that both the email and username are unique
			$query = mysql_query("SELECT * FROM login WHERE username = '$username' OR email = '$email'");
			$rows = mysql_num_rows($query);
				
			//if it was not in the db, log the error and exit
			if ($rows != 0)
			{
				$error = "The username or email already exists, try something else.";
				print($error);
				exit();
			}
			else if ($password != $password_ver) //if the password & verification do not match, log error
			{
				$error = "The passwords did not match.";
				print($error);
				exit();
			}
			else if (!eregi("^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,3})$", $email))
			{
				//make sure the email given is in the format xxx@xxx.xxx
				$error = "Invalid email address.";
				print($error);
				exit();
			}
			else if (strlen($username) > 32 || strlen($password) > 32)
			{
				//next, make sure the username and password are not too long
				$error = "Username or Password is too long.";
				print($error);
				exit();
			}
			else //we insert the user into the database
			{
				$sql = "INSERT INTO login (`login_id`,`username`,`password`,`email`) VALUES (NULL,'$username','$password','$email')";
				$result=mysql_query($sql);
				
				//send out the email for account verification
				//not in yet
				
				header("location: index.php"); //go back to the main page
				exit();
			}
		}
	}
?>