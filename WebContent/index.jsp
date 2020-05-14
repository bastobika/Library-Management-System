<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" 
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login </title>
</head>
<body>

<br>
<div class="container">
	<div class="jumbotron">
	<p class="h2" > Login Here ! </p>
		<c:if test="${loginFailed }">
			<p class="h4"> Login Failed. Please check your password. </p>
		</c:if>
		
		<form action="Authentication"  method="POST"  >
			  <div class="form-group">
				    <label for="exampleInputEmail1">User Name : </label>
				    <input type="text" class="form-control"  id="exampleInputEmail1" aria-describedby="emailHelp"  name="UserName"> 
			  </div>
			  <div class="form-group">
				    <label for="exampleInputPassword1">Password :</label>
				    <input type="password" class="form-control" id="exampleInputPassword1"  name="password" >
				    <small id="emailHelp" class="form-text text-muted">We'll never share your password with anyone else.</small>
			  </div>
			  <button type="submit" class="btn btn-primary">Submit</button>
		</form>
	</div>
</div>
</body>
</html>