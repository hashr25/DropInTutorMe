<!--
This script is meant to log the user in to the website
-->
<?php
	include '/home/a5055916/public_html/core/init.php';
	include '/home/a5055916/public_html/core/functions/widgets/nav_bar_widget.php';
	include '/home/a5055916/public_html/core/functions/widgets/login_widget.php';

	//if the user is logged in, send them back to the main page, they do not need to sign in
	if (isLoggedIn())
	{
		header("location: index.php");
		exit();
	}

	$error='';

	if($_SERVER["REQUEST_METHOD"] == "POST")
	{
		//first, make sure at lease 1 field is filled out
		if (!empty($_POST['username']) || !empty($_POST['password']))
		{
			$username=$_POST['username'];
			$password=$_POST['password'];
					
			//next, do individual error message for username or password
			if (empty($_POST['username']))
			{
				$error = "Username has not been entered.";
				print($error);
				exit();
			}
			else if (empty($_POST['password']))
			{
				$error = "Password has not been entered.";
				print($error);
				exit();
			}
			
			//next, make sure the username and password are not too long
			if (strlen($username) > 32 || strlen($password) > 32)
			{
				$error = "Username or Password is too long.";
				print($error);
				exit();
			}
			
			//check the database for the login credentials
			$username = mysql_real_escape_string($username);
			$password = md5(mysql_real_escape_string($password));
			$query = mysql_query("SELECT * FROM login WHERE username = '$username' AND password='$password'");
			$rows = mysql_num_rows($query);
					
			//if it was there, login and return to the main page
			if($rows == 1)
			{
				$login = loginToSession($username, $password);
				
				/* uncomment out this block and the ending brace to implement account verification check
				
				//make sure the account is active, otherwise tell them to check their email and try again
				$active_query = mysql_query("SELECT active FROM login WHERE login_id = '$login'");
				$active_result = mysql_result($active_query, 0);
				
				if ($active_result == 0) //not active
				{
					$error = "That account is not active, please activate that account through the email it was registered with: '$email'.";
					print($error);
					exit();
				}	
				else //it's active so try to log in
				{
					
				*/
					//make sure no funky errors happened
					if ($login == false)
					{
						$error = "Failed to sign-in.";
						print($error);
						exit();
					}
					else //login
					{
						$_SESSION['login_id'] = $login;
						header("location: index.php");
						exit();	
					}
					
				//}	
				
			}
			else //it's not there
			{
				$error = "Username or Password is invalid.";
				print($error);
				exit();
			}
		}
		else //there is nothing in either field
		{
			$error = "Username and Password has not been entered.";
			print($error);
			exit();
		}
	}
?>