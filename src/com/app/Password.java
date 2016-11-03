package com.app;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Password extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		PrintWriter out = res.getWriter();

		HttpSession session = req.getSession(false);
		if (session != null) {

			String password = req.getParameter("pass");

			try {
				if (password.length() < 6) {
					out.println("<p>Enter a password that contain 6 characters</p>");

					RequestDispatcher rd = req.getRequestDispatcher("Password.html");

					rd.forward(req, res);

				}

				if (!(password.isEmpty() || password == null || password.length() < 6)) {
					PersistenceManager pm = PMF.get().getPersistenceManager();

					String email = (String) session.getAttribute("email");

					User e = pm.getObjectById(User.class, email);

					e.setPassword(password);

					out.println("<p>Password changed successfully</p>");
					res.sendRedirect("Login");

				}

				else {

					out.println("<p>Enter a valid password</p>");

					RequestDispatcher rd = req.getRequestDispatcher("Password.html");

					rd.forward(req, res);

				}

			}

			catch (Exception e) {

				out.println("<p>Enter a valid Password</p>");

				RequestDispatcher rd = req.getRequestDispatcher("Password.html");

				rd.forward(req, res);

			}
		} else {
			out.println("<p>Enter to login</p>");

			RequestDispatcher rd = req.getRequestDispatcher("index.html");

			rd.forward(req, res);
		}

	}
}
