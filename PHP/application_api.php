<?php

$dbhost = 'mysql2.000webhost.com';
$dbuser = 'a5055916_team3';
$dbpass = 'bowedog410';
$db = 'a5055916_tutors';

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
}

mysql_close($con);

?>