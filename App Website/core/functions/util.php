<!--
util.php is meant to have functions that simply make coding easier and more readable in other files
-->
<?php
	//get the login_id from a provided username that has already been sanitized with mysql_real_escape_string()
	function getLoginId($username)
	{
		//the username is unique, so we can use it to find the id
		$query = mysql_query("SELECT `login_id` FROM `login` WHERE `username` = '$username'");
		
		//return the login_id from the first row (it assumes user has been logged-in already and exists)
		return(mysql_result($query, 0, 'login_id'));
	}

	//log the user into the system after their information has been confirmed, return the user_id on success, 
	//false if it fails (assume parameters have been sanitized with mysql_real_escape_string() and the password has been encoded)
	function loginToSession($username, $password)
	{
		$login_id = getLoginId($username);
		$query = mysql_query("SELECT COUNT(`login_id`) FROM `login` WHERE `username` = '$username' AND `password` = '$password'");
		
		//if the combination was found, return the id, else return false
		return(mysql_result($query, 0) == 1) ? $login_id : false;
	}

	//return true if the user is logged in, false if not
	function isLoggedIn()
	{
		return(isset($_SESSION['login_id'])) ? true : false;
	}

	//get the login id of the user currently logged in
	function getCurrentUser()
	{
		return $_SESSION['login_id'];
	}

	//get the college id that is associated with the user, return false if there has been no college connected to the user
	function collegeId()
	{
		$login_id = getCurrentUser();
		$query = mysql_query("SELECT `college_id` FROM `login` WHERE `login_id` = '$login_id'");
		$rows = mysql_num_rows($query);
		
		//if there is no college, return false, else return the college_id
		if ($rows == 0)
		{
			return false;
		}
		else
		{
			return(mysql_result($query, 0, 'college_id'));
		}
	}

	//take an array of times and return true if they all have AM or PM on the end
	function invalidTimes($times)
	{
		foreach ($times as $time)
		{
			//get a sub string of the last 2 characters of the string
			$sub_str = substr($time, -2, 2);
			
			//make the last 2 characters upper case
			$sub_str = strtoupper($substr);
			
			//see if those last 2 characters are AM or PM
			if ($sub_str != "AM" || $sub_str != "PM")
			{
				//we need to make sure n/a was not entered before returning false
				$naCheck = strtoupper($time);
				
				if ($naCheck != "N/A")
				{
					return false;
				}
			}
		}
		
		return true;
	}

	//take an array of 10 times (mon-fri start and end), and parse them together into a 5 slot array ordered by days
	//this will most likely crash if an array of not 10 is passed, so make sure to error check before using!
	function timeCombine($times)
	{
		$new_times = array();
		
		//turning 10 into 5 (0-4), so use a 5
		for ($i = 0; $i < 5; $i++)
		{
			$start = $times[$i];
			$end = $times[$i + 5];
			
			//combine the string into something nice looking
			$comb_str = $start . "-" . $end;
			
			//add new time
			$new_times[$i] = $comb_str;
		}
		
		return $new_times;
	}
?>	