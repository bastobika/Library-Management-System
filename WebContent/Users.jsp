<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" 
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Users</title>
</head>
<body>
<br>
<div class="container">
	<div class="jumbotron">
		<a href="Home.jsp"   class="btn btn-primary"  role="button" > Home </a>
		<br>
		<br>
		<button type="button" class="btn btn-primary"  onClick="location.href='AddUser.jsp'"> Add User  </button> 
		<br>
		<br>
		<c:if test="${isUserAdded }">
			<p class="h4"> User added successfully ! </p>
		</c:if>
		<c:if test="${isUserDeleted }">
			<p class="h4"> User deleted successfully ! </p>
		</c:if>
		<c:if test="${isUserUpdated }">
			<p class="h4"> User updated successfully ! </p>
		</c:if>
		<br>
		<table class="table table-borderless" >
			<caption>List of users</caption>
			<thead class="thead-dark">
				<tr> 
					<th scope="col"> User ID </th>
					<th scope="col"> User Name </th>
					<th scope="col"> User Type </th>
					<th scope="col"> No. Of Books Issued </th>
				</tr>
			 </thead>
	  		<tbody>
				<c:forEach var="user"  items="${users}">
				
					<c:url var="deleteLink"  value="UsersController">
						<c:param name="userID"  value="${user.userID }"/>
						<c:param name="action"  value="delete" />
					</c:url>
					<c:url var="updateLink"  value="UsersController">
						<c:param name="userID"  value="${user.userID }"/>
						<c:param name="action"  value="update" />
					</c:url>
				
					<tr>
						<td> ${user.userID } </td>
						<td> ${user.name }</td>
						<td> ${user.userType }</td>
						<td> ${user.bookIssuedCount }</td>
						
						<c:if test="${user.userType.equalsIgnoreCase('User') }"  >
							<td>		<a href="${deleteLink }"  class="btn btn-primary"  role="button"  >  Delete User</a> </td>
							<td>		<a href="${updateLink }"  class="btn btn-primary"  role="button"  > Update User Status To Admin </a> </td>
						</c:if>
					</tr>
					
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
</body>
</html>