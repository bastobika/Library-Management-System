package com.developerground.services;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.developerground.constants.Constants;
import com.developerground.daos.BookDao;
import com.developerground.entities.Book;
import com.developerground.entities.IssuedBook;

public class BookService {
	    BookDao dao = new BookDao();
	    
		public Map<String,String> issueBook(int userID, int bookID, String action) {
			
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Calendar cal = Calendar.getInstance();
			Date issueDate = cal.getTime();
			cal.add(Calendar.DATE, Constants.ISSUE_PERIOD_IN_DAYS);
			Date returnDate = cal.getTime();
			Map<String,String> map = new HashMap<String,String>();
			String isBookIssued = dao.issueBook(userID,bookID, dateFormat.format(issueDate), dateFormat.format(returnDate),action);
			map.put("isBookIssued", isBookIssued);
			map.put("returnDate", dateFormat.format(returnDate));
			return map;
		}

		public List<Book> viewBooks(int userID, String userType, String action) {
			List<Book> books = new ArrayList<>();
			books = dao.viewBooks(userID, userType,action );
			return books;
		}
		public boolean addBook(String bookName, String authorName, String noOfBooks) {
			int numberOfBooks = Integer.parseInt(noOfBooks);
			boolean isBookAdded = dao.addBook(bookName,authorName,numberOfBooks);
			return isBookAdded;
		}
		public String deleteBook(String ID, String noOfBooks) {
			int bookID =  Integer.parseInt(ID);
			int numberOfBooks = Integer.parseInt(noOfBooks);
			String isBookDeleted = dao.deleteBook(bookID,numberOfBooks);
			return isBookDeleted;
		}
		public String returnBook(int userID, int bookID ) {
			String isBookReturned = dao.returnBook(userID, bookID );
			return isBookReturned;
		}
		public List<IssuedBook> viewIssuedBooks(int userID) {
			List<IssuedBook> issuedBooks = dao.viewIssuedBooks(userID);
			return issuedBooks;
		}	
}
