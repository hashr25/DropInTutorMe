<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta id="view" name="viewport" content="width=device-width"/> <!-- maximum-scale=1.0, minimum-scale=1.0, initial-scale=1" /> -->
		<meta name="viewport" content="width=device-width">
		<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
		
		<title>Drop'n'TutorMe: List Tutors</title>
		
		<link rel="stylesheet" type="text/stylesheet" href="css/screen_style.css" />
		<link rel="stylesheet" type="text/stylesheet" href="css/screen_layout_large.css" />
		<link rel="stylesheet" type="text/stylesheet" media="only screen and (min-width:50px) and (max-width:500px)" href="css/screen_layout_small.css" />
		<link rel="stylesheet" type="text/stylesheet" media="only screen and (min-width:501px) and (max-width:800px)" href="css/screen_layout_medium.css" />
		
		<!--[if lt IE 9]>
			<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
	</head>
	
	<?php
		include('core/init.php');
	?>
	
	
	<body>

		<div class="page">
		
			<header>
				<a class="logo" href="index.html"><!-- This will be handled in CSS --></a>
				
				
			</header>

<!-- ------------------------------------------------------------------------------------------------->

									<!--	Enter Code into Here 	-->
			<?php					
			$db = mysqli_connect('mysql1.000webhost.com','a2670294_admin','matt0324','a2670294_tutor1');
			
			if (!$db)
			{
				print("Unable to connect to db");
			}
			
			$sql_query 	=	"SELECT *";
			$sql_query .=	"FROM tutor";
			
			$result 	=	mysqli_query($db, $sql_query);
			
			$output_display = "<div class='tutors'>";
			$row_count = 0;
			
			if(!$result) {
				$output_display .= "MySQL Error #: ".mysqli_errno($db)."<br>";
				$output_display .= "MySQL Error  : ".mysqli_error($db)."<br>";
				$output_display .= "SQL          : ".$sql_query."<br>";
				$output_display .= "MySQL RowsAff: ".mysqli_affected_rows($db)."<br>";
				$output_display .= "</div>";
			} else {
				$output_display .= "Tutors";
				
				$output_display .= '<table border=1 style="color: black">';
				$output_display .= '<tr><td>Name</td><td>E-mail</td><td>Location</td></tr>';
				
				$num_results = mysqli_num_rows($result);
				
				for(int $i = 0; $i < $num_results; $i++ ) {
					if (!($i%2) == 0)
					{
						$
					}
				}
			}
			
			
			?>

<!-- -------------------------------------------------------------------------------------------------->
			
			
			<nav>
				<a href="index.html">About Us</a>
				<a href="index.html">Services</a>
				<a href="mailto:hashr25@mycu.concord.edu?subject=Drop'n'TutorMe">Contact Us</a>
			</nav> <!-- End of nav -->

			
			
			<footer>
			
				<div class="emailer">
					<h4>Keep up to date with the latest news from our company</h4>
					
					<form>
						<input type="email" placeholder="Enter your e-mail here">
						<input type="submit" class="button button-small" value="Sign up">
					</form>
				</div> <!-- end of emailer div -->
				
				<div class="clearfix"></div>
			
				<div class="copyright">
					&copy; First Venture Software
					<a href="mailto:hashr25@mycu.concord.edu?subject=Drop'n'TutorMe">Contact Us</a> 
					<a href="index.html">Services</a>		
					<a href="index.html">About Us</a>
				</div> <!-- End of copyright section -->
				
			</footer> <!-- end of footer -->

			
		</div> <!-- End of Page div -->

	</body>

</html>
