package com.developerground.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.developerground.BCrypt.BCrypt;
import com.developerground.constants.Constants;
import com.developerground.services.UserService;

/**
 * Servlet implementation class Authentication
 */
@WebServlet("/Authentication")
public class Authentication extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserService userService = new UserService();
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		String userID = null;	
		String userName = request.getParameter("UserName");
		boolean resetPassword = false;
		RequestDispatcher dispatcher = null;
		List<String> resultList = userService.authenticate(userName, password);
		if(resultList.get(0)!= null && resultList.get(0).equalsIgnoreCase("True")) {
				String userType = resultList.get(1);
				userID = resultList.get(2);
				session.setAttribute("userType", userType);
				session.setAttribute("userName", userName);
				session.setAttribute("userID", userID);
				if (password.equalsIgnoreCase(Constants.DEFAULT_PASSWORD)) {
					resetPassword = true;
				}
				request.setAttribute("resetPassword", resetPassword);
				dispatcher = request.getRequestDispatcher("/Home.jsp");
					
		}else {
				request.setAttribute("loginFailed", true);
				dispatcher = request.getRequestDispatcher("/index.jsp");
		}
		dispatcher.forward(request, response);
	}
}
		

		
		
		

