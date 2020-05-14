/**
 * 
 */
package com.developerground.entities;

/**
 * @author Bastobika Guha
 *
 */
public class User {
		private int userID;
		private String name;
		private String password;
		private String userType;
		private int bookIssuedCount;
		
		public int getUserID() {
			return userID;
		}
		public void setUserID(int userID) {
			this.userID = userID;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getUserType() {
			return userType;
		}
		public void setUserType(String userType) {
			this.userType = userType;
		}
		public int getBookIssuedCount() {
			return bookIssuedCount;
		}
		public void setBookIssuedCount(int bookIssuedCount) {
			this.bookIssuedCount = bookIssuedCount;
		}
}
