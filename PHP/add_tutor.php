<!--
This is the script that is run to add a tutor to the database
-->
<?php
	include '/home/a5055916/public_html/core/init.php';
	include '/home/a5055916/public_html/core/functions/widgets/nav_bar_widget.php';
	include '/home/a5055916/public_html/core/functions/widgets/add_tutor_widget.php';
	
	$error = '';

	//check if user is logged in
	if (isLoggedIn())
	{
		//make sure the user has registered a college
		if (collegeId() != false)
		{
			if($_SERVER["REQUEST_METHOD"] == "POST")
			{
				//first, check if any field was not filled out, if so, log error
				if (empty($_POST['first_name']) || empty($_POST['last_name']) || empty($_POST['email']) || empty($_POST['subject'])
				|| empty($_POST['building']) || empty($_POST['location']) || empty($_POST['mon_start']) || empty($_POST['tues_start'])
				|| empty($_POST['wed_start']) || empty($_POST['thurs_start']) || empty($_POST['fri_start']) || empty($_POST['mon_end'])
				|| empty($_POST['tues_end']) || empty($_POST['wed_end']) || empty($_POST['thurs_end']) || empty($_POST['fri_end'])
				|| empty($_POST['course_1']) || empty($_POST['course_2']) || empty($_POST['course_3']) || empty($_POST['course_4'])
				|| empty($_POST['course_5']))
				{
					$error = "A field was not entered.";
					print($error);
					exit();
				}
				else 
				{
					//sanitize the data
					$f_name = mysql_real_escape_string($_POST['first_name']);
					$l_name = mysql_real_escape_string($_POST['last_name']);
					$email = mysql_real_escape_string($_POST['email']);
					$subject = mysql_real_escape_string($_POST['subject']);
					$building = mysql_real_escape_string($_POST['building']);
					$location = mysql_real_escape_string($_POST['location']);
					$m_start = mysql_real_escape_string($_POST['mon_start']);
					$tu_start = mysql_real_escape_string($_POST['tues_start']);
					$w_start = mysql_real_escape_string($_POST['wed_start']);
					$th_start = mysql_real_escape_string($_POST['thurs_start']);
					$f_start = mysql_real_escape_string($_POST['fri_start']);
					$m_end = mysql_real_escape_string($_POST['mon_end']);
					$tu_end = mysql_real_escape_string($_POST['tues_end']);
					$w_end = mysql_real_escape_string($_POST['wed_end']);
					$th_end = mysql_real_escape_string($_POST['thurs_end']);
					$f_end = mysql_real_escape_string($_POST['fri_end']);
					$course_1 = mysql_real_escape_string($_POST['course_1']);
					$course_2 = mysql_real_escape_string($_POST['course_2']);
					$course_3 = mysql_real_escape_string($_POST['course_3']);
					$course_4 = mysql_real_escape_string($_POST['course_4']);
					$course_5 = mysql_real_escape_string($_POST['course_5']);

					//put the times into an array, for easy access later (indexing is $row_wanted + (0 if start time) or (5 if end time))
					$times = array($m_start, $tu_start, $w_start, $th_start, $f_start,
									$m_end, $tu_end, $w_end, $th_end, $f_end);
									
					//also put courses into an array
					$courses = array($course_1, $course_2, $course_3, $course_4, $course_5);
					
					//make sure the email given is in the format xxx@xxx.xxx
					if (!eregi("^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,3})$", $email))
					{
						$error = "Invalid email address.";
						print($error);
						exit();
					}
					
					//we need to make sure that the tutor's email is unique (only thing besides id that should be unique)
					$query = mysql_query("SELECT * FROM `tutors` WHERE `email` = '$email'");
					$rows = mysql_num_rows($query);
					
					//the tutor is already in the database, so log the error
					if ($rows != 0)
					{
						$error = "That tutor already exists, as indicated by duplicate emails";
						print($error);
						exit();
					}
					else if (invalidTimes($times)) //check if the times had am or pm on the end of them
					{
						$error = "You need to have some form of AM or PM on the end of every time entry.";
						print($error);
						exit();
					}
					else //we insert the tutor into the database and return to home
					{
						//get the college id
						$college_id = collegeId();
						
						//create the tutor
						$tutor_query = "INSERT INTO tutors (`tutor_id`,`first_name`,`last_name`,`email`,`subject`,`college_id`,`building`,`location`) 
													VALUES (NULL,'$f_name','$l_name','$email','$subject','$college_id','$building','$location')";
						$result = mysql_query($tutor_query);
						
						//get the tutor's id
						$tutor_id = mysql_result(mysql_query("SELECT `tutor_id` FROM `tutors` WHERE `email` = '$email'"), 0, 'tutor_id');
						
						//get an array of 5 display string (mon - fri)
						$schedule_times = timeCombine($times);
						
						//if a time is N/A, we need to make it NULL, so it is not added to the database
						for($i = 0; $i < count($schedule_times); $i++)
						{
							$naCheck = strtoupper($schedule_times[$i]);
							
							if ($naCheck == "N/A-N/A")
							{
								$schedule_times[$i] = "";
							}
						}
						
						//create the schedule for the tutor
						$schedule_query = "INSERT INTO schedules (`schedule_id`,`tutor_id`,`monday`,`tuesday`,`wednesday`,`thursday`,`friday`) 
												VALUES (NULL,'$tutor_id','$schedule_times[0]','$schedule_times[1]','$schedule_times[2]','$schedule_times[3]','$schedule_times[4]')";
						$result = mysql_query($schedule_query);
						
						//get the tutor's schedule id
						$schedule_id = mysql_result(mysql_query("SELECT `schedule_id` FROM `schedules` WHERE `tutor_id` = '$tutor_id'"), 0, 'schedule_id');
						
						//link schedule to the tutor
						$tutor_update = "UPDATE `tutors` SET `schedule_id` = '$schedule_id' WHERE `tutor_id` = '$tutor_id'"; 
						$result = mysql_query($tutor_update);
						
						//now, we have to create the courses and add them + the tutor to the junction table
						foreach($courses as $course)
						{
							$naCheck = strtoupper($course);
							
							if ($naCheck != "N/A")
							{
								//first, check to make sure the course does not already exist, if it doesn't create a new one
								$query = mysql_query("SELECT * FROM `courses` WHERE `college_id` = '$college_id' AND `display_text` = '$course'");
								$rows = mysql_num_rows($query);
								
								if ($rows == 0)
								{
									//insert the course
									$course_query = "INSERT INTO courses (`course_id`,`college_id`,`display_text`) 
													VALUES (NULL,'$college_id','$course')";
									$result = mysql_query($course_query);
								}
					
								//get the course ID
								$course_id = mysql_result(mysql_query("SELECT `course_id` FROM `courses` WHERE `display_text` = '$course' AND `college_id` = '$college_id'"), 0, 'course_id');
								
								//tutor has just been created, so no need to check for existing connection_aborted
								//insert the course - tutor junction
								$tutor_course_query = "INSERT INTO tutors_courses (`tutor_course_id`,`tutor_id`,`course_id`) 
																				VALUES (NULL,'$tutor_id','$course_id')";
								$result = mysql_query($tutor_course_query);	
							}
						}
						
						header("location: index.php"); //go back to the main page
						exit();
					}
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