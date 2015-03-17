<!--
registration widget for the website
-->
<head>
	<!CSS to make it look even prettier>
	<style>
		#reg_widget
		{
			float:left;
			color:black;
			padding:5px;
		}
		#content
		{
			line-height:25px;
		}
	</style>
</head>

<div class="widget" id = "reg_widget">
	<h2><ins><i><font face="arial">Registration</font></i></ins></h2>
	<div class="inner" id = "content">
		<form action="registration.php" method="post">
			<ul style="list-style-type:none">
				<!-- uncomment this part once email verification has been implemented
				<li>
					Note: once you have registered, you must activate your account through email.
				</li>
				-->
				<li>
					Username: <br>
					<input type="text" name="username">
				</li>
				<li>
					Password: <br>
					<input type="password" name="password">
				</li>
				<li>
					Password Again: <br>
					<input type="password" name="password_ver">
				</li>				
				<li>
					Email: <br>
					<input type="text" name="email">
				</li>
				<li>
					<input type="submit" value="Register">
				</li>
			</ul>
		</form>
	</div>
</div>