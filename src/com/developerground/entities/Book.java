package com.developerground.entities;

public class Book {
		private int bookID;
		private String bookName;
		private String authorName;
		private int totalNumberOfIssues;
		private int availableNumberOfIssues;
		
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
		public int getTotalNumberOfIssues() {
			return totalNumberOfIssues;
		}
		public void setTotalNumberOfIssues(int totalNumberOfIssues) {
			this.totalNumberOfIssues = totalNumberOfIssues;
		}
		public int getAvailableNumberOfIssues() {
			return availableNumberOfIssues;
		}
		public void setAvailableNumberOfIssues(int availableNumberOfIssues) {
			this.availableNumberOfIssues = availableNumberOfIssues;
		}
}
