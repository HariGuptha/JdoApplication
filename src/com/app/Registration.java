package com.app;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Registration extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		HttpSession session = req.getSession(false);

		PrintWriter out = res.getWriter();

		if (session != null) {

			String name = req.getParameter("name");
			String password = req.getParameter("pass");
			String email = req.getParameter("email");

			if ((name.isEmpty() || name == null)) {
				out.println("<p>Fill the Name</p>");

				RequestDispatcher rd = req.getRequestDispatcher("Registration.html");

				rd.forward(req, res);

				return;

			}

			if (email == null) {
				out.println("<p>Fill the email</p>");

				RequestDispatcher rd = req.getRequestDispatcher("Registration.html");

				rd.forward(req, res);

				return;

			}

			if ((password == null || password.length() < 6) || password.isEmpty()) {

				out.println("<p>Password should be of 6 characters</p>");

				RequestDispatcher rd = req.getRequestDispatcher("Registration.html");

				rd.forward(req, res);

				return;
			}

			else {

				try {

					PersistenceManager pm = PMF.get().getPersistenceManager();

					User e = pm.getObjectById(User.class, email);

					out.println("<p>User already registered Please use another email id</p>");

					RequestDispatcher rd = req.getRequestDispatcher("Registration.html");

					rd.forward(req, res);

					return;

				}

				catch (JDOObjectNotFoundException ne) {

					PersistenceManager pm = PMF.get().getPersistenceManager();

					User user = new User(name, email, password);

					pm.makePersistent(user);

					out.println("<p>User Registered successfully</p>");

					Login.execute(req, res);

				}

			}

		} else {
			out.println("<p>Enter to login</p>");

			RequestDispatcher rd = req.getRequestDispatcher("index.html");

			rd.forward(req, res);

		}
	}
}
