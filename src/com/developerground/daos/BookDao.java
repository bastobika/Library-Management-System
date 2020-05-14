package com.developerground.daos;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.developerground.constants.Constants;
import com.developerground.entities.Book;
import com.developerground.entities.IssuedBook;

public class BookDao {

	public boolean addBook(String bookName, String authorName, int numberOfBooks){
		
		boolean isBookAdded = false;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementsystem?useSSL=false","Bastobika Guha","bastobika" );
				Statement stmt = conn.createStatement(); ){
			
				String query = "select * from book where Book_Name='"+bookName+"' and Author_Name='"+authorName+"'";
				ResultSet result = stmt.executeQuery(query);
				if (result.next()) {
					int totalNumberOfIssues = result.getInt("Total_Count")+ numberOfBooks;
					int availableIssues = result.getInt("Availability_Count")+ numberOfBooks;
					int bookID = result.getInt("Book_ID");
					query = "update book set Total_Count="+totalNumberOfIssues+", Availability_Count="+availableIssues+" where Book_ID="+bookID;
				}else {
					query="insert into book (Book_Name,Author_Name,Total_Count,Availability_Count ) values('"+bookName+"','"+authorName+"',"+numberOfBooks+","+numberOfBooks+")";
				}
				int rowsAffected = stmt.executeUpdate(query);
				if (rowsAffected > 0 ) {
					isBookAdded = true;
				}
		} catch (SQLException e) {
			System.out.println("DB connection error !");
			e.printStackTrace();
		}
		return isBookAdded;
	}

	public String deleteBook(int bookID, int numberOfBooks) {
		
		String isBookDeleted = "N";
		int totalNumberOfIssues = 0;
		int availableNumberOfIssues = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementsystem?useSSL=false","Bastobika Guha","bastobika" );
				Statement stmt = conn.createStatement();){
			
				String query = "select * from book where Book_ID="+bookID;
				ResultSet result = stmt.executeQuery(query);
				if(result.next()) {
					if (result.getInt("Availability_Count" )>= numberOfBooks ) {
						totalNumberOfIssues = result.getInt("Total_Count" )- numberOfBooks;
						availableNumberOfIssues = result.getInt("Availability_Count" )- numberOfBooks;
						if (totalNumberOfIssues != 0) {
							query = "update book set Total_Count="+totalNumberOfIssues+",Availability_Count="+availableNumberOfIssues+" where Book_ID="+bookID;
						}else {
							query = "delete from book where Book_ID="+bookID;
						}
						int rowsAffected = stmt.executeUpdate(query);
						if (rowsAffected > 0 ) {
							isBookDeleted = "Y";
						}
					}else {
						isBookDeleted = "CountError";
					}
				}
		} catch (SQLException e) {
			System.out.println("DB connection error !");
			e.printStackTrace();
		}
		return isBookDeleted;
		
	}

	public List<Book> viewBooks(int userID, String userType, String action ) {
		
		List<Book> books = new ArrayList<Book>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementsystem?useSSL=false","Bastobika Guha","bastobika" );
				Statement stmt = conn.createStatement();){
			
				String query = null;
				ResultSet result = null;
				if (userType.equalsIgnoreCase("Admin") ) {
					query = "select * from book";
				}else if(userType.equalsIgnoreCase("User")&& action.equalsIgnoreCase("viewAll")){
					query = "select * from book where Book_ID not in ( select Book_ID from user_book where User_ID="+userID+")";
				}
				if (query != null && query != "") {
					result = stmt.executeQuery(query);
				}
				if (result != null ) {
					while(result.next()) {
						Book book = new Book();
						book.setBookID(result.getInt("Book_ID"));
						book.setBookName(result.getString("Book_Name"));
						book.setAuthorName(result.getString("Author_Name"));
						book.setTotalNumberOfIssues(result.getInt("Total_Count"));
						book.setAvailableNumberOfIssues(result.getInt("Availability_Count"));
						
						books.add(book);
					}
				}
		} catch (SQLException e) {
			System.out.println("DB connection error !");
			e.printStackTrace();
		}
		return books;
	}

	public String issueBook(int userID, int bookID, String issueDate,String returnDate, String action) {

		String isBookIssued = "N"; 
		int reissueCount = Constants.REISSUE_COUNT;
		int maxIssueCount = Constants.MAX_ISSUE_COUNT;
		int rowsAffected = 0;
		int availabilityCount = 0;
		int bookIssuedCount = 0;
		boolean reissueValidity = false;
		if (action.equalsIgnoreCase("reissue")) {
			reissueValidity = checkReissueValidity(userID,bookID);
		}
		
		if (reissueValidity) {
			isBookIssued = "You cannot reissue this book because you have exceeded the return date.";
		}else {
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementsystem?useSSL=false","Bastobika Guha","bastobika" );
					Statement stmt = conn.createStatement();){
				
					String query = "select * from user_book where User_ID="+userID+" and Book_ID="+bookID;
					ResultSet result = stmt.executeQuery(query);
					if ( result.next()) {
						reissueCount = result.getInt("Reissue_Count")- 1;
					}
					if (reissueCount > 0 ) {
						
						if (action.equalsIgnoreCase("issue")) {
								
							query = "select Availability_Count from book where Book_ID="+bookID;
							result = stmt.executeQuery(query);
							while (result.next()) {
									availabilityCount = result.getInt("Availability_Count")-1;
							}
							
							query = "select Book_Issued_Count from user where User_ID="+userID;
							result = stmt.executeQuery(query);
							while (result.next()) {
								bookIssuedCount = result.getInt("Book_Issued_Count");
							}
							if (availabilityCount >= 0 && bookIssuedCount < maxIssueCount) {
									query = "update book set Availability_Count="+availabilityCount+"  where Book_ID="+bookID;
									int flag1 = stmt.executeUpdate(query);
									
									query = "update user set Book_Issued_Count="+(bookIssuedCount+1)+" where User_ID="+userID;
									int flag2 = stmt.executeUpdate(query);
									
									query = "insert into user_book values ("+userID+","+bookID+",str_to_date('"+issueDate+"','%d-%m-%Y'),str_to_date('"+returnDate+"','%d-%m-%Y'),"+reissueCount+")";
									rowsAffected = stmt.executeUpdate(query);
							}		
						}else {
								query ="update user_book set Issue_Date=str_to_date('"+issueDate+"','%d-%m-%Y'),Return_Date=str_to_date('"+returnDate+"','%d-%m-%Y'),Reissue_Count="+reissueCount+" where User_ID="+userID+" and Book_ID="+bookID;
								rowsAffected = stmt.executeUpdate(query);
						}
						
					}
					if (rowsAffected > 0 ) {
						isBookIssued = "Y";
					}else if (reissueCount == 0 ) {
						isBookIssued = "Maximum Reissue Reached";
					}else if (availabilityCount < 0) {
						isBookIssued = "Sorry book is not available at this moment !";
					}else if (bookIssuedCount == maxIssueCount) {
						isBookIssued = "You cannot issue more than 4 books at a time !";
					}
			} catch (SQLException e) {
				System.out.println("DB connection error !");
				e.printStackTrace();
			}
			
		}
		return isBookIssued;
	}


	public String returnBook(int userID, int bookID) {
		
		String isBookReturned= "N";
		int rowsAffected = 0;
		int bookIssuedCount = 0;
		int issuesAvailabilityCount = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementsystem?useSSL=false","Bastobika Guha","bastobika" );
				Statement stmt = conn.createStatement();){
			
			/*	boolean isReturnDateCrossed = true;
				String query = "select Return_Date from user_book where User_ID="+userID+" and Book_ID="+bookID;
				ResultSet result = stmt.executeQuery(query);
				while(result.next()) {
					Date returnDate = result.getDate("Return_Date");
					Calendar cal = Calendar.getInstance();
					cal.after(returnDate);
				}*/
				String query = "delete from user_book where User_ID="+userID+" and Book_ID="+bookID;
				rowsAffected = stmt.executeUpdate(query);
				
				query = "select Book_Issued_Count from user where User_ID="+userID;
				ResultSet result = stmt.executeQuery(query);
				while (result.next()) {
					bookIssuedCount = result.getInt("Book_Issued_Count");
				}
				query = "update user set Book_Issued_Count="+(bookIssuedCount-1)+" where User_ID="+userID;
				int flag1 = stmt.executeUpdate(query);
				
				query = "select Availability_Count from book where Book_ID="+bookID;
				result = stmt.executeQuery(query);
				while (result.next()) {
					issuesAvailabilityCount = result.getInt("Availability_Count");
				}
				query = "update book set Availability_Count="+(issuesAvailabilityCount+1)+" where Book_ID="+bookID;
				int flag2 = stmt.executeUpdate(query);
				
				if(rowsAffected > 0 ) {
					isBookReturned="Book returned successfully !";
				}
		} catch (SQLException e) {
			System.out.println("DB connection error !");
			e.printStackTrace();
		}
		return isBookReturned;
	}

	public List<IssuedBook> viewIssuedBooks(int userID) {

		List<IssuedBook> issuedBooks = new ArrayList<IssuedBook>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementsystem?useSSL=false","Bastobika Guha","bastobika" );
				Statement stmt = conn.createStatement();){
			
				String query = "select * from user_book,book where user_book.User_ID="+userID+" and book.book_ID=user_book.book_ID";
				ResultSet result = stmt.executeQuery(query);
				while(result.next()) {
						IssuedBook issuedBook = new IssuedBook();
						issuedBook.setUserID(result.getInt("User_ID"));
						issuedBook.setBookID(result.getInt("Book_ID"));
						issuedBook.setIssueDate(result.getDate("Issue_Date"));
						issuedBook.setReturnDate(result.getDate("Return_Date"));
						issuedBook.setReissueCount(result.getInt("Reissue_Count"));
						
						issuedBook.setBookName(result.getString("Book_Name"));
						issuedBook.setAuthorName(result.getString("Author_Name"));
						
						issuedBooks.add(issuedBook);
				}
				
				
		} catch (SQLException e) {
			System.out.println("DB connection error !");
			e.printStackTrace();
		}
		return issuedBooks;
	}

	public boolean checkReissueValidity(int userID,int bookID) {
		
		boolean reissueValidity = false;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementsystem?useSSL=false","Bastobika Guha","bastobika" );
				Statement stmt = conn.createStatement();){
			
				Date returnDate = new Date();
				String query = "select Return_Date from user_book where User_ID="+userID+" and Book_ID="+bookID;
				ResultSet result = stmt.executeQuery(query);
				while(result.next()) {
						returnDate = result.getDate("Return_Date");
				}
				Calendar cal = Calendar.getInstance();
				reissueValidity = cal.after(returnDate);
		} catch (SQLException e) {
			System.out.println("DB Connection Error !");
			e.printStackTrace();
		}
		return reissueValidity;
	}
}

	
