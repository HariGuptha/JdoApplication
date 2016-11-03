package com.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Deleteremainder extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		HttpSession session = req.getSession(false);
		PrintWriter out = res.getWriter();

		if (session != null) {
			String title = req.getParameter("title");

			PersistenceManager pm = PMF.get().getPersistenceManager();

			String email = (String) req.getSession().getAttribute("email");

			try {
				Remainder rm = pm.getObjectById(Remainder.class, email);
				ArrayList<String> titlearray = rm.getTitle();
				boolean check = titlearray.contains(title);
				if (check) {
					int i = titlearray.indexOf(title);
					rm.removeTitle(i);
					rm.removeMessage(i);

					out.println("<p>Remainder Deleted Successfully</p>");

					res.sendRedirect("Login");

				}

				else {
					out.println("<p>No remainder found</p>");

					RequestDispatcher rd = req.getRequestDispatcher("Deleteremainder.html");
					rd.include(req, res);

				}

			} catch (JDOObjectNotFoundException e) {
				out.println("<p>No remainder found</p>");

				RequestDispatcher rd = req.getRequestDispatcher("Deleteremainder.html");
				rd.include(req, res);

			}

		} else {
			out.println("<p>Enter to login</p>");

			RequestDispatcher rd = req.getRequestDispatcher("index.html");

			rd.forward(req, res);

		}
	}
}
