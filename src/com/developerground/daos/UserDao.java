package com.developerground.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.developerground.BCrypt.BCrypt;
import com.developerground.constants.Constants;
import com.developerground.entities.User;

public class UserDao {
	public boolean addUser(String name, String password, String userType ){
		
		boolean isUserAdded = false;
		int bookIssuedCount;
		if(userType.equalsIgnoreCase("Admin"))
			bookIssuedCount = Constants.INIT_ISSUE_COUNT_ADMIN;
		else
			bookIssuedCount = Constants.INIT_ISSUE_COUNT_USER;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementsystem?useSSL=false","Bastobika Guha","bastobika" );
				Statement stmt = conn.createStatement(); ){
			
			    String query = "insert into user (User_Name,Password,User_Type, Book_Issued_Count ) values ('"+name+"','"+password+"','"+userType+"',"+bookIssuedCount+")";
				int rowsAffected = stmt.executeUpdate(query);
				if (rowsAffected > 0 ) {
					isUserAdded = true;
				}
		} catch (SQLException e) {
			System.out.println("DB connection error !");
			e.printStackTrace();
		}
		return isUserAdded;
	}

	public boolean deleteUser(int userID) {
		
		boolean isUserDeleted = false;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementsystem?useSSL=false","Bastobika Guha","bastobika" );
				Statement stmt = conn.createStatement();){
			
				String query = "delete from user where User_ID="+userID;
				int rowsAffected = stmt.executeUpdate(query);
				if (rowsAffected > 0 ) {
					isUserDeleted = true;
				}
		} catch (SQLException e) {
			System.out.println("DB connection error !");
			e.printStackTrace();
		}
		return isUserDeleted;
	}

	public List<User> viewUsers(int userID) {
		
		
		List<User> users = new ArrayList<User>();
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementsystem?useSSL=false","Bastobika Guha","bastobika" );
				Statement stmt = conn.createStatement();){
			
				String query = " select * from user where User_ID!="+userID;
				ResultSet result = stmt.executeQuery(query);
				while(result.next()) {
						User user = new User();
						user.setUserID(result.getInt("User_ID"));
						user.setName(result.getString("User_Name"));
						user.setUserType(result.getString("User_Type"));
						user.setBookIssuedCount(result.getInt("Book_Issued_Count"));
						
						users.add(user);
				}
		} catch (SQLException e) {
			System.out.println("DB connection error !");
			e.printStackTrace();
		}
		return users;
	}

	public List<String> authenticate(String userName, String plainPassword) {
		
		String loginFlag = "False";
		String userType = null;
		String userID = null;
		List<String> resultList = new ArrayList<String>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementsystem?useSSL=false","Bastobika Guha","bastobika" );
				Statement stmt = conn.createStatement();){
			
				String query = "select * from user where User_Name='"+userName+"'" ;
				ResultSet result = stmt.executeQuery(query);
				if (result.next()) {
					String hashedPassword = result.getString("Password");
					if(BCrypt.checkpw(plainPassword, hashedPassword)) {
						loginFlag = "True";
						userType = result.getString("User_Type");
						userID = result.getString("User_ID");
					}	
				}
		} catch (SQLException e) {
			System.out.println("DB connection error !");
			e.printStackTrace();
		}
		resultList.add(loginFlag);
		resultList.add(userType);
		resultList.add(userID);
		return resultList;
	}

	public boolean updateUser(int userID) {
		
		boolean isUserUpdated = false;
		List<Integer> bookIDList = new ArrayList<Integer>();
		BookDao bookDao = new BookDao();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementsystem?useSSL=false","Bastobika Guha","bastobika" );
				Statement stmt = conn.createStatement();){
				
				String query = "select Book_ID from user_book where User_ID="+userID;
				ResultSet result = stmt.executeQuery(query);
				while(result.next()) {
					bookIDList.add(result.getInt("Book_ID"));
				}
				for (int i=0 ; i<bookIDList.size(); i++) {
					bookDao.returnBook(userID, bookIDList.get(i));
				}
				query = "update user set User_Type='Admin',Book_Issued_Count="+Constants.INIT_ISSUE_COUNT_ADMIN+" where User_ID="+userID;
				int rowsAffected = stmt.executeUpdate(query);
				if (rowsAffected > 0 ) {
					isUserUpdated = true;
				}
		} catch (SQLException e) {
			System.out.println("DB connection error !");
			e.printStackTrace();
		}
		return isUserUpdated;
	}

	public boolean resetPassword(int userID, String password) {

		boolean isPasswordResetted = false;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		if (null != password && !password.isEmpty()) {
			try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementsystem?useSSL=false","Bastobika Guha","bastobika" );
					Statement stmt = conn.createStatement();){
				
					String query = "update user set Password='"+password+"' where User_ID="+userID;
					int rowsAffected = stmt.executeUpdate(query);
					if (rowsAffected > 0 ) {
						isPasswordResetted = true;
					}
				
			} catch (SQLException e) {
				System.out.println("DB connection error !");
				e.printStackTrace();
			}
		}
		return isPasswordResetted;
	}
}
