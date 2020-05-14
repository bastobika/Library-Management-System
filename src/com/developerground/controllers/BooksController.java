package com.developerground.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.developerground.entities.Book;
import com.developerground.entities.IssuedBook;
import com.developerground.services.BookService;

/**
 * Servlet implementation class BooksController
 */
@WebServlet("/BooksController")
public class BooksController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BookService bookService = new BookService();
		HttpSession session = request.getSession();
		String userType = (String) session.getAttribute("userType");
		String userID = (String)session.getAttribute("userID");
		String bookID = null;
		String action = (request.getParameter("action")!= null )? request.getParameter("action"): "view";
		RequestDispatcher dispatcher = null;
		request.setAttribute("userType", userType);
		List<Book> books = bookService.viewBooks(Integer.parseInt((String) session.getAttribute("userID")), userType, action);
		dispatcher = request.getRequestDispatcher("Books.jsp");
		
		if (userType.equalsIgnoreCase("Admin")) {
				boolean isBookAdded = false;
				String isBookDeleted = "N";
				switch (action ) {
				case "add"  :  String bookName = request.getParameter("bookName");
									  String authorName = request.getParameter("authorName");
									  String numberOfIssues = request.getParameter("numberOfIssues");
									  isBookAdded = bookService.addBook(bookName, authorName,numberOfIssues);
									  books = bookService.viewBooks(Integer.parseInt((String) session.getAttribute("userID")), userType, "view");
									  break;
									  
				case "delete"  : bookID = request.getParameter("bookID");
										 String deleteCount = request.getParameter("deleteCount");
										 isBookDeleted = bookService.deleteBook(bookID,deleteCount);
										 books = bookService.viewBooks(Integer.parseInt((String) session.getAttribute("userID")), userType,  "view");
										 break;
				}
				request.setAttribute("isBookAdded", isBookAdded);
				request.setAttribute("isBookDeleted", isBookDeleted);
		}else {
				Map<String,String> issueMap = new HashMap<String,String>();
				List<IssuedBook> issuedBooks = null;
				String isBookReturned = null;
				switch (action ) {
				case "issue" :	bookID = request.getParameter("bookID");
										issueMap= bookService.issueBook(Integer.parseInt(userID), Integer.parseInt(bookID),action);
										issuedBooks = bookService.viewIssuedBooks(Integer.parseInt(userID));
										dispatcher = request.getRequestDispatcher("IssuedBooks.jsp");
										break;
										
				case "reissue" :	bookID = request.getParameter("bookID");
											issueMap= bookService.issueBook(Integer.parseInt(userID), Integer.parseInt(bookID),action);
											issuedBooks = bookService.viewIssuedBooks(Integer.parseInt(userID));
											dispatcher = request.getRequestDispatcher("IssuedBooks.jsp");
											break;
										
				case "return" : bookID = request.getParameter("bookID");
									   isBookReturned = bookService.returnBook(Integer.parseInt(userID), Integer.parseInt(bookID));
									   issuedBooks = bookService.viewIssuedBooks(Integer.parseInt((String) session.getAttribute("userID")));
									   dispatcher = request.getRequestDispatcher("IssuedBooks.jsp");
									   break;
									   
				case "viewIssued" : issuedBooks = bookService.viewIssuedBooks(Integer.parseInt((String) session.getAttribute("userID")));
														 dispatcher = request.getRequestDispatcher("IssuedBooks.jsp");
												         break;
												
				}
				request.setAttribute("issuedBooks",issuedBooks);
				request.setAttribute("isBookReturned", isBookReturned);
				request.setAttribute("issueMap", issueMap);
		}
		request.setAttribute("books",books);
		dispatcher.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
}
