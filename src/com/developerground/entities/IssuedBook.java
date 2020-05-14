package com.developerground.entities;

import java.util.Date;

public class IssuedBook {
		private int userID;
		private int bookID;
		private String bookName;
		private String authorName;
		private Date issueDate;
		private Date returnDate;
		private int reissueCount;
		
		public int getUserID() {
			return userID;
		}
		public void setUserID(int userID) {
			this.userID = userID;
		}
		public int getBookID() {
			return bookID;
		}
		public void setBookID(int bookID) {
			this.bookID = bookID;
		}
		public String getBookName() {
			return bookName;
		}
		public void setBookName(String bookName) {
			this.bookName = bookName;
		}
		public String getAuthorName() {
			return authorName;
		}
		public void setAuthorName(String authorName) {
			this.authorName = authorName;
		}
		public Date getIssueDate() {
			return issueDate;
		}
		public void setIssueDate(Date issueDate) {
			this.issueDate = issueDate;
		}
		public Date getReturnDate() {
			return returnDate;
		}
		public void setReturnDate(Date returnDate) {
			this.returnDate = returnDate;
		}
		public int getReissueCount() {
			return reissueCount;
		}
		public void setReissueCount(int reissueCount) {
			this.reissueCount = reissueCount;
		}
}
