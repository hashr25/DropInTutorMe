<!--
This script will simply log the user out and take the back to the main page
-->
<?php
	session_start();
	session_destroy(); //logout
	header("location: index.php"); //return;
	exit();
?>