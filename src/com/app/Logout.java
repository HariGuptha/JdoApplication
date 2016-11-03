package com.app;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Logout extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		PrintWriter out = res.getWriter();

		HttpSession session = req.getSession(false);

		String name = (String) session.getAttribute("name");
		try {
			if (session != null) {
				out.println("<p>You are successfully logged out " + name + "</p> ");

				RequestDispatcher rd = req.getRequestDispatcher("index.html");
				rd.forward(req, res);

				session.invalidate();
			}
		} catch (NullPointerException e) {
			RequestDispatcher rd = req.getRequestDispatcher("index.html");
			rd.forward(req, res);
			session.invalidate();
		}
	}
}
