<!--
navigation bar widget for the website
-->
<head>
	<!CSS to make it look even prettier>
	<style>
		#header 
		{
			background-color:maroon;
			color:white;
			text-align:center;
			padding:4px;
		}
		#nav 
		{
			line-height:25px;
			height:400px;
			width:200px;
			background-color:silver;
			color:black;
			float:left;
			text-align:left;
			padding:8px;
		}
	</style>
</head>

<body>
	<!Header stuff>
	<div id="header">
		<h1><font face="arial">Tutor App Admin Site</font></h1>
	</div>

	<!Navigation bar on the left of the page>
	<div id="nav">
		<h2><ins><i><font face="arial">Options</font></i></ins></h2> <!header for the options>
		<a href = "login.php">Sign In</a><br>
		<a href = "logout.php">Sign Out</a><br>
		<a href = "reg_college.php">Register a College</a><br>
		<a hreg = "add_tutor.php">Add a Tutor</a><br>
		Drop a Tutor<br>
		Modify a Tutor<br>
		Check Reported Reviews<br>
		Personal Settings<br>
	</div>
</body>