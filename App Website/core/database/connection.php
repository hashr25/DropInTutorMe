<?php
	
	$dbhost = 'mysql1.000webhost.com';
	$dbuser = 'a2670294_admin';
	$dbpass = 'matt0324';
	$db = 'a2670294_tutor1';

	$conn = mysql_connect($dbhost,$dbuser,$dbpass);
	
	if(!$conn)
	{
		die('error connecting to database');
	}
	
	mysql_select_db($db);
?>