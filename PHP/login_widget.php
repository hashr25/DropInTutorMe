<!--
sign-in widget for the website
-->
<head>
	<!CSS to make it look even prettier>
	<style>
		#login_widget
		{
			float:left;
			color:black;
			padding:5px;
		}
		#content
		{
			line-height:50px;
		}
	</style>
</head>

<div class = "widget" id = "login_widget">
	<h2><ins><i><font face = "arial">Login</font></i></ins></h2>
	<div class = "inner" id = "content">
		<form action = "login.php" method = "post">
			<ul style = "list-style-type:none">
				<li>
					Username: <br>
					<input type = "text" name = "username">
				</li>
				<li>
					Password: <br>
					<input type = "password" name = "password">
				</li>
				<li>
					<input type = "submit" value = "Sign-In">
				</li>
				<li>
					<a href = "forgot_stuff.php">Did you forget your username or password?</a>
				</li>
				<li>
					<a href = "registration.php">Register</a>
				</li>
			</ul>
		</form>
	</div>
</div>