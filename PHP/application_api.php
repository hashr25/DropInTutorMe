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
		$query = "SELECT * FROM tutors";
	
		$result = mysql_query($query) or trigger_error(mysql_error()." in ".$query);
		print mysql_error();

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
		
		$id_query = "SELECT freelance_id FROM freelancers ORDER BY freelance_id DESC";
		$query = "INSERT INTO freelancers (first_name, last_name, email, lat_cord, long_cord)
							   VALUES ('$first_name', '$last_name', '$email', '$latitude', '$longitude')";
		
		$id_results = mysql_query($id_query) or trigger_error(mysql_error()." in ".$id_query);

		$result = mysql_query($query) or trigger_error(mysql_error()." in ".$query);
		print mysql_error();
		
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
		
		
		$query = "";
		
		if($subject_id > 0 && $course_id > 0)	///BOTH SUBJECT AND COURSE SET
		{
			$query = "SELECT DISTINCT tutors.*, GROUP_CONCAT(day_times.start_time), GROUP_CONCAT(day_times.end_time), GROUP_CONCAT(day_times.day)
						FROM tutors 
						JOIN day_times 
						ON tutors.schedule_id = day_times.schedule_id
						WHERE tutor_id IN 
						( SELECT tutor_id FROM tutors_courses WHERE course_id = $course_id)
						AND college_id = ( 
						SELECT college_id
						FROM colleges
						WHERE college_name LIKE  '$college_name' ) 
						GROUP BY tutor_id";
		}
		else if($subject_id > 0 && $course_id == -1) 	///ONLY SUBJECT SET
		{
			$query = "SELECT DISTINCT tutors.*, GROUP_CONCAT(day_times.start_time), GROUP_CONCAT(day_times.end_time), GROUP_CONCAT(day_times.day)
						FROM tutors 
						JOIN day_times 
						ON tutors.schedule_id = day_times.schedule_id
						WHERE tutor_id IN 
						( SELECT tutor_id FROM tutors_courses WHERE course_id IN
							( SELECT course_id FROM courses WHERE subject_id = $subject_id ) )
						AND college_id = ( 
						SELECT college_id
						FROM colleges
						WHERE college_name LIKE  '$college_name' ) 
						GROUP BY tutor_id";
		}
		else 	///NOTHING SET
		{
			$query = "SELECT DISTINCT tutors.* , GROUP_CONCAT(day_times.start_time) , GROUP_CONCAT(day_times.end_time) , GROUP_CONCAT(day_times.day) 
					FROM tutors
					JOIN day_times 
					ON tutors.schedule_id = day_times.schedule_id
					WHERE college_id = ( 
					SELECT college_id
					FROM colleges
					WHERE college_name LIKE  '$college_name' ) 
					GROUP BY tutor_id";
		}
		
		$result = mysql_query($query) or trigger_error(mysql_error()." in ".$query);
		print mysql_error();

		
		while($row = mysql_fetch_assoc($result))
		{
			$output[]=$row;
		}

		print(json_encode($output)); 
	}
	else if($_POST['tag'] == "get_colleges")
	{
		$query = "SELECT * FROM colleges";
	
		$result = mysql_query($query) or trigger_error(mysql_error()." in ".$query);
		print mysql_error();
		
		while($row = mysql_fetch_assoc($result))
		{
			$output[] = $row;
		}
		
		print(json_encode($output));
	}
	else if($_POST['tag'] == "get_subjects")
	{
		$college_name = mysql_real_escape_string($_POST['college_name']);
		
		$query = "SELECT DISTINCT * FROM subjects WHERE college_id IN (SELECT college_id FROM colleges WHERE college_name LIKE '$college_name' )";
		
		$result = mysql_query($query) or trigger_error(mysql_error()." in ".$query);
		print mysql_error();
		
		while($row = mysql_fetch_assoc($result))
		{
			$output[] = $row;
		}
		
		print(json_encode($output));
	}
	else if($_POST['tag'] == "get_freelance_subjects")
	{
		
	}
	else if($_POST['tag'] == "get_reviews")
	{
		$freelance_id = mysql_real_escape_string($_POST['freelance_id']);
		$tutor_id = mysql_real_escape_string($_POST['tutor_id']);
		
		$query = "";
		
		if(isset($tutor_id))
		{
			$query = "SELECT * FROM reviews WHERE tutor_id = '$tutor_id'";
		}
		else
		{
			$query = "SELECT * FROM reviews WHERE freelance_id = '$freelance_id'";
		}
		
		$result = mysql_query($query) or trigger_error(mysql_error()." in ".$query);
		print mysql_error();
		
		while($row = mysql_fetch_assoc($result))
		{
			$output[] = $row;
		}
		
		print(json_encode($output));
	}
	else if($_POST['tag'] == "get_courses")
	{
		$subject_id = mysql_real_escape_string($_POST['subject_id']);
		
		$query = "SELECT * FROM courses WHERE subject_id = '$subject_id' ORDER BY display_text";
		
		$result = mysql_query($query) or trigger_error(mysql_error()." in ".$query);
		print mysql_error();
		
		while($row = mysql_fetch_assoc($result))
		{
			$output[] = $row;
		}
		 
		print(json_encode($output));
	}
	else if($_POST['tag'] == "add_review")
	{
		
	}
	else if($_POST['tag'] == "report_review")
	{
		
	}
	else if($_POST['tag'] == "get_tutor_courses")
	{
		$tutor_id = mysql_real_escape_string($_POST['tutor_id']);
		
		$query = "SELECT * FROM courses WHERE course_id IN (SELECT course_id FROM tutors_courses WHERE tutor_id = '$tutor_id')";
		
		$result = mysql_query($query) or trigger_error(mysql_error()." in ".$query);
		print mysql_error();
		
		while($row = mysql_fetch_assoc($result))
		{
			$output[] = $row;
		}
		
		print(json_encode($output));
	}
	
	
}

mysql_close($con);

?>