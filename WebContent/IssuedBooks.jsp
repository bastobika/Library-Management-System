<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" 
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>My Issued Books</title>
</head>
<body>
<br>
<div class="container">
	<div class="jumbotron">
			<a href="Home.jsp"   class="btn btn-primary"  role="button" > Home </a>
			<br>
			<br>
			<c:if test="${isBookReturned eq 'Book returned successfully !'}" 	 >
				<p class="h4"> ${isBookReturned  }</p>
			</c:if>
			<br>
			<c:choose>
					<c:when test="${issueMap.isBookIssued eq 'Y' }">
						<c:set var="returnDate"  value="${issueMap.returnDate }"  />
						<p class="h4"> Book issued successfully ! The return date is ${ returnDate}.</p>
					</c:when>
					<c:when test= "${issueMap.isBookIssued eq  'Maximum Reissue Reached' }" >
						<p class="h4"> You have reached the maximum reissue count for this book.  </p>
					</c:when>
					<c:when test= "${issueMap.isBookIssued eq  'Sorry book is not available at this moment !' }" >
						<p class="h4"> ${issueMap.isBookIssued  } </p>
					</c:when>
					<c:when test= "${issueMap.isBookIssued eq  'You cannot issue more than 4 books at a time !' }" >
						<p class="h4"> ${issueMap.isBookIssued  } </p>
					</c:when>
					<c:when test= "${issueMap.isBookIssued eq  'You cannot reissue this book because you have exceeded the return date.' }" >
						<p class="h4"> ${issueMap.isBookIssued  } </p>
					</c:when>
			</c:choose>
			<br>
			<c:choose>
				<c:when test="${issuedBooks.size() > 0 }"  >
					<table class="table table-borderless" >
						<caption>List of issued books</caption>
						<thead class="thead-dark">
						<tr>
							<th scope="col"> Book ID</th>
							<th scope="col"> Book Name </th>
							<th scope="col"> Author Name </th>
							<th scope="col"> Issue Date </th>
							<th scope="col"> Return Date </th>
							<th scope="col"> Number Of Times You Can Reissue </th>
						</tr>
						<c:forEach var="issuedBook"  items="${issuedBooks }">
						
							<tr>
							
								<td> ${issuedBook.bookID} </td>
								<td> ${issuedBook.bookName} </td>
								<td> ${issuedBook.authorName} </td>
								<td> ${issuedBook.issueDate} </td>
								<td> ${issuedBook.returnDate} </td>
								<td> ${issuedBook.reissueCount} </td>
								
								<td>
										<c:if test="${issuedBook.reissueCount > 0 }"  >
											<form method="POST"   action="BooksController" >
													<input type="hidden"  name="action"  value="reissue"  />
													<input type="hidden"  name="bookID"  value="${issuedBook.bookID}"  />
													<button type="submit" class="btn btn-primary">Reissue</button>
											</form>  
										</c:if>
								</td>
								<td>
										<form method="POST"   action="BooksController" >
													<input type="hidden"  name="action"  value="return"  />
													<input type="hidden"  name="bookID"  value="${issuedBook.bookID}"  />
													<button type="submit" class="btn btn-primary">Return</button>
										</form> 
								</td>
							
							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
						<p class="h4"> You have not issued any book. View all books available !</p>
				</c:otherwise>
			</c:choose>
	</div>
</div>
</body>
</html>