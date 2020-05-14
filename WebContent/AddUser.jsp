<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" 
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add User</title>
</head>
<body>

<br>
<div class="container">
	<div class="jumbotron">
		<p class="h2" > Enter User Details :</p>
		<form action="UsersController"  method="GET">
			<input type="hidden"  name="action"  value="add"  />
			<div class="form-group">
			    <label for="exampleInputEmail1">User Name :</label>
			    <input type="text" class="form-control"  id="exampleInputEmail1" aria-describedby="emailHelp"  name="UserName"> 
			</div>
			<div class="form-group">
			<label for="exampleInputEmail1">User Type :</label>
			  <select class="custom-select" id="inputGroupSelect01"  name="userType">
				    <option value="Admin">Admin</option>
				    <option value="User">User</option>
			  </select>
			  </div>
			  <br>
			 <button type="submit" class="btn btn-primary">Submit</button>
		</form>
	</div>
</div>
	
</body>
</html>