<?php

$dbhost = 'mysql1.000webhost.com';
$dbuser = 'a2670294_admin';
$dbpass = 'matt0324';
$db = 'a2670294_tutor1';

$con = $con = mysql_connect($dbhost,$dbuser,$dbpass);

if (!$con)
{
        print('Could not connect: You suck.');
	die('Could not connect: ' . mysql_error());
}

mysql_select_db($db, $con);

if(isset($_POST['tag']))
{
	if($_POST['tag'] == "get_tutors")
	{
		$result = mysql_query("SELECT * FROM tutors");

		while($row = mysql_fetch_assoc($result))
		{
			$output[]=$row;
		}
		
		print(json_encode($output));
	}
	else if($_POST['tag'] == "add_tutor")
	{
		$first_name = mysql_real_escape_string($_POST['first_name']);
		$last_name = mysql_real_escape_string($_POST['last_name']);
		$email = mysql_real_escape_string($_POST['email']);
		$longitude = mysql_real_escape_string($_POST['longitude']);
		$latitude = mysql_real_escape_string($_POST['latitude']);
		
		$id_results = mysql_query("SELECT freelance_id FROM freelancers ORDER BY freelance_id DESC");

		$result = mysql_query("INSERT INTO freelancers (first_name, last_name, email, lat_cord, long_cord)
							   VALUES ('$first_name', '$last_name', '$email', '$latitude', '$longitude')");
		
                while($row = mysql_fetch_assoc($id_results))
		{
			$output[]=$row;
		}

		print(json_encode($output));
	}
	else if($_POST['tag'] == "get_tutors_narrowed")
	{
		$college_name = mysql_real_escape_string($_POST['college_name']);
		$subject_id = mysql_real_escape_string($_POST['subject_id']);
		$course_id = mysql_real_escape_string($_POST['course_id']);
		
		
	}
	else if($_POST['tag'] == "get_colleges")
	{
		$result = mysql_query("SELECT * FROM colleges");
		
		while($row = mysql_fetch_assoc($result))
		{
			$output[] = $row;
		}
		
		print(json_encode($output);
	}
	else if($_POST['tag'] == "get_subjects")
	{
		$college_name = mysql_real_escape_string($_POST['college_name']);
		
		$result = mysql_query("SELECT * FROM subjects WHERE college_id = (SELECT * WHERE college_name = $college_name");
		
		while($row = mysql_fetch_assoc($result))
		{
			$output[] = $row;
		}
		
		print(json_encode($output);
	}
	else if($_POST['tag'] == "get_freelance_subjects")
	{
		
	}
	else if($_POST['tag'] == "get_reviews")
	{
		
	}
	else if($_POST['tag'] == "get_courses")
	{
		
	}
	else if($_POST['tag'] == "add_review")
	{
		
	}
	else if($_POST['tag'] == "report_review")
	{
		
	}
	else if($_POST['tag'] == "get_tutor_courses")
	{
		
	}
}

mysql_close($con);

?>