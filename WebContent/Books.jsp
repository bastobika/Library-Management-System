<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" 
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Books</title>
</head>
<body>
<br>
<div class="container">
	<div class="jumbotron">
		<a href="Home.jsp"   class="btn btn-primary"  role="button" > Home </a>
		<br>
		<br>
		<c:if test="${userType.equalsIgnoreCase('Admin')}" >
			<button type="button"  class="btn btn-primary"  onClick="location.href='AddBook.jsp'"> Add Book </button>
		</c:if>
		<br>
		<br>
		<c:if test="${isBookAdded }">
			<p class="h4"> Book added successfully !</p>
		</c:if>
		<c:choose>
				<c:when test="${isBookDeleted eq 'Y' }">
					<p class="h4"> Book deleted successfully !</p>
				</c:when>
				<c:when test= "${isBookDeleted eq  'CountError' }" >
					<p class="h4"> Number of books you want to delete should be lower than the number of available issues. </p>
				</c:when>
		</c:choose>
		<br>
		<c:choose>
			<c:when test="${books.size()> 0 }" >
				<table class="table table-borderless">
				<caption>List of books</caption>
				<thead class="thead-dark">
					<tr> 
						<th scope="col"> Book ID </th>
						<th scope="col"> Book Name </th>
						<th scope="col"> Author Name </th>
						<c:if test="${userType.equalsIgnoreCase('Admin')}" >
								<th scope="col"> Total No. of Issues </th>
						</c:if>
						<th scope="col"> No. Of Available Issues </th>
					</tr>
					<c:forEach var="book"  items="${books}">
				
					<tr>
						<td> ${book.bookID } </td>
						<td> ${book.bookName }</td>
						<td> ${book.authorName }</td>
						<c:if test="${userType.equalsIgnoreCase('Admin')}" >
								<td> ${book.totalNumberOfIssues }</td>
						</c:if>
						<td> ${book.availableNumberOfIssues }</td>
						<c:choose>
								<c:when test="${userType.equalsIgnoreCase('Admin')}" >
										<td>
												<form action="BooksController"  method="GET" > 
													<div class="form-group">
															<input type="hidden"  name="bookID"  value="${book.bookID }"  />
															<input type="hidden"  name="action"  value="delete"  />
				    										<label for="exampleInputEmail1">Enter Number Of Issues You Want To Remove :  </label>
				    										<input type="text" class="form-control"  id="exampleInputEmail1" aria-describedby="emailHelp"  name="deleteCount"> 
													</div>
													<button type="submit" class="btn btn-primary">Delete Books</button>
												</form>
										</td>
								</c:when>
								<c:otherwise>
										<td>
												<form action="BooksController"  method="GET">
														<input type="hidden"  name="action"  value="issue" />
														<input type="hidden"  name="bookID"  value="${book.bookID }"  />
													<button type="submit" class="btn btn-primary">Issue Book</button>
												</form>
										</td>
								</c:otherwise>
						</c:choose>
					</tr>
					</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${userType.equalsIgnoreCase('Admin')}" >
							<p class="h4"> No books are available in the library. Add some books ! </p>
					</c:when>
					<c:otherwise>
							<p class="h4"> No books are available in the library.   </p>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</div>
</div>
</body>
</html>