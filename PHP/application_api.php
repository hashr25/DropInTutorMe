<?php

$dbhost = 'mysql2.000webhost.com';
$dbuser = 'a5055916_team3';
$dbpass = 'bowedog410';
$db = 'a5055916_tutors';

$con = $con = mysql_connect($dbhost,$dbuser,$dbpass);

if (!$con)
{
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
		$first_name = $_POST['first_name'];
		$last_name = $_POST['last_name'];
		$email = $_POST['email'];
		$longitude = $_POST['longitude'];
		$latitude = $_POST['latitude'];
		
		$id_results = mysql_query("SELECT freelance_id FROM freelancers ORDER BY freelance_id DESC");
		
		$new_id = $id_results[0] + 1;
		
		$result = mysql_query("INSERT INTO tutors (freelance_id, first_name, last_name, email, lat_cord, long_cord)
							   VALUES ($new_id, $first_name, $last_name, $email, $latitude, $longitude)");
		
		while($row = mysql_fetch_assoc($result))
		{
			$output[] = $row;
		}
		
		print(json_encode($output));
	}
}

mysql_close($con);

?>