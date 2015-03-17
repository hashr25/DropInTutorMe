<!--
college registration widget for the website
-->
<head>
	<!CSS to make it look even prettier>
	<style>
		#find_tutor_widget
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

<!-- 
when we run the php script, we don't want to load a new page, so with this,
it will run the script, but the user will not see the php page load
-->
<iframe style="display:none;" name="no_show"></iframe>

<div class="widget" id = "find_tutor_widget">
	<div class="inner" id = "content">
		<form action = "find_tutor.php" method="post" target="no_show">
			<ul style = "list-style-type:none">
				<li>
					These Fields help find the tutor. <br>
					Email is guaranteed match, but name and subject are also available if you do not have the tutor's email. <br>
					Leaving everything empty will simply show all tutors. <br>
				</li>
				<li id = "fields">
					<table style="width:100%">
						<tr style = "text-align: left">
							<td>First Name:</td>
							<td>Last Name:</td>
							<td>Subject:</td>
							<td>Email:</td>
						</tr>
						<tr style = "text-align: left">
							<td><input type = "text" name="first_name"></td>
							<td><input type = "text" name="last_name"></td>
							<td><input type = "text" name="subject"></td>
							<td><input type = "text" name="email"></td>
						</tr>
					</table>
				</li>
				<li>
					<input type = "submit" value="Find Tutor">
				</li>
				<li>
					<select id = "tutor_select" name = "tutor_select">
						<option selected>Choose a tutor</option>
					</select>
				</li>
			</ul>
		</form>
	</div>
</div>