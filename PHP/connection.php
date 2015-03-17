<?php
	
	$dbhost = 'mysql2.000webhost.com';
	$dbuser = 'a5055916_team3';
	$dbpass = 'bowedog410';
	$db = 'a5055916_tutors';

	$conn = mysql_connect($dbhost,$dbuser,$dbpass);
	
	/*if(!$conn)
	{
		die('error connecting to database');
	}
	echo 'you have connected successfully';*/
	
	mysql_select_db($db);
?>