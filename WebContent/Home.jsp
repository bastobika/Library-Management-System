<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" 
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>
</head>
<body>	

<br>
<div class="container">
	<div class="jumbotron">
		<p class="h2" > Hey <c:out value="${userName }" />  ! Welcome to the Virtual Library !</p>
		<br>
		<c:if test="${resetPassword  }">
			<p class="h4"> Reset Your Password</p>
			<form action="UsersController"  method ="POST"  >
				<p class="h5"> Please do not keep the default password. </p>
				<div class="form-group">
				   <label for="exampleInputEmail1">Enter New Password :</label>
				   <input type="password" class="form-control"  id="exampleInputEmail1" aria-describedby="emailHelp"  name="password">
				</div>
				<button type="submit" class="btn btn-primary">Submit</button> 
			</form>
		</c:if>
		<c:if test="${isPasswordResetted }" >
			<p class="h4"> Password reseted successfully ! </p>
		</c:if>
		<br>
		<c:choose>
			<c:when test="${userType.equalsIgnoreCase('Admin') }">
					<c:url var="booksControllerLink"  value="BooksController" >
					<c:param name="userType"  value="${userType }"/>
					<c:param name="action"  value="view"  />
					</c:url>
					<a href="UsersController" class="btn btn-primary"  role="button">View Users </a>
					<br>
					<br>
					<a href="${booksControllerLink }"  class="btn btn-primary"  role="button"> View Books </a>
			</c:when>
			<c:otherwise>
					<c:url var="allBooks"  value="BooksController" >
							<c:param name="userType"  value="${userType }"/>
							<c:param name="action"  value="viewAll"  />
					</c:url>
					<c:url var="viewIssuedBooks"  value="BooksController" >
							<c:param name="userType"  value="${userType }"/>
							<c:param name="action"  value="viewIssued"  />
					</c:url>
					<a href="${allBooks }"  class="btn btn-primary"  role="button" > View All Books </a>    
					<br>
					<br>
					<a href="${viewIssuedBooks }"   class="btn btn-primary"  role="button" > View Issued Books </a>
			</c:otherwise>
		</c:choose>
	</div>
</div>
</body>
</html>