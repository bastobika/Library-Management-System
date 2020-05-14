package com.developerground.services;

import java.util.ArrayList;
import java.util.List;

import com.developerground.BCrypt.BCrypt;
import com.developerground.constants.Constants;
import com.developerground.daos.UserDao;
import com.developerground.entities.User;

public class UserService {
	UserDao dao = new UserDao();
	public List<User> viewUsers(int userID) {
		List<User> users = new ArrayList<>();
		users = dao.viewUsers(userID);
		return users;
	}
	public boolean addUser(String name,  String userType ) {
		String password = BCrypt.hashpw(Constants.DEFAULT_PASSWORD,BCrypt.gensalt());
		boolean isUserAdded = dao.addUser(name, password, userType );
		return isUserAdded;
	}
	public boolean deleteUser(String ID ) {
		int userID = Integer.parseInt(ID);
		boolean isUserDeleted = dao.deleteUser(userID);
		return isUserDeleted;
	}
	public List<String> authenticate (String userName, String password ) {
		List<String> resultList = dao.authenticate(userName, password );
		return resultList;
	}
	public boolean updateUser(String ID) {
		
		int userID = Integer.parseInt(ID);
		boolean isUserUpdated = dao.updateUser(userID);
		return isUserUpdated;
	}
	public boolean resetPassword(String ID, String password) {
		int userID = Integer.parseInt(ID);
		boolean isPasswordResetted = dao.resetPassword(userID,password);
		return isPasswordResetted;
	}
}
