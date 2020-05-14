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
import com.developerground.entities.User;
import com.developerground.services.UserService;

/**
 * Servlet implementation class UsersController
 */
@WebServlet("/UsersController")
public class UsersController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserService userService = new UserService(); 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsersController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = (request.getParameter("action")!= null )? request.getParameter("action"): "view";
		boolean isUserAdded = false;
		boolean isUserDeleted = false;
		boolean isUserUpdated = false;
		HttpSession session = request.getSession();
		String userID = (String) session.getAttribute("userID");
		switch (action ) {
		case "add"  :  String name = request.getParameter("UserName");
							  String userType = request.getParameter("userType");
							  isUserAdded = userService.addUser(name, userType);
							  break;
							  
		case "delete"  : String deleteUserID = request.getParameter("userID");
								 isUserDeleted = userService.deleteUser(deleteUserID);
								 break;
		
		case "update" : String updateUserID = request.getParameter("userID");
								 isUserUpdated = userService.updateUser(updateUserID);
								 break;
		}
		
		List<User> users = userService.viewUsers(Integer.parseInt(userID));
		request.setAttribute("users",users);
		request.setAttribute("isUserAdded", isUserAdded);
		request.setAttribute("isUserDeleted", isUserDeleted);
		request.setAttribute("isUserUpdated", isUserUpdated);
		RequestDispatcher dispatcher = request.getRequestDispatcher("Users.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String password = BCrypt.hashpw((String) request.getParameter("password"),BCrypt.gensalt());
		HttpSession session = request.getSession();
		String userID = (String) session.getAttribute("userID");
		boolean isPasswordResetted = userService.resetPassword(userID, password);
		request.setAttribute("isPasswordResetted", isPasswordResetted);
		RequestDispatcher dispatcher = request.getRequestDispatcher("Home.jsp");
		dispatcher.forward(request, response);
	}
	
}
