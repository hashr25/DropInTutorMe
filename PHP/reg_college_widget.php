<!--
college registration widget for the website
-->
<head>
	<!CSS to make it look even prettier>
	<style>
		#reg_col_widget
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

<div class="widget" id = "reg_col_widget">
	<h2><ins><i><font face="arial">Register a College</font></i></ins></h2>
	<div class="inner" id = "content">
		<form action="reg_college.php" method="post">
			<ul style="list-style-type:none">
				<li>
					Note: Keep in mind, you cannot manage more than one college per account. <br>
					Also keep in mind that the zipcode you enter will be how others find your college.
				</li>
				<li>
					College Name (full name please): <br>
					<input type="text" name="college_name">
				</li>
				<li>
					Zipcode: <br>
					<input type="text" name="college_zip">
				</li>
				<li>
					<input type="submit" value="Register College">
				</li>
			</ul>
		</form>
	</div>
</div>